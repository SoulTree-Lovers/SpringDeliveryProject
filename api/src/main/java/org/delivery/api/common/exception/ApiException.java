package org.delivery.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.delivery.api.common.error.ErrorCodeInterface;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionInterface{

    private final ErrorCodeInterface errorCodeInterface;
    private final String errorDescription;

    public ApiException(ErrorCodeInterface errorCodeInterface) {
        super(errorCodeInterface.getDescription());
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, String errorDescription) {
        super(errorDescription);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, Throwable throwable) {
        super(throwable);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, Throwable throwable, String errorDescription) {
        super(throwable);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;
    }




}
