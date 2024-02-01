package org.delivery.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.delivery.common.error.ErrorCodeInterface;

/* 코틀린으로 변경
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
*/
