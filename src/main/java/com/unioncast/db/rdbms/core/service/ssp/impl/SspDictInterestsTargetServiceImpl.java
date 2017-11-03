package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictInterestsTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictInterestsTargetDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictInterestsTargetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictInterestsTargetService")
@Transactional
public class SspDictInterestsTargetServiceImpl extends SspGeneralService<SspDictInterestsTarget, Long> implements SspDictInterestsTargetService {
    @Resource
    private SspDictInterestsTargetDao sspDictInterestsTargetDao;

    @Autowired
    @Qualifier("sspDictInterestsTargetDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictInterestsTarget, Long> generalDao) {
        this.generalDao = generalDao;
    }

	@Override
	public SspDictInterestsTarget[] batchFindbyCodes(String[] codes) {
		return sspDictInterestsTargetDao.batchFindbyCodes(codes);
	}
}
