package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspMediaDayReport;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspMediaDayReportService;
import org.springframework.stereotype.Service;

@Service
public class SspMediaDayReportServiceImpl implements SspMediaDayReportService {

    @Override
    public Pagination<SspMediaDayReport> page(PageCriteria entity) throws DaoException {
		return null;
    }
}
