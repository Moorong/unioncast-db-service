package com.unioncast.db.rdbms.core.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.user.model.SysDic;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.SysDicService;

@Service("sysDicService")
@Transactional
public class SysDicServiceImpl extends CommonDBGeneralService<SysDic, Long>implements SysDicService {

	@Autowired
	@Qualifier("sysDicDao")
	@Override
	public void setGeneralDao(CommonGeneralDao<SysDic, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	/* (non-Javadoc)
	 * @see com.unioncast.db.rdbms.common.service.GeneralService#find(java.io.Serializable)
	 */
	@Override
	public SysDic[] find(Long id) throws DaoException {
		return null;
	}

}
