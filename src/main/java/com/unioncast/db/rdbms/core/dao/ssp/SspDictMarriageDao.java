package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.dao.GeneralDao;


public interface SspDictMarriageDao extends GeneralDao<SspDictMarriageTarget , Long>{

	SspDictMarriageTarget[] batchFindbyCodes(String[] codes);

}
