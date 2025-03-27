package org.example.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Main class for the Spring Boot application.
 * It scans the specified base packages for Spring components.
 */
@SpringBootApplication(scanBasePackages = {
        "org.example.boot.config",
        "org.example.newsnow.apirest",
        "org.example.newsnow.apirest.mapper",
        "org.example.newsnow.domain.repository",
        "org.example.newsnow.application.helper",
        "org.example.newsnow.application.usecase",
        "org.example.newsnow.infrastructure.output.service",
        "org.example.newsnow.infrastructure.output.repository",
})
@EnableMongoRepositories(basePackages = "org.example.newsnow.domain.repository")
public class BootApplication {

    /**
     * Main method to run the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}