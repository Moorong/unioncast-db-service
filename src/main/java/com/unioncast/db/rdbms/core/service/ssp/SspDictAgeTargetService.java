package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictAgeTarget;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspDictAgeTargetService extends GeneralService<SspDictAgeTarget, Long> {

	SspDictAgeTarget[] batchFindbyCodes(String[] codes);

}
