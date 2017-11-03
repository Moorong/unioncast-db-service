package com.unioncast.db.rdbms.core.service.adx;

import com.unioncast.common.adx.model.AdxSspFinanceSetting;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface AdxSspFinanceSettingService extends GeneralService<AdxSspFinanceSetting, Long> {

	Pagination<AdxSspFinanceSetting> page(PageCriteria pageCriteria);

}
