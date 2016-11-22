package util;

import org.joda.time.DateTime;
import view.Controller;

public class ParseReport {

    static int ordersCount = 0;
    static int optionsCount = 0;
    static String lastOrderSubmitTime;
    public static String status = "";
    public static String path;

    public static void setLabelText(Controller controller){
        controller.setLabel1Text(status + "\nTotal options parsed : " + optionsCount + "\nTotal orders parsed : " + ordersCount );
    }
    public static void setLabelOrderTimeText(Controller controller){
        if (lastOrderSubmitTime != null) {
            controller.setLastOrderTimeLabel("Last order read : " + lastOrderSubmitTime);
        }
        else {
            controller.setLastOrderTimeLabel("No orders are read yet");
        }
    }
}
