
package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspPlatformDayReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspPlatformDayReportService;
import org.springframework.stereotype.Service;

@Service
public class SspPlatformDayReportServiceImpl implements SspPlatformDayReportService {

    @Override
    public Pagination<SspPlatformDayReport> page(PageCriteria entity) throws DaoException {
		return null;
    }
}
