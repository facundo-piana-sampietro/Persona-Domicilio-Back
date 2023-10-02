package com.nttdata.ejercicio.equipo1.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.nttdata.ejercicio.equipo1.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@RestController
public class UserController {

    @Value("${login.usuario}")
    private String loginUsuario;

    @Value("${login.password}")
    private String loginPassword;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/user")
    public ResponseEntity<User> login(@RequestBody User usuario) {
        if (!(loginUsuario.equals(usuario.getUser()) && loginPassword.equals(usuario.getPwd()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String token = getJWTToken(usuario.getUser());
        usuario.setToken(token);
        return new ResponseEntity<User>(usuario, HttpStatus.OK);
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("nttdataJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}

