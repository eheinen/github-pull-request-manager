package com.eheinen.pullrequestmanager.api.github.pullrequest

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@RedisHash("pullrequest")
@EnableRedisRepositories
data class PullRequestCache(

    @Id val key: String,
    val pullRequestDto: PullRequestDto
)
