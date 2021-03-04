package de.dhbw.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@EnableScheduling
@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    int index = 0;

    @MessageMapping("/greeting") //beginnt mit frontend -> siehe websocketconfig! Frontend schickt dort hin
    public void greeting(HelloMessage message) {
        System.out.println("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @Scheduled(fixedRate = 5000)
    public void autoBackendAnswer() {
        this.template.convertAndSend("/backend/start", new Greeting("Hello from Backend #" + index++));
    }

}
