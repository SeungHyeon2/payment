package com.example.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.security.persistence.dao.ChargeRepository;
import com.example.security.persistence.model.Charge;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargeService {

	private final Logger log = LoggerFactory.getLogger(getClass());	
	private final ChargeRepository chargeRepository;
	
	public List<Charge> getAllUsers() {
		log.info("ChargeService의 getAllUsers 메서드 실행");
		return chargeRepository.findAll();
	}
	
}
