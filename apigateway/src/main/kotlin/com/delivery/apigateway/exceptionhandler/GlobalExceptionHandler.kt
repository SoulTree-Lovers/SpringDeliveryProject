package com.delivery.apigateway.exceptionhandler

import com.delivery.apigateway.common.Log
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalExceptionHandler(
    val objectMapper: ObjectMapper
): ErrorWebExceptionHandler {

    data class ErrorResponse(val error: String)
    companion object: Log

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        log.error("Global error exception url: {}", exchange.request.uri, ex)

        val response = exchange.response
        if (response.isCommitted) {
            return Mono.error(ex)
        }

        // 클라이언트로 전달이 제대로 되지 않은 경우
        response.headers.contentType = MediaType.APPLICATION_JSON
        val errorResponseByteArray = ErrorResponse(
            error = ex.localizedMessage
        ).run {
            objectMapper.writeValueAsBytes(this)
        }

        val dataBuffer = response.bufferFactory()

        return response.writeWith(
            Mono.fromSupplier {
                dataBuffer.wrap(errorResponseByteArray)
            }
        )
    }
}