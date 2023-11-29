package com.ulises.crudapi.controller;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

    @PostMapping("/user")
    public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        String token = getJWTToken(username);
        User user = new User();
        user.setUser(username);
        user.setToken(token);
        return user;

    }

    private String getJWTToken(String username) {
        String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + token;
    }

    private Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

