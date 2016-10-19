package util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import dao.FillsDao;
import dto.Deal;
import dto.Order;
import dto.PlannedDeal;
import dto.UnplannedDeal;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import parser.FileParser;


public class OrderUpdater {

    private static final int MILLIS_IN_A_DAY = 86_400_000;
    private static final int DAY_RANGE = 15;
    private static final long BEFORE_RANGE = DAY_RANGE * MILLIS_IN_A_DAY;
    private static final long BEFORE_RANGE10 = 10 * MILLIS_IN_A_DAY;
    private static final long AFTER_RANGE = (DAY_RANGE + 1) * MILLIS_IN_A_DAY;

    private FillsDao fillsDao;
    private int ordersCount;
    private String path;

    public OrderUpdater(FillsDao fillsDao, String path) {
        this.fillsDao = fillsDao;
        this.path = path;
    }

    public void perform() {
        Long maxSubmitTime = fillsDao.getMaxSubmitTime();
        FileParser fileParser = new FileParser();

        List<Order> orders = fileParser.getParsedValues(path, Charset.defaultCharset(), maxSubmitTime);
        ordersCount = orders.size();
        System.out.println(ordersCount);
        for (Order order : orders) {
            fillsDao.updateOrder(order);
            System.out.println(order);
            assignOrder(order);
        }
    }

    private void assignOrder(Order order) {
        if (!assignOrderToUnlannedDealOnClose(order)) {
            if (!assignOrderToPlannedDeal(order)) {
                if (!assignOrderToPlannedDealsGroup(order)) {
                    if (!assignOrderToUnPlannedDealsGroup(order)) {
                        assignOrderToUnplannedDeal(order);
                        System.out.println("UNPLANNED");
                    } else {
                        System.out.println("UNPLANNED GROUP");
                    }
                } else {
                    System.out.println("PLANNED GROUP");
                }

            } else {
                System.out.println("PLANNED");
            }
        } else {
            System.out.println("ON CLOSE");
        }

    }

    private boolean assignOrderToUnlannedDealOnClose(Order order) {
        boolean assignment = false;
        List<UnplannedDeal> dealFull = fillsDao.getUnplannedDealByInstrAcc(order.getInstrument(), order.getAccount());
        if (dealFull != null) {
            List<UnplannedDeal> deals = new ArrayList();
            for (UnplannedDeal deal : dealFull) {
                if (deal.getDirection() != order.isBuy()) {
                    Set<Order> dealOrders = deal.getOrders();
                    int dealDir = 0;
                    int unDealDir = 0;
                    for (Order o : dealOrders) {
                        if (o.isBuy() == deal.getDirection()) {
                            dealDir++;
                        } else {
                            unDealDir++;
                        }
                    }
                    if (dealDir > unDealDir) {
                        deals.add(deal);
                    }
                }
            }
            if (!deals.isEmpty()) {
                UnplannedDeal deal = deals.get(0);
                if (deals.size() > 1) {
                    System.out.println("Unpl on close: " + deals.size());
                    for (int i = 1; i < deals.size(); i++) {
                        if (deal.getEnd() < deals.get(i).getEnd()) {
                            deal = deals.get(i);
                        }
                    }
                }
                Set<Order> dealOrders = deal.getOrders();
                dealOrders.add(order);
                deal.setOrders(dealOrders);
                //DealsUtil.providePlannedLikeInfo(deal);
                fillsDao.updateUnplannedDeal(deal);
                assignment = true;
            }
        }

        return assignment;
    }

