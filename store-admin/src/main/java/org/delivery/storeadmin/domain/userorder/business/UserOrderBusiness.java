package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;

    private final SseConnectionPool sseConnectionPool;

    /**
     * 1. 주문이 들어옴
     * 2. 주문 내역을 찾음
     * 3. 스토어를 찾음
     * 4. 연결된 세션을 찾음
     * 5. 푸시 알림을 전송함
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
            () -> new RuntimeException("사용자 주문 내역 없음.")
        );

        // user order entity

        // user order menu

        // user order menu -> store menu

        // response

        // push

        // 현재 연결된 가맹점 유저 세션을 가져옴
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());


        // 유저에게 푸시 알림 전송
//        userConnection.sendMessage();
    }
}
