package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;


import javafx.stage.WindowEvent;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample.fxml"));
        Parent root = fxmlLoader.load();


        primaryStage.setTitle("Huluwa VS Monster");
        Scene scene = new Scene(root,1000,600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        Controller controller = fxmlLoader.getController();

        controller.Initial();


        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

}