package com.unioncast.db.rdbms.core.dao.common;

import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface ApiInfoDao extends GeneralDao<ApiInfo, Long>{
	
	public ApiInfo[] findBySystemId(Long systemId) throws DaoException;
	
	public ApiInfo[] findByIdAndName(Long systemId, String systemName);

}
