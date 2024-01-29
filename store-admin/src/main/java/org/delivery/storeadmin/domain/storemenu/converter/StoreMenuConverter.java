package org.delivery.storeadmin.domain.storemenu.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.storeadmin.common.annotation.Converter;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;

@Converter
public class StoreMenuConverter {

    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity) {
        return StoreMenuResponse.builder()
            .id(storeMenuEntity.getId())
            .name(storeMenuEntity.getName())
            .status(storeMenuEntity.getStatus())
            .amount(storeMenuEntity.getAmount())
            .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
            .likeCount(storeMenuEntity.getLikeCount())
            .sequence(storeMenuEntity.getSequence())
            .build();
    }


    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> storeMenuEntities) {
        return storeMenuEntities.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
