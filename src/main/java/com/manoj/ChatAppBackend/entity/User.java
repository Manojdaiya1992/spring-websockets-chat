package com.manoj.ChatAppBackend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
	
	@Id
	private String mobileNumber;
	private String name;
	private String profile;
	private String status;
	@ColumnDefault(value = "true")
	private boolean profileStatus;
	@ColumnDefault(value = "false")
	private boolean isVerified;

}
