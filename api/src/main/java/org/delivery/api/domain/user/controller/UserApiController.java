package org.delivery.api.domain.user.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me() {
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()); // RequestContext는 request 하나가 들어올 동안 생성됨.
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST); // 인터셉터에서 저장한 유저 아이디 가져오기

        var response = userBusiness.me(Long.parseLong(userId.toString()));
        return Api.OK(response);
    }
}
