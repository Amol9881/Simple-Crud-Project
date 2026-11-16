package com.crud.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final SecretKey key;
	private final long expirationMs;

	public JwtUtil(@Value("${jwt.secret}") String secret,
			@Value("${jwt.expiration-ms}") long expirationMs) {

		byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
		if (bytes.length < 32) {
			throw new IllegalStateException("jwt.secret must be at least 32 characters long");
		}
		this.key = Keys.hmacShaKeyFor(bytes);
		this.expirationMs = expirationMs;
	}

	public String generateToken(String username, String role) {
		Date now = new Date();
		return Jwts.builder()
				.subject(username)
				.claim("role", role)
				.issuedAt(now)
				.expiration(new Date(now.getTime() + expirationMs))
				.signWith(key)
				.compact();
	}

	public Claims parse(String token) {
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
	}

	public String extractUsername(String token) {
		return parse(token).getSubject();
	}

	public boolean isValid(String token) {
		try {
			parse(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}