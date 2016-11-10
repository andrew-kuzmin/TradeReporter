package parser;

import dto.Option;
import dto.Order;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import util.DealOps;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileParser {

    private static final DateTimeFormatter BUILDER = DateTimeFormat.forPattern("HH:mm:ss yyyyMMdd").withLocale(Locale.ENGLISH);

    private List<String> readFile(String path, Charset encoding) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), encoding);
        return lines;
    }

    public List<Order> parseOrders(String path, Charset encoding, long maxSubmitTime) {
        Order order = null;
        List<Order> orders = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        try {
            strings = readFile(path, encoding);
            strings.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String str : strings) {
            String[] words = str.split(",");
            String subTimeStr = words[6] + " " + words[7];
            Long submitTime = BUILDER.parseDateTime(subTimeStr).getMillis();
            if (maxSubmitTime < submitTime) {
                maxSubmitTime = submitTime;
                String securityType = words[0];
                if (securityType.equals("BAG")) {
                    boolean direction = words[1].equalsIgnoreCase("BOT");
                    int lots = Integer.parseInt(words[2]);
                    String contract = words[4];
                    String[] contractSplited = contract.split(" ");
                    String instrument;
                    if (contractSplited[1].charAt(3) == '\'') {
                        instrument = DealOps.MARKET_NAMES.get(contractSplited[0]) + " +" + contract.substring(3, 6) + contract.substring(7, 9)
                                + "-" + contract.substring(10, 13) + contract.substring(14, 16);
                    } else {
                        instrument = DealOps.MARKET_NAMES.get(contractSplited[0]) + " +" + contract.substring(3, 6) + contract.substring(10, 12)
                                + "-" + contract.substring(13, 16) + contract.substring(20, 22);
                    }

                    double price = round(Double.parseDouble(words[5]) * 100, 2);


                    order = new Order();
                    order.setAccount(words[8]);
                    order.setBuy(direction);
                    order.setLots(lots);
                    order.setPrice(price);
                    order.setInstrument(instrument);
                    order.setSubmitTime(submitTime);
                    order.setTwsId(words[9]);
                    order.setCommission(Double.parseDouble(words[10]));
                    order.setDeleted(false);

                    orders.add(order);

                }
            }
            //result.add(dateTime.toString());
        }
        return orders;
    }

    public List<Option> parseOptions(String path, Charset encoding, long maxSubmitTime) {

        Option option = null;
        List<Option> options = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        try {
            strings = readFile(path, encoding);
            strings.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String str : strings) {
            String[] words = str.split(",");
            String subTimeStr = words[6] + " " + words[7];
            Long submitTime = BUILDER.parseDateTime(subTimeStr).getMillis();
            if (maxSubmitTime <= submitTime) {
                maxSubmitTime = submitTime;

                String securityType = words[0];
                if (securityType.equals("OPT")) {
                    boolean direction = words[1].equalsIgnoreCase("BOT");
                    int lots = Integer.parseInt(words[2]);
                    String[] contractSplited = words[4].split(" ");
                    String instrument = contractSplited[0];
                    String expDateStr = contractSplited[1].substring(0, 5) + " " + contractSplited[1].substring(6);
                    System.out.println("date : " + expDateStr);
                    DateFormat format = new SimpleDateFormat("MMMdd yy", Locale.ENGLISH);
                    Date expDate = null;
                    try {
                        expDate = format.parse(expDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Double strike = Double.parseDouble(contractSplited[2]);
                    String type = contractSplited[3];
                    double price = Double.parseDouble(words[5]);

                    option = new Option();
                    option.setAccount(words[8]);
                    option.setBuy(direction);
                    option.setLots(lots);
                    option.setInstrument(instrument);
                    if (expDate != null) option.setExpDate(expDate);
                    option.setStrike(strike);
                    option.setPrice(price);
                    option.setTwsId(words[9]);
                    option.setCommission(Double.parseDouble(words[10]));
                    option.setSubmitTime(submitTime);
                    option.setType(type);

                    options.add(option);
                }
            }
        }


        return options;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
