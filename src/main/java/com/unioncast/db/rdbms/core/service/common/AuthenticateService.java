package com.unioncast.db.rdbms.core.service.common;

import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.AuthenticationApiInfo;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface AuthenticateService extends GeneralService<Authentication, Long> {

    /**
     * @param id
     * @return
     * @throws DaoException
     * @author 根据SystemId查询
     * @date 2016年10月9日 上午10:40:21
     */
    public Authentication findBySysId(Long id) throws DaoException;

    public int updateNotNullFieldAndReturn(Authentication authentications) throws DaoException;

    public Long[] batchAddAuthenticationApiInfo(AuthenticationApiInfo[] authenticationApiInfos)
            throws DaoException;

    public int batchDeleteAuthApiInfoByAuthId(Long[] ids) throws DaoException;

    public int deleteAuthApiInfoByApiInfoId(Long id) throws DaoException;

    public int deleteAuthApiInfoByAuthId(Long id) throws DaoException;

    /**
     * 根据systemId和token验证权限
     *
     * @param systemId
     * @param token
     * @return
     * @author 琚超超
     * @date 2016年10月20日 下午5:06:49
     */
    public Authentication findByToken(Integer systemId, String token);

    public int batchDeleteAuthApiInfoByApiInfoId(Long[] ids) throws DaoException;

    ApiInfo[] findApiInfoByAuthId(Long id) throws DaoException;

    Authentication[] findAuthByAuth(Authentication authentication);
}
