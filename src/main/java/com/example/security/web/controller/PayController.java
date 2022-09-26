package com.example.security.web.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.persistence.model.CallbackPayload;
import com.example.security.persistence.model.Charge;
import com.example.security.persistence.model.Product;
import com.example.security.service.ChargeService;
import com.example.security.service.ProductService;
import com.example.security.service.RecordService;
import com.example.security.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PayController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserService userService;
	
	@Autowired
	ChargeService chargeService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	RecordService recordService;
	
	// 게이트웨이로 이동
	@GetMapping("pay/list")
	public ModelAndView list(HttpServletRequest request) {
		
		// SecurityContextHolder 빈을 통해 SpringSecurity 로그인 객체를 불러옴
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String id = userDetails.getUsername();
		
		log.info("상품 리스트");
		log.info("아이디 = " + id);
		
		int cash = userService.getUserCash(id);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pay/list");
		mv.addObject("userCash", cash);
		
		return mv;
	}
	
	@GetMapping("/pay/productList")
	public String ProductList(HttpServletRequest request, Model model) {
		
		List<Product> list = null;
		list = productService.getAllUsers();
		
		model.addAttribute("list", list);
		
		return "pay/productList";
	}
	
	// 구매 페이지로 이동
	@GetMapping("/purchase")
	public String purchase(HttpServletRequest request, Model model, @RequestParam(value = "product_name", required = false) String product_name,
			@RequestParam("org_file_name") String org_file_name, @RequestParam("stored_file_name") String stored_file_name,
			@RequestParam("file_size") int file_size, @RequestParam("price") long price) {
		log.info("구매 페이지로 이동", request);
		log.info("상품 이름 : " + product_name);
		log.info("파일 이름 : " + org_file_name);
		log.info("저장된 파일 이름 : " + stored_file_name);
		log.info("파일 사이즈 : " + file_size);
		log.info("상품 가격 : " + price);
		
		
		model.addAttribute("product_name", product_name);
		model.addAttribute("org_file_name", org_file_name);
		model.addAttribute("stored_file_name", stored_file_name);
		model.addAttribute("file_size", file_size);
		model.addAttribute("price", price);

		// SecurityContextHolder 빈을 통해 SpringSecurity 로그인 객체를 불러옴
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String customer_name = userDetails.getUsername();
		log.info("customer_name : " + customer_name);
	
		model.addAttribute("customer_name", customer_name);
		
		
		String imgPath = "/products/" + stored_file_name;
		model.addAttribute("imgPath", imgPath);
		return "pay/purchase";
	}
	
	// 충전 리스트로 이동
	@GetMapping("pay/chargeList")
	public String chargeList(HttpServletRequest request, Model model) {
		
		List<Charge> list = null;
		list = chargeService.getAllUsers();
		
		model.addAttribute("list", list);
		
		return "pay/chargeList";
	}
	
	// 충전 페이지로 이동
	@RequestMapping(value = "/charge")
	public String charge(HttpServletRequest request, Model model, @RequestParam(value="amount", required=false) String amount) {
		log.info("충전 페이지로 이동", request);
		log.info("amount : " + amount);
		
		// SecurityContextHolder 빈을 통해 SpringSecurity 로그인 객체를 불러옴
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String name = userDetails.getUsername();
		
		model.addAttribute("name", name);
		model.addAttribute("amount", amount);
		
		return "pay/charge";
	}
	
	
	// 게시물 조회
//	@RequestMapping(value="/view", method = RequestMethod.GET)
//	public void getView(@RequestParam("bno") int bno, Model model) throws Exception{
//		
//		BoardVO vo = service.view(bno);
//		
//		model.addAttribute("view", vo);
//		
//		// 댓글 조회
//		List<ReplyVO> reply = null;
//		reply = replyService.list(bno);
//		model.addAttribute("reply", reply);
//		
//		// 첨부파일 조회
//		List<Map<String, Object>> fileList = service.selectFileList(vo.getBno());
//		model.addAttribute("file", fileList);
//	}
	
	
	private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    private void init() {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });
    }

    private final String SECRET_KEY = "test_ak_mnRQoOaPz8LwjZD1Oljry47BMw6v";

    // 토스 페이먼츠 결제 성공 시
    @RequestMapping("/paymentSuccess")
    public String confirmPayment(
            @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount, @RequestParam String customerName, @RequestParam String productName,
            Model model) throws Exception {

    	// SecurityContextHolder 빈을 통해 SpringSecurity 로그인 객체를 불러옴
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String id = userDetails.getUsername();
    	
    	log.info("상품 결제");
		log.info("아이디 : " + id);
    	
        HttpHeaders headers = new HttpHeaders();
        // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 이상 버전에서 지원
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));


        log.info("충전량 : " + amount);
        
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonNode successNode = responseEntity.getBody();
            model.addAttribute("orderId", successNode.get("orderId").asText());
            String secret = successNode.get("secret").asText(); // 가상계좌의 경우 입금 callback 검증을 위해서 secret을 저장하기를 권장함
            
            String result = userService.purchase(amount.intValue(), id);
            
            if(result == "잔액이 부족합니다.\n충전이 필요합니다.") {
            	model.addAttribute("result", result);
            	return "pay/fail";
            }
            
            int cash = userService.getUserCash(id);
            
            
            model.addAttribute("orderCash", amount);
            model.addAttribute("userCash", cash);
            
            //////////////////////////////////////////////////////////////////////////////////
            // uid, pname, pamount
            recordService.insertRecord(id, productName, amount);
            //////////////////////////////////////////////////////////////////////////////////
            
            
            // 결제 완료 까지 완료했으나 amount 값이 반영이 되지 않음 -> 서비스 로직 문제
            log.info("결제완료");
            
            return "pay/paymentSuccess";
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "pay/fail";
        }
    }
    
    // 토스 페이먼츠 충전 성공 시
    @RequestMapping("/chargementSuccess")
    public String confirmCharge(
            @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount,
            Model model) throws Exception {

    	// SecurityContextHolder 빈을 통해 SpringSecurity 로그인 객체를 불러옴
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String id = userDetails.getUsername();
    	
    	log.info("상품 결제");
		log.info("아이디 : " + id);
    	
        HttpHeaders headers = new HttpHeaders();
        // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 이상 버전에서 지원
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));
        

        log.info("충전량 : " + amount);
        
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonNode successNode = responseEntity.getBody();
            model.addAttribute("orderId", successNode.get("orderId").asText());
            String secret = successNode.get("secret").asText(); // 가상계좌의 경우 입금 callback 검증을 위해서 secret을 저장하기를 권장함
            
            userService.charge(amount.intValue(), id);

            int cash = userService.getUserCash(id);
    
            model.addAttribute("orderCash", amount);
            model.addAttribute("userCash", cash);
            
            // 결제 완료 까지 완료했으나 amount 값이 반영이 되지 않음 -> 서비스 로직 문제
            log.info("결제완료");
            
            return "pay/ChargementSuccess";
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "pay/fail";
        }
    }
    

    // 토스 페이먼츠 구매 실패 시
    @RequestMapping("/fail")
    public String failPayment(@RequestParam String message, @RequestParam String code, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("code", code);
        return "pay/fail";
    }

    @RequestMapping("/virtual-account/callback")
    @ResponseStatus(HttpStatus.OK)
    public void handleVirtualAccountCallback(@RequestBody CallbackPayload payload) {
        if (payload.getStatus().equals("DONE")) {
            // handle deposit result
        }
    }

}
