package com.eheinen.pullrequestmanager.api.github

import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestDto
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryDto
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryWrapperDto
import com.eheinen.pullrequestmanager.api.github.review.ReviewDto
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

private const val ACCEPT_HEADER = "application/vnd.github.v3+json"

interface GithubApi {

    @RequestMapping(method = [RequestMethod.GET], path = ["/search/repositories"])
    fun searchRepositories(
        @RequestHeader(value = "Accept") acceptedHeader: String = ACCEPT_HEADER,
        @RequestHeader(value = "Authorization") authorization: String,
        @RequestParam(value = "q") query: String
    ): RepositoryWrapperDto

    @RequestMapping(method = [RequestMethod.GET], path = ["/repositories/{repositoryId}"])
    fun getRepository(
        @RequestHeader(value = "Accept") acceptedHeader: String = ACCEPT_HEADER,
        @RequestHeader(value = "Authorization") authorization: String,
        @PathVariable(value = "repositoryId") repositoryId: String
    ): RepositoryDto

    @RequestMapping(method = [RequestMethod.GET], path = ["/repositories/{repositoryId}/pulls"])
    fun getPullRequests(
        @RequestHeader(value = "Accept") acceptedHeader: String = ACCEPT_HEADER,
        @RequestHeader(value = "Authorization") authorization: String,
        @PathVariable(value = "repositoryId") repositoryId: String,
        @RequestParam(value = "state", required = false) state: String,
        @RequestParam(value = "direction", required = false) direction: String
    ): List<PullRequestDto>

    @RequestMapping(method = [RequestMethod.GET], path = ["/repositories/{repositoryId}/pulls/{pullRequestNumber}"])
    fun getPullRequest(
        @RequestHeader(value = "Accept") acceptedHeader: String = ACCEPT_HEADER,
        @RequestHeader(value = "Authorization") authorization: String,
        @PathVariable(value = "repositoryId") repositoryId: String,
        @PathVariable(value = "pullRequestNumber") pullRequestNumber: String
    ): PullRequestDto

    @RequestMapping(method = [RequestMethod.GET], path = ["/repositories/{repositoryId}/pulls/{pullRequestNumber}/reviews"])
    fun getReviews(
        @RequestHeader(value = "Accept") acceptedHeader: String = ACCEPT_HEADER,
        @RequestHeader(value = "Authorization") authorization: String,
        @PathVariable(value = "repositoryId") repositoryId: String,
        @PathVariable(value = "pullRequestNumber") pullRequestNumber: String
    ): List<ReviewDto>
}
