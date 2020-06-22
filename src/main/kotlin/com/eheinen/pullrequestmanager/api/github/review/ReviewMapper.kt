package com.eheinen.pullrequestmanager.api.github.review

import org.springframework.stereotype.Component

@Component
class ReviewMapper {

    fun mapToCache(cacheKey: String, reviewsDto: List<ReviewDto>): ReviewCache {
        return ReviewCache(key = cacheKey, reviewsDto = reviewsDto)
    }
}
