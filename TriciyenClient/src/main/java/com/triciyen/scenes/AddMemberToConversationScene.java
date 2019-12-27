package com.triciyen.scenes;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class AddMemberToConversationScene implements BaseScene {
    private static final AddMemberToConversationScene instance = new AddMemberToConversationScene();
    private Scene scene;

    private AddMemberToConversationScene() {
        this.scene = new Scene(new StackPane(), 300, 300);
    }
    public static AddMemberToConversationScene getInstance() {
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
    public void destroy() {

    }
    @Override
    public void handle(Event event) {

    }
}
