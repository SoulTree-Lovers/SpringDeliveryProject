package org.delivery.common.api

import jakarta.validation.Valid
import org.delivery.common.error.ErrorCodeInterface


data class Api<T> (
    var result: Result?=null,

    @field:Valid
    var body: T?=null,
){

    companion object {

        @JvmStatic // 자바에서 Companion을 사용하지 않고 접근할 수 있도록 설정
        fun <T> OK(body: T?) : Api<T> {
            return Api(
                result = Result.OK(),
                body = body
            )
        }

        @JvmStatic
        fun <T> ERROR(result: Result) : Api<T> {
            return Api(
                result = result,
            )
        }

        @JvmStatic
        fun <T> ERROR(errorCodeInterface: ErrorCodeInterface) : Api<T> {
            return Api(
                result = Result.ERROR(errorCodeInterface),
            )
        }

        @JvmStatic
        fun <T> ERROR(errorCodeInterface: ErrorCodeInterface, throwable: Throwable) : Api<T> {
            return Api(
                result = Result.ERROR(errorCodeInterface, throwable),
            )
        }

        @JvmStatic
        fun <T> ERROR(errorCodeInterface: ErrorCodeInterface, message: String) : Api<T> {
            return Api(
                result = Result.ERROR(errorCodeInterface, message),
            )
        }

    }

}