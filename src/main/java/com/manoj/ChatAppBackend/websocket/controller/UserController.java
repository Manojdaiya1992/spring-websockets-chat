package com.manoj.ChatAppBackend.websocket.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manoj.ChatAppBackend.entity.User;
import com.manoj.ChatAppBackend.entity.service.IUserService;

@RestController
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/user/register/account")
	public ResponseEntity<Object> createAccount(@RequestBody Map<String, String> phoneNumber){
		return new ResponseEntity<Object>(userService.createAccount(phoneNumber.get("phone_number")), HttpStatus.CREATED);
	}
	
	@PutMapping("/user/verify/account")
	public ResponseEntity<Object> verifyAccount(@RequestBody Map<String, String> verificationCode){
		String mobileNumber = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.verifyAccount(mobileNumber, verificationCode.get("code"));
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PutMapping("/user/update/account")
	public ResponseEntity<Object> updateAccount(@RequestParam("image") MultipartFile multipartFile, @RequestParam("user") String juser) throws JsonMappingException, JsonProcessingException{
		 ObjectMapper mapper = new ObjectMapper();
		 User user = mapper.readValue(juser, User.class);
		 String mobileNumber = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<Object>(userService.updateAccount(mobileNumber, multipartFile, user),HttpStatus.OK);
	}
	
	@GetMapping("user/contact/list")
	public ResponseEntity<Object> getContactList(){
		  String mobileNumber = SecurityContextHolder.getContext().getAuthentication().getName();
		  return new ResponseEntity<Object>(this.userService.getContactListNotIn(mobileNumber), HttpStatus.OK);
	}

}
