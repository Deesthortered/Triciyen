package com.triciyen.scenes;

import com.triciyen.MessageListener;
import com.triciyen.TriciyenApplication;
import com.triciyen.entity.Conversation;
import com.triciyen.entity.UserAccount;
import com.triciyen.service.ConversationService;
import com.triciyen.service.MessageService;
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
    private static final int conversationButtonWidth = 250;
    private static final int conversationButtonHeight = 70;
    private static final int conversationScrollPaneWidth = 265;
    private static final int conversationScrollPaneHeight = sceneHeight - leftCornerHeight;

    // Big right box settings - None state
    private StackPane emptyRightPane;
    private Label emptyRightTitle;
    private static final int emptyRightPaneWidth = sceneWidth - conversationScrollPaneWidth;
    private static final int emptyRightPaneHeight = leftCornerHeight;

    // Big right box settings - Conversation state
    private VBox fullRightPane;
    private StackPane fullRightTitlePane;
    private Label fullRightConversationLabel;

    private ScrollPane fullRightScrollPane;
    private VBox messageBox;
    private List<Button> messageButton;

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

    List<MessageListener> messageListeners;
    Integer oldestReadMessageId = -1;

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
        logoutButton.setOnMouseClicked(this::handleLogout);

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
        writeMessageSendButton.setOnMouseClicked(this::handleSendMessageButton);
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
        initializeUserCorner();
        initializeRightPane();
        initializeConversations();
    }
    @Override
    public void destroy() {
        destroyUserCorner();
        destroyRightPane();
        destroyConversations();
    }
    @Override
    public void handle(Event event) {
        System.out.println("Login Scene: Unknown event.");
    }


    private void initializeUserCorner() {
        UserAccount currentLoggedAccount = localStorage.getLoggedAccount();
        loginLabel.setText("Login: " + currentLoggedAccount.getLogin());
        usernameLabel.setText(currentLoggedAccount.getName());
    }
    private void initializeRightPane() {
        emptyRightTitle.setText("Please, choose the conversation");
        mainPane.setCenter(emptyRightPane);

    }
    private void initializeConversations() {
        conversationsBox.getChildren().clear();
        conversationButtons = new ArrayList<>();
        messageListeners = new ArrayList<>();

        ConversationService conversationService = ConversationService.getInstance();
        Optional<List<Conversation>> conversationListEnvelop = conversationService.getAllSubscribedConversations();
        if (localStorage.wasError()) {
            System.err.println(localStorage.getInternalErrorMessage());
            Button button = makeErrorConversationButton();
            conversationButtons.add(button);
            conversationsBox.getChildren().add(button);
            localStorage.closeError();
        } else {
            if (conversationListEnvelop.isPresent() && !conversationListEnvelop.get().isEmpty()) {
                List<Conversation> conversationList = conversationListEnvelop.get();
                conversationList.stream()
                        .map(conversation -> {
                            MessageListener listener = new MessageListener(conversation.getConversationId());
                            messageListeners.add(listener);
                            listener.start();
                            return mapConversationToButton(conversation);
                        })
                        .forEach(button -> {
                            conversationButtons.add(button);
                            conversationsBox.getChildren().add(button);
                        });
            } else {
                Button button = makeNoConversationButton();
                conversationButtons.add(button);
                conversationsBox.getChildren().add(button);
            }
        }
    }
    private void initializeMessages(Integer conversationId) {
        messageBox.getChildren().clear();
        messageButton = new ArrayList<>();
        localStorage.setCurrentActiveConversation(conversationId);

        MessageService messageService = MessageService.getInstance();
        oldestReadMessageId = messageService.getLastReadMessageIdOfConversation(conversationId);
        if (localStorage.wasError()) {
            System.err.println(localStorage.getInternalErrorMessage());
            localStorage.closeError();
        } else {
            System.out.println("Last read: " + oldestReadMessageId);
        }
    }

    private void destroyUserCorner() {
        loginLabel.setText("Shutdown....");
        usernameLabel.setText("Shutdown....");
    }
    private void destroyRightPane() {
        emptyRightTitle.setText("Shutdown....");
        mainPane.setCenter(emptyRightPane);
    }
    private void destroyConversations() {
        conversationsBox.getChildren().clear();
        if (conversationButtons != null)
            conversationButtons.clear();
        if (messageListeners != null) {
            messageListeners.forEach(Thread::interrupt);
            messageListeners.clear();
        }
    }
    private void destroyMessages() {
        localStorage.setCurrentActiveConversation(-1);
        messageBox.getChildren().clear();
        if (messageButton != null)
            messageButton.clear();
        this.oldestReadMessageId = -1;
    }

    private Button mapConversationToButton(Conversation conversation) {
        Button button = new Button();
        button.setId(conversation.getConversationId().toString());
        button.setText(conversation.getName());
        button.setMinWidth(conversationButtonWidth);
        button.setMinHeight(conversationButtonHeight);
        button.setOnMouseClicked(this::handleConversationButton);
        return button;
    }
    private Button makeNoConversationButton() {
        Button button = new Button();
        button.setText("You have no conversations");
        button.setMinWidth(conversationButtonWidth);
        return button;
    }
    private Button makeErrorConversationButton() {
        Button button = new Button();
        button.setText("Error: " + localStorage.getInterfaceErrorMessage());
        button.setMinWidth(conversationButtonWidth);
        return button;
    }

    private void handleLogout(Event event) {
        localStorage.setDefaultState();
        destroy();
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
    private void handleConversationButton(Event event) {
        Button conversationButton = (Button) event.getSource();
        Integer conversationId = Integer.valueOf(conversationButton.getId());
        destroyMessages();
        initializeMessages(conversationId);
    }
    private void handleSendMessageButton(Event event) {

    }
}
