package com.triciyen.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class ChatMessageBox extends HBox {

    private final int MAX_TEXT_WIDTH = 60;

    private Rectangle messageBox;
    private ImageView profileImage;
    private Text text;
    private Text fullname;

    public ChatMessageBox(Image image) {
        this.profileImage = new ImageView(image);
        this.profileImage.setFitHeight(30);
        this.profileImage.setFitWidth(30);

        messageBox = new Rectangle(100.0d, 100.0d, 120.0d, 40.0d);
        messageBox.setFill(Color.web("#ebf0fa"));
        messageBox.setArcHeight(10.0d);
        messageBox.setArcWidth(10.0d);

        fullname = new Text();

        text = new Text();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(fullname, text);
        StackPane stack = new StackPane();
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().addAll(messageBox, vbox);

        getChildren().addAll(profileImage, stack);

        messageBox.widthProperty().bind(stack.widthProperty());
        messageBox.heightProperty().bind(stack.heightProperty());

        setSpacing(3);
    }
    public ChatMessageBox(Image image, String fullname) {
        this(image);

        this.fullname.setText(fullname);
    }

    public void setText(String text) {

        for (int i = MAX_TEXT_WIDTH; i < text.length(); i += MAX_TEXT_WIDTH + 1) {
            text = text.substring(0, i) + "\n" + text.substring(i, text.length());
        }
        this.text.setText(text);
    }
}
