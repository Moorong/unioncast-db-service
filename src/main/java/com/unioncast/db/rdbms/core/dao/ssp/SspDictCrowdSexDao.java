package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspDictCrowdSexDao extends GeneralDao<SspDictCrowdSexType , Long>{

	SspDictCrowdSexType[] batchFindbyCodes(String[] codes);

}
