package org.example.boot.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Cloudinary.
 */
@Configuration
public class CloudinaryConfig {

    /**
     * The Cloudinary URL.
     * This value is injected from the application properties.
     */
    @Value("${cloudinary.url:}")
    private String cloudinaryUrl;

    /**
     * Creates a Cloudinary bean.
     *
     * @return a Cloudinary instance configured with the provided URL.
     * @throws IllegalArgumentException if the Cloudinary URL is not provided.
     */
    @Bean
    public Cloudinary cloudinary() {
        if (cloudinaryUrl == null || cloudinaryUrl.isEmpty()) {
            throw new IllegalArgumentException("Cloudinary URL must be provided");
        }
        return new Cloudinary(cloudinaryUrl);
    }
}