package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspAdRegionDayReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspAdRegionDayReportService;
import org.springframework.stereotype.Service;

@Service
public class SspAdRegionDayReportServiceImpl implements SspAdRegionDayReportService {

    @Override
    public Pagination<SspAdRegionDayReport> page(PageCriteria entity) throws DaoException {
        return null;
    }
}
