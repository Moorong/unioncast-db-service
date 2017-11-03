package com.unioncast.db.rdbms.core.service.ssp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.ssp.model.SspPlanTargetCondition;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlanTargetConditionDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanTargetConditionService;

@Service("sspPlanTargetConditionService")
@Transactional
public class SspPlanTargetConditionServiceImpl extends SspGeneralService<SspPlanTargetCondition, Long> implements
		SspPlanTargetConditionService {

	private SspPlanTargetConditionDao sspPlanTargetConditionDao;

	@Autowired
	@Qualifier("sspPlanTargetConditionDao")
	@Override
	public void setGeneralDao(SspGeneralDao<SspPlanTargetCondition, Long> generalDao) {
		this.generalDao = generalDao;
		this.sspPlanTargetConditionDao = (SspPlanTargetConditionDao) generalDao;
	}

	@Override
	public SspPlanTargetCondition findById(Long aLong) throws DaoException {
		SspPlanTargetCondition[] sspPlanTargetConditions = sspPlanTargetConditionDao.find(aLong);
		if (null != sspPlanTargetConditions && sspPlanTargetConditions.length > 0)
			return sspPlanTargetConditions[0];
		return null;
	}
}
