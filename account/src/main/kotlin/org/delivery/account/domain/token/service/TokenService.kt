package org.delivery.account.domain.token.service

import org.delivery.account.domain.token.ifs.TokenHelperInterface
import org.delivery.account.domain.token.model.TokenDto
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenHelperInterface: TokenHelperInterface,
) {

    fun issueAccessToken(userId: Long?): TokenDto? {
        return userId?.let {
            val data = mapOf("userId" to it)
            tokenHelperInterface.issueAccessToken(data)
        }
    }

    fun issueRefreshToken(userId: Long?): TokenDto? {
        requireNotNull(userId) // 이 코드 하위의 userId가 null이 아닌 것을 보장

        val data = mapOf("userId" to userId)
        return tokenHelperInterface.issueRefreshToken(data)
    }

    fun validationToken(token: String?): Long? {
        requireNotNull(token)

        val map = tokenHelperInterface.validationTokenWithThrow(token)
        val userId = map?.get("userId")

        requireNotNull(userId)
        return userId.toString().toLong()
    }
}