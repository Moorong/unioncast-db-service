package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictIncomeTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspDictIncomeService  extends GeneralService<SspDictIncomeTarget,Long>{

	SspDictIncomeTarget[] batchFindbyCodes(String[] codes);

}
