package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictAgeTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAgeTargetDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAgeTargetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictAgeTargetService")
@Transactional
public class SspDictAgeTargetServiceImpl extends SspGeneralService<SspDictAgeTarget, Long> implements SspDictAgeTargetService {
    @Resource
    private SspDictAgeTargetDao sspDictAgeTargetDao;

    @Autowired
    @Qualifier("sspDictAgeTargetDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictAgeTarget, Long> generalDao) {
        this.generalDao = generalDao;
    }

	@Override
	public SspDictAgeTarget[] batchFindbyCodes(String[] codes) {
		return sspDictAgeTargetDao.batchFindbyCodes(codes);
	}
}
