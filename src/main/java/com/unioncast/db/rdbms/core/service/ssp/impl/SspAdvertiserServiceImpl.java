package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspAdvertiserDao;
import com.unioncast.db.rdbms.core.service.ssp.SspAdvertiserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspAdvertiserService")
@Transactional
public class SspAdvertiserServiceImpl extends SspGeneralService<SspAdvertiser, Long> implements SspAdvertiserService {
    @Resource
    private SspAdvertiserDao sspAdvertiserDao;

    @Autowired
    @Qualifier("sspAdvertiserDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspAdvertiser, Long> generalDao) {
        this.generalDao = generalDao;
    }

    @Override
    public SspAdvertiser[] findByUserId(SspAdvertiser sspAdvertiser) {
        return sspAdvertiserDao.findByUserId(sspAdvertiser);
    }
}
