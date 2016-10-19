package sample;

import controller.TaskController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import parser.FileParser;
import util.OrderUpdater;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Controller {

    @FXML
    Button chooseFileButton = new Button();
    @FXML
    Label label1 = new Label();
    @FXML
    GridPane mainPane = new GridPane();
    @FXML
    FileChooser fileChooser = new FileChooser();

    @FXML
    void initialize() {
        String s = "";
        //System.out.println(" s : "+ s );

        label1.setText(s);
    }


    public void onChooseFileClicked(MouseEvent mouseEvent) {
        fileChooser.setTitle("Open Resource File");
        List<String> strings = new ArrayList<>();
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
            ctx.load("/application-context.xml");
            setProperty(file.getPath());
            ctx.refresh();
            for (String ff : strings)
            label1.setText(label1.getText() + " " + ff);
        }
        else {
            label1.setText("smth wrong ! ");
        }

    }

    public void setProperty(String path){
        final File propsFile = new File(System.getProperty("user.dir") + "/src/main/resources/application.properties");
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propsFile));
            props.setProperty("filePath", path);
            props.store(new FileOutputStream(propsFile), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
