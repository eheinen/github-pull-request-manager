package com.eheinen.pullrequestmanager.api.github.pullrequest

import com.eheinen.pullrequestmanager.api.github.GithubApiService
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestDto
import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestState
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PullRequestController(private val githubApiService: GithubApiService) {

    @GetMapping("/api/1/repositories/{repositoryId}/pulls")
    fun findPullRequests(
        @PathVariable(value = "repositoryId") repositoryId: String,
        @RequestParam(value = "state", required = false) state: PullRequestState?,
        @RequestParam(value = "direction", required = false) direction: String?
    ): List<PullRequestDto> {
        return githubApiService.findPullRequests(repositoryId, state, direction)
    }

    @GetMapping("/api/1/repositories/{repositoryId}/pulls/{pullRequestNumber}")
    fun findPullRequests(
        @PathVariable(value = "repositoryId") repositoryId: String,
        @PathVariable(value = "pullRequestNumber") pullRequestNumber: String
    ): PullRequestDto {
        return githubApiService.findPullRequest(repositoryId, pullRequestNumber)
    }
}
