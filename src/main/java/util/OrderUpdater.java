package util;

import java.nio.charset.Charset;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goebl.david.Webb;
import dao.FillsDao;
import dto.*;
import parser.FileParser;


public class OrderUpdater {

    private static final int MILLIS_IN_A_DAY = 86_400_000;
    private static final int DAY_RANGE = 15;
    private static final long BEFORE_RANGE = DAY_RANGE * MILLIS_IN_A_DAY;
    private static final long BEFORE_RANGE10 = 10 * MILLIS_IN_A_DAY;
    private static final long AFTER_RANGE = (DAY_RANGE + 1) * MILLIS_IN_A_DAY;

    private FillsDao fillsDao;
    private int ordersCount;
    private int optionsCount;
    private String path;

    public OrderUpdater(FillsDao fillsDao, String path) {
        this.fillsDao = fillsDao;
        this.path = path;
    }

    public void perform() {
        Webb webb = Webb.create();
//        Long maxSubmitTime = Long.parseLong(webb.get("http://trading-amirustech.rhcloud.com/tasks/max-submit-time").ensureSuccess().asString().getBody());
        Long orderMaxSubmitTime = Long.parseLong(webb.get("http://localhost:8080/tasks/max-submit-time").param("entity", "order").ensureSuccess().asString().getBody());
        Long optionMaxSubmitTime = Long.parseLong(webb.get("http://localhost:8080/tasks/max-submit-time").param("entity", "option").ensureSuccess().asString().getBody());
        System.out.println("Order max submit time : " + orderMaxSubmitTime);
        System.out.println("Option max submit time : " + optionMaxSubmitTime);
        FileParser fileParser = new FileParser();
        List<Order> orders = fileParser.parseOrders(path, Charset.defaultCharset(), orderMaxSubmitTime);
        List<Option> options = fileParser.parseOptions(path, Charset.defaultCharset(), optionMaxSubmitTime);
        String ordersJSON = "";
        String optionsJSON = "";
        try {
            ordersJSON = toJSON(orders);
            optionsJSON = toJSON(options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        webb.post("http://trading-amirustech.rhcloud.com/tasks/order-updater").param("orders", ordersJSON).ensureSuccess().asString().getBody();
        webb.post("http://localhost:8080/tasks/order-updater").param("orders", ordersJSON).ensureSuccess().asString().getBody();
        webb.post("http://localhost:8080/tasks/option-updater").param("options", optionsJSON).ensureSuccess().asString().getBody();
        System.out.println(ordersJSON);
        System.out.println("_____");
        System.out.println(optionsJSON);
        ordersCount = orders.size();
        optionsCount = options.size();
        System.out.println("ordersCount : " + ordersCount);
        System.out.println("optionsCount : " + optionsCount);
    }

    private String toJSON(List<?> orders) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(orders);
    }

}
