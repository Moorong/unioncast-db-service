package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictSysOperationType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictSysOperationTypeDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictSysOperationTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictSysOperationTypeService")
@Transactional
public class SspDictSysOperationTypeServiceImpl extends SspGeneralService<SspDictSysOperationType, Long> implements SspDictSysOperationTypeService {

    @Resource
    private SspDictSysOperationTypeDao sspDictSysOperationTypeDao;

    @Autowired
    @Qualifier("sspDictSysOperationTypeDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictSysOperationType, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
