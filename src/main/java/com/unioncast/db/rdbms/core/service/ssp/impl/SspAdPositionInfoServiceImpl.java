package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspAdPositionInfo;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspAdPositionInfoDao;
import com.unioncast.db.rdbms.core.service.ssp.SspAdPositionInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspAdPositionInfoService")
@Transactional
public class SspAdPositionInfoServiceImpl extends SspGeneralService<SspAdPositionInfo, Long> implements SspAdPositionInfoService {
    @Resource
    private SspAdPositionInfoDao sspAdPositionInfoDao;

    @Autowired
    @Qualifier("sspAdPositionInfoDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspAdPositionInfo, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
