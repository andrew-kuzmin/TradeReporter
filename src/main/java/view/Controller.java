package view;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import scheduler.UpdateOrderJob;
import util.OrderUpdater;
import util.ParseReport;

import java.io.*;
import java.text.ParseException;

public class Controller {

    @FXML
    Button chooseDirButton = new Button();
    @FXML
    Label label1 = new Label();
    @FXML
    GridPane mainPane = new GridPane();
    @FXML
    DirectoryChooser directoryChooser = new DirectoryChooser();
    @FXML
    Label lastOrderTimeLabel = new Label();
    @FXML
    Button updateButton = new Button();

    private static Controller instance = null;

    public static Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }
        return instance;
    }

    private Controller(){

    }

    @FXML
    void initialize() {
        JobDetail job = JobBuilder
                .newJob(UpdateOrderJob.class)
                .withIdentity("updateOrdersJob")
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("updateOrdersTrigger")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 01 13 1/1 * ? *"))
                .build();

        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            ParseReport.status = "UNSUCCESSFUL. SCHEDULER EXCEPTION";
            e.printStackTrace();
        }
        String s = "";
        label1.setText(s);
    }

    public void setLabel1Text(String text) {
        label1.setText(text);
    }

    public void setLastOrderTimeLabel(String time) {
        lastOrderTimeLabel.setText(time);
    }

    public void onChooseDirClicked() {
        directoryChooser.setTitle("Choose Resource Directory");
        File file = directoryChooser.showDialog(null);
        if (file != null) {
            ParseReport.path = file.getPath();
            OrderUpdater mu = new OrderUpdater();
            try {
                mu.perform();
            } catch (JsonProcessingException e) {
                ParseReport.status = "UNSUCCESSFUL. JsonProcessingException";
                e.printStackTrace();
            } catch (ParseException e) {
                ParseReport.status = "UNSUCCESSFUL. WRONG REPORT FORMAT";
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                ParseReport.status = "UNSUCCESSFUL. FILE path.txt NOT FOUND";
                e.printStackTrace();
            }

            try {
                setFile(file.getPath());
            } catch (IOException e) {
                ParseReport.status = "UNSUCCESSFUL. Error with path.txt file";
                e.printStackTrace();
            }
        }
        ParseReport.setLabelText(this);
        ParseReport.setLabelOrderTimeText(this);
    }

    private void setFile(String path) throws IOException {
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        File pathFile = new File(dir.toString() + "\\path.txt");
        pathFile.createNewFile();
        PrintWriter printWriter = new PrintWriter(pathFile);
        printWriter.write("");
        printWriter.write(path);
        printWriter.close();
    }

    public void onUpdateClicked() {
        ParseReport.setLabelOrderTimeText(this);
        ParseReport.setLabelText(this);
    }

}
