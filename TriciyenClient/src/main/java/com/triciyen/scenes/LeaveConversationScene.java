package com.triciyen.scenes;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeaveConversationScene implements BaseScene {
    private static final LeaveConversationScene instance = new LeaveConversationScene();
    private Scene scene;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 100;

    Button submitButton;
    Button cancelButton;

    private LeaveConversationScene() {
        Label titleLabel = new Label("Do you want to leave the conversation?");

        submitButton = new Button("Submit");
        submitButton.setMinWidth(WINDOW_WIDTH);
        submitButton.setOnMouseClicked(this);

        cancelButton = new Button("Cancel");
        cancelButton.setMinWidth(WINDOW_WIDTH);
        cancelButton.setOnMouseClicked(this);

        VBox box = new VBox();
        box.getChildren().addAll(titleLabel, submitButton, cancelButton);
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(box);

        this.scene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public static LeaveConversationScene getInstance() {
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

    }
    @Override
    public void handle(Event event) {
        if (event.getSource() == this.cancelButton) {
            ((Stage) scene.getWindow()).close();
        } else if (event.getSource() == this.submitButton) {

        }
    }
}
