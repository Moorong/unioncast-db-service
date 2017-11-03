package com.unioncast.db.rdbms.core.service.ssp.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlanCreativeRelationDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanCreativeRelationService;

@Service("sspPlanCreativeRelationService")
@Transactional
public class SspPlanCreativeRelationServiceImpl extends SspGeneralService<SspPlanCreativeRelation, Long> implements SspPlanCreativeRelationService {

    @Resource
    private SspPlanCreativeRelationDao sspPlanCreativeRelationDao;

    @Autowired
    @Qualifier("sspPlanCreativeRelationDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspPlanCreativeRelation, Long> generalDao) {
        this.generalDao = generalDao;
    }
	@Override
	public SspPlanCreativeRelation[] findByAdvertiserId(Long advertiserId) throws DaoException {
		return sspPlanCreativeRelationDao.findByAdvertiserId(advertiserId);
	}

	@Override
	public SspPlanCreativeRelation[] findByPlanId(Long planId) throws DaoException {
		return sspPlanCreativeRelationDao.findByPlanId(planId);
	}

	@Override
	public int addPlanCreativeRelations(SspPlanCreativeRelation[] relations) throws DaoException {
		return sspPlanCreativeRelationDao.addPlanCreativeRelations(relations);
	}

	
	public int changePlanCreativeRelationState(SspPlanCreativeRelation params) {
		return sspPlanCreativeRelationDao.changePlanCreativeRelationState(params);
	}

	@Override
	public int deletePlanCreativeRelationById(SspPlanCreativeRelation pcr) {
		
		return sspPlanCreativeRelationDao.deletePlanCreativeRelationById(pcr);
	}
}
