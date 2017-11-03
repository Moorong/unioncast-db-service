
package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspPlatformHourReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspPlatformHourReportService;
import org.springframework.stereotype.Service;

@Service
public class SspPlatformHourReportServiceImpl implements SspPlatformHourReportService {

    @Override
    public Pagination<SspPlatformHourReport> page(PageCriteria entity) throws DaoException {
		return null;
    }
}
