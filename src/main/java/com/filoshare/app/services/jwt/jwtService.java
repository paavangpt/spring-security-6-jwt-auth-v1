package com.filoshare.app.services.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtService {

    private static final String SECRET_KEY = "66556A586E327234753778214125442A472D4B6150645367566B597033733676";

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userName)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                        .signWith(getSignKey(), SignatureAlgorithm.HS256)
                        .compact();
        return token;
    }

    public Key getSignKey() {
        byte[] keybytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keybytes);
    }

}
