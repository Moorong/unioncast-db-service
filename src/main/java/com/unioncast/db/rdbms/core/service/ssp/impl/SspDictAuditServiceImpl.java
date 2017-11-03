package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictAudit;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAuditDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictAuditService")
@Transactional
public class SspDictAuditServiceImpl extends SspGeneralService<SspDictAudit, Long> implements SspDictAuditService {
    @Resource
    private SspDictAuditDao sspDictAuditDao;

    @Autowired
    @Qualifier("sspDictAuditDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictAudit, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
