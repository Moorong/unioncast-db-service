package com.unioncast.db.rdbms.core.service.adx.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspAccessSettings;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspAccessSettingsDao;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAccessSettingsService;

@Service("adxDspAccessSettingsService")
@Transactional
public class AdxDspAccessSettingsServiceImpl extends AdxDBGeneralService<AdxDspAccessSettings, Long>
		implements AdxDspAccessSettingsService {

	@Resource
	private AdxDspAccessSettingsDao adxDspAccessSettingsDao;
	
	@Resource
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspAccessSettings, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Override
	public AdxDspAccessSettings findByDspId(long dspId) {
		return adxDspAccessSettingsDao.findByDspId(dspId);
	}

	@Override
	public AdxDspAccessSettings procedure(Long id) throws SQLException {
		return adxDspAccessSettingsDao.procedure(id);
	}

	@Override
	public AdxDspAccessSettings[] findByFlowType(Integer flowType) {
		return adxDspAccessSettingsDao.findByFlowType(flowType);
	}
}
