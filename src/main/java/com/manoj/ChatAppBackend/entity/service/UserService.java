package com.manoj.ChatAppBackend.entity.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.manoj.ChatAppBackend.entity.User;
import com.manoj.ChatAppBackend.entity.dao.IUserDao;
import com.manoj.ChatAppBackend.entity.vo.UserVO;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Transactional
	@Override
	public User createAccount(String phoneNumber) {
		User user = new User();
		user.setMobileNumber(phoneNumber);
		this.userDao.saveUser(phoneNumber);
		return user;
	}
	
	@Override
	public void verifyAccount(String mobileNumber, String verifyCode) {
		User user = this.userDao.findByMobileNumber(mobileNumber).orElseThrow((()-> new UsernameNotFoundException("User not exist")));
		user.setVerified(true);
		this.userDao.save(user);
	}
	
	@Override
	public User updateAccount(String mobileNumber, MultipartFile multipartFile, User user) {
		User existUser = this.userDao.findByMobileNumber(mobileNumber).orElseThrow((()-> new UsernameNotFoundException("User not exist")));
		if(user.getName()!=null)
	         existUser.setName(user.getName());
		if(multipartFile!=null) {
	        String path = "ChatApp/profile/"+mobileNumber+"/";
	        File file = new File(path);
	        if(!file.exists()) {
	    	     file.mkdirs();
	        }
	        path = path+multipartFile.getOriginalFilename();
	        Path filePath =  Paths.get(path);
	        try {
			   multipartFile.transferTo(filePath);
		   } catch (IllegalStateException e) {
			   e.printStackTrace();
		   } catch (IOException e) {
			  e.printStackTrace();
		   }
	      existUser.setProfile(path);
	  }   
		return this.userDao.save(existUser);
	}
	
	
	@Override
	public List<UserVO> getContactListNotIn(String mobileNumber) {
		   List<User> friendsList = userDao.findByMobileNumberNot(mobileNumber);
		   List<UserVO> userVOs = new ArrayList<>(friendsList.size());
		   List<Tuple> chatRoomTuple = userDao.findContactList(mobileNumber);
		   friendsList.forEach(user->{
			     UserVO userVO = new UserVO();
			     userVO.setUser(user);
			     Optional<Tuple> optionalTuple = chatRoomTuple.stream().filter(tuple-> tuple.get("mobileNumber",String.class).equals(user.getMobileNumber())).findFirst();
		         if(optionalTuple.isPresent()) {
		        	   Tuple tuple = optionalTuple.get();
		        	   userVO.setBlocked(tuple.get("isBlocked", Boolean.class));
		        	   userVO.setChatRoom(tuple.get("roomName", String.class));
		         }
		         userVOs.add(userVO);
		    });
		return userVOs;
	}
	
	@Override
	public String findUserStatusByMobileNumber(String mobileNumber) {
		return this.userDao.findUserStatusByMobileNumber(mobileNumber);
	}
	
}
