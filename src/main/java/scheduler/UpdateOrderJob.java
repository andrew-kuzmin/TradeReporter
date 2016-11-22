package scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import util.OrderUpdater;
import util.ParseReport;
import view.Controller;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class UpdateOrderJob implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        OrderUpdater mu = new OrderUpdater();
        System.out.println("Start updating orders");
        try {
            mu.perform();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ParseReport.status = "UNSUCCESSFUL. JsonProcessingException";
        } catch (ParseException e) {
            e.printStackTrace();
            ParseReport.status = "UNSUCCESSFUL. WRONG REPORT FORMAT";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ParseReport.status = "UNSUCCESSFUL. FILE path.txt NOT FOUND";
        }
        System.out.println("End updating orders");
        Controller.getInstance().onUpdateClicked();
    }
}
