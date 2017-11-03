package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxSspReportFormDay;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspReportFormDayDao;
import com.unioncast.db.rdbms.core.service.adx.AdxSspReportFormDayService;

@Service("adxSspReportFormDayService")
@Transactional
public class AdxSspReportFormDayServiceImpl extends AdxDBGeneralService<AdxSspReportFormDay, Long>
		implements AdxSspReportFormDayService {

	@Autowired
	@Qualifier("adxSspReportFormDayDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxSspReportFormDay, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Resource
	private AdxSspReportFormDayDao adxSspReportFormDayDao;

	@Override
	public Pagination<AdxSspReportFormDay> page(PageCriteria pageCriteria) {
		return adxSspReportFormDayDao.page(pageCriteria);
	}

}
