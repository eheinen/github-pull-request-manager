package com.eheinen.pullrequestmanager.api.github.repository

import com.google.gson.annotations.SerializedName

data class RepositoryWrapperDto(

    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("items") val repositories: List<RepositoryDto>? = null
)
