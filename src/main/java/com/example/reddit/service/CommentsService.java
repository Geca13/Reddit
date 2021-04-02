package com.example.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit.dto.CommentsDto;
import com.example.reddit.exceptions.PostNotFoundException;
import com.example.reddit.exceptions.UserNotFoundException;
import com.example.reddit.mapper.CommentsMapper;
import com.example.reddit.modal.Comment;
import com.example.reddit.modal.NotificationEmail;
import com.example.reddit.modal.Post;
import com.example.reddit.modal.User;
import com.example.reddit.repository.CommentRepository;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {
	
	private static final String POST_URL = "";
	private final PostRepository postRepository;
	private final AuthService authService;
	private final UserRepository userRepository;
	private final CommentsMapper mapper;
	private CommentRepository commentRepository;
	private final MailContentBuilder builder;
	private final MailService mailService;
	
	@Transactional
	public void save(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getId())	
		.orElseThrow(() -> new PostNotFoundException(commentsDto.getId().toString()));
		Comment comment = mapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);
		
		String message = builder.build(post.getUser() + " posted a comment on your post" + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendEmail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
		
	}

	public List<CommentsDto> getCommentsForPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id.toString()));
		return commentRepository.findAllByPost(post).stream()
				.map(mapper::mapToDto).collect(Collectors.toList());
		
	}

	public List<CommentsDto> getCommentsForUser(String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new UserNotFoundException(userName));
				
		return commentRepository.findAllByUser(user).stream()
				.map(mapper:: mapToDto).collect(Collectors.toList());
	}

}
