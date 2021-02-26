package com.manoj.ChatAppBackend.websocket.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.ChatAppBackend.entity.service.IChatRoomService;

@RestController
public class ChatRoomController {
	
	@Autowired
	private IChatRoomService chatRoomService;
	
	@PostMapping("/create/room")
	public ResponseEntity<Object> createChatRoom(@RequestBody Map<String, String> senderRecieverIds){
		return new ResponseEntity<Object>(this.chatRoomService.createChatRoom(senderRecieverIds), HttpStatus.CREATED);
	}

}
