package org.delivery.db.userorder;

import java.util.List;
import java.util.Optional;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/* 코틀린으로 변경
public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    // 특정 유저의 모든 주문내역 가져오기
    // select * from user_order where user_id = ? and status = ? order by id desc
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    // select * from user_order where user_id = ? and status = in(?, ?, ..) order by id desc
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> statusList);


    // 특정 주문 가져오기
    // select * from user_order where id = ? and status = ? and user_id = ?
    Optional<UserOrderEntity> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);

    // 특정 주문 가져오기 (상태를 제외한)
    // select * from user_order where id = ? and user_id = ?
    Optional<UserOrderEntity> findAllByIdAndUserId(Long id, Long userId);
}
*/
