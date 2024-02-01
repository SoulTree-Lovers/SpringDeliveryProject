package org.delivery.api.domain.userorder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;


    public List<UserOrderEntity> getUserOrderList(Long userId) {
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }

    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList) {
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId) {
        return Optional.ofNullable(userOrderRepository.findAllByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId))
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public UserOrderEntity getUserOrderWithoutStatusWithThrow(Long id, Long userId) {
        return Optional.ofNullable(userOrderRepository.findAllByIdAndUserId(id, userId))
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 현재 진행 중인 내역 가져오기
    public List<UserOrderEntity> current(Long userId) {
        return getUserOrderList(userId, List.of(
                UserOrderStatus.ORDER,
                UserOrderStatus.COOKING,
                UserOrderStatus.DELIVERY,
                UserOrderStatus.ACCEPT
        ));
    }

    // 과거 주문한 내역 가져오기
    public List<UserOrderEntity> history(Long userId) {
        return getUserOrderList(userId, List.of(
                UserOrderStatus.RECEIVE
        ));
    }

    // 주문
    public UserOrderEntity order(UserOrderEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 상태 변경
    public UserOrderEntity setStatus(UserOrderEntity entity, UserOrderStatus status) {
        entity.setStatus(status);
        return userOrderRepository.save(entity);
    }

    // 주문 확인
    public UserOrderEntity accept(UserOrderEntity entity) {
        entity.setAcceptedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.ACCEPT);
    }

    // 조리 시작
    public UserOrderEntity cooking(UserOrderEntity entity) {
        entity.setCookingStartedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.COOKING);
    }

    // 배달 시작
    public UserOrderEntity delivery(UserOrderEntity entity) {
        entity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.DELIVERY);
    }

    // 배달 완료
    public UserOrderEntity receive(UserOrderEntity entity) {
        entity.setReceivedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.RECEIVE);
    }
}
