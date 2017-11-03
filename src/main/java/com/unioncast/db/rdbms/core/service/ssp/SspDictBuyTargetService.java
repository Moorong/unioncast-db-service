package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspDictBuyTargetService extends GeneralService<SspDictBuyTarget, Long> {

	SspDictBuyTarget[] batchFindbyCodes(String[] codes);

}
