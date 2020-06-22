package com.eheinen.pullrequestmanager.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate


@Configuration
class RedisConfiguration {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration("localhost", 6379))
    }

    @Bean
    fun redisTemplate(): RedisTemplate<ByteArray, ByteArray> {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.setConnectionFactory(redisConnectionFactory())

        return template
    }
}
