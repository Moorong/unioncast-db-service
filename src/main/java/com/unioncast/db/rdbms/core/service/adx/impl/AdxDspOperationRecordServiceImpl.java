package com.unioncast.db.rdbms.core.service.adx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspOperationRecord;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.service.adx.AdxDspOperationRecordService;

@Service("adxDspOperationRecordService")
@Transactional
public class AdxDspOperationRecordServiceImpl extends AdxDBGeneralService<AdxDspOperationRecord, Long>
		implements AdxDspOperationRecordService {

	@Autowired
	@Qualifier("adxDspOperationRecordDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspOperationRecord, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

}
