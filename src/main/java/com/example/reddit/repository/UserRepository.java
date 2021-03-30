package com.example.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
