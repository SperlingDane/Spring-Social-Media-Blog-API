package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    @Query("FROM Message where posted_by = :posted_by")
    List<Message> findAllByPostedBy(@Param(value = "posted_by") int id);

}
