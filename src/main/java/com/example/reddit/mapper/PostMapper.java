package com.example.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponce;
import com.example.reddit.modal.Post;
import com.example.reddit.modal.Subreddit;
import com.example.reddit.modal.User;

@Mapper(componentModel = "spring")
public interface PostMapper {

	@Mapping(target = "createdDate" , expression = "java(java.time.Instant.now())")
	//@Mapping(target = "subreddit" , source = "subreddit")
	//@Mapping(target = "user" , source = "user")
	@Mapping(target = "description" , source = "postRequest.description")
	Post map(PostRequest request,Subreddit subreddit, User user);
	
	@Mapping(target = "id" , source = "postId")
	//@Mapping(target = "postName" , source = "postName")
	//@Mapping(target = "description" , source = "description")
	//@Mapping(target = "url" , source = "url")
	@Mapping(target = "subredditName" , source = "subreddit.name")
	@Mapping(target = "userName" , source = "user.username")
	PostResponce mapToDto(Post post);
}
