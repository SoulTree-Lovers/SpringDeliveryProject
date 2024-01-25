package org.delivery.api.common.api;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeInterface;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK() {
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage("OK")
                .resultDescription(ErrorCode.OK.getDescription())
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface) {
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage("ERROR")
                .resultDescription(errorCodeInterface.getDescription())
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface, Throwable throwable) {
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(throwable.getLocalizedMessage())
                .resultDescription(errorCodeInterface.getDescription())
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface, String message) {
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(message)
                .resultDescription(errorCodeInterface.getDescription())
                .build();
    }
}
