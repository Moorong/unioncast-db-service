package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictMediaType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictMediaTypeDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictMediaTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictMediaTypeService")
@Transactional
public class SspDictMediaTypeServiceImpl extends SspGeneralService<SspDictMediaType, Long> implements SspDictMediaTypeService {
    @Resource
    private SspDictMediaTypeDao sspDictMediaTypeDao;

    @Autowired
    @Qualifier("sspDictMediaTypeDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictMediaType, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
