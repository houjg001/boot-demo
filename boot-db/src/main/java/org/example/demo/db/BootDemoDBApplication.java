package org.example.demo.db;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.example.demo.db.mapper", annotationClass = Mapper.class)
public class BootDemoDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootDemoDBApplication.class, args);
    }
}
