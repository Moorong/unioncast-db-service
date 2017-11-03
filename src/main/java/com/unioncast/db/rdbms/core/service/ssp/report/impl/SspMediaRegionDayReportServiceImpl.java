package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspMediaRegionDayReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspMediaRegionDayReportService;
import org.springframework.stereotype.Service;

@Service
public class SspMediaRegionDayReportServiceImpl implements SspMediaRegionDayReportService {

    @Override
    public Pagination<SspMediaRegionDayReport> page(PageCriteria entity) throws DaoException {
		return null;
    }
}
