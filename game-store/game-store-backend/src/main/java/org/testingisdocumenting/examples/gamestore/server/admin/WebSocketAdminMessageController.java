package org.testingisdocumenting.examples.gamestore.server.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketAdminMessageController {
    private static final Logger log = LoggerFactory.getLogger(WebSocketAdminMessageController.class);

    @MessageMapping("/admin-message")
    @SendTo("/visitors/message")
    public AdminMessage broadcastMessage(AdminMessage message) {
        log.info("admin message received: " + message.getMessage());
        return message;
    }
}
