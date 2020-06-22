package com.eheinen.pullrequestmanager.api.errors

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorInfo(val message: String = "", val code: String = "")
