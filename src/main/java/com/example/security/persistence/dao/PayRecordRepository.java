package com.example.security.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.security.persistence.model.PayRecord;

@Repository
public interface PayRecordRepository extends JpaRepository<PayRecord, String>{

	List<PayRecord> findAll();

	@Query(value = "INSERT INTO tb_pay_record(uid, pname, pamount) VALUES (:uid, :pname, :pamount)", nativeQuery = true)
	void insertRecord(@Param("uid") String uid, @Param("pname") String panme, @Param("pamount") long pamount);
	
	
	@Query(value = "SELECT * FROM tb_pay_record WHERE uid = :uid", nativeQuery = true)
	List<PayRecord> findRecord(@Param("uid") String uid);
	
	
	@Query(value = "SELECT COUNT(*) FROM tb_pay_record WHERE uid = :uid", nativeQuery = true)
	int countRecord(@Param("uid") String uid);
}
