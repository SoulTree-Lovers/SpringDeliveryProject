package org.delivery.storeadmin.domain.userorder.business;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final SseConnectionPool sseConnectionPool;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final UserOrderConverter userOrderConverter;


    /**
     * 1. 주문이 들어옴
     * 2. 주문 내역을 찾음
     * 3. 스토어를 찾음
     * 4. 연결된 세션을 찾음
     * 5. 푸시 알림을 전송함
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        // user order entity
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
            () -> new RuntimeException("사용자 주문 내역 없음.")
        );

        // user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // user order menu -> store menu
        var storeMenuEntityList = userOrderMenuList.stream()
            .map(it -> {
                return storeMenuService.getStoreMenuWithThrow(it.getStoreMenu().getId());
            })
            .collect(Collectors.toList());

        // storeMenuEntityList -> storeMenuResponseList로 변환
        var storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList);

        // userOrderResponse 생성
        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // push할 데이터 (userOrderDetailResponse)
        var push = UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderResponse)
            .storeMenuResponseList(storeMenuResponseList)
            .build();

        // 현재 연결된 가맹점 유저 세션을 가져옴
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStore().getId().toString());

        // 유저에게 푸시 알림 전송
        userConnection.sendMessage(push);
    }
}
