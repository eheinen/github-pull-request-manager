package com.eheinen.pullrequestmanager.api.github.repository

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@RedisHash("repository")
@EnableRedisRepositories
data class RepositoryCache(

    @Id val key: String,
    val repositoryDto: RepositoryDto
)
