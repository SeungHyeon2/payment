package com.example.security;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.security.persistence.dao.UserRepository;
import com.example.security.persistence.model.User;

@SpringBootTest
public class UserRepositoryTests extends SecurityApplicationTests{
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testClass() {
		System.out.println("________" + userRepository.getClass().getName());
	}
	
	@Test
	public void create() {
		User user = new User();
		user.setId("JUnit");
		user.setPassword("JUnit");
		user.setName("JUnit");
		user.setCash(10000);
		
		User newUser = userRepository.save(user);
	}
	
}
