package org.example.web.config;

import com.google.gson.Gson;
import org.example.demo.api.utils.GsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BootWebConfig {

    @Bean
    public Gson gson() {
        return GsonUtils.buildGson();
    }
}
