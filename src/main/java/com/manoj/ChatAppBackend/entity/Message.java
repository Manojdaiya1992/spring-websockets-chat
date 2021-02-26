package com.manoj.ChatAppBackend.entity;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="chatMessages")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String message;
	private String sender;
	private String receiver;
	private String chatRoom;
	private OffsetDateTime sentDateTime;
	private MessageStatus status;

}
