package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    private boolean firstTime;
    private TrayIcon trayIcon;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Trade Reporter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        javafx.scene.image.Image image = new javafx.scene.image.Image(Main.class.getResourceAsStream("/favicon.png"));
        primaryStage.getIcons().add(image);
        createTrayIcon(primaryStage);
        firstTime = true;
        Platform.setImplicitExit(false);
        primaryStage.show();

    }

    public static void main(String[] args) {

       launch(args);

    }

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            java.awt.Image image = null;
            try {
                image = ImageIO.read(Main.class.getResourceAsStream("/favicon.png"));
            } catch (IOException ex) {
                System.out.println(ex);
            }

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    hide(stage);
                }
            });
            // create a action listener to listen for default action executed on the tray icon
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "Trade Reporter", popup);
            trayIcon.setImageAutoSize(true);
            // set the TrayIcon properties
            trayIcon.addActionListener(showListener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        }
    }

    public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("Now Trade Reporter running in the system tray.",
                    "Program will parse reports 2 times in a day.",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    System.exit(0);
                }
            }
        });
    }
}
