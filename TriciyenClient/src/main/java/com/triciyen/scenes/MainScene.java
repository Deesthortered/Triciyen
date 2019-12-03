package com.triciyen.scenes;

import com.triciyen.entity.Conversation;
import com.triciyen.entity.Message;
import com.triciyen.service.ConversationService;
import com.triciyen.service.MessageService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainScene implements BaseScene {
    private static final MainScene instance = new MainScene();
    private static Scene scene;

    private static final int sceneWidth = 1000;
    private static final int sceneHeight = 700;

    private static final int leftCornerHeight = 100;

    private VBox conversationsBox;
    private static final int conversationButtonWidth = 200;
    private static final int conversationButtonHeight = 70;
    private static final int conversationScrollPaneWidth = 215;
    private static final int conversationScrollPaneHeight = sceneHeight - leftCornerHeight;

    private MainScene() {
        BorderPane mainPane = new BorderPane();

        StackPane leftCornerPane = new StackPane();
        leftCornerPane.setMinHeight(leftCornerHeight);
        leftCornerPane.setMaxHeight(leftCornerHeight);
        leftCornerPane.setPrefHeight(leftCornerHeight);

        Button b = new Button("bbbbbbbbb");
        leftCornerPane.getChildren().addAll(b);

        conversationsBox = new VBox();
        ScrollPane conversationScrollPane = new ScrollPane(conversationsBox);
        conversationScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        conversationScrollPane.setMaxWidth(conversationScrollPaneWidth);
        conversationScrollPane.setMinWidth(conversationScrollPaneWidth);
        conversationScrollPane.setPrefWidth(conversationScrollPaneWidth);
        conversationScrollPane.setMaxHeight(conversationScrollPaneHeight);
        conversationScrollPane.setMinHeight(conversationScrollPaneHeight);
        conversationScrollPane.setPrefHeight(conversationScrollPaneHeight);


        VBox leftPane = new VBox();
        leftPane.getChildren().addAll(leftCornerPane, conversationScrollPane);

        mainPane.setLeft(leftPane);

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
            Button button = new Button(source.get(i).getName() + "\n" + lastMessages.get(i).getContent());
            button.setMinWidth(conversationButtonWidth);
            button.setPrefWidth(conversationButtonWidth);
            button.setMaxWidth(conversationButtonWidth);
            button.setMinHeight(conversationButtonHeight);
            button.setPrefHeight(conversationButtonHeight);
            button.setMaxHeight(conversationButtonHeight);
            conversationsBox.getChildren().add(button);
        }
    }
}
