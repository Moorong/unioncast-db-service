package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspMediaRegionHourReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspMediaRegionHourReportService;
import org.springframework.stereotype.Service;

@Service
public class SspMediaRegionHourReportServiceImpl implements SspMediaRegionHourReportService {


    @Override
    public Pagination<SspMediaRegionHourReport> page(PageCriteria entity) throws DaoException {
        return null;
    }
}
