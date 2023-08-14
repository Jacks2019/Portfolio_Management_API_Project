package com.portfolio.mgmtsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.Assets;

public interface AssetsRepo extends JpaRepository<Assets, Integer> {
    
}
