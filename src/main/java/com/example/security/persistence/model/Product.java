package com.example.security.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "tb_product")
@DynamicUpdate
public class Product {

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "product_name")
	private String product_name;
	
	@Column(name = "org_file_name")
	private String org_file_name;
	
	@Column(name = "stored_file_name")
	private String stored_file_name;
	
	@Column(name = "file_size")
	private int file_size;
	
	@Column(name = "price")
	private long price;

	@Builder
	public Product(int id, String product_name, String org_file_name ,String stored_file_name, int file_size, long price) {
		this.id = id;
		this.product_name = product_name;
		this.org_file_name = org_file_name;
		this.stored_file_name = stored_file_name;
		this.file_size = file_size;
		this.price = price;
	}
}
