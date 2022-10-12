package org.example.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Configuración de swagger para la documentación de la API
 * url: http://localhost:8080/swagger-ui
 */
@Configuration
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiDetalles())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
  }

  private ApiInfo apiDetalles() {
    return new ApiInfo("Titulo de la api",
            "descripción de la api",
            "1.0.0",
            "http://localhost:8080",
            new Contact("Victor", "www.victor.tk", "victor@gmail.com"),
            "licencia MIT",
            "www.licenciaMIT.org",
            Collections.emptyList() );
  }
}
