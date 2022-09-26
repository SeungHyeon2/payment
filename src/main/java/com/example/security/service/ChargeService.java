package com.example.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.security.persistence.dao.ChargeRecordRepository;
import com.example.security.persistence.dao.ChargeRepository;
import com.example.security.persistence.model.Charge;
import com.example.security.persistence.model.ChargeRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargeService {

	private final Logger log = LoggerFactory.getLogger(getClass());	
	private final ChargeRepository chargeRepository;
	private final ChargeRecordRepository chargeRecordRepository;
	
	public List<Charge> getAllUsers() {
		log.info("ChargeService의 getAllUsers 메서드 실행");
		return chargeRepository.findAll();
	}
	
	public void insertChargeRecord(String uid, long camount) {
		chargeRecordRepository.insertChargeRecord(uid, camount);
	}
	
	public List<ChargeRecord> findChargeRecord(String uid){
		log.info("ChargeService의 findChargeRecord 메서드 실행");
		return chargeRecordRepository.findChargeRecord(uid);
	}
	
	public int count(String uid) {
		return chargeRecordRepository.countChargeRecord(uid);
	}

	
}
