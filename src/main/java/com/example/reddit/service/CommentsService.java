package com.example.reddit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit.dto.CommentsDto;
import com.example.reddit.exceptions.PostNotFoundException;
import com.example.reddit.modal.Post;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {
	
	private final PostRepository postRepository;
	private AuthService authService;
	private final UserRepository userRepository;
	
	@Transactional
	public void save(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getId())	
		.orElseThrow(() -> new PostNotFoundException(commentsDto.getId().toString()));
	
	}

}
