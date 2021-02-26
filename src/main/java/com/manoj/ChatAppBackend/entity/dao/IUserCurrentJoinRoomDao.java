package com.manoj.ChatAppBackend.entity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manoj.ChatAppBackend.entity.UserCurrentJoinRoom;

@Repository
public interface IUserCurrentJoinRoomDao extends JpaRepository<UserCurrentJoinRoom, Long> {
	
	UserCurrentJoinRoom findByUserIdAndRoomName(String userId, String roomName);
	@Query("select ucjr.userId from UserCurrentJoinRoom ucjr where ucjr.userId in(?1) and ucjr.roomName = ?2")
	List<String> findByUsersIdAndRoomName(List<String> usersId, String roomName);

}
