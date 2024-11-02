package dev.flab.studytogether.config;

import dev.flab.studytogether.websocket.CustomHandShakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // "stomp-room" 는 웹소캣 또는 SocketJS Client가 웹소캣 핸드셰이크 커넥션을 생성할 경로이다.
        registry.addEndpoint("/stomp-room")
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();

        registry.addEndpoint("/study-group")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new CustomHandShakeHandler())
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
           config.enableSimpleBroker("/subscribe");
           config.setApplicationDestinationPrefixes("/publish");
    }


}
