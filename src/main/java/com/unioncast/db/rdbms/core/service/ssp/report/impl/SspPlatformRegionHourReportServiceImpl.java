package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspPlatformRegionHourReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspPlatformRegionHourReportService;
import org.springframework.stereotype.Service;

@Service
public class SspPlatformRegionHourReportServiceImpl implements SspPlatformRegionHourReportService {

    @Override
    public Pagination<SspPlatformRegionHourReport> page(PageCriteria entity) throws DaoException {
		return null;
    }
}
