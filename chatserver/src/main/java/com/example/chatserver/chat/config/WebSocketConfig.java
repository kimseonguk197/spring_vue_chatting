package com.example.chatserver.chat.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // websocket stomp로 연결하는 흐름에 대한 제어를 위한 interceptor
    // JWT 인증을 위해 사용
    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // queue로 클라이언트로 메시지 전달(sub)
        config.enableSimpleBroker("/sub");
        // @Controller 객체의 @MessageMapping 메서드로 라우팅, 클라이언트가 서버로 메시지 보낼 URL 접두사(pub)
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws") //web socket connection이 최초로 이루어지는 곳(handshake)
                .setAllowedOriginPatterns("*")
                .setAllowedOrigins("http://localhost:3000", "https://test.com")
                .withSockJS();
    }

    // TCP handshake 시 JWT 인증을 위함. 처음 연결될 때 JWT를 이용해서 단 한 번 유효한 유저인가 판단한다.
    // 클라이언트로부터 들어오는 메시지를 처리하는 MessageChannel을 구성하는 역할을 한다.
    // registration.interceptors 메서드를 사용해서 STOMP 메시지 처리를 구성하는 메시지 채널에 custom한 인터셉터를 추가 구성하여
    // 채널의 현재 인터셉터 목록에 추가하는 단계를 거친다.
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
