package com.unioncast.db.rdbms.core.dao.adx;

import com.unioncast.common.adx.model.AdxSspReportFormHour;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface AdxSspReportFormHourDao extends GeneralDao<AdxSspReportFormHour, Long> {

	Pagination<AdxSspReportFormHour> page(PageCriteria pageCriteria);

}
