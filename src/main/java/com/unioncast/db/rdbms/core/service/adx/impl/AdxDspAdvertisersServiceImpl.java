package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspAdvertisers;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAdvertisersService;

@Service("adxDspAdvertisersService")
@Transactional
public class AdxDspAdvertisersServiceImpl extends AdxDBGeneralService<AdxDspAdvertisers, Long>
		implements AdxDspAdvertisersService {

	@Resource
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspAdvertisers, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}
}
