package com.triciyen;

import com.triciyen.stomp.StompConnector;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
    private static final String stompEndPoint2 = "/endpoint2";
    private static final String stompEndPoint3 = "/endpoint3";
    private static final String broker1 = "/broker1";
    private static final String broker2 = "/broker2";
    private static final String broker3 = "/broker3";
    private static final String topic1 = "/topic1";
    private static final String topic2 = "/topic2";
    private static final String topic3 = "/topic3";
    private static final String brokerPrefix = "/app_pref";

    private StompConnector connector1 = new StompConnector();
    private StompConnector connector2 = new StompConnector();
    private StompConnector connector3 = new StompConnector();

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    public void start(Stage stage) {
        button1 = new Button("Button1 caption");
        button2 = new Button("Button2 caption");
        button3 = new Button("Button3 caption");

        button1.setOnMouseClicked(this);
        button2.setOnMouseClicked(this);
        button3.setOnMouseClicked(this);

        Pane pane = new VBox();
        pane.getChildren().addAll(button1, button2, button3);
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
        } else if (event.getSource() == button2) {
            System.out.println("Button2 pressed");
            connector1.send(brokerPrefix + stompEndPoint2, "{ \"name\" : \"dude2!\"}");
        } else if (event.getSource() == button3) {
            System.out.println("Button3 pressed");
            connector1.send(brokerPrefix + stompEndPoint2, "{ \"name\" : \"dude3!\"}");
        } else  {
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
        connector2.connect(host, port, stompEndPoint2, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Connected2!");
            }
        });
        connector3.connect(host, port, stompEndPoint2, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Connected3!");
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

        connector2.subscribe(broker1 + topic1, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("2 Received greeting " + new String((byte[]) o));
            }
        });

        connector1.subscribe(broker1 + topic1, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("3 Received greeting " + new String((byte[]) o));
            }
        });

    }
}

/// https://gluonhq.com/products/javafx/ JavaFX 13 version.
/// Run >> Edit Configurations, VM Options:  --module-path D:/2.Programming/Triciyen/javafx13lib/lib --add-modules=javafx.controls