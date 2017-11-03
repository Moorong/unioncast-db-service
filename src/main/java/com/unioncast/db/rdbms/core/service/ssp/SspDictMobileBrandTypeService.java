package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictMobileBrandType;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspDictMobileBrandTypeService extends GeneralService<SspDictMobileBrandType, Long> {

	SspDictMobileBrandType[] batchFindbyCodes(String[] codes);

}
