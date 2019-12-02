package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginScene implements EventHandler<Event> {
    private static final LoginScene instance = new LoginScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;

    private Button loginButton;
    private Button registrationButton;

    private LoginScene() {
        HBox loginBox = new HBox();
        HBox passwordBox = new HBox();
        HBox buttonBox = new HBox();
        VBox rowBox = new VBox();
        StackPane mainPane = new StackPane();

        Label titleLabel = new Label("Wellcome to Triciyen!");

        Label loginLabel = new Label("Login: ");
        TextField loginField = new TextField();
        loginBox.getChildren().addAll(loginLabel, loginField);

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        loginButton = new Button("Login");
        loginButton.setOnMouseClicked(this);
        registrationButton = new Button("Registration");
        registrationButton.setOnMouseClicked(this);
        buttonBox.getChildren().addAll(loginButton, registrationButton);

        rowBox.getChildren().addAll(titleLabel, loginBox, passwordBox, buttonBox);
        mainPane.getChildren().addAll(rowBox);

        scene = new Scene(mainPane, sceneWidth, sceneHeight);
    }
    public static LoginScene getInstance() {
        return instance;
    }
    public Scene getScene() {
        return scene;
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == loginButton) {
            loginEvent();
        } else if (event.getSource() == registrationButton) {
            registrationEvent();
        } else {
            System.out.println("Login Scene: Unknown event.");
        }
    }

    private void loginEvent() {

    }
    private void registrationEvent() {
        TriciyenApplication.setGlobalScene(RegistrationScene.getInstance().getScene());
    }
}
