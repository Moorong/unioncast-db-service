package com.unioncast.db.rdbms.core.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.ApiInfoService;

@Service("apiInfoService")
@Transactional
public class ApiInfoServiceImpl extends CommonDBGeneralService<ApiInfo, Long> implements ApiInfoService {

	@Autowired
	@Qualifier("apiInfoDao")
	@Override
	public void setGeneralDao(CommonGeneralDao<ApiInfo, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}
	
	@Override
	public ApiInfo[] findBySystemId(Long systemId) throws DaoException {
		return generalDao.findBySystemId(systemId);
	}
	
	@Override
	public ApiInfo[] findByIdAndName(Long systemId, String systemName) {
		return generalDao.findByIdAndName(systemId, systemName);
	}
	
}
