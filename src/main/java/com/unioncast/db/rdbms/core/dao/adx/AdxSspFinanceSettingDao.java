package com.unioncast.db.rdbms.core.dao.adx;

import com.unioncast.common.adx.model.AdxSspFinanceSetting;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface AdxSspFinanceSettingDao extends GeneralDao<AdxSspFinanceSetting, Long> {
	
	Pagination<AdxSspFinanceSetting> page(PageCriteria pageCriteria);

}
