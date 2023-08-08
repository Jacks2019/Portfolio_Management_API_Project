package com.portfolio.mgmtsys.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.mgmtsys.domain.Account;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Account account){
        System.out.println(account);
        return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
    }
}
