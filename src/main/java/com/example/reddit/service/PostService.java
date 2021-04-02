package com.example.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponce;
import com.example.reddit.exceptions.PostNotFoundException;
import com.example.reddit.exceptions.SubredditException;
import com.example.reddit.exceptions.UserNotFoundException;
import com.example.reddit.mapper.PostMapper;
import com.example.reddit.modal.Post;
import com.example.reddit.modal.Subreddit;
import com.example.reddit.modal.User;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.SubredditRepository;
import com.example.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

	private final SubredditRepository subRepository;
	private final AuthService authService;
	private final PostMapper mapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	public Post save(PostRequest request) {
	Subreddit subreddit=	subRepository.findByName(request.getSubredditName())
		.orElseThrow(() -> new SubredditException(request.getSubredditName()));
		User currentUser = authService.getCurrentUser();
		return mapper.map(request, subreddit, currentUser);
	}

	@Transactional(readOnly = true)
	public PostResponce getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id.toString()));
   
		return mapper.mapToDto(post);
	}
	@Transactional(readOnly = true)
	public List<PostResponce> getPostsBySubreddit(Long id) {
		Subreddit subreddit = subRepository.findById(id)
				.orElseThrow(() -> new SubredditException(id.toString()));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		
		return posts.stream().map(mapper::mapToDto).collect(Collectors.toList());
	}
	@Transactional(readOnly = true)
	public List<PostResponce> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UserNotFoundException(username));
		List<Post> posts = postRepository.findAllByUser(username);
		return posts.stream().map(mapper::mapToDto).collect(Collectors.toList());

	}
	
	@Transactional(readOnly = true)
	public List<PostResponce> getAllPosts(){
		return postRepository.findAll()
				.stream()
				.map(mapper::mapToDto)
				.collect(Collectors.toList());
	}
	
	

}
