package org.delivery.api.account.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountMeResponse {

    private String name;
    private String email;
    private LocalDateTime registeredAt;
}
