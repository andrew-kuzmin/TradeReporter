package dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import dao.FillsDao;
import dto.Order;
import dto.PlannedDeal;
import dto.UnplannedDeal;

public class FillsDaoHibernateImpl implements FillsDao {

    public static final String ENTITY_ORDER = "Order";
    public static final String ENTITY_PLANNED = "PlannedDeal";
    public static final String ENTITY_UNPLANNED = "UnplannedDeal";

    private static final int MILLIS_IN_A_DAY = 86_400_000;
    private static final int DAY_RANGE = 15;
    private static final long BEFORE_RANGE = DAY_RANGE * MILLIS_IN_A_DAY;
    private static final long AFTER_RANGE = 10 * MILLIS_IN_A_DAY;

    private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> 	getAllOrders() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_ORDER);
        dc.add(Restrictions.eq("deleted", false));
        List<Order> orders = (List<Order>) hibernateTemplate.findByCriteria(dc);
        return orders;
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        hibernateTemplate.saveOrUpdate(ENTITY_ORDER, order);
    }

    @Override
    public Order getOrderById(long id) {
        return (Order) hibernateTemplate.get(ENTITY_ORDER, id);
    }

    @Override
    public void deleteOrder(long id) {
        Order order = (Order) hibernateTemplate.get(ENTITY_ORDER, id);
        hibernateTemplate.delete(ENTITY_ORDER, order);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getDeletedOrders() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_ORDER);
        dc.add(Restrictions.eq("deleted", true));
        List<Order> orders = (List<Order>) hibernateTemplate.findByCriteria(dc);
        return orders;
    }

//    @Transactional
//    @Override
//    public void softDeleteOrder(long id) {
//        Order order = (Order) hibernateTemplate.get(ENTITY_ORDER, id);
//        order.setDeleted(true);
//        if (order.getUnplannedDealId() != null) {
//            UnplannedDeal deal = this.getUnplannedDeal(order.getUnplannedDealId());
//            DealsUtil.providePlannedLikeInfo(deal);
//            this.updateUnplannedDeal(deal);
//        }
//        hibernateTemplate.saveOrUpdate(ENTITY_ORDER, order);
//    }

