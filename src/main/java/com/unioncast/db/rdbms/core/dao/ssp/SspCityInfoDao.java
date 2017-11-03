package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspCityInfoDao extends GeneralDao<SspCityInfo , Long> {

    SspCityInfo[] batchFindbyCodes(String[] codes);
}
