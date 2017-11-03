package com.unioncast.db.rdbms.core.service.common;

import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.RoleModule;
import com.unioncast.db.rdbms.common.service.GeneralService;

public interface RoleService extends GeneralService<Role, Long> {

	int deleteRoleModule(Long roleId);

	int batchDeleteRoleModule(Long[] roleIds);

	int batchAddRoleModule(RoleModule[] roleModules);

	Role findByName(String name);

	Role findByNameAndSystemId(String name, Integer systemId);

}
