package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parser.FileParser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class Main extends Application {

    private static String s = "";

    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Trade Reporter");
        primaryStage.setScene(new Scene(root, 760, 500));
        primaryStage.setResizable(false);
//        primaryStage.set
        primaryStage.show();
    }


    public static void main(String[] args) {

       launch(args);

    }
}
