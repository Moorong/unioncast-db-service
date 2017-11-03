package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxSspFinanceSetting;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspFinanceSettingDao;
import com.unioncast.db.rdbms.core.service.adx.AdxSspFinanceSettingService;

@Service("adxSspFinanceSettingService")
@Transactional
public class AdxSspFinanceSettingServiceImpl extends AdxDBGeneralService<AdxSspFinanceSetting, Long>
		implements AdxSspFinanceSettingService {

	@Autowired
	@Qualifier("adxSspFinanceSettingDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxSspFinanceSetting, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Resource
	private AdxSspFinanceSettingDao adxSspFinanceSettingDao;

	@Override
	public Pagination<AdxSspFinanceSetting> page(PageCriteria pageCriteria) {
		return adxSspFinanceSettingDao.page(pageCriteria);
	}

}
