package com.example.security.service;

import org.springframework.stereotype.Service;

import com.example.security.persistence.dao.ProductRepository;
import com.example.security.persistence.model.User;
import com.example.security.util.FileUtils;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	FileUtils fileUtils = new FileUtils();
	
	public void upload(String product_name, String org_file_name, String stored_file_name, int file_size, int price) {
		
		productRepository.uploadProduct(product_name, org_file_name, stored_file_name, file_size, price);
		
	}
	
//	public void charge(int amount, String id) {
//		
//		User user = new User();
//		user = userRepository.findOneById(id);
//		log.info("amount : " + amount);
//		
//		int orgCash = user.getCash();
//		log.info("orgCash : " + orgCash);
//		
//		userRepository.updateCash(id, orgCash+amount);
//		
//	}
	
}
//	public void insert(UserDto userDto) {
//		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
//		Set<Role> rolesSet = new HashSet<Role>();
//		rolesSet.add(roleRepository.findQneById(2L)); // id : 2 ROLE_USER 역할
//		userRepository.save(userDto.toEntity(rolesSet));
//	}


////게시물 작성
//	@Override
//	@Async
//	public void write(BoardVO vo, MultipartHttpServletRequest mpRequest) throws Exception {
//		dao.write(vo);
//		
//		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(vo, mpRequest);
//		int size = list.size();
//		for(int i=0; i<size; i++) {
//			dao.insertFile(list.get(i));
//		}
//	}	