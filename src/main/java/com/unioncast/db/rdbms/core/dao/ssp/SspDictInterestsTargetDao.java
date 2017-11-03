package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictInterestsTarget;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspDictInterestsTargetDao extends GeneralDao<SspDictInterestsTarget , Long> {

	SspDictInterestsTarget[] batchFindbyCodes(String[] codes);

}
