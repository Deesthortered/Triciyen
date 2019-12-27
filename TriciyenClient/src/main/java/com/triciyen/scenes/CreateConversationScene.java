package com.triciyen.scenes;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class CreateConversationScene implements BaseScene {
    private static final CreateConversationScene instance = new CreateConversationScene();
    private Scene scene;

    private CreateConversationScene() {
        this.scene = new Scene(new StackPane(), 300, 300);
    }
    public static CreateConversationScene getInstance() {
        return instance;
    }
    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void initialize() {
        System.out.println("Init Create");
    }
    @Override
    public void destroy() {
        System.out.println("Destroy Create");
    }
    @Override
    public void handle(Event event) {

    }
}
