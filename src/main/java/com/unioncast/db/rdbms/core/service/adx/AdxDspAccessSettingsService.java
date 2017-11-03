package com.unioncast.db.rdbms.core.service.adx;

import java.sql.SQLException;

import com.unioncast.common.adx.model.AdxDspAccessSettings;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface AdxDspAccessSettingsService extends GeneralService<AdxDspAccessSettings, Long> {

	AdxDspAccessSettings findByDspId(long dspId);

	//Pagination<AdxDspAccessSettings> page(PageCriteria pageCriteria);

	AdxDspAccessSettings procedure(Long id) throws SQLException;

	AdxDspAccessSettings[] findByFlowType(Integer flowType);

}
