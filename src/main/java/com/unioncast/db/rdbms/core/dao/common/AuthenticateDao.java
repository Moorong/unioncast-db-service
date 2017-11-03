package com.unioncast.db.rdbms.core.dao.common;

import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.AuthenticationApiInfo;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface AuthenticateDao extends GeneralDao<Authentication, Long> {
	
	/**
	 * 根据SystemId查询
	 * 
	 * @author zhangzhe
	 * @date 2016年10月9日 上午10:41:51
	 * 
	 * @param id
	 * @return
	 * 
	 */
	public Authentication findBySysId(Long id) throws DaoException;
	
	public int updateNotNullFieldAndReturn(Authentication authentications) throws DaoException;

	public Long[] batchAddAuthenticationApiInfo(AuthenticationApiInfo[] authenticationApiInfos) throws DaoException;


	public int batchDeleteAuthApiInfoByAuthId(Long[] ids) throws DaoException;


	public int deleteAuthApiInfoByApiInfoId(Long id) throws DaoException;


	public int deleteAuthApiInfoByAuthId(Long id) throws DaoException;

	public int batchDeleteAuthApiInfoByApiInfoId(Long[] ids) throws DaoException;

	ApiInfo[] findApiInfoByAuthId(Long id) throws DaoException;

    Authentication[] findAuthByAuth(Authentication authentication);
}

