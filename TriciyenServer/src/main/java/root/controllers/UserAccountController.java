package root.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import root.entity.Greeting;
import root.entity.HelloMessage;

@Controller
public class UserAccountController {
    private static final String stompEndPoint1 = "/endpoint1";
    private static final String broker1 = "/broker1";
    private static final String topic1 = "/topic1";

    @MessageMapping(stompEndPoint1)
    @SendTo((broker1 + topic1))
    public Greeting greeting1(HelloMessage message) throws Exception {
        System.out.println("Sending Greeting1");
        Thread.sleep(1000);
        return new Greeting("Hello1, " + message.getName() + "!");
    }
}