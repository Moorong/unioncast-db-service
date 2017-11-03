package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspCityInfoService extends GeneralService<SspCityInfo, Long> {

    SspCityInfo[] batchFindbyCodes(String[] codes);

}
