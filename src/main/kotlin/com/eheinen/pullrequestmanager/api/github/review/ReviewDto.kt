package com.eheinen.pullrequestmanager.api.github.review


import com.eheinen.pullrequestmanager.api.github.user.UserDto
import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("body") val body: String,
    @SerializedName("commit_id") val commitId: String,
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("state") val state: String,
    @SerializedName("submitted_at") val submittedAt: String,
    @SerializedName("user") val user: UserDto
)
