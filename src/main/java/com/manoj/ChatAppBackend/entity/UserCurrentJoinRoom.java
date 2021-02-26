package com.manoj.ChatAppBackend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="userCurrentRoom", indexes = {@Index(columnList="userId"), @Index(columnList="roomName")})
public class UserCurrentJoinRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userId;
	private String roomName;
	private boolean isJoined;
	
}
