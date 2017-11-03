package com.unioncast.db.rdbms.core.service.adx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspQualifications;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspQualificationsService;

@Service("adxDspQualificationsService")
@Transactional
public class AdxDspQualificationsServiceImpl extends AdxDBGeneralService<AdxDspQualifications, Long>
		implements AdxDspQualificationsService {

	@Autowired
	@Qualifier("adxDspQualificationsDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspQualifications, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

}
