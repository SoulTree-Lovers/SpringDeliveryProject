package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/* 코틀린으로 변경
@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeInterface{
    OK(200, 200, "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point 에러")
    ;

    private final Integer httpStatusCode; // HTTP status code
    private final Integer errorCode; // 세분화된 에러 코드 (자체 에러 코드 생성)
    private final String description; // 에러 이유

}
*/
