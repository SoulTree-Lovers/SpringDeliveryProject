package org.delivery.db.user;

import java.util.Optional;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/* 코틀린으로 변경
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // select * from user where id = ? and status = ? order by id desc limit 1
    Optional<UserEntity> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus userStatus);

    // select * from user where email = ? and password = ? and status = ? order by id desc limit 1
    Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus userStatus);
}
*/
