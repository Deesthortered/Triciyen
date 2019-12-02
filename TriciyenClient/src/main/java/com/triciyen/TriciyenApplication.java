package com.triciyen;

import com.triciyen.scenes.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class TriciyenApplication extends Application {
    private LoginScene loginScene = LoginScene.getInstance();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(loginScene.getScene());
        stage.setTitle("Triciyen Application");
        stage.show();
    }
}
