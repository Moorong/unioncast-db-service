package com.unioncast.db.rdbms.core.dao.adx;

import java.sql.SQLException;

import com.unioncast.common.adx.model.AdxDspAccessSettings;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface AdxDspAccessSettingsDao extends GeneralDao<AdxDspAccessSettings, Long> {

	AdxDspAccessSettings findByDspId(long dspId);

	//Pagination<AdxDspAccessSettings> page(PageCriteria pageCriteria);

	AdxDspAccessSettings procedure(Long id) throws SQLException;

	AdxDspAccessSettings[] findByFlowType(Integer flowType);

}
