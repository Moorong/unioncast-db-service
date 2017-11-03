package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxSspReportFormHour;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspReportFormHourDao;
import com.unioncast.db.rdbms.core.service.adx.AdxSspReportFormHourService;

@Service("adxSspReportFormHourService")
@Transactional
public class AdxSspReportFormHourServiceImpl extends AdxDBGeneralService<AdxSspReportFormHour, Long>
		implements AdxSspReportFormHourService {

	@Autowired
	@Qualifier("adxSspReportFormHourDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxSspReportFormHour, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}
	
	@Resource
	private AdxSspReportFormHourDao adxSspReportFormHourDao;

	@Override
	public Pagination<AdxSspReportFormHour> page(PageCriteria pageCriteria) {
		return adxSspReportFormHourDao.page(pageCriteria);
	}

}
