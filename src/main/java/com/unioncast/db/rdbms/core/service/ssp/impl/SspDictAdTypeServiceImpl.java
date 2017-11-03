package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictAdType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAdTypeDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAdTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictAdTypeService")
@Transactional
public class SspDictAdTypeServiceImpl extends SspGeneralService<SspDictAdType, Long> implements SspDictAdTypeService {
    @Resource
    private SspDictAdTypeDao sspDictAdTypeDao;

    @Autowired
    @Qualifier("sspDictAdTypeDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictAdType, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
