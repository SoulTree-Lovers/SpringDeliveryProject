package org.delivery.api.domain.user.business;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness; // Business는 동 레벨의 Business를 가져다 쓸 수 있다.

    /**
     *  사용자에 대한 가입처리 로직
     *  1. Request -> Entity
     *  2. Entity 저장 (save)
     *  3. save Entity -> response
     *  4. response return
     */
    public UserResponse register(UserRegisterRequest userRegisterRequest) {
        var entity = userConverter.toEntity(userRegisterRequest);
        var newEntity = userService.register(entity);
        var response = userConverter.toResponse(newEntity);
        return response;

        // 아래 코드도 동일하게 동작 (람다식읉 통한 매핑)
//        return Optional.ofNullable(userRegisterRequest)
//                .map(userConverter::toEntity)
//                .map(userService::register)
//                .map(userConverter::toResponse)
//                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request null"));
    }

    /**
     * 1. email, password를 통해 사용자 체크
     * 2. user entity 로그인 확인
     * TODO:
     * 3. token 생성
     * 4. token을 response로 전달
     */
    public TokenResponse login(UserLoginRequest request) {
        var userEntity = userService.login(request.getEmail(), request.getPassword());
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }

    public UserResponse me(Long userId) {
        var userEntity = userService.getUserWithThrow(userId);
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
