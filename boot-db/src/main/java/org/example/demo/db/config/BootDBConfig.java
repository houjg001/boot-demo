package org.example.demo.db.config;

import com.google.gson.Gson;
import org.example.demo.api.utils.GsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring/context.xml")
public class BootDBConfig {

    @Bean
    public Gson gson() {
        return GsonUtils.buildGson();
    }
}
