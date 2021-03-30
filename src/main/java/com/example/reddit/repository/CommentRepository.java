package com.example.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit.modal.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
