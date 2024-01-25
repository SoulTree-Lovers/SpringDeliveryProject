package org.delivery.api.account;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.account.AccountEntity;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountRepository accountRepository;
    @GetMapping("/me")
    public Api<AccountMeResponse> me() {
        var response = AccountMeResponse.builder()
                .name("강승민")
                .email("kang@naver.com")
                .registeredAt(LocalDateTime.now())
                .build();

        if (true) {
            throw new ApiException(ErrorCode.SERVER_ERROR, "사용자 me 호출 시 에러 발생");
        }

        return Api.OK(response);
    }
}
