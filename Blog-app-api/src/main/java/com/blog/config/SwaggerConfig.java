package com.blog.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public org.springdoc.core.models.GroupedOpenApi publicApi() {
        return org.springdoc.core.models.GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info()
                        .title("Blog Application API")
                        .description("API Documentation by Sakshi Gopal Ghardale.")
                        .version("1.0.0"))
                        .addSecurityItem(new SecurityRequirement().addList("JWT"))
                        .components(new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("JWT", new SecurityScheme()
                                        .name("JWT")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(In.HEADER)
                                        .name(AUTHORIZATION_HEADER))))
                .build();
    }
}
