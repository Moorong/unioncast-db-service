package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspPlatformReport;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlatformReportDao;
import com.unioncast.db.rdbms.core.service.ssp.SspPlatformReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspPlatformReportService")
@Transactional
public class SspPlatformReportServiceImpl extends SspGeneralService<SspPlatformReport, Long> implements SspPlatformReportService {

    @Resource
    private SspPlatformReportDao sspPlatformReportDao;

    @Autowired
    @Qualifier("sspPlatformReportDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspPlatformReport, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
