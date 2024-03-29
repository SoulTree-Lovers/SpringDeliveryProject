package org.delivery.api.domain.storemenu.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.delivery.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store-menu")
public class StoreMenuApiController {

    private final StoreMenuBusiness storeMenuBusiness;


    @GetMapping("/search")
    public Api<List<StoreMenuResponse>> search(
            @RequestParam Long storeId
    ) {
        var response = storeMenuBusiness.getStoreMenuByStoreId(storeId);
        return Api.OK(response);
    }
}
