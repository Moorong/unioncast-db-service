package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspFinanceInfo;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspFinanceInfoDao;
import com.unioncast.db.rdbms.core.service.ssp.SspFinanceInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspFinanceInfoService")
@Transactional
public class SspFinanceInfoServiceImpl extends SspGeneralService<SspFinanceInfo, Long> implements SspFinanceInfoService {

    @Resource
    private SspFinanceInfoDao sspFinanceInfoDao;

    @Autowired
    @Qualifier("sspFinanceInfoDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspFinanceInfo, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
