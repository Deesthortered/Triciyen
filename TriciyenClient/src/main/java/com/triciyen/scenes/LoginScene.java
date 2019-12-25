package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import com.triciyen.service.UserAccountService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginScene implements BaseScene {
    private static final LoginScene instance = new LoginScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;

    private Button loginButton;
    private Button registrationButton;

    private TextField loginField;
    private PasswordField passwordField;

    private Label errorLabel;

    private LoginScene() {
        HBox loginBox = new HBox();
        HBox passwordBox = new HBox();
        HBox buttonBox = new HBox();
        VBox rowBox = new VBox();
        StackPane mainPane = new StackPane();

        Label titleLabel = new Label("Wellcome to Triciyen!");

        Label loginLabel = new Label("Login: ");
        loginField = new TextField();
        loginBox.getChildren().addAll(loginLabel, loginField);

        Label passwordLabel = new Label("Password: ");
        passwordField = new PasswordField();
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        loginButton = new Button("Login");
        loginButton.setOnMouseClicked(this);
        registrationButton = new Button("Registration");
        registrationButton.setOnMouseClicked(this);
        buttonBox.getChildren().addAll(loginButton, registrationButton);

        errorLabel = new Label("");

        rowBox.getChildren().addAll(titleLabel, loginBox, passwordBox, buttonBox, errorLabel);
        mainPane.getChildren().addAll(rowBox);

        scene = new Scene(mainPane, sceneWidth, sceneHeight);
    }
    public static LoginScene getInstance() {
        return instance;
    }

    @Override
    public Scene getScene() {
        return scene;
    }
    @Override
    public void initialize() {
        this.loginField.setText("");
        this.passwordField.setText("");
        this.errorLabel.setText("");
    }
    @Override
    public void destroy() {

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
        UserAccountService service = UserAccountService.getInstance();
        String login = loginField.getText();
        String password = passwordField.getText();
        boolean success = service.authenticate(login, password);
        if (success) {
            TriciyenApplication.setGlobalScene(MainScene.getInstance());
        } else {
            errorMessage();
        }
    }
    private void registrationEvent() {
        TriciyenApplication.setGlobalScene(RegistrationScene.getInstance());
    }
    private void errorMessage() {
        this.errorLabel.setText(localStorage.getServerErrorMessage());
    }
}
