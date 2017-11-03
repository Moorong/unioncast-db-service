package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspDictBuyTargetDao extends GeneralDao<SspDictBuyTarget , Long> {

	SspDictBuyTarget[] batchFindbyCodes(String[] codes);

}
