package com.triciyen.scenes;

import com.triciyen.TriciyenApplication;
import com.triciyen.service.UserAccountService;
import javafx.event.Event;
import javafx.geometry.Insets;
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

public class LoginScene implements BaseScene {
    private static final LoginScene instance = new LoginScene();
    private static Scene scene;

    private int sceneWidth = 500;
    private int sceneHeight = 300;

    private Button loginButton;
    private Button registrationButton;

    private TextField loginField;
    private PasswordField passwordField;

    private Label errorLabel;

    private LoginScene() {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        loginField = new TextField();
        Label lblPassword = new Label("Password");
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        loginButton.setOnMouseClicked(this);
        registrationButton = new Button("Registration");
        registrationButton.setOnMouseClicked(this);
        errorLabel = new Label("");

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(loginField, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 2, 0);
        gridPane.add(registrationButton, 2, 1);
        gridPane.add(errorLabel, 1, 2);

        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("Wellcome to Triciyen!");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        loginButton.setId("btnLogin");
        registrationButton.setId("btnRegistration");
        text.setId("text");

        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        scene = new Scene(bp, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/login_registration.css").toExternalForm());
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
            destroy();
            TriciyenApplication.setGlobalScene(MainScene.getInstance());
        } else {
            errorMessage();
        }
    }
    private void registrationEvent() {
        destroy();
        TriciyenApplication.setGlobalScene(RegistrationScene.getInstance());
    }
    private void errorMessage() {
        errorLabel.setText(localStorage.getInterfaceErrorMessage());
        System.err.println(localStorage.getInternalErrorMessage());
        localStorage.closeError();
    }
}
