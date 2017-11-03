package com.unioncast.db.rdbms.core.service.adx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspReportFormDay;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspReportFormDayService;

@Service("adxDspReportFormDayService")
@Transactional
public class AdxDspReportFormDayServiceImpl extends AdxDBGeneralService<AdxDspReportFormDay, Long>
		implements AdxDspReportFormDayService {

	@Autowired
	@Qualifier("adxDspReportFormDayDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspReportFormDay, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

}
