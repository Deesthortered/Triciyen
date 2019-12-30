package com.triciyen.scenes;

import com.triciyen.entity.Conversation;
import com.triciyen.entity.UserConversation;
import com.triciyen.service.ConversationService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FindConversationScene implements BaseScene {
    private static final FindConversationScene instance = new FindConversationScene();
    private Scene scene;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 130;

    private TextField searchTextField;
    private Label infoLabel;
    private Label nextInfoLabel;

    private Button searchButton;
    private Button joinButton;
    private int foundConversationId;

    private FindConversationScene() {
        Label titleLabel = new Label("Type ID for search:");

        this.searchTextField = new TextField();
        searchButton = new Button("Search");
        searchButton.setMinWidth(WINDOW_WIDTH);
        searchButton.setOnMouseClicked(this);

        this.infoLabel = new Label("Waiting to search...");

        joinButton = new Button("Join to the conversation");
        joinButton.setMinWidth(WINDOW_WIDTH);
        joinButton.setOnMouseClicked(this);
        joinButton.setDisable(true);

        this.nextInfoLabel = new Label("");
        this.foundConversationId = -1;

        VBox box = new VBox();
        box.getChildren().addAll(titleLabel, this.searchTextField, searchButton, this.infoLabel, joinButton, nextInfoLabel);
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(box);

        this.scene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public static FindConversationScene getInstance() {
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
        searchTextField.setText("");
        infoLabel.setText("Waiting to search...");
        joinButton.setDisable(true);
        this.nextInfoLabel.setText("");
        this.foundConversationId = -1;
    }
    @Override
    public void handle(Event event) {
        if (event.getSource() == searchButton) {
            joinButton.setDisable(true);
            this.nextInfoLabel.setText("");
            this.foundConversationId = -1;
            if (searchTextField.getText().equals("")) {
                infoLabel.setText("Name must not be empty.");
            } else {
                ConversationService conversationService = ConversationService.getInstance();
                Conversation foundConversation = conversationService.findConversationById
                        (Integer.valueOf(searchTextField.getText()));
                if (localStorage.wasError()) {
                    infoLabel.setText(localStorage.getInterfaceErrorMessage());
                    System.err.println(localStorage.getInternalErrorMessage());
                    localStorage.closeError();
                } else {
                    infoLabel.setText("Found conversation: (" + foundConversation.getConversationId() + ") "
                            + foundConversation.getName());
                    joinButton.setDisable(false);
                    this.nextInfoLabel.setText("Do you want to join?");
                    this.foundConversationId = foundConversation.getConversationId();
                }
            }
        } else if (event.getSource() == joinButton) {
            ConversationService conversationService = ConversationService.getInstance();
            UserConversation newUserConversation = conversationService.addUserToConversation(
                    this.foundConversationId,
                    localStorage.getLoggedAccount().getLogin()
            );
            if (localStorage.wasError()) {
                infoLabel.setText(localStorage.getInterfaceErrorMessage());
                System.err.println(localStorage.getInternalErrorMessage());
                localStorage.closeError();
            } else {
                MainScene.getInstance().destroy();
                MainScene.getInstance().initialize();
                destroy();
                ((Stage) scene.getWindow()).close();
            }
        }
    }
}
