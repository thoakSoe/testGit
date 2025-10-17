package com.ojt.OJT19SpringBoot.controller;

import com.ojt.OJT19SpringBoot.dto.UserDTO;
import com.ojt.OJT19SpringBoot.entity.UserRole;
import com.ojt.OJT19SpringBoot.security.JwtUtil;
import com.ojt.OJT19SpringBoot.security.CustomUserDetailsService;
import com.ojt.OJT19SpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) {
        user.setRole(UserRole.USER);
        return ResponseEntity.ok(userService.saveUser(user));
    }
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can create another ADMIN
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserDTO user) {
        user.setRole(UserRole.ADMIN);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtil.generateToken(userDetails.getUsername(),role);
        return ResponseEntity.ok(Map.of("token", token));
    }
    @GetMapping("/test")
    public String test() {
        return "Hello, secured world!";
    }

}
