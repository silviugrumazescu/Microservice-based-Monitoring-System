package com.example.usernamagementmicroservice.service;

import com.example.usernamagementmicroservice.dto.RegisterDTO;
import com.example.usernamagementmicroservice.dto.UserDTO;
import com.example.usernamagementmicroservice.dto.UserSyncDTO;
import com.example.usernamagementmicroservice.model.Role;
import com.example.usernamagementmicroservice.model.User;
import com.example.usernamagementmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.PasswordAuthentication;
import java.util.ArrayList;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebClient webClient;

    public void registerUser(RegisterDTO registerDTO) {

        ArrayList<User> user = (ArrayList<User>) userRepository.findByUsername(registerDTO.getUsername());
        if(user.size() > 0) {
            System.out.println("User already registered");
        } else {
            String encodedPass = passwordEncoder.encode(registerDTO.getPassword());
            registerDTO.setPassword(encodedPass);
            User toRegister = new User(
                    registerDTO.getName(),
                    registerDTO.getUsername(),
                    registerDTO.getEmail(),
                    registerDTO.getPassword(),
                    Role.ROLE_USER);

            userRepository.save(toRegister);

            UserSyncDTO userSyncDTO = new UserSyncDTO(toRegister.getId().toString(), toRegister.getUsername(), toRegister.getRole());

            ResponseEntity<String> STATUS = webClient.post()
                    .uri("/createUser")
                    .body(Mono.just(userSyncDTO), UserSyncDTO.class)
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            System.out.println("STATUS for /createUser: " + STATUS);

        }
    }

    public void registerAdmin(RegisterDTO registerDTO) {

        ArrayList<User> admin = (ArrayList<User>) userRepository.findByUsername(registerDTO.getUsername());
        if(admin.size() > 0) {
            System.out.println("User already registered");
        } else {

            String encodedPass = passwordEncoder.encode(registerDTO.getPassword());
            registerDTO.setPassword(encodedPass);
            User user = new User(
                    registerDTO.getName(),
                    registerDTO.getUsername(),
                    registerDTO.getEmail(),
                    registerDTO.getPassword(),
                    Role.ROLE_ADMIN);

            userRepository.save(user);

            UserSyncDTO userSyncDTO = new UserSyncDTO(user.getId().toString(), user.getUsername(), user.getRole());

            ResponseEntity<String> STATUS = webClient.post()
                    .uri("/createUser")
                    .body(Mono.just(userSyncDTO), UserSyncDTO.class)
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            System.out.println("STATUS for /createUser: " + STATUS);
        }
    }


}
