package com.example.usernamagementmicroservice.controller;

import com.example.usernamagementmicroservice.dto.*;
import com.example.usernamagementmicroservice.model.Role;
import com.example.usernamagementmicroservice.model.User;
import com.example.usernamagementmicroservice.repository.UserRepository;
import com.example.usernamagementmicroservice.security.CustomUserDetails;
import com.example.usernamagementmicroservice.service.AuthService;
import com.example.usernamagementmicroservice.service.UserService;
import com.example.usernamagementmicroservice.utils.JwtUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        System.out.println("Received " + registerDTO.getUsername() + " " +
                            registerDTO.getEmail() + " " +
                            registerDTO.getPassword() + " " +
                            registerDTO.getName());

        authService.registerUser(registerDTO);

        return ResponseEntity.ok("Succesfully registered!");
    }

    @PostMapping("auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(loginDTO.getUsername());

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String role = ((SimpleGrantedAuthority)userDetails.getAuthorities().toArray()[0]).getAuthority();
        return ResponseEntity.ok(new LoginResponseDTO(userDetails.getUsername(), jwt, role));
    }

    @PostMapping("/auth/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterDTO registerDTO) {
        System.out.println("Received " + registerDTO.getUsername() + " " +
                registerDTO.getEmail() + " " +
                registerDTO.getPassword() + " " +
                registerDTO.getName());

        authService.registerAdmin(registerDTO);

        return ResponseEntity.ok("Succesfully registered!");
    }

    @GetMapping("/validate/getUserDetails/{username}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable String username) {

        UserDetailsDTO userDTO = userService.readUser(username);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/home")
    public ResponseEntity<?> getToken() {
        return ResponseEntity.ok(jwtUtils.generateJwtToken("aurel"));
    }

    @PostMapping("/")
    public ResponseEntity<?> getUser(@RequestBody String s) {
        return ResponseEntity.ok(jwtUtils.getUserNameFromJwtToken(s));
    }

    @GetMapping("/protectedAdmin")
    public ResponseEntity<?> getProtectedAdmin() {
        return ResponseEntity.ok("You got admin resource");
    }

    @GetMapping("/protectedUser")
    public ResponseEntity<?> getProtectedUser() {
        return ResponseEntity.ok("You got user resource");
    }
}
