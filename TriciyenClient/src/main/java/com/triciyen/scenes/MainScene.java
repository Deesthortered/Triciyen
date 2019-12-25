package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import com.triciyen.entity.Conversation;
import com.triciyen.entity.UserAccount;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

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

    private ScrollPane fullRightScrollPane;
    private VBox messageBox;
    private List<Button> messageButton;
    private Conversation currentConversation;
    private Button expandMessagesButton;

    private StackPane writeMessagePane;
    private HBox writeMessageHBox;
    private TextField writeMessageField;
    private Button writeMessageSendButton;
    private static final int writeMessagePaneWidth = sceneWidth - conversationScrollPaneWidth;
    private static final int writeMessagePaneHeight = 100;

    private static final int fullRightTitlePaneWidth = sceneWidth - conversationScrollPaneWidth;
    private static final int fullRightTitlePaneHeight = leftCornerHeight;
    private static final int fullRightScrollPaneWidth = fullRightTitlePaneWidth;
    private static final int fullRightScrollPaneHeight = sceneHeight - fullRightTitlePaneHeight - writeMessagePaneHeight;

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
        emptyRightPane.setPrefWidth(emptyRightPaneWidth);
        emptyRightPane.setMaxWidth(emptyRightPaneWidth);
        emptyRightTitle = new Label("Please, choose the conversation");
        emptyRightPane.getChildren().addAll(emptyRightTitle);
        mainPane.setCenter(emptyRightPane);

        fullRightPane = new VBox();
        fullRightTitlePane = new StackPane();
        fullRightTitlePane.setMinHeight(fullRightTitlePaneHeight);
        fullRightTitlePane.setPrefHeight(fullRightTitlePaneHeight);
        fullRightTitlePane.setMaxHeight(fullRightTitlePaneHeight);
        fullRightTitlePane.setMinWidth(fullRightTitlePaneWidth);
        fullRightTitlePane.setPrefWidth(fullRightTitlePaneWidth);
        fullRightTitlePane.setMaxWidth(fullRightTitlePaneWidth);
        fullRightConversationLabel = new Label("");
        fullRightTitlePane.getChildren().addAll(fullRightConversationLabel);

        messageBox = new VBox();
        fullRightScrollPane = new ScrollPane(messageBox);
        messageBox.heightProperty().addListener(
                (ChangeListener) (observable, oldvalue, newValue) -> fullRightScrollPane.setVvalue((Double)newValue ));
        fullRightScrollPane.setMinHeight(fullRightScrollPaneHeight);
        fullRightScrollPane.setPrefHeight(fullRightScrollPaneHeight);
        fullRightScrollPane.setMaxHeight(fullRightScrollPaneHeight);
        fullRightScrollPane.setMinWidth(fullRightScrollPaneWidth);
        fullRightScrollPane.setPrefWidth(fullRightScrollPaneWidth);
        fullRightScrollPane.setMaxWidth(fullRightScrollPaneWidth);

        writeMessagePane = new StackPane();
        writeMessageHBox = new HBox();

        writeMessageField = new TextField();
        writeMessageField.setMinHeight(writeMessagePaneHeight);
        writeMessageField.setPrefHeight(writeMessagePaneHeight);
        writeMessageField.setMaxHeight(writeMessagePaneHeight);
        writeMessageField.setMinWidth(writeMessagePaneWidth - writeMessagePaneHeight);
        writeMessageField.setPrefWidth(writeMessagePaneWidth - writeMessagePaneHeight);
        writeMessageField.setMaxWidth(writeMessagePaneWidth - writeMessagePaneHeight);

        writeMessageSendButton = new Button("Send");
        writeMessageSendButton.setOnMouseClicked(this);
        writeMessageSendButton.setMinHeight(writeMessagePaneHeight);
        writeMessageSendButton.setPrefHeight(writeMessagePaneHeight);
        writeMessageSendButton.setMaxHeight(writeMessagePaneHeight);
        writeMessageSendButton.setMinWidth(writeMessagePaneHeight);
        writeMessageSendButton.setPrefWidth(writeMessagePaneHeight);
        writeMessageSendButton.setMaxWidth(writeMessagePaneHeight);

        writeMessageHBox.getChildren().addAll(writeMessageField, writeMessageSendButton);
        writeMessagePane.getChildren().add(writeMessageHBox);
        fullRightPane.getChildren().addAll(fullRightTitlePane, fullRightScrollPane, writeMessagePane);

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
        UserAccount currentLoggedAccount = localStorage.getLoggedAccount();
        loginLabel.setText("Login: " + currentLoggedAccount.getLogin());
        usernameLabel.setText(currentLoggedAccount.getName());

        emptyRightTitle.setText("Please, choose the conversation");
        mainPane.setCenter(emptyRightPane);
    }
    @Override
    public void destroy() {
        conversationsBox.getChildren().clear();
    }
    @Override
    public void handle(Event event) {
        if (event.getSource() == logoutButton) {
            logoutEvent();
        } else {
            System.out.println("Login Scene: Unknown event.");
        }
    }

    private void logoutEvent() {
        localStorage.setDefaultState();
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
}
