package com.example.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }
    //서버 엔드 포인트와 연결

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 prefix
        registry.enableSimpleBroker("/topic");
        // 서버 -> 클라이언트 메시지 보낼 때 앞에 붙는 prefix
        registry.setApplicationDestinationPrefixes("/app");
    }
}

