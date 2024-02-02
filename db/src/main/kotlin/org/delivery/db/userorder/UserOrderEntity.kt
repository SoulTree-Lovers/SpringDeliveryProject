package org.delivery.db.userorder

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.delivery.db.store.StoreEntity
import org.delivery.db.userorder.enums.UserOrderStatus
import org.delivery.db.userordermenu.UserOrderMenuEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "user_order")
data class UserOrderEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null,

    @field:Column(nullable = false)
    var userId: Long?=null,

    @field:JoinColumn(nullable = false, name = "store_id") // @ManyToOne과 사용하려면 JoinColumn 사용 / 속성 이름을 store_id로 지정
    @field:ManyToOne // 앞에 온게 자신
    var store: StoreEntity?=null,

    @field:Enumerated(EnumType.STRING)
    @field:Column(length = 50, nullable = false, columnDefinition = "varchar")
    var status: UserOrderStatus?=null,

    @field:Column(precision = 11, scale = 4, nullable = false)
    var amount: BigDecimal?=null,

    var orderedAt: LocalDateTime?=null,

    var acceptedAt: LocalDateTime?=null,

    var cookingStartedAt: LocalDateTime?=null,

    var deliveryStartedAt: LocalDateTime?=null,

    var receivedAt: LocalDateTime?=null,

    @field:OneToMany(mappedBy = "userOrder") // 매핑할 변수 이름 설정
//    @field:ToString.Exclude --> lombok을 사용하지 않으므로 동작하지 않음.
    @field:JsonIgnore
    var userOrderMenuEntityList: MutableList<UserOrderMenuEntity>?=null,
) {

    override fun toString(): String {
        return "UserOrderEntity(id=$id, userId=$userId, store=$store, status=$status, amount=$amount, orderedAt=$orderedAt, acceptedAt=$acceptedAt, cookingStartedAt=$cookingStartedAt, deliveryStartedAt=$deliveryStartedAt, receivedAt=$receivedAt)"
    }
}