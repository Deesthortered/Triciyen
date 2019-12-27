package com.triciyen.scenes;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class LeaveConversationScene implements BaseScene {
    private static final LeaveConversationScene instance = new LeaveConversationScene();
    private Scene scene;

    private LeaveConversationScene() {
        this.scene = new Scene(new StackPane(), 300, 300);
    }
    public static LeaveConversationScene getInstance() {
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
