package com.unioncast.db.rdbms.core.service.ssp.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictCrowdSexDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictCrowdSexService;

@Service("sspDictCrowdSexService")
@Transactional
public class SspDictCrowdSexServiceImpl extends SspGeneralService<SspDictCrowdSexType, Long>  implements SspDictCrowdSexService{
	 @Resource
	    private SspDictCrowdSexDao sspDictCrowdSexDao;

	    @Autowired
	    @Qualifier("sspDictCrowdSexDao")
	    @Override
	    public void setGeneralDao(SspGeneralDao<SspDictCrowdSexType, Long> generalDao) {
	        this.generalDao = generalDao;
	    }

		@Override
		public SspDictCrowdSexType[] batchFindbyCodes(String[] codes) {
			
			return sspDictCrowdSexDao.batchFindbyCodes(codes);
		}
}
