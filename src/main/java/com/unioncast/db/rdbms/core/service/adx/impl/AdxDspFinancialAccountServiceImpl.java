package com.unioncast.db.rdbms.core.service.adx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspFinancialAccount;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspFinancialAccountService;

@Service("adxDspFinancialAccountService")
@Transactional
public class AdxDspFinancialAccountServiceImpl extends AdxDBGeneralService<AdxDspFinancialAccount, Long>
		implements AdxDspFinancialAccountService {

	@Autowired
	@Qualifier("adxDspFinancialAccountDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspFinancialAccount, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

}
