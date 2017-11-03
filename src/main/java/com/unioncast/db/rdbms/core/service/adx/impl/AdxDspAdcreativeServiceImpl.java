package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unioncast.common.adx.model.AdxDspAdcreative;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAdcreativeService;

@Service("adxDspAdcreativeService")
public class AdxDspAdcreativeServiceImpl extends AdxDBGeneralService<AdxDspAdcreative, Long> implements AdxDspAdcreativeService {
	@Resource
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspAdcreative, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}
}
