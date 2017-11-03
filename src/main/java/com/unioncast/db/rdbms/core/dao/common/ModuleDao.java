package com.unioncast.db.rdbms.core.dao.common;

import com.unioncast.common.user.model.Module;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface ModuleDao extends GeneralDao<Module, Long> {

	public Module[] findByRoleId(Long id);

	public int delRoleModById(Long[] ids) throws DaoException;
	
	public Module[] findModuleBySystemId(Long systemId);
	
	public Module[] findByNameAndSystem(String moduleName, Long systemId);

}
