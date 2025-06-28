package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    public Optional<Message> findByPostedByAndMessageTextAndTimePostedEpoch(Integer userID, String message, Long timePosted);
    public Optional<Long> countByMessageId(Integer messageID);
    public Optional<List<Message>> findByPostedBy(Integer userID);
}
