package com.example.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit.dto.CommentsDto;
import com.example.reddit.modal.Comment;
import com.example.reddit.modal.Post;
import com.example.reddit.modal.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPost(Post post);

	List<Comment> findAllByUser(User user);

	List<Comment> findByPost(Post post);

}
