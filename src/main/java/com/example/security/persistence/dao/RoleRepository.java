package com.example.security.persistence.dao;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.persistence.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findQneById(Long id);
}