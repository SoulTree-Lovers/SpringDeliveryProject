package org.delivery.api.domain.store.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    // 유효한 스토어 검색
    public StoreEntity getStoreWithThrow(Long id) {
        var entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED); // 디버깅을 위해 변수로 구분하기.
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 스토어 등록
    public StoreEntity register(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity)
                .map(it -> {
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);

                    // TODO: 등록 일시 추가하기

                    return storeRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 카테고리로 스토어 검색
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory) {
        var list = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(StoreStatus.REGISTERED, storeCategory);
        return list;
    }

    // 전체 스토어 검색
    public List<StoreEntity> searchAll() {
        var list = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
        return list;
    }

}
