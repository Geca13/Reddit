package com.example.reddit.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit.exceptions.InvalidTokenException;
import com.example.reddit.modal.RefreshToken;
import com.example.reddit.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class RefreshTokenService {
	
	private final RefreshTokenRepository rtRepository;
	
	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(Instant.now());
		
		return rtRepository.save(refreshToken);
	}
	
	public void validateRerfeshToken(String token) {
		
		 rtRepository.findByToken(token)
				.orElseThrow(()-> new InvalidTokenException("Invalid Refresh Token"));
	}
	
	public void deleteRefreshToken(String token) {
		
		rtRepository.deleteByToken(token);
				
	}

}
