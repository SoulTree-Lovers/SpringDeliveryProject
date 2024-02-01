package org.delivery.db.storeuser

import org.delivery.db.storeuser.enums.StoreUserStatus
import org.springframework.data.jpa.repository.JpaRepository

interface StoreUserRepository : JpaRepository<StoreUserEntity, Long> {
    // 이메일과 상태로 가게 사용자 엔티티 하나 찾기
    // select * from store_user where email = ? and status = ? order by id desc limit 1
    fun findFirstByEmailAndStatusOrderByIdDesc(email: String?, status: StoreUserStatus?): StoreUserEntity?
}
