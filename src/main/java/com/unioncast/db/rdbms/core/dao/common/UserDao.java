package com.unioncast.db.rdbms.core.dao.common;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.user.model.*;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface UserDao extends GeneralDao<User, Long> {

    public User findByString(String str) throws DaoException;

    public int updateNotNullFieldAndReturn(User user) throws DaoException;

    public User findByUsername(String username) throws DaoException;

    public User findByEmail(String email) throws DaoException;

    public int deleteUserRoleByUserId(Long id) throws DaoException;

    public int batchAddOneUesrIdAndManyRoleId(Long uesrId, Long[] roleIds) throws DaoException;

    public int batchAddManyUesrIdAndOneRoleId(Long roleId, Long[] uesrIds) throws DaoException;

    public int deleteUserRoleByRoleId(Long id) throws DaoException;

    public int batchDeleteUserRoleByRoleId(Long[] ids) throws DaoException;

    public Long[] batchAddUserRole(UserRole[] userRoles) throws DaoException;

    public Role[] findRoleByUserId(Long id) throws DaoException;

    public User checkbyLoginPWR(String loginname, String password, Long roleid) throws DaoException;

    Pagination<User> pageByRoleIds(PageCriteria pageCriteria) throws DaoException;

    UserCount findUserCount() throws DaoException;


    User[] findUserByUser(User user);

    Pagination<User> pageByRoleSysIds(PageCriteria pageCriteria);

    Pagination<User> sspUserPage(PageCriteria pageCriteria)throws DaoException;

    User[] findByRole(Role role);

    int updateByEmail(User user)throws DaoException;

    public Long[] batchAddUserSystem(UserSystem[] userSystems) throws DaoException;

    public int batchDeleteUserSystemByUserId(Long[] ids) throws DaoException;

    User[] findUserByUserSystem(User user)throws DaoException;
}
