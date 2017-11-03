package com.unioncast.db.rdbms.core.service.adx;

import com.unioncast.common.adx.model.AdxSspReportFormDay;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface AdxSspReportFormDayService extends GeneralService<AdxSspReportFormDay, Long> {

	Pagination<AdxSspReportFormDay> page(PageCriteria pageCriteria);

}
