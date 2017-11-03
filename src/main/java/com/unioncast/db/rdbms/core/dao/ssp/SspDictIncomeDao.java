package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictIncomeTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspDictIncomeDao extends GeneralDao<SspDictIncomeTarget,Long>{

	SspDictIncomeTarget[] batchFindbyCodes(String[] codes);

}
