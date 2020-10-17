package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author hudongshan
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 设置广播节点。也就是服务端发送消息，客户端订阅就能接收消息的节点。
        registry.enableSimpleBroker("/topic", "/user");

        // 客户端向服务端发送消息需有/app 前缀。也就是客户端向服务端发送消息的节点。
        registry.setApplicationDestinationPrefixes("/app");

        // 指定用户发送（一对一）的前缀 /user/。设置一对一通信的节点
        registry.setUserDestinationPrefix("/user/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置客户端尝试连接地址
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }


}
