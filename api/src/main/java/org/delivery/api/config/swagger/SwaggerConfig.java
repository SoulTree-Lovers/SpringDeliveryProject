package org.delivery.api.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // 스웨거 사용 및 Configuration 등록 (snake case 설정)
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) { // spring bean이 자동으로 매개변수를 넘겨줌
        return new ModelResolver(objectMapper);
    }
}


