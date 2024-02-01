package org.delivery.storeadmin.domain.authorization;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    // username 인자값으로 스웨거에서 로그인할 때 입력한 username 값이 들어옴.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var storeUserEntity = storeUserService.getRegisteredUser(username);
        var storeEntity = Optional.ofNullable(
            storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeUserEntity.get().getStoreId(),
            StoreStatus.REGISTERED)
        );

        return storeUserEntity.map(it -> {

            var userSession = UserSession.builder()
                .userId(it.getId())
                .email(it.getEmail())
                .password(it.getPassword())
                .status(it.getStatus())
                .role(it.getRole())
                .registeredAt(it.getRegisteredAt())
                .unregisteredAt(it.getUnregisteredAt())
                .lastLoginAt(it.getLastLoginAt())
                .storeId(storeEntity.get().getId())
                .storeName(storeEntity.get().getName())
                .build();

            return userSession;
        })
        .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
