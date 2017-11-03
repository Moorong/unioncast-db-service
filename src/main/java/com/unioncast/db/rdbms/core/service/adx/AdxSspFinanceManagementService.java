package com.unioncast.db.rdbms.core.service.adx;

import com.unioncast.common.adx.model.AdxSspFinanceManagement;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface AdxSspFinanceManagementService extends GeneralService<AdxSspFinanceManagement, Long> {

	Pagination<AdxSspFinanceManagement> page(PageCriteria pageCriteria);
}
