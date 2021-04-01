package com.example.reddit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.service.SubredditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubRedditController {

	private final SubredditService subredditSevice;
	
	@PostMapping
	public ResponseEntity<SubredditDto> createSubReddit(@RequestBody SubredditDto subredditDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditSevice.save(subredditDto));
		
	}
	
	@GetMapping
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
	return ResponseEntity.status(HttpStatus.OK).body(subredditSevice.getAll());
	}
}
