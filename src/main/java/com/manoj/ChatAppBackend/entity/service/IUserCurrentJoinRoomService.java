package com.manoj.ChatAppBackend.entity.service;

import com.manoj.ChatAppBackend.entity.UserCurrentJoinRoom;

public interface IUserCurrentJoinRoomService {
	
	UserCurrentJoinRoom createOrUpdate(String recevier, String roomName,boolean isJoined);
	
	UserCurrentJoinRoom findByUserIdAndRoomId(String userId, String roomName);

}
