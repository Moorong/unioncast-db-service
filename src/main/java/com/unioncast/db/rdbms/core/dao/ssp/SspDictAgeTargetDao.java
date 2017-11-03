package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictAgeTarget;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspDictAgeTargetDao extends GeneralDao<SspDictAgeTarget , Long> {

	SspDictAgeTarget[] batchFindbyCodes(String[] codes);

}
