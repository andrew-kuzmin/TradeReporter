package parser;

import dto.Order;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import util.DealOps;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileParser {

    private static final DateTimeFormatter BUILDER = DateTimeFormat.forPattern("HH:mm:ss yyyyMMdd").withLocale(Locale.ENGLISH);

    private List<String> readFile(String path, Charset encoding) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(path), encoding);
        return lines;
    }

    public List<Order> getParsedValues(String path, Charset encoding, long maxSubmitTime){
        Order order = null;
        List<Order> orders = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        try {
            strings = readFile(path, encoding);
            strings.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String str : strings){
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
                    String instrument = DealOps.MARKET_NAMES.get(contract.substring(0, 2)) + " +" + contract.substring(3, 6) + contract.substring(7, 9)
                            + "-" + contract.substring(10, 13) + contract.substring(14, 16);

                    double price = Double.parseDouble(words[5]);


                    order = new Order();
                    order.setAccount(words[8]);
                    order.setBuy(direction);
                    order.setLots(lots);
                    order.setPrice(price);
                    order.setInstrument(instrument);
                    order.setSubmitTime(submitTime);
                    order.setDeleted(false);

                    orders.add(order);

                }
            }
            //result.add(dateTime.toString());
        }
        return orders;
    }

}
