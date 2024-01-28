package org.delivery.storeadmin.config.security;

import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity // security 활성화
@EnableWebMvc
public class SecurityConfig {

    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//            .csrf().disable() --> spring 6.1부터 Deprecated 되었음.
            .csrf((csrf) -> csrf.disable()) // 그래도 disable 하고 싶다면 ? 이렇게
            .authorizeHttpRequests(it -> {
                it
                    .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                    ).permitAll()

                    // swagger 주소는 인증 없이 통과
                    .requestMatchers(
                        SWAGGER.toArray(new String[0])
                    ).permitAll()

                    // open-api / ** 하위 모든 주소는 인증 없이 통과
                    .requestMatchers(
                        "/open-api/**"
                    ).permitAll()

                    // 그 외 모든 요청은 인증 사용
                    .anyRequest().authenticated();
            })
            .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 해시 방식으로 암호화
    }
}
