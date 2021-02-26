package com.manoj.ChatAppBackend.entity.vo;

import com.manoj.ChatAppBackend.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
	private User user;
	private String chatRoom;
	private boolean isBlocked;
	
	

}
