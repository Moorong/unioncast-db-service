package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspAccount;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAccountService;

@Service("adxDspAccountService")
@Transactional
public class AdxDspAccountServiceImpl extends AdxDBGeneralService<AdxDspAccount, Long> implements AdxDspAccountService {

	@Resource
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspAccount, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}
}
