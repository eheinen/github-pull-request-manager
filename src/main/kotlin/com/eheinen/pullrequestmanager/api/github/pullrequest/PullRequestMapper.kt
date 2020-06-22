package com.eheinen.pullrequestmanager.api.github.pullrequest

import org.springframework.stereotype.Component

@Component
class PullRequestMapper(
    private val pullRequestCacheKeyResolver: PullRequestCacheKeyResolver
) {

    fun mapToCache(repositoryId: String, pullRequestsDto: List<PullRequestDto>): List<PullRequestCache> {
        return pullRequestsDto.map { pullRequestDto ->
            val cacheKey = pullRequestCacheKeyResolver.resolve(repositoryId, pullRequestDto.id.toString())
            PullRequestCache(key = cacheKey, pullRequestDto = pullRequestDto)
        }
    }

    fun mapToCache(repositoryId: String, pullRequestDto: PullRequestDto): PullRequestCache {
        val cacheKey = pullRequestCacheKeyResolver.resolve(repositoryId, pullRequestDto.id.toString())
        return PullRequestCache(key = cacheKey, pullRequestDto = pullRequestDto)
    }
}
