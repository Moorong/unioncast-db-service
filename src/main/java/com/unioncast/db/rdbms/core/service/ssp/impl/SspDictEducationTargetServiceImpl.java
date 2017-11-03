package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictEducationTargetDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictEducationTargetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictEducationTargetService")
@Transactional
public class SspDictEducationTargetServiceImpl extends SspGeneralService<SspDictEducationTarget, Long> implements SspDictEducationTargetService {
    @Resource
    private SspDictEducationTargetDao sspDictEducationTargetDao;

    @Autowired
    @Qualifier("sspDictEducationTargetDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictEducationTarget, Long> generalDao) {
        this.generalDao = generalDao;
    }

	@Override
	public SspDictEducationTarget[] batchFindbyCodes(String[] codes) {
		return sspDictEducationTargetDao.batchFindbyCodes(codes);
	}
}
