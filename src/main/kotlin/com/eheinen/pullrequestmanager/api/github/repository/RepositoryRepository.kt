package com.eheinen.pullrequestmanager.api.github.repository

import org.springframework.data.repository.CrudRepository

interface RepositoryRepository : CrudRepository<RepositoryCache, String>
