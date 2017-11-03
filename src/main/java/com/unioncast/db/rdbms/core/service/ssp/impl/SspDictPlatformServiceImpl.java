package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictPlatform;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictPlatformDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictPlatformService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictPlatformService")
@Transactional
public class SspDictPlatformServiceImpl extends SspGeneralService<SspDictPlatform, Long> implements SspDictPlatformService {
    @Resource
    private SspDictPlatformDao sspDictPlatformDao;

    @Autowired
    @Qualifier("sspDictPlatformDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictPlatform, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
