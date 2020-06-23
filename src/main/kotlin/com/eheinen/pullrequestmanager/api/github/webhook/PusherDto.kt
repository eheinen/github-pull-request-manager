package com.eheinen.pullrequestmanager.api.github.webhook


import com.google.gson.annotations.SerializedName

data class PusherDto(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String
)
