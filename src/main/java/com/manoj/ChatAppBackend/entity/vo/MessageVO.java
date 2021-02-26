package com.manoj.ChatAppBackend.entity.vo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.manoj.ChatAppBackend.entity.Message;

import lombok.Data;

@Data
public class MessageVO {
	
	private String message;
	private String receiver;
	private String sentDateTime;
	private String sender;
	
	public Message convertMessageVOToMessage() {
		  Message message = new Message();
		  message.setMessage(this.message);
		  message.setReceiver(receiver);
		  message.setSender(sender);
		  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
		  ZonedDateTime zonedDatetime = ZonedDateTime.parse(sentDateTime, dateTimeFormatter);
		  message.setSentDateTime(zonedDatetime.toOffsetDateTime());
		  return message;
	}
}
