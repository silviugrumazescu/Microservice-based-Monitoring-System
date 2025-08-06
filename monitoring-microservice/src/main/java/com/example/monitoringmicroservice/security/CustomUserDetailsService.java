package com.example.monitoringmicroservice.security;

import com.example.monitoringmicroservice.model.Role;
import com.example.monitoringmicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    WebClient webClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<User> data = webClient.get()
                .uri("/getUserDetails/" + username)
                .retrieve()
                .toEntity(User.class)
                .block();

        System.out.println("Received from user service: " + data.getBody().getUserValid() + " " + data.getBody().getUsername());;
        CustomUserDetails customUserDetails = new CustomUserDetails(data.getBody());

        return customUserDetails;
    }
}
