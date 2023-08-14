package com.portfolio.mgmtsys.service;

import com.portfolio.mgmtsys.domain.Account;
import com.portfolio.mgmtsys.domain.Assets;

public interface AssetsService {
    Assets getAssetsById(Integer id);
    Assets transferIn(Account account);
    Assets transferOut(Account account);
}
