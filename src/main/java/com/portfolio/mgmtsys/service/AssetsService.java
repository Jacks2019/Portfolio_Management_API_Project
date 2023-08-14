package com.portfolio.mgmtsys.service;

import com.portfolio.mgmtsys.domain.Assets;

public interface AssetsService {
    Assets getAssets(Integer id);
    Assets transferIn(Integer id);
    Assets transferOut(Integer id);
}
