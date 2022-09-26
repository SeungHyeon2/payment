package com.example.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.security.persistence.dao.PayRecordRepository;
import com.example.security.persistence.model.PayRecord;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RecordService {

	private final Logger log = LoggerFactory.getLogger(getClass());	
	private final PayRecordRepository payRecordRepository;
	
	public List<PayRecord> getAllUsers() {
		log.info("RecordService의 getAllUsers 메서드 실행");
		return payRecordRepository.findAll();
	}
	
	public void insertRecord(String uid, String pname, long pamount) {
		
		payRecordRepository.insertRecord(uid, pname, pamount);
		
	}
	
	public List<PayRecord> findRecord(String uid){
		log.info("RecordService의 findRecord 메서드 실행");
		return payRecordRepository.findRecord(uid);
	}
	
	public int count(String uid) {
		return payRecordRepository.countRecord(uid);
	}
	
}
