package com.example.reddit.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class Controller {

	@PostMapping("/sighUp")
	public void signUp(@RequestBody RegisterRequest request) {
		
	}
}