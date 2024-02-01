package org.delivery.storeadmin.domain.storemenu.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;


    public StoreMenuEntity getStoreMenuWithThrow(Long menuId) {
        return Optional.ofNullable(storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(menuId, StoreMenuStatus.REGISTERED))
            .orElseThrow(() -> new RuntimeException("Store Menu Not Found"));
    }
}
