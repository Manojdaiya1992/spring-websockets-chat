package com.manoj.ChatAppBackend.entity.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.manoj.ChatAppBackend.entity.User;
import com.manoj.ChatAppBackend.entity.vo.UserVO;

public interface IUserService {
	
	public User createAccount(String phoneNumber);
	
	public void verifyAccount(String mobileNumber, String verifyCode);
	
	public User updateAccount(String mobileNumber, MultipartFile multipartFile, User user);
	
	public List<UserVO> getContactListNotIn(String mobileNumber);
	
	String findUserStatusByMobileNumber(String mobileNumber);

}
