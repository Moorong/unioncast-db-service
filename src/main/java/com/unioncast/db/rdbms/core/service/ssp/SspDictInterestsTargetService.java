package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictInterestsTarget;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspDictInterestsTargetService extends GeneralService<SspDictInterestsTarget, Long> {

	SspDictInterestsTarget[] batchFindbyCodes(String[] codes);

}
