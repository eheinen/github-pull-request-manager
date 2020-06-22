package com.eheinen.pullrequestmanager.api.github.review

import org.springframework.data.repository.CrudRepository

interface ReviewRepository : CrudRepository<ReviewCache, String>
