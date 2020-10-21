package com.demo.demo4ddd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan("com.demo.demo4ddd.infrastructure.persistence.mybatis.*")
public class Demo4dddApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo4dddApplication.class, args);
    }
}
