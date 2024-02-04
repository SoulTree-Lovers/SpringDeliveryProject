package org.delivery.account.domain.token.controller

import org.delivery.account.config.common.Log
import org.delivery.account.domain.token.business.TokenBusiness
import org.delivery.account.domain.token.controller.model.TokenValidationRequest
import org.delivery.account.domain.token.controller.model.TokenValidationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal-api/token") // internal-api는 내부 통신으로 규약 (별도의 검증 필요 x)
class TokenInternalApiController(
    private val tokenBusiness: TokenBusiness
) {
    companion object: Log

    @PostMapping("/validation")
    fun tokenValidation(
        @RequestBody tokenValidationRequest: TokenValidationRequest?
    ): TokenValidationResponse {
        log.info("token validation init: {}", tokenValidationRequest)
        return tokenBusiness.tokenValidation(tokenValidationRequest)
    }
}