package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import com.triciyen.entity.UserAccount;
import com.triciyen.service.UserAccountService;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.regex.Pattern;


public class RegistrationScene implements BaseScene {
    private static final RegistrationScene instance = new RegistrationScene();
    private static Scene scene;

    private int sceneWidth = 500;
    private int sceneHeight = 350;

    private TextField loginField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private TextField fullnameField;
    private TextField emailField;

    private Button submitButton;
    private Button backButton;

    private Label errorLabel;

    private RegistrationScene() {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,0,20,10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Login");
        loginField = new TextField();
        loginField.setMinWidth(200);
        Label lblPassword = new Label("Password");
        passwordField = new PasswordField();
        Label lblConfirmPassword = new Label("Confirm password");
        confirmPasswordField = new PasswordField();
        Label lblFullname = new Label("Fullname");
        fullnameField = new TextField();
        Label lblEmail = new Label("Email");
        emailField = new TextField();
        submitButton = new Button("Submit");
        submitButton.setOnMouseClicked(this);
        backButton = new Button("Back");
        backButton.setOnMouseClicked(this);
        errorLabel = new Label("");

        // Buttons HBox
        HBox btnHBox = new HBox();
        btnHBox.setSpacing(5.);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);
        btnHBox.getChildren().addAll(backButton, submitButton);

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(loginField, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(lblConfirmPassword, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(lblFullname, 0, 3);
        gridPane.add(fullnameField, 1, 3);
        gridPane.add(lblEmail, 0, 4);
        gridPane.add(emailField, 1, 4);
        gridPane.add(btnHBox, 1, 5);
        gridPane.add(errorLabel, 1, 6);

        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("New account...");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        submitButton.setId("btnSubmit");
        backButton.setId("btnBack");
        text.setId("text");

        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        scene = new Scene(bp, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/login_registration.css").toExternalForm());
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

        // Login validation
        String login = loginField.getText();

        if (login.length() < 4 || login.length() > 20) {
            errorLabel.setText("Login must contain 4-20 characters");
            return;
        }

        // Fullname validation
        String fullname = fullnameField.getText();

        if (fullname.length() < 1) {
            errorLabel.setText("Fullname must contain at least 1 character");
            return;
        }

        // Email validation
        String email = emailField.getText();
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(email).matches()) {
            errorLabel.setText("Please, enter the correct email");
            return;
        }

        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (password.equals(confirmPassword)) {
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
