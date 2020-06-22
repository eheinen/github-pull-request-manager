package com.eheinen.pullrequestmanager.api.github.review

import com.eheinen.pullrequestmanager.api.github.GithubApiService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewController(private val githubApiService: GithubApiService) {

    @GetMapping("/api/1/repositories/{repositoryId}/pulls/{pullRequestId}/reviews")
    fun findReviews(
        @PathVariable(value = "repositoryId") repositoryId: String,
        @PathVariable(value = "pullRequestId") pullRequestId: String
    ): List<ReviewDto> {
        return githubApiService.findReviews(repositoryId, pullRequestId)
    }
}
