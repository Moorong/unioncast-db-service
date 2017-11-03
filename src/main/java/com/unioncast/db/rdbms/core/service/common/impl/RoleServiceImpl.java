package com.unioncast.db.rdbms.core.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.RoleModule;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.dao.common.RoleDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl extends CommonDBGeneralService<Role, Long> implements RoleService {

	@Autowired
	@Qualifier("roleDao")
	@Override
	public void setGeneralDao(CommonGeneralDao<Role, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Autowired
	private RoleDao roleDao;
	

	@Override
	public Role[] findBySystemId(Long id) throws DaoException {
		return roleDao.findBySystemId(id);
	}
	
	@Override
	public int deleteRoleModule(Long roleId) {
		return roleDao.deleteRoleModule(roleId);
	}

	@Override
	public int batchDeleteRoleModule(Long[] roleIds) {
		return roleDao.batchDeleteRoleModule(roleIds);
	}

	@Override
	public int batchAddRoleModule(RoleModule[] roleModules) {
		return roleDao.batchAddRoleModule(roleModules);
	}

	@Override
	public Role findByName(String name) {
		return roleDao.findByName(name);
	}

	@Override
	public Role findByNameAndSystemId(String name, Integer systemId) {
		return roleDao.findByNameAndSystemId(name, systemId);
	}

}
