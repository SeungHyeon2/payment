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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	public void uploadProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file) throws ServletException, IOException{
			
		// 상품명
		String product_name = request.getParameter("productName");
		log.info("상품 명 : " + product_name);
		
		log.info("상품 가격 : " + request.getParameter("productPrice"));
		
		// 상품 가격
		if(request.getParameter("productPrice").length() == 0){
			ScriptUtils.alert(response, "가격을 입력해주세요");
		}
		int productPrice = Integer.valueOf(request.getParameter("productPrice"));
		log.info("상품 가격 : " + productPrice);
		
		// 업로드 파일명
		String orgFileName = file.getOriginalFilename();
		log.info("파일 명 : " + orgFileName);
		
		if(product_name.length()==0) {
			ScriptUtils.alert(response, "상품 이름/가격을 입력해주세요");

			//ScriptUtils.alertAndMovePage(response, "상품 이름/가격을 입력해주세요", "redirect:/system/create");
			//ScriptUtils.alertAndMovePage(response, "상품 등록에 성공했습니다.", "/pay/list");
			
		}
		
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
		
		ScriptUtils.alertAndMovePage(response, "상품 등록에 성공했습니다.","/pay/list");
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		
//		<input type="hidden" th:name="product_name" th:value="${list.product_name}"/>
//		<input type="hidden" th:name="org_file_name" th:value="${list.org_file_name}"/>
//		<input type="hidden" th:name="stored_file_name" th:value="${list.stored_file_name}"/>
//		<input type="hidden" th:name="file_size" th:value="${list.file_size}"/>
//		<input type="hidden" th:name="price" th:value="${list.price}"/>
		
		String product_name = request.getParameter("product_name");
		String stored_file_name = request.getParameter("stored_file_name");
		
		service.deleteProduct(stored_file_name);
		
		log.info("product_name" + product_name);
		
		return "redirect:/system/delete";
	}
	
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}