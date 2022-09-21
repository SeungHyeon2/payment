package com.example.security.service;
 
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.persistence.dao.RoleRepository;
import com.example.security.persistence.dao.UserRepository;
import com.example.security.persistence.model.Role;
import com.example.security.persistence.model.User;
import com.example.security.web.dto.UserDto;

import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class UserService {
 
	private final UserRepository userRepository;
 
	private final RoleRepository roleRepository;
 
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
 
	public void insert(UserDto userDto) {
		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		Set<Role> rolesSet = new HashSet<Role>();
		rolesSet.add(roleRepository.findQneById(2L)); // id : 2 ROLE_USER 역할
		userRepository.save(userDto.toEntity(rolesSet));
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public int getUserCash(String id) {
		return userRepository.getCash(id);
	}
	
	
	public void charge(int amount, String id) {
		User user = new User();
		user = userRepository.findOneById(id);
		int orgCash = user.getCash();
		user.setCash(orgCash + amount);
	}

}