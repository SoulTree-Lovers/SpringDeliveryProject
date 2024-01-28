package org.delivery.db.userordermenu;

import org.delivery.db.userorder.UserOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

}
