package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import com.triciyen.entity.UserAccount;
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

public class RegistrationScene implements BaseScene {
    private static final RegistrationScene instance = new RegistrationScene();
    private static Scene scene;

    private int sceneWidth = 300;
    private int sceneHeight = 300;

    private TextField loginField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private TextField fullnameField;
    private TextField emailField;

    private Button submitButton;
    private Button backButton;

    private Label errorLabel;

    private RegistrationScene() {
        VBox contentBox = new VBox();
        StackPane mainPane = new StackPane();

        Label titleLabel = new Label("The registration menu");

        HBox loginBox = new HBox();
        Label loginLabel = new Label("Login: ");
        loginField = new TextField();
        loginBox.getChildren().addAll(loginLabel, loginField);

        HBox passwordBox = new HBox();
        Label passwordLabel = new Label("Password: ");
        passwordField = new PasswordField();
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        HBox confirmPasswordBox = new HBox();
        Label confirmPasswordLabel = new Label("Confirm password: ");
        confirmPasswordField = new PasswordField();
        confirmPasswordBox.getChildren().addAll(confirmPasswordLabel, confirmPasswordField);

        HBox fullnameBox = new HBox();
        Label fullnameLabel = new Label("Full name: ");
        fullnameField = new TextField();
        fullnameBox.getChildren().addAll(fullnameLabel, fullnameField);

        HBox emailBox = new HBox();
        Label emailLabel = new Label("E-mail: ");
        emailField = new TextField();
        emailBox.getChildren().addAll(emailLabel, emailField);

        HBox buttonBox = new HBox();
        submitButton = new Button("Submit");
        submitButton.setOnMouseClicked(this);
        backButton = new Button("Back");
        backButton.setOnMouseClicked(this);
        buttonBox.getChildren().addAll(submitButton, backButton);

        errorLabel = new Label("");

        contentBox.getChildren().addAll(titleLabel, loginBox, passwordBox, confirmPasswordBox,
                fullnameBox, emailBox, buttonBox, errorLabel);
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
        this.passwordField.setText("");
        this.confirmPasswordField.setText("");
        this.errorLabel.setText("");
    }
    @Override
    public void destroy() {

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
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (password.equals(confirmPassword)) {
            String login = loginField.getText();
            String fullname = fullnameField.getText();
            String email = emailField.getText();

            UserAccount newAccount = UserAccount
                    .builder()
                    .login(login)
                    .password(password)
                    .name(fullname)
                    .email(email)
                    .build();

            UserAccountService service = UserAccountService.getInstance();
            boolean success = service.registration(newAccount);
            if (success) {
                destroy();
                TriciyenApplication.setGlobalScene(MainScene.getInstance());
            } else {
                errorLabel.setText(localStorage.getInterfaceErrorMessage());
                System.err.println(localStorage.getInternalErrorMessage());
                localStorage.closeError();
            }
        } else {
            errorLabel.setText("Please, do correct password confirmation.");
        }
    }
    private void backEvent() {
        destroy();
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
}
