package com.triciyen;

import com.triciyen.scenes.LoginScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TriciyenApplication extends Application {
    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }
    public static void setGlobalScene(Scene scene) {
        mainStage.setScene(scene);
        mainStage.show();
    }
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Triciyen Application");
        TriciyenApplication.setGlobalScene(LoginScene.getInstance().getScene());
    }
}
