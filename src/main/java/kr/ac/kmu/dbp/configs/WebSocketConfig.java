package kr.ac.kmu.dbp.configs;

import kr.ac.kmu.dbp.handler.CustomWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Autowired
    public WebSocketConfig(CustomWebSocketHandler customWebSocketHandler) {
        this.webSocketHandler = customWebSocketHandler;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/chatWebSocket").setAllowedOrigins("*");
    }
}
