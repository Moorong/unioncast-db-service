package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspDictEducationTargetService extends GeneralService<SspDictEducationTarget, Long> {

	SspDictEducationTarget[] batchFindbyCodes(String[] codes);

}
