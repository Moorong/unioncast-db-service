package com.unioncast.db.rdbms.core.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.user.model.Qualification;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.QualificationService;

@Service("qualificationService")
@Transactional
public class QualificationServiceImpl extends CommonDBGeneralService<Qualification, Long>
		implements QualificationService {

	@Autowired
	@Qualifier("qualificationDao")
	@Override
	public void setGeneralDao(CommonGeneralDao<Qualification, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Override
	public Qualification[] findByUserId(Long id) throws DaoException {
		return generalDao.findByUserId(id);
	}

	@Override
	public Qualification[] findBySystemId(Long id) throws DaoException {
		return generalDao.findBySystemId(id);
	}

}
