package com.triciyen.scenes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class RegistrationScene implements EventHandler<Event> {
    private static final RegistrationScene instance = new RegistrationScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;



    private RegistrationScene() {
        StackPane pane = new StackPane();
        Label test = new Label("1123");
        pane.getChildren().addAll(test);
        scene = new Scene(pane, sceneWidth, sceneHeight);
    }
    public static RegistrationScene getInstance() {
        return instance;
    }
    public Scene getScene() {
        return scene;
    }

    @Override
    public void handle(Event event) {

    }
}
