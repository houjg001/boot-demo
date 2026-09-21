package org.example.demo.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BootDemoDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootDemoDBApplication.class, args);
    }
}
