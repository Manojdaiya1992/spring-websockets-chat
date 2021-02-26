package com.manoj.ChatAppBackend.entity.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manoj.ChatAppBackend.entity.User;
import com.manoj.ChatAppBackend.entity.vo.UserVO;

@Repository
public interface IUserDao extends JpaRepository<User, Long> {

	Optional<User> findByMobileNumberAndProfileStatusTrue(String mobileNumber);
	
	Optional<User> findByMobileNumber(String mobileNumber);
	
	@Modifying
	@Query(value = "insert into users (mobile_number) value (?1)",nativeQuery = true)
	void saveUser(String mobileNumber);
	
	@Transactional
	@Modifying
	@Query("update User u set u.status=?1 where u.mobileNumber = ?2")
	void updateUserStatusByPhoneNumber(String status, String phoneNumber);
	
	@Query("select u.mobileNumber as mobileNumber,cr.roomName as roomName,cr.isBlocked as isBlocked from User u left join ChatRoom cr on (u.mobileNumber=cr.senderId or u.mobileNumber=cr.recevierId) where u.mobileNumber !=?1"
			+ " and (cr.senderId =?1 or cr.recevierId =?1)")
	List<Tuple> findContactList(String mobileNumber);
	
	List<User> findByMobileNumberNot(String mobileNumber);
	
	@Query("select u.status from User u where u.mobileNumber = ?1")
	String findUserStatusByMobileNumber(String mobileNumber);
}
