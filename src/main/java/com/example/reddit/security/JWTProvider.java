package com.example.reddit.security;


import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import static io.jsonwebtoken.Jwts.parser;
import javax.annotation.PostConstruct;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.reddit.exceptions.KeyExeption;
import com.example.reddit.modal.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTProvider {
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
		} catch (KeyStoreException| CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new KeyExeption("Something bad happened while loading keyStore");
		}
	}

	public String generateToken(Authentication auth) {
	User principal =(User) auth.getPrincipal();
	
	return Jwts.builder()
			.setSubject(principal.getUsername())
			.signWith(getPrivateKey())
			.compact();
	}

	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			 throw new KeyExeption("Problem retreiving the key");
			 }
	}
	
	public boolean validateToken(String jwt) {
		
		parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}

	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		} catch (KeyStoreException e) {
			 throw new KeyExeption("Problem retreiving the key");
		}
		
	}
	
	public String getUsernameFromJwt(String token) {
		Claims claims = (Claims) parser()
				.setSigningKey(getPublicKey())
				.parse(token)
				.getBody();
		return claims.getSubject();
	}
	
}
