package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictCreativeSize;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictCreativeSizeDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictCreativeSizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictCreativeSizeService")
@Transactional
public class SspDictCreativeSizeServiceImpl extends SspGeneralService<SspDictCreativeSize, Long> implements SspDictCreativeSizeService {
    @Resource
    private SspDictCreativeSizeDao sspDictCreativeSizeDao;

    @Autowired
    @Qualifier("sspDictCreativeSizeDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictCreativeSize, Long> generalDao) {
        this.generalDao = generalDao;
    }
}
