package com.triciyen.scenes;

import com.triciyen.service.ConversationService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteConversationScene implements BaseScene {
    private static final DeleteConversationScene instance = new DeleteConversationScene();
    private Scene scene;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 100;

    private Button submitButton;
    private Button cancelButton;
    private Label infoLabel;

    private DeleteConversationScene() {
        Label titleLabel = new Label("Do you want to delete the conversation?");

        submitButton = new Button("Submit");
        submitButton.setMinWidth(WINDOW_WIDTH);
        submitButton.setOnMouseClicked(this);

        cancelButton = new Button("Cancel");
        cancelButton.setMinWidth(WINDOW_WIDTH);
        cancelButton.setOnMouseClicked(this);

        this.infoLabel = new Label("Waiting for decision...");

        VBox box = new VBox();
        box.getChildren().addAll(titleLabel, submitButton, cancelButton, this.infoLabel);
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(box);

        this.scene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public static DeleteConversationScene getInstance() {
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
        this.infoLabel.setText("Waiting for decision...");
    }
    @Override
    public void handle(Event event) {
        if (event.getSource() == this.cancelButton) {
            ((Stage) scene.getWindow()).close();
        } else if (event.getSource() == this.submitButton) {
            ConversationService conversationService = ConversationService.getInstance();
            Boolean result = conversationService.deleteConversation(localStorage.getCurrentActiveConversation());
            if (localStorage.wasError()) {
                this.infoLabel.setText(localStorage.getInterfaceErrorMessage());
            } else {
                MainScene.getInstance().destroy();
                MainScene.getInstance().initialize();
                destroy();
                ((Stage) scene.getWindow()).close();
            }
        }
    }
}
