package com.manoj.ChatAppBackend.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "chatroom")
public class ChatRoom {
	
	@Id
	@Column(unique = true)
	private String roomName;
	private String senderId;
	private String recevierId;
	private boolean isBlocked;
	@JsonProperty("created_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private OffsetDateTime offsetDateTime;
	
}
