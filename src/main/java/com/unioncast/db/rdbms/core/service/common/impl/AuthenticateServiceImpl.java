package com.unioncast.db.rdbms.core.service.common.impl;

import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.AuthenticationApiInfo;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.dao.common.AuthenticateDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.AuthenticateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authenticateService")
@Transactional
public class AuthenticateServiceImpl extends CommonDBGeneralService<Authentication, Long>
		implements AuthenticateService {

    @Autowired
    AuthenticateDao authenticateDao;

	@Autowired
	@Qualifier("authenticateDao")
	@Override
	public void setGeneralDao(CommonGeneralDao<Authentication, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}
	
	
	@Override
	public Authentication findBySysId(Long id) throws DaoException {
		return generalDao.findBySysId(id);
	}


	@Override
	public int updateNotNullFieldAndReturn(Authentication authentications) throws DaoException {
		return generalDao.updateNotNullFieldAndReturn(authentications);
	}


	@Override
	public Long[] batchAddAuthenticationApiInfo(AuthenticationApiInfo[] authenticationApiInfos) throws DaoException{
		return generalDao.batchAddAuthenticationApiInfo(authenticationApiInfos);
	}


	@Override
	public int batchDeleteAuthApiInfoByAuthId(Long[] ids) throws DaoException {
		return generalDao.batchDeleteAuthApiInfoByAuthId(ids);
	}


	@Override
	public int deleteAuthApiInfoByApiInfoId(Long id) throws DaoException {
		return generalDao.deleteAuthApiInfoByApiInfoId(id);
	}


	@Override
	public int deleteAuthApiInfoByAuthId(Long id) throws DaoException {
		return generalDao.deleteAuthApiInfoByAuthId(id);
	}


	@Override
	public Authentication findByToken(Integer systemId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int batchDeleteAuthApiInfoByApiInfoId(Long[] ids) throws DaoException {
		return generalDao.batchDeleteAuthApiInfoByApiInfoId(ids);
	}

	@Override
	public ApiInfo[] findApiInfoByAuthId(Long id) throws DaoException {
		return generalDao.findApiInfoByAuthId(id);
	}

    @Override
    public Authentication[] findAuthByAuth(Authentication authentication) {
        return authenticateDao.findAuthByAuth(authentication);
    }
}
