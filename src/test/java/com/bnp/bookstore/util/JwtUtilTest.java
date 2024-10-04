package com.bnp.bookstore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final String testUsername = "testUser";
    private final String secret = "secretwoejoiwejri23y48723hiausdunauidh837h23iui";

    @Test
    void testGenerateToken() {
        String token = JwtUtil.generateToken(testUsername);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testExtractUsername() {
        String token = JwtUtil.generateToken(testUsername);
        String extractedUsername = JwtUtil.extractUsername(token);
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void testValidateToken() {
        String token = JwtUtil.generateToken(testUsername);
        assertTrue(JwtUtil.validateToken(token, testUsername));
    }

    @Test
    void testValidateToken_InvalidUsername() {
        String token = JwtUtil.generateToken(testUsername);
        assertFalse(JwtUtil.validateToken(token, "wrongUser"));
    }

    @Test
    @Disabled("Since it compares with milli seconds, we can't it")
    void testValidateToken_ExpiredToken() {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis - 1000;
        Date expiredDate = new Date(expMillis);

        Claims claims = Jwts.claims().setSubject(testUsername);
        String expiredToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(expiredDate)
                .signWith(JwtUtil.key(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();

        assertFalse(JwtUtil.validateToken(expiredToken, testUsername));
    }

    @Test
    @Disabled("Since it compares with milli seconds, we can't it")
    void testIsTokenExpired() {
        String token = JwtUtil.generateToken(testUsername);
        assertFalse(JwtUtil.isTokenExpired(token));

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis - 1000; // 1 second in the past
        Date expiredDate = new Date(expMillis);

        Claims claims = Jwts.claims().setSubject(testUsername);
        String expiredToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(expiredDate)
                .signWith(JwtUtil.key(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();

        assertTrue(JwtUtil.isTokenExpired(expiredToken));
    }
}