//    @Transactional
//    @Override
//    public void restoreOrder(long id) {
//        Order order = (Order) hibernateTemplate.get(ENTITY_ORDER, id);
//        order.setDeleted(false);
//        if (order.getUnplannedDealId() != null) {
//            UnplannedDeal deal = this.getUnplannedDeal(order.getUnplannedDealId());
//            DealsUtil.providePlannedLikeInfo(deal);
//            this.updateUnplannedDeal(deal);
//        }
//        hibernateTemplate.saveOrUpdate(ENTITY_ORDER, order);
//    }

    @SuppressWarnings("unchecked")
    public List<String> listAccounts() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_ORDER);
        dc.setProjection(Projections.distinct(Projections.property("account")));
        List<String> list = (List<String>) hibernateTemplate.findByCriteria(dc);
        return list;
    }

    @Override
    public Integer getMaxMessageNumber() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_ORDER);
        dc.setProjection(Projections.max("messageNumber"));
        Integer maxMessagenumber = 0;
        maxMessagenumber = (Integer) hibernateTemplate.findByCriteria(dc).get(0);
        return maxMessagenumber;
    }

    @Override
    public Long getMaxSubmitTime() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_ORDER);
        dc.setProjection(Projections.max("submitTime"));
        Long maxSubmitTime = 0l;
        maxSubmitTime = (Long) hibernateTemplate.findByCriteria(dc).get(0);
        return maxSubmitTime;
    }

    @Transactional
    @Override
    public void updatePlannedDeal(PlannedDeal plannedDeal) {
        hibernateTemplate.saveOrUpdate(ENTITY_PLANNED, plannedDeal);
    }

    @Transactional
    @Override
    public void deletePlannedDeal(long id) {
        PlannedDeal plannedDeal = (PlannedDeal) hibernateTemplate.get(ENTITY_PLANNED, id);
        hibernateTemplate.delete(ENTITY_PLANNED, plannedDeal);
    }

    @Override
    public PlannedDeal getPlannedDeal(long id) {
        return (PlannedDeal) hibernateTemplate.get(ENTITY_PLANNED, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getUnarchivedPlannedDeals() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.or(Restrictions.isNull("archived"), Restrictions.eq("archived", false)));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getUnarchivedUnplannedDeals() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        dc.add(Restrictions.or(Restrictions.isNull("archived"), Restrictions.eq("archived", false)));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getPlannedDealsByInstr(String instrument) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.like("instrument", "%" + instrument + "%"));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getUnplannedDealsByInstr(String instrument) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        dc.add(Restrictions.like("instrument", "%" + instrument + "%"));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getPlannedDealsByInstrAcc(String instrument, String account) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.eq("instrument", instrument)).add(Restrictions.eq("accountName", account));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getPlannedDealsByInstrAccTime(String instrument, String account, long date,
                                                           boolean direction) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.like("instrument", "%" + instrument + "%")).add(Restrictions.eq("accountName", account))
                .add(Restrictions.le("start", date + BEFORE_RANGE)).add(Restrictions.ge("end", date - BEFORE_RANGE))
                .add(Restrictions.eqOrIsNull("direction", !direction));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getUnplannedDealsByInstrAccTime(String instrument, String account, long date,
                                                               boolean direction) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        dc.add(Restrictions.like("instrument", "%" + instrument + "%")).add(Restrictions.eq("accountName", account))
                .add(Restrictions.le("start", date + BEFORE_RANGE)).add(Restrictions.ge("end", date - BEFORE_RANGE))
                .add(Restrictions.eqOrIsNull("direction", !direction));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getPlannedDealsByInstrAccLikeLeft(String instrument, String leftPart, String account,
                                                               long date, boolean direction) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.like("instrument", instrument + " +" + leftPart + "%"))
                .add(Restrictions.eq("accountName", account)).add(Restrictions.le("start", date + BEFORE_RANGE))
                .add(Restrictions.ge("end", date - BEFORE_RANGE))
                .add(Restrictions.eqOrIsNull("direction", !direction));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getUnplannedDealsByInstrAccLikeLeft(String instrument, String leftPart, String account,
                                                                   long date, boolean direction) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        dc.add(Restrictions.like("instrument", instrument + " +" + leftPart + "%"))
                .add(Restrictions.eq("accountName", account)).add(Restrictions.le("start", date + BEFORE_RANGE))
                .add(Restrictions.ge("end", date - BEFORE_RANGE))
                .add(Restrictions.eqOrIsNull("direction", !direction));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getPlannedDealsByInstrAccLikeRight(String instrument, String rightPart, String account,
                                                                long date) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.like("instrument", instrument + "%" + "-" + rightPart))
                .add(Restrictions.eq("accountName", account)).add(Restrictions.le("start", date + BEFORE_RANGE))
                .add(Restrictions.ge("end", date - BEFORE_RANGE));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getArchivedPlannedDeals() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.eq("archived", true));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getArchivedUnplannedDeals() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        dc.add(Restrictions.eq("archived", true));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getAllPlannedDeals() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED).add(Restrictions.ge("id", 1000l));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @Transactional
    @Override
    public void updateUnplannedDeal(UnplannedDeal unplannedDeal) {
        hibernateTemplate.saveOrUpdate(ENTITY_UNPLANNED, unplannedDeal);
    }

    @Transactional
    @Override
    public void deleteUnplannedDeal(long id) {
        UnplannedDeal unplannedDeal = (UnplannedDeal) hibernateTemplate.get(ENTITY_UNPLANNED, id);
        hibernateTemplate.delete(ENTITY_UNPLANNED, unplannedDeal);
    }

    @Override
    public UnplannedDeal getUnplannedDeal(long id) {
        return (UnplannedDeal) hibernateTemplate.get(ENTITY_UNPLANNED, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getAllUnplannedDeals() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getUnplannedDealByInstrAcc(String instrument, String account) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        dc.add(Restrictions.eq("instrument", instrument)).add(Restrictions.eq("accountName", account));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        if (deals.isEmpty()) {
            return null;
        }
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getRecentPlannedDeals(long timestampInTwoMonths) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.or(Restrictions.isNull("archived"), Restrictions.eq("archived", false)));
        dc.add(Restrictions.le("end", timestampInTwoMonths));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getRecentUnplannedDeals(long timestampInTwoMonths) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
        dc.add(Restrictions.or(Restrictions.isNull("archived"), Restrictions.eq("archived", false)));
        dc.add(Restrictions.le("end", timestampInTwoMonths));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getPlannedDealsInRange(long start, long end) {

        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
//		dc.add(Restrictions.or(Restrictions.isNull("archived"), Restrictions.eq("archived", false)));
        Criterion criterion1 = Restrictions.and(Restrictions.lt("end", start - BEFORE_RANGE),Restrictions.lt("start", start - BEFORE_RANGE));
        Criterion criterion2 = Restrictions.and(Restrictions.gt("start", end + AFTER_RANGE),Restrictions.gt("end", end + AFTER_RANGE));
        dc.add(Restrictions.not(Restrictions.or(criterion1, criterion2)));
//        dc.add(Restrictions.not(criterion1));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<UnplannedDeal> getUnplannedDealsInRange(long start, long end) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_UNPLANNED);
//		dc.add(Restrictions.or(Restrictions.isNull("archived"), Restrictions.eq("archived", false)));
        Criterion criterion1 = Restrictions.and(Restrictions.lt("end", start - BEFORE_RANGE),Restrictions.lt("start", start - BEFORE_RANGE));
        Criterion criterion2 = Restrictions.and(Restrictions.gt("start", end + AFTER_RANGE),Restrictions.gt("end", end + AFTER_RANGE));
        dc.add(Restrictions.not(Restrictions.or(criterion1, criterion2)));
//        dc.add(Restrictions.not(criterion1));
        List<UnplannedDeal> deals = (List<UnplannedDeal>) hibernateTemplate.findByCriteria(dc);
        return deals;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public void deletePlans(int year, String plan) {
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.and(Restrictions.eq("year", year), Restrictions.eq("plan", plan)));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        hibernateTemplate.deleteAll(deals);

    }

    @Override
    public LocalDate getStartDateForChart() {
        List<PlannedDeal> deals = getAllPlannedDeals();
        LocalDate date = new LocalDate();
        for (PlannedDeal deal : deals) {
            for (Entry<LocalDate, Double> entry : deal.getPriceChart().entrySet()) {
                if (entry.getKey().isBefore(date)) {
                    date = entry.getKey();
                }
            }
        }
        return date;
    }

    @Override
    public LocalDate getStartDateForUnplannedChart() {
        List<UnplannedDeal> deals = getAllUnplannedDeals();
        LocalDate date = new LocalDate();
        for (UnplannedDeal deal : deals) {
            if (deal.getPriceChart().firstKey().isBefore(date)) {
                date = deal.getPriceChart().firstKey();
            }
        }
        return date;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlannedDeal> getPairsOfPlannedDeals(long timestampInTwoMonths) {
		/*
		 * SELECT f.id, f.account, f.instrument, s.id, s.account FROM
		 * amirus_planned as f join amirus_planned as s on (f.instrument =
		 * s.instrument) where f.account = 86430515 and f.account != s.account
		 * and f.start = s.start and f.end = s.end and f.start < UNIX_TIMESTAMP(
		 * CURDATE( ) ) *1000 order by f.id
		 */
        DetachedCriteria dc = DetachedCriteria.forEntityName(ENTITY_PLANNED);
        dc.add(Restrictions.le("start", System.currentTimeMillis()));
        dc.add(Restrictions.or(Restrictions.isNull("archived"), Restrictions.eq("archived", false)));
        dc.add(Restrictions.le("end", timestampInTwoMonths));
        List<PlannedDeal> deals = (List<PlannedDeal>) hibernateTemplate.findByCriteria(dc);
        System.out.println(deals.size());
        List<PlannedDeal> result = new ArrayList();
        for (int i = 0; i < deals.size() - 1; i++) {
            if (deals.get(i).getAccountName().equals("86430515")) {
                for (int j = i + 1; j < deals.size(); j++) {
                    if (deals.get(j).getAccountName().equals("86430516")) {
                        if (deals.get(i).getInstrument().equals(deals.get(j).getInstrument())
                                && deals.get(i).getStart().equals(deals.get(j).getStart())
                                && deals.get(i).getEnd().equals(deals.get(j).getEnd())) {
                            result.add(deals.get(i));
                            result.add(deals.get(j));
                        }
                    }
                }
            }
        }
        Collections.reverse(result);
        return result;
    }
}
