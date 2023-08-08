package com.portfolio.mgmtsys.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.portfolio.mgmtsys.domain.Account;
import com.portfolio.mgmtsys.repository.AccountRepo;
import com.portfolio.mgmtsys.service.AccountService;

public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo repo;

    public Integer login(Account account){
        Example<Account> nameAndPwd = Example.of(account);
        Optional<Account> verifiedAccount = repo.findOne(nameAndPwd);
        if(verifiedAccount.isPresent()){
            return verifiedAccount.get().getId();
        }
        return null;
    }
}
