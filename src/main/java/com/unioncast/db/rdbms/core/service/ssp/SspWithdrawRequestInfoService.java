package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspWithdrawRequestInfo;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface SspWithdrawRequestInfoService extends GeneralService<SspWithdrawRequestInfo, Long> {

	SspWithdrawRequestInfo findByDeveloperId(Long id);

}
