package com.triciyen.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LoginScene {
    private static final LoginScene instance = new LoginScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;



    private LoginScene() {
        Label label = new Label("123");
        StackPane pane = new StackPane();
        pane.getChildren().add(label);
        scene = new Scene(pane, sceneWidth, sceneHeight);
    }
    public static LoginScene getInstance() {
        return instance;
    }
    public Scene getScene() {
        return scene;
    }
}
