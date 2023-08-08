package com.portfolio.mgmtsys.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.mgmtsys.domain.Account;
import com.portfolio.mgmtsys.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    AccountService service;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Account account){
        Integer accountId = service.login(account);
        if(accountId!=null){
            Map<String, Object> body = new HashMap<>();
            body.put("id", accountId);
            return new ResponseEntity<Object>(body, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Account account){
        Account registeredAccount = service.register(account);
        if(registeredAccount!=null){
            return new ResponseEntity<Object>(registeredAccount, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<Object>("Account name may already exist.", HttpStatus.CONFLICT);
    }
}
