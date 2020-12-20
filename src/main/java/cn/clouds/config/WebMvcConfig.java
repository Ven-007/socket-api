package cn.clouds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author clouds
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public static final int MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PATCH", "DELETE", "PUT")
                .maxAge(MAX_AGE_SECS);
    }
}
