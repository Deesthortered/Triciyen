package com.triciyen.components;

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
    private Label lasttime;

    public ConversationButton(String conversationName, Image image) {

        setMinSize(250, 100);

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
        this.lasttime = new Label("20.01.1999 00:00");
        this.lasttime.setTextAlignment(TextAlignment.LEFT);
        this.lasttime.setFont(Font.font("Cambria", 13));
        this.lasttime.setAlignment(Pos.BOTTOM_LEFT);
        this.lasttime.setStyle("-fx-text-fill: grey");



        HBox counterTimeHBox = new HBox();
        counterTimeHBox.getChildren().addAll( this.lasttime,  this.unreadCounter);
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

        setOnMouseClicked(event -> {
            setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                    + "-fx-border-color: blue;");
        });
    }

    public void setUnreadCounter(String counter) {
        this.unreadCounter.setText(counter);
        if (counter.equals("0")) {
            this.unreadCounter.setVisible(false);
        }
        else {
            this.unreadCounter.setVisible(true);
        }
    }

    public void setProfileImage(Image image) {
        this.imageView.setImage(image);
    }

    public void setLastMessage(String message) {
        this.lastMessage.setText(message);
    }

    public void setLasttime(String lasttime) {
        this.lasttime.setText(lasttime);
    }


}
