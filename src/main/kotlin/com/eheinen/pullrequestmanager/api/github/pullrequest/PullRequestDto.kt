package com.eheinen.pullrequestmanager.api.github.pullrequest

import com.eheinen.pullrequestmanager.api.github.user.UserDto
import com.google.gson.annotations.SerializedName

data class PullRequestDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("issue_url") val issueUrl: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("body") val body: String = "",
    @SerializedName("number") val number: Int = 0,
    @SerializedName("updated_at") val updatedAt: String = "",
    @SerializedName("draft") val draft: Boolean = false,
    @SerializedName("state") val state: String = "",
    @SerializedName("closed_at") val closedAt: String = "",
    @SerializedName("merged_at") val mergedAt: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("labels") val labels: List<LabelsItemDto>?,
    @SerializedName("user") val user: UserDto,
    @SerializedName("node_id") val nodeId: String = ""
)
