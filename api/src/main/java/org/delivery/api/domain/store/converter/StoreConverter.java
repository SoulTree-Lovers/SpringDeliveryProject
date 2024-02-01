package org.delivery.api.domain.store.converter;

import java.util.Optional;
import javax.swing.text.html.Option;
import org.delivery.common.annotation.Converter;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

@Converter
public class StoreConverter {

    public StoreEntity toEntity(StoreRegisterRequest request) {

        return Optional.ofNullable(request)
                .map(it -> {
                    return StoreEntity.builder()
                            .name(request.getName())
                            .address(request.getAddress())
                            .category(request.getCategory())
                            .minimumAmount(request.getMinimumAmount())
                            .minimumDeliveryAmount(request.getMinimumDeliveryAmount())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .phoneNumber(request.getPhoneNumber())
                            .build()
                            ;
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreResponse toResponse(StoreEntity storeEntity) {

        return Optional.ofNullable(storeEntity)
                .map(it -> {
                    return StoreResponse.builder()
                            .id(storeEntity.getId())
                            .name(storeEntity.getName())
                            .status(storeEntity.getStatus())
                            .address(storeEntity.getAddress())
                            .category(storeEntity.getCategory())
                            .star(storeEntity.getStar())
                            .thumbnailUrl(storeEntity.getThumbnailUrl())
                            .minimumAmount(storeEntity.getMinimumAmount())
                            .minimumDeliveryAmount(storeEntity.getMinimumDeliveryAmount())
                            .phoneNumber(storeEntity.getPhoneNumber())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
