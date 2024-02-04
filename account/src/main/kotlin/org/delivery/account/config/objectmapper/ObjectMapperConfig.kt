package org.delivery.account.config.objectmapper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper() : ObjectMapper {
        // kotlin module
        val kotlinModule = KotlinModule.Builder()
            .apply {
                withReflectionCacheSize(512) // 캐시 사이즈 설정
                configure(KotlinFeature.NullToEmptyCollection, false)   // 컬렉션 자료구조가 들어왔을 때 false로 설정하면 null로 초기화 / ture면 빈 컬렉션으로 초기화
                configure(KotlinFeature.NullToEmptyMap, false)          // map에 대한 자료구조가 들어왔을 때 위와 동일
                configure(KotlinFeature.NullIsSameAsDefault, false)
                configure(KotlinFeature.SingletonSupport, false)
                configure(KotlinFeature.StrictNullChecks, false)
            }.build()

        val objectMapper = ObjectMapper().apply {
            registerModule(Jdk8Module())
            registerModule(JavaTimeModule())
            registerModule(kotlinModule)

            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        }

        return objectMapper
    }

}