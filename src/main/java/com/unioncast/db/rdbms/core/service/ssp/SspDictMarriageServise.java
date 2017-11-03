package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspDictMarriageServise  extends GeneralService<SspDictMarriageTarget, Long> {

	SspDictMarriageTarget[] batchFindbyCodes(String[] codes);

}
