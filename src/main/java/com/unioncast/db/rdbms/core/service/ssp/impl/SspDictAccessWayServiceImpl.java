package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictAccessWay;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAccessWayDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAccessWayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictAccessWayService")
@Transactional
public class SspDictAccessWayServiceImpl extends SspGeneralService<SspDictAccessWay, Long> implements SspDictAccessWayService {
    @Resource
    private SspDictAccessWayDao sspDictAccessWayDao;

    @Autowired
    @Qualifier("sspDictAccessWayDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictAccessWay, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
