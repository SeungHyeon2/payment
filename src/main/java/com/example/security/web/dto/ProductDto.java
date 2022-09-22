package com.example.security.web.dto;

import com.example.security.persistence.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

	private int id;
	private String org_file_name;
	private String stored_file_name;
	private int file_size;
	private long price;
	
	public Product toEntity() {
		return Product.builder().id(id).org_file_name(org_file_name).stored_file_name(stored_file_name).file_size(file_size).price(price).build();
	}
}
