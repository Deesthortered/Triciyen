package com.triciyen.scenes;

import com.triciyen.entity.Conversation;
import com.triciyen.entity.Message;
import com.triciyen.service.ConversationService;
import com.triciyen.service.MessageService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainScene implements BaseScene {
    private static final MainScene instance = new MainScene();
    private static Scene scene;

    private int sceneWidth = 1000;
    private int sceneHeight = 700;

    private VBox conversationsBox;
    private int conversationButtonWidth = 30;
    private int conversationButtonHeight = 30;


    private MainScene() {
        BorderPane mainPane = new BorderPane();

        conversationsBox = new VBox();
        mainPane.setLeft(conversationsBox);

        scene = new Scene(mainPane, sceneWidth, sceneHeight);
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
        MessageService messageService = MessageService.getInstance();
        Optional<List<Conversation>> packedList = conversationService.getAllSubscribedConversations();
        if (packedList.isEmpty()) {
            System.out.println(stateService.getServerErrorMessage());
        } else {
            List<Conversation> conversations = packedList.get();
            List<Message> lastMessages = new ArrayList<>();
            conversations.stream()
                    .map(messageService::getLastMessageOfConversation)
                    .forEach(lastMessages::add);

            initConversationButtons(conversations, lastMessages);
        }
    }
    @Override
    public void handle(Event event) {

    }

    private void initConversationButtons(List<Conversation> source, List<Message> lastMessages) {
        for (int i = 0; i < source.size(); i++) {
            conversationsBox.getChildren().add(new Button(source.get(i).getName() + "\n" +
                    lastMessages.get(i).getContent()));
        }
    }
}
