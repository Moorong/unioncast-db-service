package com.unioncast.db.rdbms.core.service.adx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspUser;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspUserService;

@Service("adxDspUserService")
@Transactional
public class AdxDspUserServiceImpl extends AdxDBGeneralService<AdxDspUser, Long> implements AdxDspUserService {

	@Autowired
	@Qualifier("adxDspUserDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspUser, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

}
