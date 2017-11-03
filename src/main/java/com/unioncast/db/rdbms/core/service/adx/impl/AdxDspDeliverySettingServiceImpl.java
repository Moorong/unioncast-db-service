package com.unioncast.db.rdbms.core.service.adx.impl;

import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxDspDeliverySettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspDeliverySetting;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;

import java.util.List;

@Service("adxDspDeliverySettingService")
@Transactional
public class AdxDspDeliverySettingServiceImpl extends AdxDBGeneralService<AdxDspDeliverySetting, Long>
		implements AdxDspDeliverySettingService {

	@Autowired
	@Qualifier("adxDspDeliverySettingDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspDeliverySetting, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Override
	public AdxDspDeliverySetting[] findByAdxOrSspId(Long adxOrSspId) throws DaoException {
		return generalDao.findByAdxOrSspId(adxOrSspId);
	}
}
