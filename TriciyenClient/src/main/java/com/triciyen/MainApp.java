package com.triciyen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Pane());
        stage.setScene(scene);
        stage.setTitle("JavaFX and Gradle");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

/// https://gluonhq.com/products/javafx/ JavaFX 13 version.
/// Run >> Edit Configurations, VM Options: --module-path /path/to/JavaFX/lib --add-modules=javafx.controls