package com.example.airportapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI airportApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Airport Management API")
                        .description("A comprehensive REST API for managing airport operations including flights, aircraft, airlines, gates, and passenger information. " +
                                   "This API supports full CRUD operations and provides specialized endpoints for arrivals/departures management.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Airport API Team")
                                .email("support@airportapi.com")
                                .url("https://github.com/NickPow/Airport-API"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server"),
                        new Server().url("https://airport-api.aws.com").description("Production Server")
                ));
    }
}
