package com.noticegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NoticeGeneratorApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NoticeGeneratorApp.class, args);
        System.out.println("Spring Boot Application Started");
    }

}
