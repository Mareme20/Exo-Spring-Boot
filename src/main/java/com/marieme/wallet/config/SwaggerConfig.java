package com.marieme.wallet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de la documentation OpenAPI / Swagger.
 * Accessible sur http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI walletOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wallet API")
                        .description("API de gestion de portefeuilles electroniques (Utilisateurs, Portefeuilles, Transactions)")
                        .version("v1.0.0")
                        .contact(new Contact().name("Marieme").email("contact@wallet-api.local"))
                        .license(new License().name("Apache 2.0")));
    }
}
