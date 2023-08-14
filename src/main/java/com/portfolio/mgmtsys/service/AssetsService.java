package com.portfolio.mgmtsys.service;

import java.util.Map;

import com.portfolio.mgmtsys.domain.Assets;

public interface AssetsService {
    Assets getAssetsById(Integer id);
    Assets transferIn(Map<String, Object> request);
    Assets transferOut(Map<String, Object> request);
}
