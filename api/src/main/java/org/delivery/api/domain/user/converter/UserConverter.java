package org.delivery.api.domain.user.converter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.db.user.UserEntity;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest userRegisterRequest) {

        return Optional.ofNullable(userRegisterRequest)
                .map(it -> {
                    // Entity로 변환
                    return UserEntity.builder()
                            .name(userRegisterRequest.getName())
                            .email(userRegisterRequest.getEmail())
                            .password(userRegisterRequest.getPassword())
                            .address(userRegisterRequest.getAddress())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(it -> {
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .status(userEntity.getStatus())
                            .email(userEntity.getEmail())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();

                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null "));
    }
}
