package com.triciyen.components;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ConversationPanel extends HBox {

    private Label title;
    private Button addButton;
    private Button leaveButton;
    private Button deleteButton;


    public ConversationPanel(String conversationName) {

        this.title = new Label(conversationName);
        this.title.setFont(Font.font ("Verdana", 15));

        HBox rHBox = new HBox();

        ImageView addIView = new ImageView(new Image("images/plus_add_green.png"));
        addIView.setFitWidth(20);
        addIView.setFitHeight(20);

        addButton = new Button("", addIView);
        addButton.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 20px; " +
                        "-fx-min-height: 20px; " +
                        "-fx-max-width: 20px; " +
                        "-fx-max-height: 20px;"
        );
        addButton.setOnMouseClicked(this::handleAddMemberButton);

        ImageView leaveIView = new ImageView(new Image("images/minus_leave_red.png"));
        leaveIView.setFitHeight(20);
        leaveIView.setFitWidth(20);

        leaveButton = new Button("", leaveIView);
        leaveButton.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 20px; " +
                        "-fx-min-height: 20px; " +
                        "-fx-max-width: 20px; " +
                        "-fx-max-height: 20px;"
        );
        leaveButton.setOnMouseClicked(this::handleLeaveConversationButton);


        ImageView deleteIView = new ImageView(new Image("images/cross_delete_black.png"));
        deleteIView.setFitHeight(20);
        deleteIView.setFitWidth(20);

        deleteButton = new Button("", deleteIView);
        deleteButton.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 20px; " +
                        "-fx-min-height: 20px; " +
                        "-fx-max-width: 20px; " +
                        "-fx-max-height: 20px;"
        );
        deleteButton.setOnMouseClicked(this::handleDeleteConversationButton);

        rHBox.getChildren().addAll(addButton, leaveButton, deleteButton);
        rHBox.setSpacing(5);
        rHBox.setAlignment(Pos.BOTTOM_LEFT);

        setSpacing(20);
        setPadding(new Insets(0,0,10,5));
        setAlignment(Pos.BOTTOM_LEFT);
        getChildren().addAll(title, rHBox);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    private void handleAddMemberButton(Event event) {

    }

    private void handleLeaveConversationButton(Event event) {

    }

    private void handleDeleteConversationButton(Event event) {

    }
}
