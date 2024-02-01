package org.delivery.api.config.swagger

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    fun modelResolver(objectMapper: ObjectMapper) : ModelResolver {
        return ModelResolver(objectMapper)
    }
}