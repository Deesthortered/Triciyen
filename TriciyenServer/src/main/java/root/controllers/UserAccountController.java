package root.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import root.entity.Greeting;
import root.entity.HelloMessage;

@Controller
public class UserAccountController {
    private static final String stompEndPoint1 = "/endpoint1";
    private static final String stompEndPoint2 = "/endpoint2";
    private static final String stompEndPoint3 = "/endpoint3";
    private static final String broker1 = "/broker1";
    private static final String broker2 = "/broker2";
    private static final String broker3 = "/broker3";
    private static final String topic1 = "/topic1";
    private static final String topic2 = "/topic2";
    private static final String topic3 = "/topic3";

    @MessageMapping(stompEndPoint1)
    @SendTo(broker1 + topic1)
    public Greeting greeting1(HelloMessage message) throws Exception {
        System.out.println("Sending Greeting1");
        Thread.sleep(1000);
        return new Greeting("Hello1, " + message.getName() + "!");
    }

    @MessageMapping(stompEndPoint2)
    @SendTo(broker2 + topic2)
    public Greeting greeting2(HelloMessage message) throws Exception {
        System.out.println("Sending Greeting2");
        Thread.sleep(1000);
        return new Greeting("Hello2, " + message.getName() + "!");
    }

    @MessageMapping(stompEndPoint3)
    @SendTo(broker3 + topic3)
    public Greeting greeting3(HelloMessage message) throws Exception {
        System.out.println("Sending Greeting3");
        Thread.sleep(1000);
        return new Greeting("Hello3, " + message.getName() + "!");
    }
}