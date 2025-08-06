package com.example.usernamagementmicroservice.service;

import com.example.usernamagementmicroservice.dto.UserDTO;
import com.example.usernamagementmicroservice.dto.UserDetailsDTO;
import com.example.usernamagementmicroservice.dto.UserSyncDTO;
import com.example.usernamagementmicroservice.model.Role;
import com.example.usernamagementmicroservice.model.User;
import com.example.usernamagementmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private WebClient webClient;

    public void createUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getName(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                Role.ROLE_USER);

        user = userRepository.save(user);

        UserSyncDTO userSyncDTO = new UserSyncDTO(user.getId().toString(), user.getUsername(), user.getRole());

        ResponseEntity<String> STATUS = webClient.post()
                .uri("/createUser")
                .body(Mono.just(userSyncDTO), UserSyncDTO.class)
                .retrieve()
                .toEntity(String.class)
                .block();
        System.out.println("STATUS for /createUser: " + STATUS);
    }

    public UserDTO readUser(UUID uuid) {
        Optional<User> userOptional = userRepository.findById(uuid);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDTO(
                    user.getId().toString(),
                    user.getName(),
                    user.getUsername(),
                    null,
                    user.getEmail());
        } else {
            return null;
        }
    }

    public UserDetailsDTO readUser(String username) {
        List<User> users = userRepository.findByUsername(username);

        if(users.size() > 0) {
            User user = users.get(0);
            return new UserDetailsDTO(true, user.getId().toString(), user.getUsername(), user.getRole().toString());
        } else {
            return new UserDetailsDTO(false, "", "", "");
        }
    }

    public ArrayList<UserDTO> readAllUsers() {
        List<User> users = userRepository.findAll();

        ArrayList<UserDTO> userDTOS = new ArrayList<UserDTO>();
        for(User u : users) {
            System.out.println(u.getId().toString());
            userDTOS.add(
                    new UserDTO(
                            u.getId().toString(),
                            u.getName(),
                            u.getUsername(),
                            null,
                            u.getEmail()
                    )
            );
        }
        return userDTOS;
    }

    public void deleteUser(UUID uuid) {
        Optional<User> userToDelete = userRepository.findById(uuid);
        if(userToDelete.isPresent()) {
            userRepository.delete(userToDelete.get());

            ResponseEntity<String> STATUS =
                    webClient.post()
                            .uri("/deleteUser")
                            .body(Mono.just(uuid.toString()), String.class)
                            .retrieve()
                            .toEntity(String.class)
                            .block();
            System.out.println("STATUS for /deleteUser: " + STATUS);
        }
    }

    public void updateUser(UserDTO userDTO) {
        UUID uuid = UUID.fromString(userDTO.getUuid());
        Optional<User> user = userRepository.findById(uuid);

        if(!user.isPresent()) {
            // throw ex
        }
        else {
            User toUpdate = user.get();
            toUpdate.setEmail(userDTO.getEmail());
            toUpdate.setName(userDTO.getName());
            toUpdate.setUsername(userDTO.getUsername());

            userRepository.save(toUpdate);

            UserSyncDTO userSyncDTO = new UserSyncDTO(toUpdate.getId().toString(), toUpdate.getUsername(), toUpdate.getRole());

            ResponseEntity<String> STATUS = webClient.post()
                    .uri("/updateUser")
                    .body(Mono.just(userSyncDTO), UserSyncDTO.class)
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            System.out.println("STATUS for /updateUser: " + STATUS);

        }



    }





}
