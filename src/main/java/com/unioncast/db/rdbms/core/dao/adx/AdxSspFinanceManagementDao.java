package com.unioncast.db.rdbms.core.dao.adx;

import com.unioncast.common.adx.model.AdxSspFinanceManagement;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface AdxSspFinanceManagementDao extends GeneralDao<AdxSspFinanceManagement, Long> {
	
	Pagination<AdxSspFinanceManagement> page(PageCriteria pageCriteria);

}
