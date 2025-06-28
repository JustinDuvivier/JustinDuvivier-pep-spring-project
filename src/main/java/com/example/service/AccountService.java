package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    public AccountRepository accountRepository;
    

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createUser(String username, String password){

        if(accountRepository.findByUsername(username).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        
        if(username.trim().isEmpty() || password.length() < 4){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Account newAccount = new Account(username,password);

        accountRepository.save(newAccount);

        return accountRepository.findByUsername(username).get();
        
    }

    public Account loginAccount(String username, String password){

        if(accountRepository.findByUsernameAndPassword(username,password).isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        else{
            return accountRepository.findByUsername(username).get();
        }

    }


}
