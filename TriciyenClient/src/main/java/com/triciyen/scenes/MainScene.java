package com.triciyen.scenes;

import com.triciyen.entity.Conversation;
import com.triciyen.service.ConversationService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;

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
        ConversationService conversationService = ConversationService.getInstance();
        Optional<List<Conversation>> list = conversationService.getAllSubscribedConversations();
        if (list.isEmpty()) {
            System.out.println("OOps");
            System.out.println(stateService.getServerErrorMessage());
        } else {
            System.out.println("Here we go!");
            List<Conversation> unpacked = list.get();
            unpacked.forEach(System.out::println);
            System.out.println("\n\n");
        }
    }
    @Override
    public void handle(Event event) {

    }
}
