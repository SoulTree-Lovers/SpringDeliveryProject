package org.delivery.api.domain.token.converter;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Converter;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.model.TokenDto;

@RequiredArgsConstructor
@Converter
public class TokenConverter {

    public TokenResponse toResponse(TokenDto accessToken, TokenDto refreshToken) {
        // 토큰 null 체크 (token이 null이면 예외 발생하도록 설정)
        Objects.requireNonNull(accessToken, () -> { throw new ApiException(ErrorCode.NULL_POINT); });
        Objects.requireNonNull(refreshToken, () -> { throw new ApiException(ErrorCode.NULL_POINT); });

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }
}
