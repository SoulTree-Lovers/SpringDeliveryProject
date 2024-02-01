package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User의 경우 1000번대 에러 코드 사용
 */
/* 코틀린으로 변경
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeInterface {
    USER_NOT_FOUND(400, 1404, "사용자를 찾을 수 없음."),
    ;

    private final Integer httpStatusCode; // HTTP status code
    private final Integer errorCode; // 세분화된 에러 코드 (자체 에러 코드 생성)
    private final String description; // 에러 이유
}
*/
