package com.eheinen.pullrequestmanager.api.github.repository

import com.google.gson.annotations.SerializedName

data class RepositoryDto(

    @SerializedName("id") val id: Int? = null,
    @SerializedName("node_id") val nodeId: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("open_issues_count") val openIssuesCount: Int? = null,
    @SerializedName("open_issues") val openIssues: Int? = null,
    @SerializedName("default_branch") val defaultBranch: String? = null
)
