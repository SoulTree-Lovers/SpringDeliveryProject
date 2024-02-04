package com.delivery.apigateway.route

import com.delivery.apigateway.filter.ServiceApiPrivateFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfig (
    private val serviceApiPrivateFilter: ServiceApiPrivateFilter
) {
    @Bean
    fun gatewayRoutes(builder: RouteLocatorBuilder): RouteLocator {

        return builder.routes()
            .route { spec ->
                spec.order(-1) // 최우선순위: -1
                spec.path( // 매칭할 주소
                    "/service-api/api/**"
                ).filters {filterSpec -> // 필터 지정
                    filterSpec.filter(serviceApiPrivateFilter.apply(ServiceApiPrivateFilter.Config()))
                    filterSpec.rewritePath("/service-api(?<segment>/?.*)", "\${segment}")
                }.uri( // 라우팅할 주소
                    "http://localhost:8080"
                )
            }
            .build()
    }

}