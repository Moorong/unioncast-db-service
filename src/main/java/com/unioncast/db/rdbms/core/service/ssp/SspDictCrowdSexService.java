package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface  SspDictCrowdSexService  extends GeneralService<SspDictCrowdSexType, Long>{

	SspDictCrowdSexType[] batchFindbyCodes(String[] codes);

}
