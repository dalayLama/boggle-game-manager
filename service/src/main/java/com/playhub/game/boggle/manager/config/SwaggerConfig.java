package com.playhub.game.boggle.manager.config;

import com.playhub.game.boggle.manager.consts.LocaleConsts;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title(applicationName));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .packagesToScan("com.playhub.game.boggle.manager.controllers")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(securityCustomizer())
                .addOpenApiCustomizer(languageHeaderCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi internalApi() {
        return GroupedOpenApi.builder()
                .group("internal-api")
                .packagesToScan("com.playhub.game.boggle.manager.controllers")
                .pathsToMatch("/internal/api/**")
                .addOpenApiCustomizer(languageHeaderCustomizer())
                .build();
    }

    private OpenApiCustomizer securityCustomizer() {
        final String securitySchemeName = "bearerAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);
        SecurityScheme securityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return openApi -> {
            openApi.addSecurityItem(securityRequirement);
            openApi.getComponents().addSecuritySchemes(securitySchemeName, securityScheme);
        };
    }

    private OpenApiCustomizer languageHeaderCustomizer() {
        Parameter acceptLanguageHeader = new Parameter()
                .in("header")
                .name(HttpHeaders.ACCEPT_LANGUAGE)
                .description("Language preference for the response")
                .required(false)
                .schema(new StringSchema())
                .example(LocaleConsts.EN.toLanguageTag());

        return openApi -> openApi.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> operation.addParametersItem(acceptLanguageHeader));
    }

}
