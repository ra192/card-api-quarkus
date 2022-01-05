package com.card.service;

import com.card.entity.Merchant;
import com.card.service.exception.TokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@ApplicationScoped
public class TokenService {
    private final Logger logger= Logger.getLogger(TokenService.class);

    @ConfigProperty(name = "com.card.auth.jwt.secret")
    String jwtSecretKey;

    @ConfigProperty(name = "com.card.auth.jwt.expiration.minutes")
    Long expirationInMinutes;

    public String create(Merchant merchant, String secret) throws TokenException, NoSuchAlgorithmException {
        if (!merchant.getSecret().equalsIgnoreCase(sha256Hash(secret)))
            throw new TokenException("Secret is not valid");
        final var token = Jwts.builder().setSubject(String.valueOf(merchant.getId()))
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expirationInMinutes)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSecret()).compact();

        logger.info("Token was created");

        return token;
    }

    public Long validate(String token) {
        final var claimsJws = Jwts.parserBuilder().setSigningKey(getSecret()).build().parseClaimsJws(token);

        return Long.valueOf(claimsJws.getBody().getSubject());
    }

    private SecretKey getSecret() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String sha256Hash(String text) throws NoSuchAlgorithmException {
        final var digest = MessageDigest.getInstance("SHA-256");
        return new String(Base64.getEncoder().encode(digest.digest(text.getBytes(StandardCharsets.UTF_8))),
                StandardCharsets.UTF_8);
    }
}
