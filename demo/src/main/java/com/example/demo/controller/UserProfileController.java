package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserProfile;

//클래스 위에 @RestController 어노테이션 작성 후 -> import시킨다.
//Spring이 알아서 이 클래스를 Controller로 인식하고 Controller의 인스턴스를 생성해준다.
@RestController
public class UserProfileController {
	
	//UserProfile을 담는 맵
	private Map<String, UserProfile> userMap;
	
	//스프링이 USerProfileController 클래스 인스턴스를 만들고
	//그 직후에 호출하는 메서드이기 때문에 초기화해야할 데이터를 초기화 할 수 있다.
	@PostConstruct
	public void init() {
		userMap = new HashMap<String, UserProfile>();
		userMap.put("1", new UserProfile("1","홍길동1","111-1111","서울시 강남구 대치 1동"));
		userMap.put("2", new UserProfile("2","홍길동2","222-2222","서울시 강남구 대치 2동"));
		userMap.put("3", new UserProfile("3","홍길동3","333-3333","서울시 강남구 대치 3동"));
	}
	
	/*
	 	*Restful APi란? (Representational State Transfer)
	 	Restful API는 HTTP통신에서 어떤 자원에 대한 CRUD 요청을 Resource와 Http의 다양한 Method로 표현하여
		특정한 형태로 전달하는 방식입니다.
		
		*Resource란? 자원, 즉 URI를 의미합니다.
	
		*Method란? 요청 방식을 의미합니다. GET or POST
		
		*Representational of Resource란? 자원의 형태 즉, JSON 또는 XML등을 의미합니다.
		
	
		*Method 4가지 
		데이터를 조회하는 api는 보통 get방식을 쓴다.
		get(데이터 조회), post(데이터 생성), put(데이터 수정), delete(데이터 삭제) 방식 4가지가 있다.
	*/

	
	//이 api의 path를 설정해준다. uri 예)localhost:8081/user/1
	@GetMapping("user/{id}")
	public UserProfile getUserProfile(@PathVariable("id") String id) { 
		//@PathVariable 어노테이션 + 변수
		//클라이언트에서 호출한 path에서 {id}부분을 Pathvarable로 인식하고 id를 파라메터와 함께 getUserProfile() 메소드를 호출해준다.
		//json으로 자동으로 매핑해서 클라이언트에 응답한다. 
		return userMap.get(id);
	}
	
	//전체를 리스트 형태로 조회하는 api
	//이 api의 path를 설정해준다. uri 예)localhost:8081/user/all
	@GetMapping("/user/all")
	public List<UserProfile> getUserProflieList() {
		return new ArrayList<UserProfile>(userMap.values());
	}
	
	//이 api의 path를 설정해준다. uri 예)localhost:8081/user/1
	//생성에 필요한 데이터는 파라메터 형식으로 전달하는 것이 일방적이다. 
	@PostMapping("/user/{id}")
	public void postUserProfile(@PathVariable("id") String id, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("address") String address) {
		UserProfile userProfile = new UserProfile(id, name, phone, address);
		userMap.put(id, userProfile); 
	}
	
	//이 api의 path를 설정해준다. uri 예)localhost:8081/user/1
	//수정에 필요한 데이터는 파라메터 형식으로 전달하는 것이 일방적이다. 
	@PutMapping("/user/{id}")
	public void putUserProfile(@PathVariable("id") String id, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("address") String address) {
		UserProfile userProfile = userMap.get(id);
		userProfile.setName(name);
		userProfile.setPhone(phone);
		userProfile.setAddress(address);
	}
	
	//이렇게도 가능!
	@PutMapping("/user2/{id}")
	public void getUserProfile(@PathVariable("id") String id, @ModelAttribute UserProfile users) {
		UserProfile userProfile = userMap.get(id);
		userProfile.setName(users.getName());
		userProfile.setPhone(users.getPhone());
		userProfile.setAddress(users.getAddress());
	}
	
	//이 api의 path를 설정해준다. uri 예)localhost:8081/user/1
	@DeleteMapping("/user/{id}")
	public void putUserProfile(@PathVariable("id") String id) {
		userMap.remove(id);
	}
	


	
	
}
