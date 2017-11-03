package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictAdPositionType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAdPositionTypeDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAdPositionTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictAdPositionTypeService")
@Transactional
public class SspDictAdPositionTypeServiceImpl extends SspGeneralService<SspDictAdPositionType, Long> implements SspDictAdPositionTypeService {
    @Resource
    private SspDictAdPositionTypeDao sspDictAdPositionTypeDao;

    @Autowired
    @Qualifier("sspDictAdPositionTypeDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictAdPositionType, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
