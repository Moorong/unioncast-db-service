package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspMediaHourReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspMediaHourReportService;
import org.springframework.stereotype.Service;

@Service
public class SspMediaHourReportServiceImpl implements SspMediaHourReportService {

    @Override
    public Pagination<SspMediaHourReport> page(PageCriteria entity) throws DaoException {
		return null;
    }
}
