package com.eheinen.pullrequestmanager.api.github.pullrequest

import org.springframework.data.repository.CrudRepository

interface PullRequestRepository : CrudRepository<PullRequestCache, String>
