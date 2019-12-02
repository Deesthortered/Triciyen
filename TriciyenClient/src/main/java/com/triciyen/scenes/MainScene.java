package com.triciyen.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
public class MainScene {
    private static final MainScene instance = new MainScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;



    private MainScene() {
        scene = new Scene(new Pane(), sceneWidth, sceneHeight);
    }
    public static MainScene getInstance() {
        return instance;
    }
    public Scene getScene() {
        return scene;
    }
}
