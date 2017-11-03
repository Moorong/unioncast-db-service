package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspCreativeReport;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspCreativeReportDao;
import com.unioncast.db.rdbms.core.service.ssp.SspCreativeReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspCreativeReportService")
@Transactional
public class SspCreativeReportServiceImpl extends SspGeneralService<SspCreativeReport, Long> implements SspCreativeReportService {
    @Resource
    private SspCreativeReportDao sspCreativeReportDao;

    @Autowired
    @Qualifier("sspCreativeReportDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspCreativeReport, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
