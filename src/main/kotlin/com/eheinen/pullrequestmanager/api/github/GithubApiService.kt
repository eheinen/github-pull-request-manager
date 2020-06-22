package com.eheinen.pullrequestmanager.api.github

import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestCache
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestCacheKeyResolver
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestDto
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestMapper
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestNotFoundException
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestRepository
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestState
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryCache
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryDto
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryMapper
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryNotFoundException
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryRepository
import com.eheinen.pullrequestmanager.api.github.review.ReviewCache
import com.eheinen.pullrequestmanager.api.github.review.ReviewDto
import com.eheinen.pullrequestmanager.api.github.review.ReviewMapper
import com.eheinen.pullrequestmanager.api.github.review.ReviewNotFoundException
import com.eheinen.pullrequestmanager.api.github.review.ReviewRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GithubApiService(
    private val githubApiClient: GithubApi,
    private val repositoryRepository: RepositoryRepository,
    private val repositoryMapper: RepositoryMapper,
    private val pullRequestRepository: PullRequestRepository,
    private val pullRequestMapper: PullRequestMapper,
    private val pullRequestCacheKeyResolver: PullRequestCacheKeyResolver,
    private val reviewRepository: ReviewRepository,
    private val reviewMapper: ReviewMapper,
    @Value("\${api.github.token}") private val githubApiToken: String
) {
    fun searchRepositories(repositoryName: String): List<RepositoryDto> {
        val repositoryWrapperDto = githubApiClient.searchRepositories(authorization = githubApiToken, query = repositoryName)

        if (repositoryWrapperDto.repositories == null || repositoryWrapperDto.repositories.isEmpty()) {
            throw RepositoryNotFoundException()
        }

        val repositoriesCache = repositoryMapper.mapToCache(repositoryWrapperDto.repositories)

        repositoryRepository.saveAll(repositoriesCache)

        return repositoryWrapperDto.repositories
    }

    fun findRepository(repositoryId: String): RepositoryDto {
        return repositoryRepository.findById(repositoryId).map(RepositoryCache::repositoryDto).orElseGet {
            val repositoryDto = githubApiClient.getRepository(authorization = githubApiToken, repositoryId = repositoryId)
            val repositoryCache = repositoryMapper.mapToCache(repositoryDto)
            repositoryRepository.save(repositoryCache).repositoryDto
        }
    }

    fun findPullRequests(repositoryId: String, state: PullRequestState?, direction: String?): List<PullRequestDto> {
        val stateOrDefault = state ?: PullRequestState.OPEN
        val directionOrDefault = direction ?: "desc"

        val pullRequestsDto = githubApiClient.getPullRequests(
            authorization = githubApiToken,
            repositoryId = repositoryId,
            state = stateOrDefault.name,
            direction = directionOrDefault
        )

        if (pullRequestsDto.isEmpty()) {
            throw PullRequestNotFoundException()
        }

        val pullRequestCache = pullRequestMapper.mapToCache(repositoryId, pullRequestsDto)

        pullRequestRepository.saveAll(pullRequestCache)

        return pullRequestsDto
    }

    fun findPullRequest(repositoryId: String, pullRequestNumber: String): PullRequestDto {
        val cacheKey = pullRequestCacheKeyResolver.resolve(repositoryId, pullRequestNumber)

        return pullRequestRepository.findById(cacheKey).map(PullRequestCache::pullRequestDto).orElseGet {
            val pullRequestDto = githubApiClient.getPullRequest(
                authorization = githubApiToken,
                repositoryId = repositoryId,
                pullRequestNumber = pullRequestNumber
            )
            val pullRequestCache = pullRequestMapper.mapToCache(repositoryId, pullRequestDto)
            pullRequestRepository.save(pullRequestCache).pullRequestDto
        }
    }

    fun findReviews(repositoryId: String, pullRequestNumber: String): List<ReviewDto> {
        val cacheKey = pullRequestCacheKeyResolver.resolve(repositoryId, pullRequestNumber)

        return reviewRepository.findById(cacheKey).map(ReviewCache::reviewsDto)
            .orElseGet { getReviewsFromExternalService(repositoryId, pullRequestNumber, cacheKey) }
    }

    private fun getReviewsFromExternalService(repositoryId: String, pullRequestNumber: String, cacheKey: String): List<ReviewDto> {
        val reviewsDto = githubApiClient.getReviews(
            authorization = githubApiToken,
            repositoryId = repositoryId,
            pullRequestNumber = pullRequestNumber
        )

        if (reviewsDto.isEmpty()) {
            throw ReviewNotFoundException()
        }

        val reviewsCache = reviewMapper.mapToCache(cacheKey, reviewsDto)
        reviewRepository.save(reviewsCache)

        return reviewsCache.reviewsDto
    }
}

// Webhook no git
