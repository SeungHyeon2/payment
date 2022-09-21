package com.example.security.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.security.persistence.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	
	User findOneById(String id);
	
	List<User> findAll();
	
	@Query(value = "SELECT cash FROM tb_user WHERE id = :id", nativeQuery=true)
	int getCash(@Param("id") String id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE tb_user SET cash = :cash WHERE id = :id", nativeQuery = true)
	void updateCash(@Param("id") String id, @Param("cash") int cash);
	
}