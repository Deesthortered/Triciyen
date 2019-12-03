package com.triciyen;

import com.triciyen.scenes.BaseScene;
import com.triciyen.scenes.LoginScene;
import com.triciyen.service.StateService;
import javafx.application.Application;
import javafx.stage.Stage;

public class TriciyenApplication extends Application {
    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }
    public static void setGlobalScene(BaseScene scene) {
        mainStage.setScene(scene.getScene());
        scene.initialize();
        mainStage.show();
    }
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Triciyen Application");
        mainStage.setResizable(false);
        StateService.getInstance().setDefaultState();
        TriciyenApplication.setGlobalScene(LoginScene.getInstance());
    }
}
