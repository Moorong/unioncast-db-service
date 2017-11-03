package com.unioncast.db.rdbms.core.service.common;

import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface ApiInfoService extends GeneralService<ApiInfo, Long> {
	
	/**
	 * 根据(systemId)系统id查询
	 * @author zylei
	 * @data   2016年12月26日 下午12:00:10
	 * @param systemId
	 * @return
	 */
	public ApiInfo[] findBySystemId(Long systemId) throws DaoException;
	
	/**
	 * 根据系统id和系统name查找模块
	 * @author zylei
	 * @data   2016年12月26日 下午2:05:42
	 * @param systemId
	 * @param systemName
	 * @return
	 */
	public ApiInfo[] findByIdAndName(Long systemId, String systemName);

}
