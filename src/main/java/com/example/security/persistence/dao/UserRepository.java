package com.example.security.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.security.persistence.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	
	User findOneById(String id);
	
	List<User> findAll();
	
	@Query(value = "select cash from tb_user where id = :id", nativeQuery=true)
	int getCash(@Param("id") String id);
	
}