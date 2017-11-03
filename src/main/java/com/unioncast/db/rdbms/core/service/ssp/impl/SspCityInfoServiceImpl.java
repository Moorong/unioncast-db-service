package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspCityInfoDao;
import com.unioncast.db.rdbms.core.service.ssp.SspCityInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspCityInfoService")
@Transactional
public class SspCityInfoServiceImpl extends SspGeneralService<SspCityInfo, Long> implements SspCityInfoService {
    @Resource
    private SspCityInfoDao sspCityInfoDao;

    @Autowired
    @Qualifier("sspCityInfoDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspCityInfo, Long> generalDao) {
        this.generalDao = generalDao;
    }

    @Override
    public SspCityInfo[] batchFindbyCodes(String[] codes) {
        return sspCityInfoDao.batchFindbyCodes(codes);
    }
}
