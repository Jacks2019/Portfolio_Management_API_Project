package com.portfolio.mgmtsys.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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
    public Assets transferIn(Map<String, Object> request) {
        Integer id = (Integer)request.get("id");
        Double amount = (Double)request.get("amount");
        Optional<Assets> assetsOptional = repo.findById(id);
        if(!assetsOptional.isPresent()){
            return null;
        }
        Assets assets = assetsOptional.get();
        assets.setBalance(assets.getBalance() + amount);
        repo.save(assets);
        return assets;
    }

    @Override
    public Assets transferOut(Map<String, Object> request) {
        Integer id = (Integer)request.get("id");
        Double amount = (Double)request.get("amount");
        Optional<Assets> assetsOptional = repo.findById(id);
        if(!assetsOptional.isPresent()){
            return null;
        }
        Assets assets = assetsOptional.get();
        assets.setBalance(assets.getBalance() - amount);
        repo.save(assets);
        return assets;
    }
    
}
