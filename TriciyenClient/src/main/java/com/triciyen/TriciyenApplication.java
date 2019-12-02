package com.triciyen;

import com.triciyen.stomp.StompConnector;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class TriciyenApplication extends Application implements EventHandler<Event> {
    private static final String host = "localhost";
    private static final String port = "8080";
    private static final String stompEndPoint1 = "/endpoint1";
    private static final String broker1 = "/broker1";
    private static final String topic1 = "/topic1";
    private static final String brokerPrefix = "/app_pref";

    private StompConnector connector1 = new StompConnector();

    private Button button1;

    @Override
    public void start(Stage stage) {
        button1 = new Button("Button1 caption");

        button1.setOnMouseClicked(this);

        Pane pane = new VBox();
        pane.getChildren().addAll(button1);
        Scene scene = new Scene(pane, 300, 300);
        stage.setScene(scene);
        stage.setTitle("JavaFX and Gradle");
        stage.show();

        try {
            connectToServer();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == button1) {
            System.out.println("Button1 pressed");
            connector1.send(brokerPrefix + stompEndPoint1, "{ \"name\" : \"dude1!\"}");
        } else {
            System.out.println("Another event");
        }
    }

    private void connectToServer() throws ExecutionException, InterruptedException {

        connector1.connect(host, port, stompEndPoint1, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Connected1!");
            }
        });

        connector1.subscribe(broker1 + topic1, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("1 Received greeting " + new String((byte[]) o));
            }
        });
    }


    private Scene getLoginPage() {
        StackPane mainPane = new StackPane();
        Scene result = new Scene(mainPane, );
        return result;
    }
}
