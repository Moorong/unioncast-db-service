package com.unioncast.db.rdbms.core.service.ssp.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictMarriageDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictMarriageServise;

@Service("sspDictMarriageService")
@Transactional
public class SspDictMarriageTargetServiceImpl extends SspGeneralService<SspDictMarriageTarget, Long> implements
		SspDictMarriageServise {

	@Resource
	private SspDictMarriageDao sspDictMarriageDao;

	@Autowired
	@Qualifier("sspDictMarriageDao")
	@Override
	public void setGeneralDao(SspGeneralDao<SspDictMarriageTarget, Long> generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	public SspDictMarriageTarget[] batchFindbyCodes(String[] codes) {
		
		return sspDictMarriageDao.batchFindbyCodes(codes);
	}
}
