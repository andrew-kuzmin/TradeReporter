package util;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goebl.david.Webb;
import dto.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import parser.FileParser;


public class OrderUpdater {

    public void perform() throws JsonProcessingException, ParseException, FileNotFoundException {
        String path;
        if (ParseReport.path != null) {
            path = ParseReport.path;
        }
        else {
            path = getPathFromFile();
        }
        Webb webb = Webb.create();
//        Long orderMaxSubmitTime = Long.parseLong(webb.get("http://localhost:8080/tasks/max-submit-time").param("entity", "order").ensureSuccess().asString().getBody());
//        Long optionMaxSubmitTime = Long.parseLong(webb.get("http://localhost:8080/tasks/max-submit-time").param("entity", "option").ensureSuccess().asString().getBody());
        Long orderMaxSubmitTime = Long.parseLong(webb.get("http://trading-amirustech.rhcloud.com/tasks/max-submit-time").param("entity", "order").ensureSuccess().asString().getBody());
        Long optionMaxSubmitTime = Long.parseLong(webb.get("http://trading-amirustech.rhcloud.com/tasks/max-submit-time").param("entity", "option").ensureSuccess().asString().getBody());
        System.out.println("Order max submit time : " + orderMaxSubmitTime);
        System.out.println("Option max submit time : " + optionMaxSubmitTime);
        Long lastOrderRead;
        if (orderMaxSubmitTime > optionMaxSubmitTime) {
            lastOrderRead = orderMaxSubmitTime;
        } else {
            lastOrderRead = optionMaxSubmitTime;
        }
        List<String> paths = getListOfPaths(path, lastOrderRead);
        FileParser fileParser = new FileParser();
        for (String reportPath: paths) {
            List<Order> orders = fileParser.parseOrders(reportPath, Charset.defaultCharset(), orderMaxSubmitTime);
            List<Option> options = fileParser.parseOptions(reportPath, Charset.defaultCharset(), optionMaxSubmitTime);
            String ordersJSON = toJSON(orders);
            String optionsJSON = toJSON(options);
            ParseReport.ordersCount += orders.size();
            ParseReport.optionsCount += options.size();
//        webb.post("http://trading-amirustech.rhcloud.com/tasks/order-updater").param("orders", ordersJSON).ensureSuccess().asString().getBody();
            webb.post("http://trading-amirustech.rhcloud.com/tasks/order-updater").param("orders", ordersJSON).ensureSuccess().asString().getBody();
            webb.post("http://trading-amirustech.rhcloud.com/tasks/option-updater").param("options", optionsJSON).ensureSuccess().asString().getBody();
        }
        ParseReport.status = "SUCCESSFUL";
        setLastOrderTime();
    }

    private List<String> getListOfPaths(String path, Long lastOrderRead) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<String> paths = new ArrayList<>();
        File[] reports = new File(path).listFiles();
        LocalDate lastOrderReadDate = new LocalDate(lastOrderRead);
        if (reports != null) {
            for (File report : reports) {
                LocalDate reportDate;
                reportDate = new LocalDate(dateFormat.parse(report.getName().substring(7, 15)).getTime());
                if (reportDate.compareTo(lastOrderReadDate) >= 0) {
                    paths.add(report.getPath());
                }
            }
        }
        return paths;
    }

    private String toJSON(List<?> orders) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(orders);
    }

    private void setLastOrderTime(){
        Webb webb = Webb.create();
        Long orderMaxSubmitTime = Long.parseLong(webb.get("http://trading-amirustech.rhcloud.com/tasks/max-submit-time").param("entity", "order").ensureSuccess().asString().getBody());
        Long optionMaxSubmitTime = Long.parseLong(webb.get("http://trading-amirustech.rhcloud.com/tasks/max-submit-time").param("entity", "option").ensureSuccess().asString().getBody());
        if (orderMaxSubmitTime > optionMaxSubmitTime){
            ParseReport.lastOrderSubmitTime = new Date(orderMaxSubmitTime).toString();
        }
        else {
            ParseReport.lastOrderSubmitTime = new Date(optionMaxSubmitTime).toString();
        }
    }

    private String getPathFromFile() throws FileNotFoundException {
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        File pathFile = new File(dir.toString() + "\\path.txt");
        return new Scanner(pathFile).nextLine();
    }

}
