package org.delivery.db.userorder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_order")
public class UserOrderEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId; // user 테이블과 1:n 매핑

    @JoinColumn(nullable = false, name = "store_id") // @ManyToOne과 사용하려면 JoinColumn 사용 / 속성 이름을 store_id로 지정
    @ManyToOne // 앞에 온게 자신
    private StoreEntity store; // store 테이블과 1:n 매핑 + 쿼리 메소드에서 변수 이름을 사용할 수 있도록 storeEntity -> store로 변경

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, columnDefinition = "varchar")
    private UserOrderStatus status;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal amount;

    private LocalDateTime orderedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime cookingStartedAt;

    private LocalDateTime deliveryStartedAt;

    private LocalDateTime receivedAt;

    @OneToMany(mappedBy = "userOrder") // 매핑할 변수 이름 설정
    private List<UserOrderMenuEntity> userOrderMenuEntityList;

}
