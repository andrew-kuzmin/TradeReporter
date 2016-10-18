package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import parser.FileParser;
import util.OrderUpdater;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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
        FileParser fileParser = new FileParser();
        List<String> strings = new ArrayList<>();
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            OrderUpdater orderUpdater = new OrderUpdater()
            for (String ff : strings)
            label1.setText(label1.getText() + " " + ff);
        }
        else {
            label1.setText("smth wrong ! ");
        }

    }
}
