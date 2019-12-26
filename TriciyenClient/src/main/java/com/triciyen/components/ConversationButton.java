package com.triciyen.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


public class ConversationButton extends HBox {

    private ImageView imageView;
    private Label conversationName;
    private Label lastMessage;
    private Label unreadCounter;
    private Label lastTime;

    public ConversationButton(String conversationName, Image image) {
        Platform.runLater(() -> {
            setMinSize(310, 100);

            this.imageView = new ImageView(image);
            this.imageView.setFitHeight(50);
            this.imageView.setFitWidth(50);

            final Circle clip = new Circle(25, 25, 25);
            this.imageView.setClip(clip);

            this.conversationName = new Label(conversationName);
            this.conversationName.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
            lastMessage = new Label();
            lastMessage.setFont(Font.font("Cambria", FontPosture.ITALIC, 16));
            this.unreadCounter = new Label("0");
            this.unreadCounter.setStyle("-fx-border-color: red;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-width: 1;" +
                    "-fx-text-fill: #c8cadd;" +
                    "-fx-padding: 2");
            this.unreadCounter.setFont(Font.font("Cambria", FontWeight.BOLD, 13));
            this.lastTime = new Label("20.01.1999 00:00");
            this.lastTime.setTextAlignment(TextAlignment.LEFT);
            this.lastTime.setFont(Font.font("Cambria", 13));
            this.lastTime.setAlignment(Pos.BOTTOM_LEFT);
            this.lastTime.setStyle("-fx-text-fill: grey");


            HBox counterTimeHBox = new HBox();
            counterTimeHBox.getChildren().addAll( this.lastTime,  this.unreadCounter);
            counterTimeHBox.setMinSize( this.conversationName.getMinWidth(),  this.conversationName.getMinHeight());
            counterTimeHBox.setMaxSize( this.conversationName.getMinWidth(),  this.conversationName.getMinHeight());
            counterTimeHBox.setSpacing(35);

            VBox vbox = new VBox();
            vbox.getChildren().addAll( this.conversationName,  this.lastMessage, counterTimeHBox);
            vbox.setSpacing(5);


            getChildren().addAll(this.imageView, vbox);
            setSpacing(10);
            setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                    + "-fx-border-color: none;");
        });
    }

    public void setUnreadCounter(String counter) {
        Platform.runLater(() -> {
            this.unreadCounter.setText(counter);
            if (counter.equals("0")) {
                this.unreadCounter.setVisible(false);
            }
            else {
                this.unreadCounter.setVisible(true);
            }
        });
    }
    public void setProfileImage(Image image) {
        Platform.runLater(() -> this.imageView.setImage(image));
    }
    public void setLastMessage(String message) {
        Platform.runLater(() -> this.lastMessage.setText(message));
    }
    public void setLastTime(String lastTime) {
        Platform.runLater(() -> this.lastTime.setText(lastTime));
    }
    public void clickStyle(boolean clicked) {
        Platform.runLater(() -> {
            if (clicked) {
                setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                                + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                                + "-fx-border-color: blue;");
            } else {
                setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                        + "-fx-border-color: none;");
            }
        });
    }
}
