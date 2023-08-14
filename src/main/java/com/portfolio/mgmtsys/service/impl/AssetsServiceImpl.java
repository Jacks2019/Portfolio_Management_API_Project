package com.portfolio.mgmtsys.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.portfolio.mgmtsys.domain.Account;
import com.portfolio.mgmtsys.domain.Assets;
import com.portfolio.mgmtsys.repository.AssetsRepo;
import com.portfolio.mgmtsys.service.AssetsService;

public class AssetsServiceImpl implements AssetsService {

    @Autowired
    AssetsRepo repo;

    @Override
    public Assets getAssetsById(Integer id) {
        Optional<Assets> assetsOptional = repo.findById(id);
        if(assetsOptional.isPresent()){
            return assetsOptional.get();
        }
        return null;
    }

    @Override
    public Assets transferIn(Account account) {
        throw new UnsupportedOperationException("Unimplemented method 'transferIn'");
    }

    @Override
    public Assets transferOut(Account account) {
        throw new UnsupportedOperationException("Unimplemented method 'transferOut'");
    }
    
}
