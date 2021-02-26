package com.manoj.ChatAppBackend.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.ChatAppBackend.entity.Message;
import com.manoj.ChatAppBackend.entity.MessageStatus;
import com.manoj.ChatAppBackend.entity.dao.IMessageDao;

@Service
public class MessageService implements IMessageService {
	
	@Autowired
	private IMessageDao messageDao;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IChatRoomService chatRoomService;
	
	@Override
	public Message saveMessage(Message message) {
		/*String userStatus = userService.findUserById(message.getReceiver()).getStatus();
		if(!userStatus.equalsIgnoreCase("Online"))
			   message.setStatus(MessageStatus.SENT);
		else {
			boolean isReceiverJoinRoom = chatRoomService.findByReceiverIdAndChatRoom(message.getReceiver(), message.getChatRoom()).isReceiverJoinRoom();
		    if(isReceiverJoinRoom)
		    	   message.setStatus(MessageStatus.READ);
		    else
		    	   message.setStatus(MessageStatus.DELEVIRED);
		}	 */    
		return this.messageDao.save(message);
	}

}
