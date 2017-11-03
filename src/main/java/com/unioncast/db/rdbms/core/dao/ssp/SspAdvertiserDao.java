package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspAdvertiserDao extends GeneralDao<SspAdvertiser , Long> {


    SspAdvertiser[] findByUserId(SspAdvertiser sspAdvertiser);
}
