package org.rafal.featureflags.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(List.of(SecurityContext.builder()
                        .operationSelector(o ->
                                PathSelectors.regex("/users.*").test(o.requestMappingPattern())
                                        || PathSelectors.regex("/features.*").test(o.requestMappingPattern()))
                        .securityReferences(List.of(SecurityReference.builder()
                                .reference("basicAuth")
                                .scopes(new AuthorizationScope[]{
                                        new AuthorizationScope("global", "accessEverything")
                                })
                                .build()))
                        .build()))
                .securitySchemes(List.of(new BasicAuth("basicAuth")))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}