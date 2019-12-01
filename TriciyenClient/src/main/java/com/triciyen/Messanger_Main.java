package com.triciyen;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

import com.triciyen.view.Messanger_Panel;

public class Messanger_Main extends Application {

    public static Messanger_Panel messangerPanel = new Messanger_Panel();

    @Override
    public void start(Stage primaryStage) throws IOException {

        Messanger_Panel messangerPanel = new Messanger_Panel();

        messangerPanel.loginStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
