package org.delivery.api.domain.token.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.helper.JwtTokenHelper;
import org.delivery.api.domain.token.ifs.TokenHelperInterface;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

/**
 * 토큰에 대한 도메인 로직
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperInterface tokenHelperInterface; // 두 가지 헬퍼 모두 사용 가능
    private final JwtTokenHelper jwtTokenHelper;

    public TokenDto issueAccessToken(Long userId) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userId", userId);

        return jwtTokenHelper.issueAccessToken(data); // 인터페이스를 구현한 헬퍼 클래스로 사용
    }

    public TokenDto issueRefreshToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelperInterface.issueRefreshToken(data); // 헬퍼 인터페이스로 그대로 사용
    }

    public Long validationToken(String token) {
        Map<String, Object> map = tokenHelperInterface.validationTokenWithThrow(token);
        Object userId = map.get("userId");
        Objects.requireNonNull(userId, () -> {throw new ApiException(ErrorCode.NULL_POINT);}); // userId가 null이면 예외 발생
        return Long.parseLong(userId.toString());
    }
}
