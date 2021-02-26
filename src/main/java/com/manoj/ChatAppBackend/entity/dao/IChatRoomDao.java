package com.manoj.ChatAppBackend.entity.dao;

import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manoj.ChatAppBackend.entity.ChatRoom;

@Repository
public interface IChatRoomDao extends JpaRepository<ChatRoom, Long> {
	
	/*Optional<ChatRoom> findByRoomNameAndRecevierId(String roomName, String receiverId);
	@Query("from ChatRoom cr where cr.roomName= ?1 and (cr.senderId=?2 or cr.recevierId=?2)")
	Optional<ChatRoom> findByRoomNameAndRecevierIdOrSenderId(String roomName, String receiverId);*/
	@Query("from ChatRoom cr where (cr.senderId = ?1 and cr.recevierId = ?2) or (cr.senderId = ?2 and cr.recevierId = ?1)")
	Optional<ChatRoom> findBySenderIdAndRecevierId(String senderId, String receiverId);
	@Query("select cr.senderId as senderId,cr.recevierId as recevierId,cr.id as id from ChatRoom cr where cr.roomName= ?1")
	Tuple findSenderIdRecevierIdAndRoomIdByRoomName(String roomName);

}
