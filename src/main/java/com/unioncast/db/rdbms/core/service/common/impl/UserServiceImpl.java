package com.unioncast.db.rdbms.core.service.common.impl;

import com.unioncast.common.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl extends CommonDBGeneralService<User, Long> implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	@Qualifier("userDao")
	@Override
	public void setGeneralDao(CommonGeneralDao<User, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Override
	public User findByString(String str) throws DaoException {
		return generalDao.findByString(str);
	}

	// @Override
	// public User[] find(Long aLong) throws DaoException {
	// return new User[0];
	// }

	@Override
	public int updateNotNullFieldAndReturn(User user) throws DaoException {
		return generalDao.updateNotNullFieldAndReturn(user);
	}

	@Override
	public User findByUsername(String username) throws DaoException {
		return generalDao.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) throws DaoException {
		return generalDao.findByEmail(email);
	}

	@Override
	public int deleteUserRoleByUserId(Long id) throws DaoException {
		return generalDao.deleteUserRoleByUserId(id);
	}

	@Override
	public int deleteUserRoleByRoleId(Long id) throws DaoException {
		return generalDao.deleteUserRoleByRoleId(id);
	}

	@Override
	public int batchAddOneUesrIdAndManyRoleId(Long uesrId, Long[] roleIds) throws DaoException {
		return generalDao.batchAddOneUesrIdAndManyRoleId(uesrId, roleIds);
	}

	@Override
	public int batchAddManyUesrIdAndOneRoleId(Long roleId, Long[] uesrIds) throws DaoException {
		return generalDao.batchAddManyUesrIdAndOneRoleId(roleId, uesrIds);
	}

	@Override
	public int batchDeleteUserRoleByRoleId(Long[] ids) throws DaoException {
		return generalDao.batchDeleteUserRoleByRoleId(ids);
	}

	@Override
	public Long[] batchAddUserRole(UserRole[] userRoles) throws DaoException {
		return generalDao.batchAddUserRole(userRoles);
	}

	@Override
	public Role[] findRoleByUserId(Long id) throws DaoException {
		return generalDao.findRoleByUserId(id);
	}

	@Override
	public User checkbyLoginPWR(String loginname, String password, Long roleid) throws DaoException {
		return generalDao.checkbyLoginPWR(loginname, password, roleid);
	}

	@Override
	public Pagination<User> pageByRoleIds(PageCriteria pageCriteria) throws DaoException {
		return generalDao.pageByRoleIds(pageCriteria);
	}

	@Override
	public UserCount findUserCount() throws DaoException {
		return generalDao.findUserCount();
	}

	@Override
	public User[] findUserByUser(User user) {
		return userDao.findUserByUser(user);
	}

	@Override
	public Pagination<User> pageByRoleSysIds(PageCriteria pageCriteria) {
		return userDao.pageByRoleSysIds(pageCriteria);
	}

	@Override
	public Pagination<User> sspUserPage(PageCriteria pageCriteria) throws DaoException {
		return userDao.sspUserPage(pageCriteria);
	}

	@Override
	public User[] findByRole(Role role) {
		return userDao.findByRole(role);
	}

	@Override
	public int updateByEmail(User user) throws DaoException {
		return userDao.updateByEmail(user);
	}

	@Override
	public Long[] batchAddUserSystem(UserSystem[] userSystems) throws DaoException {
		return userDao.batchAddUserSystem(userSystems);
	}

	@Override
	public int batchDeleteUserSystemByUserId(Long[] ids) throws DaoException {
		return userDao.batchDeleteUserSystemByUserId(ids);
	}

	@Override
	public User[] findUserByUserSystem(User user) throws DaoException {
		return userDao.findUserByUserSystem(user);
	}
}
