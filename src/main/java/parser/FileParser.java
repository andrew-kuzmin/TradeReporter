package parser;

import dto.Order;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileParser {

    private static final DateTimeFormatter BUILDER = DateTimeFormat.forPattern("HH:mm:ss yyyyMMdd").withLocale(Locale.ENGLISH);

    private List<String> readFile(String path, Charset encoding)
            throws IOException
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
            String subTimeStr = words[5] + " " + words[6];
            Long submitTime = BUILDER.parseDateTime(subTimeStr).getMillis();
            if (maxSubmitTime < submitTime) {
                maxSubmitTime = submitTime;
                boolean direction = words[0].equalsIgnoreCase("BOT");
                int lots = Integer.parseInt(words[1]);
                double price = Double.parseDouble(words[4]);
                String instrument = words[2].substring(0, 2);


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
            //result.add(dateTime.toString());
        }
        return orders;
    }

}
