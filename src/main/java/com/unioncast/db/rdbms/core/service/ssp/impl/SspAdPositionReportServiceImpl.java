package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspAdPositionReport;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspAdPositionReportDao;
import com.unioncast.db.rdbms.core.service.ssp.SspAdPositionReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspAdPositionReportService")
@Transactional
public class SspAdPositionReportServiceImpl extends SspGeneralService<SspAdPositionReport, Long> implements SspAdPositionReportService {
    @Resource
    private SspAdPositionReportDao sspAdPositionReportDao;

    @Autowired
    @Qualifier("sspAdPositionReportDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspAdPositionReport, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
