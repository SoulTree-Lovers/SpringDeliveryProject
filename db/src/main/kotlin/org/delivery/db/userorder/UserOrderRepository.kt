package org.delivery.db.userorder

import org.delivery.db.userorder.enums.UserOrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserOrderRepository : JpaRepository<UserOrderEntity, Long> {
    // 특정 유저의 모든 주문내역 가져오기
    // select * from user_order where user_id = ? and status = ? order by id desc
    fun findAllByUserIdAndStatusOrderByIdDesc(userId: Long?, status: UserOrderStatus?): List<UserOrderEntity>

    // select * from user_order where user_id = ? and status = in(?, ?, ..) order by id desc
    fun findAllByUserIdAndStatusInOrderByIdDesc(
        userId: Long?,
        statusList: List<UserOrderStatus>?
    ): List<UserOrderEntity>

    // 특정 주문 가져오기
    // select * from user_order where id = ? and status = ? and user_id = ?
    fun findAllByIdAndStatusAndUserId(id: Long?, status: UserOrderStatus?, userId: Long?): UserOrderEntity?

    // 특정 주문 가져오기 (상태를 제외한)
    // select * from user_order where id = ? and user_id = ?
    fun findAllByIdAndUserId(id: Long?, userId: Long?): UserOrderEntity?
}
