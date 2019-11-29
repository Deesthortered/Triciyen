package root.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    private static final String broker1 = "/broker1";
    private static final String broker2 = "/broker2";
    private static final String broker3 = "/broker3";
    private static final String brokerPrefix = "/app_pref";
    private static final String stompEndPoint1 = "/endpoint1";
    private static final String stompEndPoint2 = "/endpoint2";
    private static final String stompEndPoint3 = "/endpoint3";


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(broker1);
        config.enableSimpleBroker(broker2);
        config.enableSimpleBroker(broker3);
        config.setApplicationDestinationPrefixes(brokerPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(stompEndPoint1).withSockJS();
        registry.addEndpoint(stompEndPoint2).withSockJS();
        registry.addEndpoint(stompEndPoint3).withSockJS();
    }
}