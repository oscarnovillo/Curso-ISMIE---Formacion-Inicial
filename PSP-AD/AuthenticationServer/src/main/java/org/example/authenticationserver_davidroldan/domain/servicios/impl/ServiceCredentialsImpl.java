package org.example.authenticationserver_davidroldan.domain.servicios.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.example.authenticationserver_davidroldan.common.Constantes;
import org.example.authenticationserver_davidroldan.data.repositories.CredentialsRepository;
import org.example.authenticationserver_davidroldan.domain.modelo.Credentials;
import org.example.authenticationserver_davidroldan.domain.modelo.LoginTokens;
import org.example.authenticationserver_davidroldan.domain.modelo.errores.CertificateException;
import org.example.authenticationserver_davidroldan.domain.modelo.errores.ValidationException;
import org.example.authenticationserver_davidroldan.domain.modelo.mappers.CredentialsEntityMapper;
import org.example.authenticationserver_davidroldan.domain.servicios.ServiceCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class ServiceCredentialsImpl implements ServiceCredentials {

    private final PasswordEncoder passwordEncoder;
    private final CredentialsRepository credentialsRepository;
    private final CredentialsEntityMapper credentialsMapper;
    private final AuthenticationManager authenticationManager;
    @Value(Constantes.APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION)
    private int refreshExpiration;
    @Value(Constantes.APPLICATION_SECURITY_JWT_ACCESS_TOKEN_EXPIRATION)
    private int accessExpiration;
    @Value(Constantes.APPLICATION_SECURITY_KEYSTORE_PATH)
    private String path;
    @Value(Constantes.APPLICATION_SECURITY_KEYSTORE_PASSWORD)
    private String password;
    @Value(Constantes.APPLICATION_SECURITY_KEYSTORE_USERKEYSTORE)
    private String userkeystore;


    @Override
    public LoginTokens doLogin(String username, String password) {
        LoginTokens result = new LoginTokens();
        if (username == null || password == null) {
            throw new ValidationException(Constantes.USER_OR_PASSWORD_IS_EMPTY);
        } else {
            Credentials credentials = credentialsMapper.toCredentials(credentialsRepository.findByUsername(username));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (authentication.isAuthenticated()) {
                result.setAccessToken(generateAccessToken(username, credentials.getRole()));
                result.setRefreshToken(generateRefreshToken(username));
            }
        }
        return result;
    }

    @Override
    public Boolean singUp(Credentials c) {
        boolean result = false;
        Credentials credentials = credentialsMapper.toCredentials(credentialsRepository.findByUsername(c.getUsername()));
        if (credentials == null) {
            c.setPassword(passwordEncoder.encode(c.getPassword()));
            credentialsRepository.save(credentialsMapper.toCredentialsEntity(c));
            result = true;
        } else {
            throw new ValidationException(Constantes.USER_ALREADY_EXISTS);
        }
        return result;
    }

    @Override
    public LoginTokens refreshToken(String refreshToken) {
        if (refreshToken != null) {
            if (validateToken(refreshToken)) {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(getPublicKey())
                        .build()
                        .parseClaimsJws(refreshToken);
                String username = claimsJws.getBody().getSubject();
                Credentials credentials = credentialsMapper.toCredentials(credentialsRepository.findByUsername(username));
                String role = credentials.getRole();
                return new LoginTokens(generateAccessToken(username, role), refreshToken);
            } else {
                throw new ValidationException(Constantes.TOKEN_EXPIRED);
            }
        } else {
            throw new ValidationException(Constantes.TOKEN_IS_EMPTY);
        }
    }

    private String generateAccessToken(String subject, String role) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .claim(Constantes.ROLE, role)
                .setIssuedAt(now)
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(accessExpiration)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getPrivateKey())
                .compact();
    }

    private String generateRefreshToken(String subject) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(refreshExpiration)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getPrivateKey())
                .compact();
    }


    private PrivateKey getPrivateKey() {
        try {
            KeyStore ks = KeyStore.getInstance(Constantes.PKCS_12);
            try (FileInputStream fis = new FileInputStream(path)) {
                ks.load(fis, password.toCharArray());
            }
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(userkeystore, pt);
            if (pkEntry != null) {
                return pkEntry.getPrivateKey();
            } else {
                throw new CertificateException(Constantes.NO_SE_ENCONTRO_LA_ENTRADA_DE_CLAVE_PRIVADA_EN_EL_KEYSTORE);
            }
        } catch (Exception e) {
            throw new CertificateException(Constantes.ERROR_AL_CARGAR_LA_CLAVE_PRIVADA_DEL_KEYSTORE);
        }
    }


    private PublicKey getPublicKey() {
        try {
            KeyStore ks = KeyStore.getInstance(Constantes.PKCS_12);
            try (FileInputStream fis = new FileInputStream(path)) {
                ks.load(fis, password.toCharArray());
            }
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(userkeystore, pt);
            if (pkEntry != null) {
                return pkEntry.getCertificate().getPublicKey();
            } else {
                throw new CertificateException(Constantes.NO_SE_ENCONTRO_LA_ENTRADA_DE_CLAVE_PRIVADA_EN_EL_KEYSTORE);
            }
        } catch (Exception e) {
            throw new CertificateException(Constantes.ERROR_AL_CARGAR_LA_CLAVE_PRIVADA_DEL_KEYSTORE);
        }
    }

    private boolean validateToken(String refreshToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(refreshToken);

            long expirationMillis = claimsJws.getBody().getExpiration().getTime();
            return System.currentTimeMillis() < expirationMillis;

        } catch (ExpiredJwtException e) {
            throw new ValidationException(Constantes.TOKEN_EXPIRADO);
        }
    }
}
