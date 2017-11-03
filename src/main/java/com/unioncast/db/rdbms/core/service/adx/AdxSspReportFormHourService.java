package com.unioncast.db.rdbms.core.service.adx;

import com.unioncast.common.adx.model.AdxSspReportFormHour;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface AdxSspReportFormHourService extends GeneralService<AdxSspReportFormHour, Long> {

	Pagination<AdxSspReportFormHour> page(PageCriteria pageCriteria);

}
