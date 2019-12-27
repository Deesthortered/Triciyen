package com.triciyen.scenes;

import com.triciyen.entity.Conversation;
import com.triciyen.service.ConversationService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateConversationScene implements BaseScene {
    private static final CreateConversationScene instance = new CreateConversationScene();
    private Scene scene;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 100;

    private TextField conversationNameField;
    private Label infoLabel;

    private CreateConversationScene() {
        Label title = new Label("Type a name of the new conversation:");
        this.conversationNameField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setMinWidth(WINDOW_WIDTH);
        submitButton.setOnMouseClicked(this);
        infoLabel = new Label("Waiting to submit...");

        VBox box = new VBox();
        box.getChildren().addAll(title, this.conversationNameField, submitButton, infoLabel);
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(box);

        this.scene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public static CreateConversationScene getInstance() {
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
        conversationNameField.setText("");
        infoLabel.setText("Waiting to submit...");
    }
    @Override
    public void handle(Event event) {
        if (conversationNameField.getText().equals("")) {
            infoLabel.setText("Name must not be empty.");
        } else {
            ConversationService conversationService = ConversationService.getInstance();
            Conversation newConversation = conversationService.createConversation(conversationNameField.getText());
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
