package com.unioncast.db.rdbms.core.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.user.model.Module;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.ModuleService;

@Service("moduleService")
@Transactional
public class ModuleServiceImpl extends CommonDBGeneralService<Module, Long> implements ModuleService {

	@Autowired
	@Qualifier("moduleDao")
	@Override
	public void setGeneralDao(CommonGeneralDao<Module, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Override
	public Module[] findByRoleId(Long id) {
		return generalDao.findByRoleId(id);
	}

	@Override
	public int delRoleModById(Long[] ids) throws DaoException {
		return generalDao.delRoleModById(ids);
	}

	@Override
	public Module[] findModuleBySystemId(Long systemId) {
		return generalDao.findModuleBySystemId(systemId);
	}

	@Override
	public Module[] findByNameAndSystem(String moduleName, Long systemId) {
		return generalDao.findByNameAndSystem(moduleName, systemId);
	}

}
