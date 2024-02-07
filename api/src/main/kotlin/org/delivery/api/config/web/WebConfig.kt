package org.delivery.api.config.web

import org.delivery.api.interceptor.AuthorizationInterceptor
import org.delivery.api.resolver.UserSessionResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val userSessionResolver: UserSessionResolver
) : WebMvcConfigurer {

    private val OPEN_API = listOf(
        "/open-api/**"
    )

    private val DEFAULT_EXCLUDE = listOf( // 기본 주소
        "/",
        "favicon.ico", // 아이콘
        "/error",
        "/actuator/**" // actuator 인증
    );

    private val SWAGGER = listOf(
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    )

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authorizationInterceptor)
            .excludePathPatterns(OPEN_API)
            .excludePathPatterns(DEFAULT_EXCLUDE)
            .excludePathPatterns(SWAGGER)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userSessionResolver)
    }
}