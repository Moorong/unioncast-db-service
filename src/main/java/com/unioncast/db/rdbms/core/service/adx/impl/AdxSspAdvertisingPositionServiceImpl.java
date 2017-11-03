package com.unioncast.db.rdbms.core.service.adx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxSspAdvertisingPosition;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxSspAdvertisingPositionService;

@Service("adxSspAdvertisingPositionService")
@Transactional
public class AdxSspAdvertisingPositionServiceImpl extends AdxDBGeneralService<AdxSspAdvertisingPosition, Long>
		implements AdxSspAdvertisingPositionService {

	@Autowired
	@Qualifier("adxSspAdvertisingPositionDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxSspAdvertisingPosition, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

}
