package com.unioncast.db.rdbms.core.dao.adx;

import com.unioncast.common.adx.model.AdxSspReportFormDay;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface AdxSspReportFormDayDao extends GeneralDao<AdxSspReportFormDay, Long> {

	Pagination<AdxSspReportFormDay> page(PageCriteria pageCriteria);

}
