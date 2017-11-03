package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface SspPlanCreativeRelationDao extends GeneralDao<SspPlanCreativeRelation , Long> {

	SspPlanCreativeRelation[] findByAdvertiserId(Long advertiserId) throws DaoException;
	SspPlanCreativeRelation[] findByPlanId(Long planId) throws DaoException;

	int addPlanCreativeRelations(SspPlanCreativeRelation[] relations) throws DaoException;

	int changePlanCreativeRelationState(SspPlanCreativeRelation params);

	int deletePlanCreativeRelationById(SspPlanCreativeRelation pcr);

}
