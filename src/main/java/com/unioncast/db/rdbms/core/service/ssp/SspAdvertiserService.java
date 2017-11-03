package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspAdvertiserService extends GeneralService<SspAdvertiser, Long> {

    SspAdvertiser[] findByUserId(SspAdvertiser sspAdvertiser);
}
