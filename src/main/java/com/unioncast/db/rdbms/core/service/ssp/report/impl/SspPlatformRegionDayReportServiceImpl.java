package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspPlatformRegionDayReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspPlatformRegionDayReportService;
import org.springframework.stereotype.Service;

@Service
public class SspPlatformRegionDayReportServiceImpl implements SspPlatformRegionDayReportService {

    @Override
    public Pagination<SspPlatformRegionDayReport> page(PageCriteria entity) throws DaoException {
		return null;
    }
}
