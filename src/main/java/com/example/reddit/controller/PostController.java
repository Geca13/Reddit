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

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponce;
import com.example.reddit.service.PostService;
import static org.springframework.http.ResponseEntity.status;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest request) {
		postService.save(request);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponce>> getAllPosts(){
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}
	
	@GetMapping("/id")
	public PostResponce getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}
	
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponce>> getPostsBySubreddit(@PathVariable Long id){
		return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponce>> getPostsBySubreddit(@PathVariable String username){
		return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}
	

}
