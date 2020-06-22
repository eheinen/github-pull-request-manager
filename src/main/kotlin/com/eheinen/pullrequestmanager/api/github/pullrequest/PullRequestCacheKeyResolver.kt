package com.eheinen.pullrequestmanager.api.github.pullrequest

import org.springframework.stereotype.Component

@Component
class PullRequestCacheKeyResolver {

    fun resolve(repositoryId: String, pullRequestNumber: String): String {
        return "${repositoryId}_${pullRequestNumber}"
    }
}
