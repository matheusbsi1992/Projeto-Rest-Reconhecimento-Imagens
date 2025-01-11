package br.com.projeto.estudos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Estudos API com SpringBoot")
                                .version("v1")
                                .description("Uso de API para Swagger")
                                .termsOfService("http://google.com.br")
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .url("http://botafogo.com.br")));
    }

}
