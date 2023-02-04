package com.DemoMbi.demo_Mbi.config;

import jakarta.servlet.MultipartConfigElement;
import lombok.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipartConfig {

    @Value(staticConstructor = "${file.upload-dir}")
    private String uploadDir;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadDir);
        return factory.createMultipartConfig();
    }
}