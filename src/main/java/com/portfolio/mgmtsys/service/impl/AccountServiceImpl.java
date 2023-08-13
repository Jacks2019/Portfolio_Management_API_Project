package com.portfolio.mgmtsys.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.portfolio.mgmtsys.domain.Account;
import com.portfolio.mgmtsys.repository.AccountRepo;
import com.portfolio.mgmtsys.service.AccountService;
import org.springframework.stereotype.Service;

@Service
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

    public Account register(Account account){
        Account accountToBeRegistered = new Account();
        accountToBeRegistered.setName(account.getName());
        Example<Account> nameToBeRegistered = Example.of(accountToBeRegistered);
        Optional<Account> foundAccount = repo.findOne(nameToBeRegistered);
        if(foundAccount.isPresent()){
            return null;
        }
        repo.save(account);
        Account registeredAccount = repo.findOne(nameToBeRegistered).get();
        registeredAccount.setPassword(null);
        return registeredAccount;
    }
}
