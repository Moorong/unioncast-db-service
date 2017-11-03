package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxSspFinanceManagement;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspFinanceManagementDao;
import com.unioncast.db.rdbms.core.service.adx.AdxSspFinanceManagementService;

@Service("adxSspFinanceManagementService")
@Transactional
public class AdxSspFinanceManagementServiceImpl extends AdxDBGeneralService<AdxSspFinanceManagement, Long>
		implements AdxSspFinanceManagementService {

	@Autowired
	@Qualifier("adxSspFinanceManagementDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxSspFinanceManagement, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Resource
	private AdxSspFinanceManagementDao adxSspFinanceManagementDao;

	@Override
	public Pagination<AdxSspFinanceManagement> page(PageCriteria pageCriteria) {
		return adxSspFinanceManagementDao.page(pageCriteria);
	}

}
