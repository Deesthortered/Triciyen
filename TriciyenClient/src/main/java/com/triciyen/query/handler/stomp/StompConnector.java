package com.triciyen.query.handler.stomp;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StompConnector {
    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    private StompSession stompSession;

    public StompConnector() {
    }

    public void connect(String host, String port, String endPoint, StompSessionHandlerAdapter eventHandler) throws ExecutionException, InterruptedException {
        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = "ws://{host}:{port}" + endPoint;
        ListenableFuture<StompSession> listener = stompClient.connect(url, headers, eventHandler, host, port);
        stompSession = listener.get();
    }

    public void subscribe(String broker, StompFrameHandler eventHandler) {
        System.out.println(stompSession.subscribe(broker, eventHandler));
    }

    public void send(String endPoint, String jsonData) {
        System.out.println(stompSession.send(endPoint, jsonData.getBytes()));
    }
}
