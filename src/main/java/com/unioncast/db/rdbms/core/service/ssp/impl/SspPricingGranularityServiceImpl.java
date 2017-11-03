package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspPricingGranularity;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspPricingGranularityDao;
import com.unioncast.db.rdbms.core.service.ssp.SspPricingGranularityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @auther wangyao
 * @date 2017-04-24 19:36
 */
@Service("sspPricingGranularityService")
@Transactional
public class SspPricingGranularityServiceImpl extends SspGeneralService<SspPricingGranularity, Long> implements  SspPricingGranularityService{

    @Resource
    private SspPricingGranularityDao sspPricingGranularityDao;

    @Autowired
    @Qualifier("sspPricingGranularityDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspPricingGranularity, Long> generalDao) {
        this.generalDao = generalDao;
    }

}
