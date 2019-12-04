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
    private List<Conversation> conversations;
    private Map<Integer, Integer> conversationLoadedPages;
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
        ConversationService conversationService = ConversationService.getInstance();
        MessageService messageService = MessageService.getInstance();

        UserAccount currentLoggedAccount = localStorage.getLoggedAccount();
        loginLabel.setText("Login: " + currentLoggedAccount.getLogin());
        usernameLabel.setText(currentLoggedAccount.getName());

        emptyRightTitle.setText("Please, choose the conversation");
        mainPane.setCenter(emptyRightPane);
        conversationLoadedPages = new HashMap<>();

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

        expandMessagesButton = new Button(".... expand messages ...");
        expandMessagesButton.setOnMouseClicked(this);
    }
    @Override
    public void handle(Event event) {
        if (event.getSource() == logoutButton) {
            logoutEvent();
        } else if (event.getSource() == expandMessagesButton) {
            expandMessages(currentConversation);
        } else if (event.getSource() == writeMessageSendButton) {
            sendMessage();
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
            button.setOnMouseClicked(this);
        }
    }
    private void initConversation(int conversationIndex) {
        MessageService messageService = MessageService.getInstance();
        Conversation conversation = conversations.get(conversationIndex);

        fullRightConversationLabel.setText(conversation.getName());

        currentConversation = conversation;

        int currentPage = 0;
        conversationLoadedPages.put(conversation.getConversationId(), currentPage);

        List<Message> currentMessages = messageService.getMessagesOfConversationWithPagination(conversation,0);

        if (localStorage.isWasError()) {
            emptyRightTitle.setText("Error was happened:\n" +
                    localStorage.getServerErrorMessage());
            System.err.println(localStorage.getServerErrorMessage());
        } else {
            initMessages(currentMessages);
            mainPane.setCenter(fullRightPane);
        }
    }
    private void initMessages(List<Message> currentMessages) {
        messageButton = new ArrayList<>();
        expandMessagesButton.setDisable(false);
        messageButton.add(expandMessagesButton);
        currentMessages.stream()
                .map(message -> new Button(message.getUser().getName() + ": " + message.getContent()))
                .forEach( (button) -> { messageButton.add(1, button);});
        messageBox.getChildren().clear();
        messageButton.forEach(messageBox.getChildren()::add);
    }

    private void expandMessages(Conversation conversation) {
        MessageService messageService = MessageService.getInstance();
        int nextPage = conversationLoadedPages.get(conversation.getConversationId()) + 1;
        conversationLoadedPages.put(conversation.getConversationId(), nextPage);

        List<Message> currentMessages = messageService.getMessagesOfConversationWithPagination(conversation, nextPage);
        if (currentMessages.isEmpty()) {
            expandMessagesButton.setDisable(true);
        } else {
            currentMessages.stream()
                    .map(message -> new Button(message.getUser().getName() + ": " + message.getContent()))
                    .forEach( (button) -> { messageButton.add(1, button);});
        }

        messageBox.getChildren().clear();
        messageButton.forEach(messageBox.getChildren()::add);
    }
    private void sendMessage() {
        MessageService messageService = MessageService.getInstance();
        String content = writeMessageField.getText();
        Integer contentType = 1;
        String login = localStorage.getLoggedAccount().getLogin();
        Integer conversationId = currentConversation.getConversationId();
        boolean success = messageService.sendMessage(content, contentType, login, conversationId);
        Button newMessage = new Button();
        String buttonContent = localStorage.getLoggedAccount().getName() + ": " + content;
        if (success) {
            newMessage.setText(buttonContent);
        } else {
            newMessage.setText("---- message is not sent ---- {" + buttonContent + "}");
        }
        messageButton.add(newMessage);
        messageBox.getChildren().addAll(newMessage);
    }
    private void logoutEvent() {
        localStorage.setDefaultState();
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
}
