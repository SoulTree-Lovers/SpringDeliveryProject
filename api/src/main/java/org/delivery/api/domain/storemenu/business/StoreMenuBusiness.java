package org.delivery.api.domain.storemenu.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.common.annotation.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreService storeService;

    public StoreMenuResponse register(StoreMenuRegisterRequest request) {
        /**
         * request -> entity -> save -> response
         */
        var storeEntity = storeService.getStoreWithThrow(request.getStoreId());
        var entity = storeMenuConverter.toEntity(request, storeEntity);
        var newEntity = storeMenuService.register(entity);
        var response = storeMenuConverter.toResponse(newEntity);
        return response;
    }

    public List<StoreMenuResponse> getStoreMenuByStoreId(Long storeId) {
        /**
         * entity list -> response list
         */

        var entityList = storeMenuService.getStoreMenuByStoreId(storeId);

        return entityList.stream()
                .map(storeMenuConverter::toResponse)
                .collect(Collectors.toList());

    }



}
