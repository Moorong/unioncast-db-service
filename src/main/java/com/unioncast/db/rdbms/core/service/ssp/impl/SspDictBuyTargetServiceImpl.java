package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictBuyTargetDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictBuyTargetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictBuyTargetService")
@Transactional
public class SspDictBuyTargetServiceImpl extends SspGeneralService<SspDictBuyTarget, Long> implements SspDictBuyTargetService {
    @Resource
    private SspDictBuyTargetDao sspDictBuyTargetDao;

    @Autowired
    @Qualifier("sspDictBuyTargetDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictBuyTarget, Long> generalDao) {
        this.generalDao = generalDao;
    }

	@Override
	public SspDictBuyTarget[] batchFindbyCodes(String[] codes) {
		
		return sspDictBuyTargetDao.batchFindbyCodes(codes);
	}
}
