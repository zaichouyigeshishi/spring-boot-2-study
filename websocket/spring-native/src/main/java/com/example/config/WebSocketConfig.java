package com.example.config;

import com.example.interceptor.WebSocketInterceptor;
import com.example.service.PushWebSocketService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * web socket 配置
 * @author hudongshan
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry.addHandler(new PushWebSocketService(),"/websocket/{ID}")
                .setAllowedOrigins("*")
                .addInterceptors(new WebSocketInterceptor());
    }
}
