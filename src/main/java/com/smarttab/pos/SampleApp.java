package com.smarttab.pos;

import com.smarttab.pos.config.MainConfiguration;
import com.smarttab.pos.views.main.MainViewController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SampleApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(MainConfiguration.class);
        MainViewController mainController = context.getBean(MainViewController.class);
        Scene scene = new Scene((Parent) mainController.getView(), 600, 200);
        scene.getStylesheets().add("fxmlapp.css");
        stage.setScene(scene);
        stage.setTitle("JFX2.0 and Spring");
        stage.show();
    }
}
