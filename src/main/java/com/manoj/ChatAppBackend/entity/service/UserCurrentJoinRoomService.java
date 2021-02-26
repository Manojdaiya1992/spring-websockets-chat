package com.manoj.ChatAppBackend.entity.service;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.ChatAppBackend.entity.UserCurrentJoinRoom;
import com.manoj.ChatAppBackend.entity.dao.IChatRoomDao;
import com.manoj.ChatAppBackend.entity.dao.IUserCurrentJoinRoomDao;

@Service
public class UserCurrentJoinRoomService implements IUserCurrentJoinRoomService {
	
	@Autowired
	private IUserCurrentJoinRoomDao currentJoinRoomDao;
	
	@Autowired
	private IChatRoomDao chatRoomDao;
	
	@Override
	public UserCurrentJoinRoom createOrUpdate(String recevier, String roomName, boolean isJoined) {
		 Tuple chatRoomTuple =  chatRoomDao.findSenderIdRecevierIdAndRoomIdByRoomName(roomName);
		 if(!chatRoomTuple.get("senderId",String.class).equals(recevier) && !chatRoomTuple.get("recevierId",String.class).equals(recevier)) {
			 return null;
		 }
		 UserCurrentJoinRoom currentJoinRoom = currentJoinRoomDao.findByUserIdAndRoomName(recevier, roomName);
		 if(currentJoinRoom!=null) {
			  currentJoinRoom.setJoined(isJoined);
		 }else {
			 currentJoinRoom = new UserCurrentJoinRoom();
			 currentJoinRoom.setJoined(isJoined);
			 currentJoinRoom.setRoomName(roomName);;
			 currentJoinRoom.setUserId(recevier);
		 }
		return currentJoinRoomDao.save(currentJoinRoom);
	}
	
	@Override
	public UserCurrentJoinRoom findByUserIdAndRoomId(String userId, String roomName) {
		 Tuple chatRoomTuple =  chatRoomDao.findSenderIdRecevierIdAndRoomIdByRoomName(roomName);
		 if(!chatRoomTuple.get("senderId",String.class).equals(userId) && !chatRoomTuple.get("recevierId",String.class).equals(userId)) {
			 return null;
		 }
		return currentJoinRoomDao.findByUserIdAndRoomName(userId, roomName);
	}

}
