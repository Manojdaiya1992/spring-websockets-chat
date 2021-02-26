package com.manoj.ChatAppBackend.websocket.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.manoj.ChatAppBackend.entity.Message;
import com.manoj.ChatAppBackend.entity.MessageStatus;
import com.manoj.ChatAppBackend.entity.service.IChatRoomService;
import com.manoj.ChatAppBackend.entity.service.IMessageService;
import com.manoj.ChatAppBackend.entity.service.IUserCurrentJoinRoomService;
import com.manoj.ChatAppBackend.entity.service.IUserService;
import com.manoj.ChatAppBackend.entity.vo.MessageVO;

@Controller
public class ChatController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private IChatRoomService chatRoomService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserCurrentJoinRoomService currentJoinedRoom;
	
	
	@MessageMapping("/message/{chatRoom}")
	public void sendMessageToRoom(@DestinationVariable("chatRoom") String chatRoom, @Payload MessageVO messageVO) {
		String mobileNumber = SecurityContextHolder.getContext().getAuthentication().getName();
		Message message = messageVO.convertMessageVOToMessage();
		message.setChatRoom(chatRoom);
		message.setSender(mobileNumber);
		String userStatus = userService.findUserStatusByMobileNumber(messageVO.getReceiver());
		if(userStatus!=null) {
		if(!userStatus.equalsIgnoreCase("Online"))
			   message.setStatus(MessageStatus.SENT);
		else {
			boolean isReceiverJoinRoom = currentJoinedRoom.findByUserIdAndRoomId(messageVO.getReceiver(), chatRoom).isJoined();
		    if(isReceiverJoinRoom)
		    	   message.setStatus(MessageStatus.READ);
		    else
		    	   message.setStatus(MessageStatus.DELEVIRED);
		}
	   }else {
		   message.setStatus(MessageStatus.SENT);
	   }
		this.simpMessagingTemplate.convertAndSend("/queue/message/"+chatRoom, message); 
		this.messageService.saveMessage(message);
	}
	
	@MessageMapping("/user/{id}/chatRoom/{chatRoom}/joinOrLeave/{joinOrLeave}")
	public void userJoinRoom(@DestinationVariable("id") String userId, @DestinationVariable("joinOrLeave") Boolean joinOrLeave,
			@DestinationVariable("chatRoom") String chatRoom) {
		      this.currentJoinedRoom.createOrUpdate(userId, chatRoom, joinOrLeave);
		      Map<String, Boolean> receiverJoinedRoom = new HashMap<>(1);
		      receiverJoinedRoom.put("joinOrLeave", joinOrLeave);
		      this.simpMessagingTemplate.convertAndSend("/queue/user/"+userId+"/chatRoom/"+chatRoom+"/joinOrLeave",
		    		  receiverJoinedRoom);
	}
	

}
