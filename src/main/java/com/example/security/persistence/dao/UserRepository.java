package com.example.security.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.persistence.model.User;
 
public interface UserRepository extends JpaRepository<User, String> {
	User findOneById(String id);
}