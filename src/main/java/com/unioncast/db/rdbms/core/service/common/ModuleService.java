package com.unioncast.db.rdbms.core.service.common;

import com.unioncast.common.user.model.Module;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface ModuleService extends GeneralService<Module, Long> {

	public Module[] findByRoleId(Long id);
	
	public int delRoleModById(Long[] ids) throws DaoException;
	
	/**
	 * 根据(systemId)系统id查询模块
	 * @author zylei
	 * @data   2016年12月19日 下午2:34:09
	 * @param systemId
	 * @return
	 */
	public Module[] findModuleBySystemId(Long systemId);
	
	/**
	 * 根据模块名称和系统id查找模块
	 * @author zylei
	 * @data   2016年12月19日 下午2:38:58
	 * @param moduleName
	 * @param systemId
	 * @return
	 */
	public Module[] findByNameAndSystem(String moduleName, Long systemId);

}
