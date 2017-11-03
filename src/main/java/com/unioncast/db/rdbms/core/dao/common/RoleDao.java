package com.unioncast.db.rdbms.core.dao.common;

import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.RoleModule;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

/**
* @author： 刘蓉
* @date： 2016年10月9日 上午9:41:41
* 类说明
*/
public interface RoleDao extends GeneralDao<Role, Long> {

	Role[] findBySystemId(Long id) throws DaoException;
	
	int deleteRoleModule(Long roleId);

	int batchDeleteRoleModule(Long[] roleIds);

	int batchAddRoleModule(RoleModule[] roleModules);

	Role findByName(String name);

	Role findByNameAndSystemId(String name, Integer systemId);

}
