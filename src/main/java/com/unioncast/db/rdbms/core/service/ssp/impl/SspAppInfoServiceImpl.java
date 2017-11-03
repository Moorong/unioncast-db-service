package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspAppInfo;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspAppInfoDao;
import com.unioncast.db.rdbms.core.service.ssp.SspAppInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspAppInfoService")
@Transactional
public class SspAppInfoServiceImpl extends SspGeneralService<SspAppInfo, Long> implements SspAppInfoService {
    @Resource
    private SspAppInfoDao sspAppInfoDao;

    @Autowired
    @Qualifier("sspAppInfoDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspAppInfo, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
