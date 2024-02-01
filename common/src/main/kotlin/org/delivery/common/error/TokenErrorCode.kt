package org.delivery.common.error

enum class TokenErrorCode(
    private val httpStatusCode: Int,
    private val errorCode: Int,
    private val description: String,
) : ErrorCodeInterface {
    INVALID_TOKEN(400, 2000, "유효하지 않은 토큰."),
    EXPIRED_TOKEN(400, 2001, "만료된 토큰."),
    TOKEN_EXCEPTION(400, 2002, "알 수 없는 토큰 에러 발생."),
    AUTHORIZATION_TOKEN_NOT_FOUNT(400, 2003, "인증 헤더 토큰이 없음."),
    ;

    override fun getHttpStatusCode(): Int {
        return this.httpStatusCode
    }

    override fun getErrorCode(): Int {
        return this.errorCode
    }

    override fun getDescription(): String {
        return this.description
    }}