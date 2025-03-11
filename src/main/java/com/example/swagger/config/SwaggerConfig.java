package com.example.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("API com Swagger e JWT")
                .version("1.0")
                .description("Documentação da API protegida com JWT"))
        .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
        .components(
            new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(
                    "BearerAuth",
                    new SecurityScheme()
                        .type(Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(In.HEADER)
                        .name("Authorization")));
  }
}
