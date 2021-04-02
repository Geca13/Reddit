package com.example.reddit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit.dto.CommentsDto;
import com.example.reddit.modal.Comment;
import com.example.reddit.service.CommentsService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor

public class CommentsController {
	
	private final CommentsService commentService;
	
	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
		
		return null;
		
		
		
	}

}
