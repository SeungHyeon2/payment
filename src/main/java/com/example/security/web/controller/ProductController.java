package com.example.security.web.controller;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.security.service.ProductService;
import com.example.security.util.ScriptUtils;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String filePath = "D:\\workspace-spring-tool-suite-4-4.15.3.RELEASE\\security\\src\\main\\resources\\static\\products\\"; // 파일이 저장될 로컬 위치
	
	@Autowired
	ProductService service;
	
	@PostMapping("/upload")
	public void uploadProduct(HttpServletRequest request, HttpServletResponse response,@RequestParam MultipartFile file) throws ServletException, IOException{
			
		// 상품명
		String product_name = request.getParameter("productName");
		log.info("상품 명 : " + product_name);
		
		// 상품 가격
		int productPrice = Integer.valueOf(request.getParameter("productPrice"));
		log.info("상품 가격 : " + productPrice);
		
		// 업로드 파일명
		String orgFileName = file.getOriginalFilename();
		log.info("파일 명 : " + orgFileName);
		
		String storedFileName = null;
		String originalFileName = null;
		String originalFileExtension = null;
		
		if(file.isEmpty() == false) {
			originalFileName = file.getOriginalFilename();
			originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			storedFileName = getRandomString() + originalFileExtension;
		}
	
	
		try {
			
			service.upload(product_name, originalFileName, storedFileName, (int)file.getSize(), productPrice);
			
			// 파일 저장
			File newfile = new File(filePath);
			if(newfile.exists() == false) {
				newfile.mkdirs();
			}


			File localfile = new File(filePath + storedFileName);
			file.transferTo(localfile);

		}catch(Exception e) {
			log.info(e.getMessage());
			ScriptUtils.alert(response, "오류가 발생했습니다\n형식에 맞게 입력해주세요. " + e.getMessage());
		}
		
		

		ScriptUtils.alertAndMovePage(response, "상품 등록에 성공했습니다.", "/pay/list");
	}
	
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}