package com.triciyen.scenes;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FindConversationScene implements BaseScene {
    private static final FindConversationScene instance = new FindConversationScene();
    private Scene scene;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 150;

    private TextField searchTextField;
    private Label infoLabel;

    private FindConversationScene() {
        Label chooseTypeLabel = new Label("Choose the key field for search:");

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton buttonId = new RadioButton("By ID");
        RadioButton buttonName = new RadioButton("By Name");
        buttonId.setSelected(true);
        buttonId.setToggleGroup(toggleGroup);
        buttonName.setToggleGroup(toggleGroup);
        HBox radioBox = new HBox();
        radioBox.getChildren().addAll(buttonId, buttonName);

        Label titleLabel = new Label("Type selected field for search:");

        this.searchTextField = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setMinWidth(WINDOW_WIDTH);
        searchButton.setOnMouseClicked(this);

        this.infoLabel = new Label("Waiting to search...");

        VBox box = new VBox();
        box.getChildren().addAll(chooseTypeLabel, radioBox, titleLabel, this.searchTextField, searchButton, this.infoLabel);
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
    }
    @Override
    public void handle(Event event) {

    }
}
