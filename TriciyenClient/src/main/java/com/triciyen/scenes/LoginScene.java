package com.triciyen.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class LoginScene {
    private static final LoginScene instance = new LoginScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;



    private LoginScene() {
        scene = new Scene(new Pane(), sceneWidth, sceneHeight);
    }
    public static LoginScene getInstance() {
        return instance;
    }
    public Scene getScene() {
        return scene;
    }
}
