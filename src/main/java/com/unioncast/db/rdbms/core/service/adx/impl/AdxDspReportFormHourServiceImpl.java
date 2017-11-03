package com.unioncast.db.rdbms.core.service.adx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspReportFormHour;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspReportFormHourService;

@Service("adxDspReportFormHourService")
@Transactional
public class AdxDspReportFormHourServiceImpl extends AdxDBGeneralService<AdxDspReportFormHour, Long>
		implements AdxDspReportFormHourService {

	@Autowired
	@Qualifier("adxDspReportFormHourDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspReportFormHour, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

}
