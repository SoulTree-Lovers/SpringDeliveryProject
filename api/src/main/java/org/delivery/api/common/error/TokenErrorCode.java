package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Token의 경우 2000번대 에러 코드 사용
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeInterface {
    INVALID_TOKEN(400, 2000, "유효하지 않은 토큰."),
    EXPIRED_TOKEN(400, 2001, "만료된 토큰."),
    TOKEN_EXCEPTION(400, 2002, "알 수 없는 토큰 에러 발생."),
    ;

    private final Integer httpStatusCode; // HTTP status code
    private final Integer errorCode; // 세분화된 에러 코드 (자체 에러 코드 생성)
    private final String description; // 에러 이유
}
