package com.eheinen.pullrequestmanager.api.errors

import com.eheinen.pullrequestmanager.api.github.pullrequest.PullRequestNotFoundException
import com.eheinen.pullrequestmanager.api.github.repository.RepositoryNotFoundException
import com.eheinen.pullrequestmanager.api.github.review.ReviewNotFoundException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import feign.FeignException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletResponse


@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [RepositoryNotFoundException::class, PullRequestNotFoundException::class, ReviewNotFoundException::class])
    fun handleNotFoundException(ex: Exception): ResponseEntity<ErrorInfo> {
        return ResponseEntity(
            ErrorInfo(ex.message!!, ApiErrors.NOT_FOUND.code),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(value = [NumberFormatException::class])
    fun handleBadRequestException(ex: Exception): ResponseEntity<ErrorInfo> {
        return buildResponseEntity(ApiErrors.BAD_REQUEST, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignStatusException(ex: FeignException, response: HttpServletResponse): ResponseEntity<ErrorInfo> {
        return when (ex.status()) {
            HttpStatus.BAD_REQUEST.value() -> buildResponseEntity(ApiErrors.BAD_REQUEST, HttpStatus.BAD_REQUEST)
            HttpStatus.FORBIDDEN.value() -> buildResponseEntity(ApiErrors.FORBIDDEN, HttpStatus.FORBIDDEN)
            HttpStatus.NOT_FOUND.value() -> buildResponseEntity(ApiErrors.NOT_FOUND, HttpStatus.NOT_FOUND)
            HttpStatus.UNAUTHORIZED.value() -> buildResponseEntity(ApiErrors.UNAUTHORIZED, HttpStatus.UNAUTHORIZED)
            else -> buildResponseEntity(ApiErrors.DOWNSTREAM_SERVICE_ERROR, HttpStatus.BAD_GATEWAY)
        }
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(ex: Exception): ResponseEntity<ErrorInfo> {
        return buildResponseEntity(ApiErrors.UNKNOWN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    override fun handleMissingServletRequestParameter(
        exception: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ) = ResponseEntity<Any>(
        ErrorInfo(
            message = exception.message,
            code = ApiErrors.BAD_REQUEST.code
        ),
        HttpStatus.BAD_REQUEST
    )

    override fun handleMethodArgumentNotValid(
        e: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorMessage = e.bindingResult.fieldErrors.joinToString(prefix = "[", postfix = "]", separator = ", ") {
            "'${it.field}' ${it.defaultMessage}."
        }

        return ResponseEntity(
            ErrorInfo(errorMessage, ApiErrors.BAD_REQUEST.code),
            HttpStatus.BAD_REQUEST
        )
    }

    override fun handleHttpMessageNotReadable(
        exception: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        var errorMessage = "The request is invalid."
        val mostSpecificCause: Throwable = exception.mostSpecificCause

        if (mostSpecificCause is InvalidFormatException) {
            if (!mostSpecificCause.path.isNullOrEmpty()) {
                val field = mostSpecificCause.path[0].fieldName
                val value = mostSpecificCause.value
                errorMessage = "[The field '$field' has invalid value: '$value'.]"
            }
        }

        return ResponseEntity(
            ErrorInfo(errorMessage, ApiErrors.BAD_REQUEST.code),
            HttpStatus.BAD_REQUEST
        )
    }

    private fun buildResponseEntity(
        apiErrors: ApiErrors,
        httpStatus: HttpStatus
    ): ResponseEntity<ErrorInfo> {
        val errorInfo = ErrorInfo(code = apiErrors.code, message = apiErrors.message)
        return ResponseEntity(errorInfo, httpStatus)
    }
}
