package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictMobileBrandType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictMobileBrandTypeDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictMobileBrandTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictMobileBrandTypeService")
@Transactional
public class SspDictMobileBrandTypeServiceImpl extends SspGeneralService<SspDictMobileBrandType, Long> implements SspDictMobileBrandTypeService {

    @Resource
    private SspDictMobileBrandTypeDao sspDictMobileBrandTypeDao;

    @Autowired
    @Qualifier("sspDictMobileBrandTypeDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictMobileBrandType, Long> generalDao) {
        this.generalDao = generalDao;
    }

	@Override
	public SspDictMobileBrandType[] batchFindbyCodes(String[] codes) {
		
		return sspDictMobileBrandTypeDao.batchFindbyCodes(codes);
	}
}