    private boolean assignOrderToPlannedDeal(Order order) {
        boolean assignment = false;
        long orderTimestamp = order.getSubmitTime();

        List<PlannedDeal> suitableDeals = fillsDao.getPlannedDealsByInstrAcc(order.getInstrument(), order.getAccount());
        if (!suitableDeals.isEmpty()) {
            List<PlannedDeal> dirDeals = new ArrayList();
            List<PlannedDeal> undirDeals = new ArrayList();
            for (PlannedDeal deal : suitableDeals) {
                if (order.isBuy() == deal.getDirection()) {
                    if (((deal.getStart() - BEFORE_RANGE) < orderTimestamp) && (orderTimestamp < deal.getEnd() + BEFORE_RANGE10)) {
                        dirDeals.add(deal);
                    }
                } else {
                    Set<Order> dealOrders = deal.getOrders();
                    int dealDir = 0;
                    int unDealDir = 0;
                    for (Order o : dealOrders) {
                        if (o.isBuy() == deal.getDirection()) {
                            dealDir++;
                        } else {
                            unDealDir++;
                        }
                    }
                    if (dealDir > unDealDir) {
                        undirDeals.add(deal);
                    }
                }
            }
            PlannedDeal deal = null;
            if (!undirDeals.isEmpty()) {
                deal = undirDeals.get(0);
                if (undirDeals.size() > 1) {
                    for (int i = 1; i < undirDeals.size(); i++) {
                        if (deal.getEnd() < undirDeals.get(i).getEnd()) {
                            deal = undirDeals.get(i);
                        } else {
                            if (deal.getEnd() == undirDeals.get(i).getEnd()) {
                                if (deal.getStart() < undirDeals.get(i).getStart()) {
                                    deal = undirDeals.get(i);
                                }
                            }
                        }
                    }
                }
            } else {
                if (!dirDeals.isEmpty()) {
                    deal = dirDeals.get(0);
                    if (dirDeals.size() > 1) {
                        for (int i = 1; i < dirDeals.size(); i++) {
                            if (deal.getEnd() > dirDeals.get(i).getEnd()) {
                                deal = dirDeals.get(i);
                            } else {
                                if (deal.getEnd() == dirDeals.get(i).getEnd()) {
                                    if (deal.getStart() > dirDeals.get(i).getStart()) {
                                        deal = dirDeals.get(i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (deal != null) {
                Set<Order> dealOrders = deal.getOrders();
                dealOrders.add(order);
                deal.setOrders(dealOrders);
                fillsDao.updatePlannedDeal(deal);
                assignment = true;
            }
        }
        return assignment;
    }

    private boolean assignOrderToPlannedDealsGroup(Order order) {
        boolean assignment = false;
        long orderTimestamp = order.getSubmitTime();
        String[] instrumentParts = null;
        String[] monthsArray = null;
        try {
            instrumentParts = order.getInstrument().split(" \\+");
            monthsArray = instrumentParts[1].split("-");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Not correct order instrument");
            return false;
        }
        List<List<PlannedDeal>> chains = new ArrayList();
        List<Long> unPlannedIds = new ArrayList<>();
        List<PlannedDeal> suitablePlanned = fillsDao.getPlannedDealsByInstrAccTime(getInstrument(order), order.getAccount(),
                orderTimestamp, order.isBuy());
        List<UnplannedDeal> suitableUnplanned = fillsDao.getUnplannedDealsByInstrAccTime(getInstrument(order), order.getAccount(),
                orderTimestamp, order.isBuy());
        for (UnplannedDeal unplannedDeal : suitableUnplanned) {
            try {
                getMonths(unplannedDeal);
                unPlannedIds.add(unplannedDeal.getId());
                suitablePlanned.add(unplannedDeal.toPlannedDeal());
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Not correct deal instrument");
            }
        }
        List<PlannedDeal> suitableDeals = new ArrayList<>();
        for (PlannedDeal deal : suitablePlanned) {
            Set<Order> dealOrders = deal.getOrders();
            int dealDir = 0;
            int unDealDir = 0;
            for (Order o : dealOrders) {
                if (o.isBuy() == deal.getDirection()) {
                    dealDir++;
                } else {
                    unDealDir++;
                }
            }
            if (dealDir > unDealDir) {
                suitableDeals.add(deal);
            }
        }


        for (PlannedDeal deal : suitableDeals) {
            List<PlannedDeal> chain = new ArrayList();
            if (getMonths(deal)[0].equals(monthsArray[0])) {
                chain.add(deal);
                chains.add(chain);
            }
        }
        for (int i = 0; i < chains.size(); i++) {
            List<PlannedDeal> chain = chains.get(i);
            while (true) {
                int last = chain.size() - 1;
                PlannedDeal pd = chain.get(last);
                String m2 = getMonths(pd)[1];
                List<PlannedDeal> potentialPlanned = fillsDao.getPlannedDealsByInstrAccLikeLeft(getInstrument(order),
                        m2, order.getAccount(), orderTimestamp, order.isBuy());
                List<UnplannedDeal> potentialUnplanned = fillsDao.getUnplannedDealsByInstrAccLikeLeft(getInstrument(order),
                        m2, order.getAccount(), orderTimestamp, order.isBuy());
                for (UnplannedDeal ud : potentialUnplanned) {
                    try {
                        getMonths(ud);
                        potentialPlanned.add(ud.toPlannedDeal());
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Not correct deal instrument");
                    }
                }
                List<PlannedDeal> potentialDeals = new ArrayList<>();
                for (PlannedDeal deal : potentialPlanned) {
                    Set<Order> dealOrders = deal.getOrders();
                    int dealDir = 0;
                    int unDealDir = 0;
                    for (Order o : dealOrders) {
                        if (o.isBuy() == deal.getDirection()) {
                            dealDir++;
                        } else {
                            unDealDir++;
                        }
                    }
                    if (dealDir > unDealDir) {
                        potentialDeals.add(deal);
                    }
                }
                if (potentialDeals.size() > 1) {
                    chain.add(potentialDeals.get(0));
                    for (int k = 1; k < potentialDeals.size(); k++) {
                        List<PlannedDeal> tempChain = chain;
                        tempChain.add(potentialDeals.get(k));
                        chains.add(tempChain);
                    }
                } else if (potentialDeals.size() == 1) {
                    chain.add(potentialDeals.get(0));
                } else break;
            }
        }
        int id = 0;
        for (int i = 0; i < chains.size(); i++) {
            List<PlannedDeal> chain = chains.get(i);
            if ((getMonths(chain.get(chain.size() - 1)))[1].equals(monthsArray[1])) {
                id = i;
                assignment = true;
            }
        }
        if (assignment) {
            for (int i = 0; i < chains.get(id).size(); i++) {
                PlannedDeal deal = chains.get(id).get(i);
                TreeMap priceChart = deal.getPriceChart();
                Set<Order> dealOrders = deal.getOrders();
                Order newOrder = new Order();
                newOrder.setAccount(order.getAccount());
                newOrder.setBuy(order.isBuy());
                newOrder.setLots(order.getLots());
                newOrder.setInstrument(order.getInstrument());
                newOrder.setSubmitTime(order.getSubmitTime());
                newOrder.setDeleted(false);
                newOrder.setMessage(order.getMessage());
                newOrder.setPrice((Double) priceChart.get(priceChart.lastKey()));
                newOrder.setMessageNumber(order.getMessageNumber());
                fillsDao.updateOrder(newOrder);
                dealOrders.add(newOrder);
                deal.setOrders(dealOrders);
                if (unPlannedIds.contains(deal.getId())) {
                    fillsDao.updateUnplannedDeal(deal.toUnplannedDeal());
                }
                else {
                    fillsDao.updatePlannedDeal(deal);
                }
                System.out.println("\t" + newOrder);
            }
            return true;
        } else return false;
    }

    private boolean assignOrderToUnPlannedDealsGroup(Order order) {
        boolean assignment = false;
        long orderTimestamp = order.getSubmitTime();
        String[] instrumentParts = null;
        String[] monthsArray = null;
        try {
            instrumentParts = order.getInstrument().split(" \\+");
            monthsArray = instrumentParts[1].split("-");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Not correct order instrument");
            return false;
        }
        List<List<UnplannedDeal>> chains = new ArrayList();
        List<UnplannedDeal> suitableUnplanned = fillsDao.getUnplannedDealsByInstrAccTime(getInstrument(order), order.getAccount(),
                orderTimestamp, order.isBuy());
        List<UnplannedDeal> suitableDeals = new ArrayList<>();
        for (UnplannedDeal deal : suitableUnplanned) {
            Set<Order> dealOrders = deal.getOrders();
            int dealDir = 0;
            int unDealDir = 0;
            for (Order o : dealOrders) {
                if (o.isBuy() == deal.getDirection()) {
                    dealDir++;
                } else {
                    unDealDir++;
                }
            }
            if (dealDir > unDealDir) {
                suitableDeals.add(deal);
            }
        }
        for (UnplannedDeal deal : suitableDeals) {
            try {
                List<UnplannedDeal> chain = new ArrayList();
                if (getMonths(deal)[0].equals(monthsArray[0])) {
                    chain.add(deal);
                    chains.add(chain);
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Not correct deal instrument");
            }
        }
        for (int i = 0; i < chains.size(); i++) {
            List<UnplannedDeal> chain = chains.get(i);
            while (true) {
                int last = chain.size() - 1;
                UnplannedDeal pd = chain.get(last);
                String m2 = getMonths(pd)[1];
                List<UnplannedDeal> potentialUnplanned = fillsDao.getUnplannedDealsByInstrAccLikeLeft(getInstrument(order),
                        m2, order.getAccount(), orderTimestamp, order.isBuy());
                List<UnplannedDeal> potentialDeals = new ArrayList<>();
                for (UnplannedDeal deal : potentialUnplanned) {
                    Set<Order> dealOrders = deal.getOrders();
                    int dealDir = 0;
                    int unDealDir = 0;
                    for (Order o : dealOrders) {
                        if (o.isBuy() == deal.getDirection()) {
                            dealDir++;
                        } else {
                            unDealDir++;
                        }
                    }
                    if (dealDir > unDealDir) {
                        potentialDeals.add(deal);
                    }
                }
                if (potentialDeals.size() > 1) {
                    chain.add(potentialDeals.get(0));
                    for (int k = 1; k < potentialDeals.size(); k++) {
                        List<UnplannedDeal> tempChain = chain;
                        tempChain.add(potentialDeals.get(k));
                        chains.add(tempChain);
                    }
                } else if (potentialDeals.size() == 1) {
                    chain.add(potentialDeals.get(0));
                } else break;
            }
        }
        int id = 0;
        for (int i = 0; i < chains.size(); i++) {
            List<UnplannedDeal> chain = chains.get(i);
            if ((getMonths(chain.get(chain.size() - 1)))[1].equals(monthsArray[1])) {
                id = i;
                assignment = true;
            }
        }
        if (assignment) {
            for (int i = 0; i < chains.get(id).size(); i++) {
                UnplannedDeal deal = chains.get(id).get(i);
                Set<Order> dealOrders = deal.getOrders();
                Order newOrder = new Order();
                newOrder.setAccount(order.getAccount());
                newOrder.setBuy(order.isBuy());
                newOrder.setLots(order.getLots());
                newOrder.setInstrument(order.getInstrument());
                newOrder.setSubmitTime(order.getSubmitTime());
                newOrder.setDeleted(false);
                newOrder.setMessage(order.getMessage());
                try {
                    newOrder.setPrice(order.getPrice());
                } catch (Exception e) {
                    return false;
                }
                fillsDao.updateOrder(newOrder);
                dealOrders.add(newOrder);
                deal.setOrders(dealOrders);
                fillsDao.updateUnplannedDeal(deal);
                System.out.println("\t" + newOrder);
            }
            return true;
        } else return false;
    }


    private void assignOrderToUnplannedDeal(Order order) {
        List<UnplannedDeal> deals = fillsDao.getUnplannedDealByInstrAcc(order.getInstrument(), order.getAccount());
        UnplannedDeal deal = null;
        if (deals != null) {
            for (int i = 0; i < deals.size(); i++) {
                if (deals.get(i).getDirection() == order.isBuy()) {
                    deal = deals.get(i);
                    break;
                }
            }
        }
        if (deal == null) {
            deal = new UnplannedDeal();
            deal.setAccountName(order.getAccount());
            deal.setInstrument(order.getInstrument());
            deal.setDirection(order.isBuy());
            deal.setStart(order.getSubmitTime());
            //DealsUtil.provideQuandlinfo(deal);
        }

        Set<Order> dealOrders = deal.getOrders();
        dealOrders.add(order);
        deal.setOrders(dealOrders);
        deal.setYear(2016);
        //DealsUtil.providePlannedLikeInfo(deal);
        fillsDao.updateUnplannedDeal(deal);

    }

    private String[] getMonths(Deal deal) throws ArrayIndexOutOfBoundsException{
        String[] instrumentParts = deal.getInstrument().split(" \\+");
        return instrumentParts[1].split("-");
    }

    private String getInstrument(Order order) {
        return order.getInstrument().split(" \\+")[0];
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("hibernate-context.xml");
        FillsDao fillsDao = (FillsDao) ctx.getBean("fillsDao");
//        OrderUpdater ou = new OrderUpdater(fillsDao, path);
//        ou.perform();
        ctx.close();
        System.out.println("Finished.");



/*        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("hibernate-context.xml");
        FillsDao fillsDao = (FillsDao) ctx.getBean("fillsDao");
        Set set = fillsDao.getPlannedDeal(1263).getOrders();*/

//        int maxMessageNumber = fillsDao.getMaxMessageNumber();
//        System.out.println(maxMessageNumber);
//        MailReader mailReader = new MailReader();
//        List<main.java.dto.Order> orders = mailReader.readStartingFrom(maxMessageNumber);
//        int ordersCount = orders.size();
//        System.out.println(ordersCount);

/*
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("hibernate-context.xml");
        FillsDao fillsDao = (FillsDao) ctx.getBean("fillsDao");
        OrderUpdater orderUpdater = new OrderUpdater(fillsDao);

        main.java.dto.Order newOrder = new main.java.dto.Order();
        newOrder.setAccount("86430515");
        newOrder.setBuy(false);
        newOrder.setLots(1);
        newOrder.setInstrument("Feeder Cattle +Sep16-Oct16");
        newOrder.setSubmitTime(1470320353000l);
        newOrder.setDeleted(false);
        newOrder.setMessage("0.25");
        newOrder.setPrice(0.25);

        orderUpdater.assignOrderToPlannedDeal(newOrder);
*/

    }

}
