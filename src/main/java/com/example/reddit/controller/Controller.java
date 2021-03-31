package com.example.reddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit.dto.AuthentiticationResponce;
import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class Controller {
	
	private final AuthService authService;

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest request) {
		authService.signUp(request);
		return new ResponseEntity<>("User Registaration Completed", HttpStatus.OK);
	}
	
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account activated successfully",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthentiticationResponce login (@RequestBody LoginRequest request){
		return authService.login(request);

	}
	
}
