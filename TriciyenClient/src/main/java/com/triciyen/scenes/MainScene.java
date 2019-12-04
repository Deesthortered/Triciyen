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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainScene implements BaseScene {
    private static final MainScene instance = new MainScene();

    // Scene settings
    private static Scene scene;
    private static final int sceneWidth = 1000;
    private static final int sceneHeight = 700;
    private BorderPane mainPane;

    // Left corner settings
    private static final int leftCornerHeight = 100;
    private static final int avatarSize = 50;

    private ImageView imageUserAvatar;
    private Label usernameLabel;
    private Label loginLabel;
    private Button logoutButton;

    // Conversation box settings
    private VBox conversationsBox;
    private List<Button> conversationButtons;
    private List<Conversation> conversations;
    private static final int conversationButtonWidth = 200;
    private static final int conversationButtonHeight = 70;
    private static final int conversationScrollPaneWidth = 215;
    private static final int conversationScrollPaneHeight = sceneHeight - leftCornerHeight;

    // Big right box settings
    private StackPane emptyRightPane;
    private Label emptyRightTitle;
    private static final int emptyRightPaneWidth = sceneWidth - conversationScrollPaneWidth;
    private static final int emptyRightPaneHeight = leftCornerHeight;

    private VBox fullRightPane;
    private StackPane fullRightTitlePane;
    private Label fullRightConversationLabel;
    private ListView<String> messageListView;

    private MainScene() {
        mainPane = new BorderPane();

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

        emptyRightPane = new StackPane();
        emptyRightPane.setMinHeight(emptyRightPaneHeight);
        emptyRightPane.setPrefHeight(emptyRightPaneHeight);
        emptyRightPane.setMaxHeight(emptyRightPaneHeight);
        emptyRightPane.setMinWidth(emptyRightPaneWidth);
        emptyRightPane.setPrefHeight(emptyRightPaneWidth);
        emptyRightPane.setMaxWidth(emptyRightPaneWidth);
        emptyRightTitle = new Label("Please, choose the conversation");
        emptyRightPane.getChildren().addAll(emptyRightTitle);
        mainPane.setCenter(emptyRightPane);

        fullRightPane = new VBox();
        fullRightTitlePane = new StackPane();
        fullRightConversationLabel = new Label("");
        fullRightTitlePane.getChildren().addAll(fullRightConversationLabel);
        messageListView = new ListView<>();
        fullRightPane.getChildren().addAll(fullRightTitlePane, messageListView);

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

        mainPane.setCenter(emptyRightPane);

        conversationsBox.getChildren().clear();
        Optional<List<Conversation>> packedList = conversationService.getAllSubscribedConversations();
        if (packedList.isEmpty()) {
            System.out.println(localStorage.getServerErrorMessage());
        } else {
            conversations = packedList.get();
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
            boolean was = false;
            for (int i = 0; i < conversationButtons.size(); i++) {
                if (event.getSource() == conversationButtons.get(i)) {
                    was = true;
                    initConversation(i);
                }
            }
            if (!was)
                System.out.println("Login Scene: Unknown event.");
        }
    }

    private void initConversationButtons(List<Conversation> source, List<Message> lastMessages) {
        conversationButtons = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            Button button = new Button(source.get(i).getName() + "\n" + lastMessages.get(i).getContent());
            button.setMinWidth(conversationButtonWidth);
            button.setPrefWidth(conversationButtonWidth);
            button.setMaxWidth(conversationButtonWidth);
            button.setMinHeight(conversationButtonHeight);
            button.setPrefHeight(conversationButtonHeight);
            button.setMaxHeight(conversationButtonHeight);
            conversationsBox.getChildren().add(button);
            conversationButtons.add(button);
        }
    }
    private void initConversation(int conversationIndex) {
        
    }

    private void logoutEvent() {
        localStorage.setDefaultState();
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
}
