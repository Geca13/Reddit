package com.example.reddit.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthentiticationResponce {

	private String authToken;
	
	private String username;
	
    private String refreshToken;
	
	private Instant expiresAt;
}
