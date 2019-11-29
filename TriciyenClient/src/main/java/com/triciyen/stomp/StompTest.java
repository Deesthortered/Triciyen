package com.triciyen.stomp;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class StompTest {

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

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        StompConnector connector = new StompConnector();

        connector.connect(host, port, stompEndPoint1, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Connected!");
            }
        });

        connector.subscribe(broker1 + topic1, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("Received greeting " + new String((byte[]) o));
            }
        });

        connector.send(brokerPrefix + stompEndPoint1, "{ \"name\" : \"dude!\"}");

        Thread.sleep(5000);
    }
}
