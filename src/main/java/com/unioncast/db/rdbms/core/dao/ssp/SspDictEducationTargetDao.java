package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspDictEducationTargetDao extends GeneralDao<SspDictEducationTarget , Long> {

	SspDictEducationTarget[] batchFindbyCodes(String[] codes);

}
