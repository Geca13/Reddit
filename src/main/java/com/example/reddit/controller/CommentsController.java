package com.example.reddit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit.dto.CommentsDto;
import com.example.reddit.modal.Comment;
import com.example.reddit.modal.Post;
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
		commentService.save(commentsDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	@GetMapping	("/by-post/{id}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long id){
		return  ResponseEntity.status(HttpStatus.OK).body( commentService.getCommentsForPost(id));
		
	}
	@GetMapping	("/by-user/{userName}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String userName){
		return  ResponseEntity.status(HttpStatus.OK).body( commentService.getCommentsForUser(userName));
		
	}

}
