package com.example.security.persistence.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
 
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
@Data
@Table(name = "tb_user")
@DynamicUpdate
public class User {
 
	@Id
	@Column(name = "id")
	private String id;
 
	@Column(name = "password")
	private String password;
 
	@Column(name = "name")
	private String name;
	
	@Column(name = "cash")
	private int cash;
 
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Builder
	public User(String id, String password, String name, int cash, Set<Role> roles) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.cash = cash;
		this.roles = roles;
	}
	
}