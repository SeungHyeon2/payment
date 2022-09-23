package com.example.security.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.persistence.model.Charge;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, String>{

	List<Charge> findAll();
	
//	Role findQneById(Long id);
	
//	@Query(value = "SELECT cash FROM tb_user WHERE id = :id", nativeQuery=true)
//	int getCash(@Param("id") String id);
}
