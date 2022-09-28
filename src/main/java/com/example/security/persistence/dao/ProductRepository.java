package com.example.security.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.security.persistence.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

	List<Product> findAll();
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE tb_user SET cash = :cash WHERE id = :id", nativeQuery = true)
	void updateCash(@Param("id") String id, @Param("cash") int cash);
	
	@Query(value = "INSERT INTO tb_product(product_name, org_file_name, stored_file_name, file_size, price) VALUES (:product_name, :org_file_name, :stored_file_name, :file_size, :price)", nativeQuery= true)
	void uploadProduct(@Param("product_name") String product_name, @Param("org_file_name") String org_file_name, 
			@Param("stored_file_name") String stored_file_name,
			@Param("file_size") int file_size, @Param("price") long price);
	
	
	@Query(value = "DELETE FROM tb_product WHERE stored_file_name = :stored_file_name" ,nativeQuery = true)
	void deleteProduct(@Param("stored_file_name") String stroed_file_name);
}
//User findOneById(String id);
//
//List<User> findAll();
//
//@Query(value = "SELECT cash FROM tb_user WHERE id = :id", nativeQuery=true)
//int getCash(@Param("id") String id);
//
//@Transactional
//@Modifying
//@Query(value = "UPDATE tb_user SET cash = :cash WHERE id = :id", nativeQuery = true)
//void updateCash(@Param("id") String id, @Param("cash") int cash);