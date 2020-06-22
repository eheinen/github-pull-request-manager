package com.eheinen.pullrequestmanager.api.github.repository

import org.springframework.stereotype.Component

@Component
class RepositoryMapper {

    fun mapToCache(repositoryDto: RepositoryDto): RepositoryCache {
        return RepositoryCache(key = repositoryDto.id.toString(), repositoryDto = repositoryDto)
    }

    fun mapToCache(repositoriesDto: List<RepositoryDto>): List<RepositoryCache> {
        return repositoriesDto.map { repositoryDto ->
            RepositoryCache(key = repositoryDto.id.toString(), repositoryDto = repositoryDto)
        }
    }
}
