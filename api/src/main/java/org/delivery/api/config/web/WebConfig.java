package org.delivery.api.config.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.delivery.api.interceptor.AuthorizationInterceptor;
import org.delivery.api.resolver.UserSessionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* 코틀린으로 변경
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor; // 인터셉터 자동 주입
    private final UserSessionResolver userSessionResolver;

    private List<String> OPEN_API = List.of( // 기본 주소 이외에 공개할 주소
            "/open-api/**"
    );

    private List<String> DEFAULT_EXCLUDE = List.of( // 기본 주소
            "/",
            "favicon.ico", // 아이콘
            "/error"
    );

    private List<String> SWAGGER = List.of( // 스웨거 주소
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns(DEFAULT_EXCLUDE)
                .excludePathPatterns(SWAGGER)
        ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionResolver);
    }
}
*/
