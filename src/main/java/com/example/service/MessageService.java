package com.example.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Integer userID, String message, Long timePosted){
        

        if(accountRepository.findById(userID).isEmpty() || message.trim().isEmpty() || message.length() > 255){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else{
            Message newMessage = new Message(userID, message, timePosted);
            messageRepository.save(newMessage);
            return messageRepository.findByPostedByAndMessageTextAndTimePostedEpoch(userID,message,timePosted).get();
        }
    }

    public List<Message> fetchMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> fetchMessageByID(Integer messageID){
        return messageRepository.findById(messageID);
    }

    public Integer deleteMessageByID(Integer messageID){
        if(messageRepository.existsById(messageID)){
            messageRepository.deleteById(messageID);
        return 1;
        }
        return null;
    }

    public Integer updateMessage(Integer messageID, String newMessage){
        if(messageRepository.findById(messageID).isEmpty() || newMessage.trim().isEmpty() || newMessage.length() > 255){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else{
            Message oldMessage = messageRepository.findById(messageID).get();
            oldMessage.setMessageText(newMessage);
            messageRepository.save(oldMessage);
            
            return 1;
        }
    }

    public List<Message> getMessagesByUserID(Integer userID){
        return messageRepository.findByPostedBy(userID).get();
    }

}
