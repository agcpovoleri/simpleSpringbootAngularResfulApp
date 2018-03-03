package com.example.forumapp.config;

import com.example.forumapp.dto.AuthenticatedUserResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, AuthenticatedUserResponse> authenticatedUserTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String, AuthenticatedUserResponse> template = new RedisTemplate<String, AuthenticatedUserResponse>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(this.jacksonAuthenticatedUserSerializer());

        return template;
    }

    @Bean
    Jackson2JsonRedisSerializer<AuthenticatedUserResponse> jacksonAuthenticatedUserSerializer() {
        return new Jackson2JsonRedisSerializer<AuthenticatedUserResponse>(AuthenticatedUserResponse.class);
    }
}