package com.manoj.ChatAppBackend.entity.service;

import java.util.Map;

import javax.persistence.Tuple;

import com.manoj.ChatAppBackend.entity.ChatRoom;

public interface IChatRoomService {
	
	 ChatRoom createChatRoom(Map<String, String> senderRecieverIds);

     void userJoinedOrLeaveRoom(String userId, String room, boolean joinedOrLeaved);
     
     ChatRoom findByReceiverIdAndChatRoom(String receiverId, String chatRoom);
     
     Tuple findSenderRecevierRoomIdByRoomName(String roomName);
     
}
