package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictMobileBrandType;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspDictMobileBrandTypeDao extends GeneralDao<SspDictMobileBrandType , Long> {

	SspDictMobileBrandType[] batchFindbyCodes(String[] codes);

}
