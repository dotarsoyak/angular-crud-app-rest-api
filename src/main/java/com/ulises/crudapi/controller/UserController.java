package com.ulises.crudapi.controller;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ulises.crudapi.security.JWTTokenProvider;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ulises.crudapi.dto.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

    @Autowired
    private JWTTokenProvider tokenProvider;

    @PostMapping("/user")
    public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        String token = tokenProvider.getJWTToken(username);
        User user = new User();
        user.setUser(username);
        user.setToken(token);
        return user;

    }




}

