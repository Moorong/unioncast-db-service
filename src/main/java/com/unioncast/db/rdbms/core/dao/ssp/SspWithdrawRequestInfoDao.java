package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspWithdrawRequestInfo;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface SspWithdrawRequestInfoDao extends GeneralDao<SspWithdrawRequestInfo , Long> {

	SspWithdrawRequestInfo findByDeveloperId(Long developerId);

}
