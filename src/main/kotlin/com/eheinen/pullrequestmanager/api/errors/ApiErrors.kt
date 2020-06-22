package com.eheinen.pullrequestmanager.api.errors

enum class ApiErrors(val code: String, val message: String) {
    BAD_REQUEST("bad-request", "Request is invalid."),
    DOWNSTREAM_SERVICE_ERROR("downstream-service-error", "Downstream service error."),
    FORBIDDEN("forbidden", "Access denied."),
    NOT_FOUND("not-found", "Resource not found."),
    UNAUTHORIZED("unauthorized-error", "Invalid Credentials."),
    UNKNOWN_ERROR("unknown-error", "An unknown occurred."),
    UPSTREAM_SERVICE_ERROR("upstream-service-error", "Upstream service error.")
}
