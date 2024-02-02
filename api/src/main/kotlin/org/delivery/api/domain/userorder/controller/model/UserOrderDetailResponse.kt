package org.delivery.api.domain.userorder.controller.model

import org.delivery.api.domain.store.controller.model.StoreResponse
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse


data class UserOrderDetailResponse (
    var userOrderResponse: UserOrderResponse? = null, // 주문건
    var storeResponse: StoreResponse? = null, // 가게 정보
    var storeMenuResponses: List<StoreMenuResponse>? = null, // 메뉴 n개 정보
)
