package org.delivery.api.domain.userorder.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.userorder.UserOrderEntity;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

    /**
     * 1. 사용자, 메뉴 id를 받음
     * 2. userOrder 생성
     * 3. userOrderMenu 생성
     * 4. 응답 생성
      */
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        var storeMenuEntityList = body.getStoreMenuIdList().stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .toList();

        var userOrderEntity = userOrderConverter.toEntity(user, body.getStoreId(), storeMenuEntityList);

        // 주문
        var newUserOrderEntiy = userOrderService.order(userOrderEntity);

        // 매핑
        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    // menu + user order
                    var userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntiy, it);
                    return userOrderMenuEntity;
                })
                .collect(Collectors.toList());

        // 주문 내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntiy);


        // response로 변경
        return userOrderConverter.toResponse(newUserOrderEntiy);
    }

    public List<UserOrderDetailResponse> current(User user) {
        var userOrderEntityList = userOrderService.current(user.getId());

        var userOrderDetailResponseList = userOrderEntityList.stream()
                .map(userOrderEntity -> {
                    // 사용자가 주문한 메뉴
                    var userOrderMenuList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

                    //
                    var storeMenuEntityList = userOrderMenuList.stream()
                            .map(userOrderMenuEntity -> {
                                var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    // 사용자가 주문한 스토어 (TODO: 리팩토링 필요)
                    var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream()
                            .findFirst()
                            .get()// null point 에러 발생 가능
                            .getStoreId()
                    );

                    // 자세한 사용자 주문 응답 생성
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                            .storeMenuResponses(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build()
                            ;
                }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        var userOrderEntityList = userOrderService.history(user.getId());

        var userOrderDetailResponseList = userOrderEntityList.stream()
                .map(userOrderEntity -> {
                    // 사용자가 주문한 메뉴
                    var userOrderMenuList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

                    var storeMenuEntityList = userOrderMenuList.stream()
                            .map(userOrderMenuEntity -> {
                                var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    // 사용자가 주문한 스토어 (TODO: 리팩토링 필요)
                    var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream()
                            .findFirst()
                            .get()// null point 에러 발생 가능
                            .getStoreId()
                    );

                    // 자세한 사용자 주문 응답 생성
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                            .storeMenuResponses(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build()
                            ;
                }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        var userOrderEntity = userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user.getId());

        // 사용자가 주문한 메뉴
        var userOrderMenuList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        var storeMenuEntityList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> {
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .collect(Collectors.toList());

        // 사용자가 주문한 스토어 (TODO: 리팩토링 필요)
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream()
                .findFirst()
                .get()// null point 에러 발생 가능
                .getStoreId()
        );

        // 자세한 사용자 주문 응답 생성
        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponses(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build()
                ;
    }
}
