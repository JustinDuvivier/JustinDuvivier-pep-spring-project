package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com.example.entity.*;
import com.example.service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @Controller
public class SocialMediaController {

    public AccountService accountService;
    public MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerNewAccount(@RequestBody Account account){
        return ResponseEntity.ok(accountService.createUser(account.getUsername(), account.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        return ResponseEntity.ok(accountService.loginAccount(account.getUsername(), account.getPassword()));
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message){
        return ResponseEntity.ok(messageService.createMessage(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch()));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.fetchMessages());
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageByID(@PathVariable Integer messageId){
        return ResponseEntity.ok(messageService.fetchMessageByID(messageId).orElse(null));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByID(@PathVariable Integer messageId){
        Integer result = messageService.deleteMessageByID(messageId);
        return ResponseEntity.ok(result);
    }
    
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Map<String, String> body){
        return ResponseEntity.ok(messageService.updateMessage(messageId, body.get("messageText")));
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserID(@PathVariable Integer accountId){
        return ResponseEntity.ok(messageService.getMessagesByUserID(accountId));
    }

}
