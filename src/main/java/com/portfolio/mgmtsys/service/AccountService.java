package com.portfolio.mgmtsys.service;

import com.portfolio.mgmtsys.domain.Account;
import org.springframework.stereotype.Service;

public interface AccountService {
    Integer login(Account account);
    Account register(Account account);
}
