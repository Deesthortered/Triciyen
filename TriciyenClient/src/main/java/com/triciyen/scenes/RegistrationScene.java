package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RegistrationScene implements BaseScene {
    private static final RegistrationScene instance = new RegistrationScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;

    private Button submitButton;
    private Button backButton;

    private RegistrationScene() {
        VBox contentBox = new VBox();
        StackPane mainPane = new StackPane();

        Label titleLabel = new Label("The registration menu");

        HBox loginBox = new HBox();
        Label loginLabel = new Label("Login: ");
        TextField loginField = new TextField();
        loginBox.getChildren().addAll(loginLabel, loginField);

        HBox passwordBox = new HBox();
        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        HBox confirmPasswordBox = new HBox();
        Label confirmPasswordLabel = new Label("Confirm password: ");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordBox.getChildren().addAll(confirmPasswordLabel, confirmPasswordField);

        HBox fullnameBox = new HBox();
        Label fullnameLabel = new Label("Full name: ");
        TextField fullnameField = new TextField();
        fullnameBox.getChildren().addAll(fullnameLabel, fullnameField);

        HBox buttonBox = new HBox();
        submitButton = new Button("Submit");
        submitButton.setOnMouseClicked(this);
        backButton = new Button("Back");
        backButton.setOnMouseClicked(this);
        buttonBox.getChildren().addAll(submitButton, backButton);

        contentBox.getChildren().addAll(titleLabel, loginBox, passwordBox, confirmPasswordBox, fullnameBox, buttonBox);
        mainPane.getChildren().add(contentBox);
        scene = new Scene(mainPane, sceneWidth, sceneHeight);
    }
    public static RegistrationScene getInstance() {
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
    public void handle(Event event) {
        if (event.getSource() == submitButton) {
            submitEvent();
        } else if (event.getSource() == backButton) {
            backEvent();
        } else {
            System.out.println("Registration Scene: Unknown event.");
        }
    }

    private void submitEvent() {

    }
    private void backEvent() {
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
}
