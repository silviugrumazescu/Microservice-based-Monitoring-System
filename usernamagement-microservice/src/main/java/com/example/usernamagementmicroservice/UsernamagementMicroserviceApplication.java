package com.example.usernamagementmicroservice;

import com.example.usernamagementmicroservice.dto.RegisterDTO;
import com.example.usernamagementmicroservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class UsernamagementMicroserviceApplication {

    @Autowired
    AuthService authService;

    public static void main(String[] args) {
        SpringApplication.run(UsernamagementMicroserviceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertAdminInDatabase() {
        authService.registerAdmin(
                new RegisterDTO(
                        "admin",
                        "admin",
                        "admin@gmail.com",
                        "admin"
                )
        );
    }

}

