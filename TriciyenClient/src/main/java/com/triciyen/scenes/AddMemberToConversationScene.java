package com.triciyen.scenes;

import com.triciyen.entity.UserConversation;
import com.triciyen.service.ConversationService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddMemberToConversationScene implements BaseScene {
    private static final AddMemberToConversationScene instance = new AddMemberToConversationScene();
    private Scene scene;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 100;

    private TextField searchTextField;
    private Label infoLabel;

    private AddMemberToConversationScene() {
        Label titleLabel = new Label("Type login of user to invite:");
        this.searchTextField = new TextField();

        Button searchButton = new Button("Search");
        searchButton.setMinWidth(WINDOW_WIDTH);
        searchButton.setOnMouseClicked(this);

        this.infoLabel = new Label("Waiting to search...");

        VBox box = new VBox();
        box. getChildren().addAll(titleLabel, this.searchTextField, searchButton, this.infoLabel);
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(box);

        this.scene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public static AddMemberToConversationScene getInstance() {
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
    }
    @Override
    public void handle(Event event) {
        if (searchTextField.getText().equals("")) {
            infoLabel.setText("Login must not be empty.");
        } else {
            ConversationService conversationService = ConversationService.getInstance();
            UserConversation newUserConversation = conversationService.addUserToConversation(
                    localStorage.getCurrentActiveConversation(),
                    searchTextField.getText()
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
