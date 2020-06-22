package com.eheinen.pullrequestmanager.api.github.review

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@RedisHash("review")
@EnableRedisRepositories
data class ReviewCache(

    @Id val key: String,
    val reviewsDto: List<ReviewDto>
)
