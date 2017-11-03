package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictIndustry;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictIndustryDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictIndustryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictIndustryService")
@Transactional
public class SspDictIndustryServiceImpl extends SspGeneralService<SspDictIndustry, Long> implements SspDictIndustryService {
    @Resource
    private SspDictIndustryDao sspDictIndustryDao;

    @Autowired
    @Qualifier("sspDictIndustryDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictIndustry, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
