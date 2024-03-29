package org.delivery.db.userordermenu

import org.delivery.db.userordermenu.enums.UserOrderMenuStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserOrderMenuRepository : JpaRepository<UserOrderMenuEntity, Long> {
    // select * from user_order_menu where user_order_id = ? and status = ?
    fun findAllByUserOrderIdAndStatus(userOrderId: Long?, status: UserOrderMenuStatus?): List<UserOrderMenuEntity> // 리스트는 null이 불가능하므로 앨비스 연산자 제거
}
