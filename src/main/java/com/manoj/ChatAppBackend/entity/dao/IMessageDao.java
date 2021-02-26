package com.manoj.ChatAppBackend.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manoj.ChatAppBackend.entity.Message;

@Repository
public interface IMessageDao extends JpaRepository<Message, Long>{

}
