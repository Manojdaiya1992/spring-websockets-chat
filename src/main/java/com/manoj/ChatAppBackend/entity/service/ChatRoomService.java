package com.manoj.ChatAppBackend.entity.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manoj.ChatAppBackend.entity.ChatRoom;
import com.manoj.ChatAppBackend.entity.User;
import com.manoj.ChatAppBackend.entity.UserCurrentJoinRoom;
import com.manoj.ChatAppBackend.entity.dao.IChatRoomDao;
import com.manoj.ChatAppBackend.entity.dao.IUserCurrentJoinRoomDao;
import com.manoj.ChatAppBackend.entity.dao.IUserDao;

@Service
public class ChatRoomService implements IChatRoomService {
	
	@Autowired
	private IChatRoomDao chatRoomDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserCurrentJoinRoomDao userCurrentJoinRoomDao;
	
	@Override
	public ChatRoom createChatRoom(Map<String, String> senderRecieverIds) {
		String senderId = SecurityContextHolder.getContext().getAuthentication().getName();
		String recevierId =senderRecieverIds.get("recevier_id");
		Optional<ChatRoom> optChatRoom = chatRoomDao.findBySenderIdAndRecevierId(senderId, recevierId);
		if(optChatRoom.isPresent())
			  return optChatRoom.get();
		
		Optional<User> optSender = userDao.findByMobileNumberAndProfileStatusTrue(senderId);
		User sender = optSender.orElseThrow(()-> new UsernameNotFoundException("User not exist"));
		Optional<User> optReceiver = userDao.findByMobileNumberAndProfileStatusTrue(recevierId);
		User receiver = optReceiver.orElseThrow(()-> new UsernameNotFoundException("User not exist"));
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setBlocked(false);
		chatRoom.setOffsetDateTime(OffsetDateTime.now());
		chatRoom.setSenderId(sender.getMobileNumber());
		chatRoom.setRecevierId(receiver.getMobileNumber());
		chatRoom.setRoomName(sender.getMobileNumber()+receiver.getMobileNumber());
		ChatRoom  dbChatRoom = this.chatRoomDao.save(chatRoom);
		List<String> usersId = new ArrayList<>(2);
		usersId.add(senderId);
		usersId.add(recevierId);
		List<String> dbUserId = userCurrentJoinRoomDao.findByUsersIdAndRoomName(usersId, dbChatRoom.getRoomName());
		if(dbUserId.isEmpty()) {
			
			 createCurrentJoinRoomForSender(senderId, dbChatRoom.getRoomName());
			 createCurrentJoinRoomForRecevier(recevierId, dbChatRoom.getRoomName());
			 
		}else if(dbUserId.size()==1){
			  String userId = dbUserId.get(0);
			  if(!userId.equals(senderId))
				     createCurrentJoinRoomForSender(senderId, dbChatRoom.getRoomName());
			  else
				    createCurrentJoinRoomForRecevier(recevierId, dbChatRoom.getRoomName());
			  
		}
		return dbChatRoom;
	}
	
	void createCurrentJoinRoomForSender(String senderId, String roomId) {
		 UserCurrentJoinRoom currentJoinRoom = new UserCurrentJoinRoom();
		 currentJoinRoom.setJoined(true);
		 currentJoinRoom.setRoomName(roomId);
		 currentJoinRoom.setUserId(senderId);
		 userCurrentJoinRoomDao.save(currentJoinRoom);
	}
	
void createCurrentJoinRoomForRecevier(String recevierId, String roomId) {
	 UserCurrentJoinRoom currentJoinRoom2 = new UserCurrentJoinRoom();
	 currentJoinRoom2.setJoined(false);
	 currentJoinRoom2.setRoomName(roomId);
	 currentJoinRoom2.setUserId(recevierId);
	 userCurrentJoinRoomDao.save(currentJoinRoom2);	 
	}
	
	@Override
	public Tuple findSenderRecevierRoomIdByRoomName(String roomName) {
		return this.chatRoomDao.findSenderIdRecevierIdAndRoomIdByRoomName(roomName);
	}

	@Override
	public void userJoinedOrLeaveRoom(String userId, String room, boolean joinedOrLeaved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChatRoom findByReceiverIdAndChatRoom(String receiverId, String chatRoom) {
		// TODO Auto-generated method stub
		return null;
	}

}
