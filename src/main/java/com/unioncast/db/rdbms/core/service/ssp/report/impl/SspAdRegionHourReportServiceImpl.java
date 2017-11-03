package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspAdRegionHourReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspAdRegionHourReportService;
import org.springframework.stereotype.Service;

@Service
public class SspAdRegionHourReportServiceImpl implements SspAdRegionHourReportService {


    @Override
    public Pagination<SspAdRegionHourReport> page(PageCriteria entity) throws DaoException {
        return null;
    }
}
