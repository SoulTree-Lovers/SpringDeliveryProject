package org.delivery.common.exception

import jdk.jfr.Description
import org.delivery.common.error.ErrorCodeInterface

class ApiException : RuntimeException, ApiExceptionInterface {

    override val errorCodeInterface: ErrorCodeInterface?

    override val errorDescription: String?


    constructor(errorCodeInterface: ErrorCodeInterface) : super(errorCodeInterface.getDescription()) {
        this.errorCodeInterface = errorCodeInterface
        this.errorDescription = errorCodeInterface.getDescription()
    }

    constructor(
        errorCodeInterface: ErrorCodeInterface,
        errorDescription: String
    ) : super(errorCodeInterface.getDescription()) {
        this.errorCodeInterface = errorCodeInterface
        this.errorDescription = errorDescription
    }

    constructor(
        errorCodeInterface: ErrorCodeInterface,
        throwable: Throwable
    ) : super(throwable) {
        this.errorCodeInterface = errorCodeInterface
        this.errorDescription = errorCodeInterface.getDescription()
    }

    constructor(
        errorCodeInterface: ErrorCodeInterface,
        errorDescription: String,
        throwable: Throwable
    ) : super(throwable) {
        this.errorCodeInterface = errorCodeInterface
        this.errorDescription = errorDescription
    }




}