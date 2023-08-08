package com.portfolio.mgmtsys.service;

import com.portfolio.mgmtsys.domain.Account;

public interface AccountService {
    Integer login(Account account);
    Account register(Account account);
}
