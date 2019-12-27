package com.triciyen.scenes;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class DeleteConversationScene implements BaseScene {
    private static final DeleteConversationScene instance = new DeleteConversationScene();
    private Scene scene;

    private DeleteConversationScene() {
        this.scene = new Scene(new StackPane(), 300, 300);
    }
    public static DeleteConversationScene getInstance() {
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
