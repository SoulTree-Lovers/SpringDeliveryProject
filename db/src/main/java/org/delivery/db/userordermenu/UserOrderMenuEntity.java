package org.delivery.db.userordermenu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_order_menu")
public class UserOrderMenuEntity extends BaseEntity {

    @JoinColumn(nullable = false, name = "user_order_id") // JoinColumn 사용 + 속성 이름 강제 지정
    @ManyToOne
    private UserOrderEntity userOrder; // 1 : n (userOrder -> userOrderMenuList와 연관관계 설정) 쿼리 메소드에서 변수 이름을 사용할 수 있도록 userOrderEntity -> userOrder로 변경

    @JoinColumn(nullable = false)
    @ManyToOne
    private StoreMenuEntity storeMenu; // 1 : n

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, columnDefinition = "varchar")
    private UserOrderMenuStatus status;
}
