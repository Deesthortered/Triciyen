package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import com.triciyen.entity.Conversation;
import com.triciyen.entity.Message;
import com.triciyen.entity.UserAccount;
import com.triciyen.service.ConversationService;
import com.triciyen.service.MessageService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private static final int avatarSize = 50;

    private ImageView imageUserAvatar;
    private Label usernameLabel;
    private Label loginLabel;
    private Button logoutButton;

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

        usernameLabel = new Label("");
        loginLabel = new Label("");
        VBox loginBox = new VBox();
        loginBox.getChildren().addAll(usernameLabel, loginLabel);

        imageUserAvatar = new ImageView(localStorage.getBaseAccountImage());
        imageUserAvatar.setFitWidth(avatarSize);
        imageUserAvatar.setFitHeight(avatarSize);

        HBox avatarBox = new HBox();
        avatarBox.getChildren().addAll(imageUserAvatar, loginBox);

        logoutButton = new Button("Logout");
        logoutButton.setOnMouseClicked(this);

        VBox leftCornerBox = new VBox();
        leftCornerBox.getChildren().addAll(avatarBox, logoutButton);

        leftCornerPane.getChildren().add(leftCornerBox);

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

        UserAccount currentLoggedAccount = localStorage.getLoggedAccount();
        loginLabel.setText("Login: " + currentLoggedAccount.getLogin());
        usernameLabel.setText(currentLoggedAccount.getName());

        conversationsBox.getChildren().clear();
        Optional<List<Conversation>> packedList = conversationService.getAllSubscribedConversations();
        if (packedList.isEmpty()) {
            System.out.println(localStorage.getServerErrorMessage());
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
        if (event.getSource() == logoutButton) {
            logoutEvent();
        } else {
            System.out.println("Login Scene: Unknown event.");
        }
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

    private void logoutEvent() {
        localStorage.setDefaultState();
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
}
