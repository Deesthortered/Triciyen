package com.triciyen.scenes;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
public class MainScene implements BaseScene {
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

    @Override
    public Scene getScene() {
        return scene;
    }
    @Override
    public void initialize() {

    }
    @Override
    public void handle(Event event) {

    }
}
