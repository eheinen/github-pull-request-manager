package com.eheinen.pullrequestmanager.api.github.repository

import com.eheinen.pullrequestmanager.api.github.GithubApiService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RepositoryController(private val githubApiService: GithubApiService) {

    @GetMapping("/api/1/repositories")
    fun searchRepositories(
        @RequestParam(value = "repositoryName", required = true) repositoryName: String
    ): List<RepositoryDto> {
        return githubApiService.searchRepositories(repositoryName)
    }

    @GetMapping("/api/1/repositories/{repositoryId}")
    fun findRepository(
        @PathVariable(value = "repositoryId") repositoryId: String
    ): RepositoryDto {
        return githubApiService.findRepository(repositoryId)
    }
}
