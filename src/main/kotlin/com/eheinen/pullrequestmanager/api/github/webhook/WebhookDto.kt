package com.eheinen.pullrequestmanager.api.github.webhook


import com.google.gson.annotations.SerializedName

data class WebhookDto(
    @SerializedName("after") val after: String,
    @SerializedName("base_ref") val baseRef: Any?,
    @SerializedName("before") val before: String,
    @SerializedName("commits") val commits: List<Any>,
    @SerializedName("compare") val compare: String,
    @SerializedName("created") val created: Boolean,
    @SerializedName("deleted") val deleted: Boolean,
    @SerializedName("forced") val forced: Boolean,
    @SerializedName("head_commit") val headCommit: Any?,
    @SerializedName("pusher") val pusher: PusherDto,
    @SerializedName("ref") val ref: String,
    @SerializedName("repository") val repository: RepositoryDto,
    @SerializedName("sender") val sender: SenderDto
)
