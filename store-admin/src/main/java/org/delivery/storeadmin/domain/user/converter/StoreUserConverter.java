package org.delivery.storeadmin.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.common.annotation.Converter;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.springframework.stereotype.Service;

@Converter
@RequiredArgsConstructor
//@Service
public class StoreUserConverter {

    public StoreUserEntity toEntity(StoreUserRegisterRequest request, StoreEntity storeEntity) {

        return StoreUserEntity.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .role(request.getRole())
            .storeId(storeEntity.getId()) // TODO: null일 때 에러 체크 필요
            .build();
    }

    public StoreUserResponse toResponse(StoreUserEntity storeUserEntity, StoreEntity storeEntity) {

        return StoreUserResponse.builder()
            .user( // user 생성
                StoreUserResponse.UserResponse.builder()
                    .id(storeUserEntity.getId())
                    .email(storeUserEntity.getEmail())
                    .status(storeUserEntity.getStatus())
                    .role(storeUserEntity.getRole())
                    .registeredAt(storeUserEntity.getRegisteredAt())
                    .unregisteredAt(storeUserEntity.getUnregisteredAt())
                    .lastLoginAt(storeUserEntity.getLastLoginAt())
                    .build()
            )
            .store( // store 생성
                StoreUserResponse.StoreResponse.builder()
                    .id(storeEntity.getId())
                    .name(storeEntity.getName())
                    .build()
            )
            .build();
    }
}
