package org.delivery.common.api

import org.delivery.common.error.ErrorCode
import org.delivery.common.error.ErrorCodeInterface

data class Result (
    var resultCode: Int? = null,
    var resultMessage: String? = null,
    var resultDescription: String? = null,
){

    // 자바에서 static 메소드와 동일
    companion object {

        @JvmStatic
        fun OK() : Result {
            return Result(
                resultCode = ErrorCode.OK.getErrorCode(),
                resultMessage = "OK",
                resultDescription = ErrorCode.OK.getDescription()
            )           
        }

        @JvmStatic
        fun ERROR(errorCodeInterface: ErrorCodeInterface) : Result {
            return Result(
                resultCode = errorCodeInterface.getErrorCode(),
                resultMessage = "ERROR",
                resultDescription = errorCodeInterface.getDescription()
            )
        }

        @JvmStatic
        fun ERROR(errorCodeInterface: ErrorCodeInterface, throwable: Throwable) : Result {
            return Result(
                resultCode = errorCodeInterface.getErrorCode(),
                resultMessage = throwable.localizedMessage,
                resultDescription = errorCodeInterface.getDescription()
            )
        }

        @JvmStatic
        fun ERROR(errorCodeInterface: ErrorCodeInterface, message: String) : Result {
            return Result(
                resultCode = errorCodeInterface.getErrorCode(),
                resultMessage = message,
                resultDescription = errorCodeInterface.getDescription()
            )
        }
    }

}