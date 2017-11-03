package com.unioncast.db.rdbms.core.dao.common.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.User;
import com.unioncast.common.user.model.UserCount;
import com.unioncast.common.user.model.UserRole;
import com.unioncast.common.user.model.UserSystem;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("userDao")
public class UserDaoImpl extends CommonGeneralDao<User, Long> implements UserDao {

	public static final String CHECK_BY_LOGINPWR = "SELECT * FROM `common_user` u INNER JOIN `common_user_role` c ON (u.id =c.`user_id` AND c.`role_id` = ?)  WHERE u.`login_name`=? AND u.`login_password`=?";
	private static final String COUNT_ALL = "select count(*) from common_user";
	private static final String INSERT_FOR_USER = "INSERT INTO common_user(login_name,login_password,username,phone,email,balance,state,user_type,is_delete,update_time,create_time,remark,is_verify,contact,parent_id,register_time,validata_code) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String DELETE_FOR_USER = "DELETE FROM `unioncast_common_manager`.`common_user`";
	private static final String DELETE_FOR_USER_ROLE = "DELETE FROM `unioncast_common_manager`.`common_user_role`";
	private static final String DELETE_FOR_USER_SYSTEM = "DELETE FROM `unioncast_common_manager`.`common_user_system`";
	private static final String INSERT_FOR_USERROLE = "INSERT INTO common_user_role(user_role_id,role_id,user_id,create_time,update_time,remark) VALUES(?,?,?,?,?,?)";
	private static final String INSERT_FOR_USERSYSTEM = "INSERT INTO common_user_system(user_system_id,system_id,user_id,create_time,update_time) VALUES(?,?,?,?,?)";
	private static final String INSERT_FOR_USER_ROLE = "INSERT INTO common_user_role(user_role_id,role_id,user_id,create_time,update_time,remark) VALUES(?,?,?,?,?,?)";
	private static String QUERY_FOR_OBJECT = "select id,login_name,login_password,username,phone,email,balance,state,user_type,is_delete,update_time,create_time,remark,is_verify,contact,parent_id,register_time,validata_code from common_user";
	private static String LEFT_JOIN = "SELECT * FROM `common_role` r LEFT  JOIN `common_user_role` u ON r.`id`=u.`role_id` WHERE u.`user_id`= ";
	private static String SELECT_FOR_USER_SYSTEM = "SELECT * FROM `common_user` u INNER JOIN `common_user_system` us ON u.`id`=us.`user_id` INNER JOIN common_system s ON s.id=us.system_id ";
	private static String QUERYADXALLUSERCOUNT = "SELECT COUNT(DISTINCT u.`id`) FROM `common_user` u INNER JOIN `common_user_role` uc ON u.`id` = uc.`user_id` AND uc.`role_id` in (SELECT id FROM `common_role` WHERE `system_id` = '2') WHERE u.`is_delete`= 0 ";
	private static String QUERYSSPALLUSERCOUNT = "SELECT COUNT(DISTINCT u.`id`) FROM `common_user` u INNER JOIN `common_user_role` uc ON u.`id` = uc.`user_id` AND uc.`role_id` in (SELECT id FROM `common_role` WHERE `system_id` = '1') WHERE u.`is_delete`= 0 ";
	private static String QUERYDSPALLUSERCOUNT = "SELECT COUNT(DISTINCT u.`id`) FROM `common_user` u INNER JOIN `common_user_role` uc ON u.`id` = uc.`user_id` AND uc.`role_id` in (SELECT id FROM `common_role` WHERE `system_id` = '3') WHERE u.`is_delete`= 0 ";
	private static String FIND_USER_COUNT_PAGE = "SELECT DISTINCT u.`id`,u.`login_name`,u.`login_password`,u.`username`,u.`phone`,u.`email`,u.`state`,u.`user_type`,u.`is_delete`,u.`update_time`,u.`create_time`,u.`remark`,u.`is_verify`,u.`contact`,u.`parent_id`,u.`register_time`,u.`validata_code` FROM `common_user` u INNER JOIN `common_user_role` uc ON u.`id` = uc.`user_id` ";
	private static String FIND_USER_COUNT = "SELECT COUNT(DISTINCT u.`id`) FROM `common_user` u INNER JOIN `common_user_role` uc ON u.`id` = uc.`user_id` ";

	private static String ROLE_SYS_ID = "SELECT DISTINCT a.id,a.login_name,a.login_password,a.username,a.phone,a.email,a.state,a.user_type,a.is_delete,a.update_time,a.create_time,a.remark,a.is_verify,a.contact,a.parent_id,a.register_time,a.validata_code  FROM `common_user` a INNER JOIN `common_user_role` b ON a.id = b.`user_id` ";

	private static String ROLE_SYS_ID_COUNT = "SELECT COUNT(DISTINCT a.`id`) FROM `common_user` a INNER JOIN `common_user_role` b ON a.id = b.`user_id` ";
	private static String SSP_FIND_USER_PAGE = "SELECT DISTINCT u.`id`,u.`login_name`,u.`login_password`,u.`username`,u.`phone`,u.`email`,u.`state`,u.`user_type`,u.`is_delete`,u.`update_time`,u.`create_time`,u.`remark`,u.`is_verify`,u.`contact`,u.`parent_id`,u.`register_time`,u.`validata_code` FROM `common_user` u LEFT JOIN `common_user_system` us ON u.`id` = us.`user_id` LEFT JOIN common_system s on s.id=us.system_id LEFT JOIN `common_user_role` ur ON u.`id` = ur.`user_id` LEFT JOIN common_role r ON ur.role_id=r.id where 1=1 ";
	private static String SSP_FIND_USER_COUNT = "SELECT COUNT(DISTINCT u.`id`) FROM `common_user` u LEFT JOIN `common_user_system` us ON u.`id` = us.`user_id` LEFT JOIN common_system s on s.id=us.system_id LEFT JOIN `common_user_role` ur ON u.`id` = ur.`user_id` LEFT JOIN common_role r ON ur.role_id=r.id where 1=1 ";
	private static String UPDATE_PWD_FOR_USER = "UPDATE common_user SET login_password=?,update_time=? where 1=1 ";

	public Pagination<User> page(PageCriteria pageCriteria) {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(QUERY_FOR_OBJECT + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 ");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = searchExpressionList.get(i).getValue().toString();
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
		            value = " '%" + value + "%' ";
		        } else {
		            value = " '" + value + "' ";
		        }
				String criteriaSql = " " + pageCriteria.getPredicate() + " "
						+ searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator()
						+ " " + value;
				pageSql.append(criteriaSql);
				countAll.append(criteriaSql);
			}
		}
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		if (orderExpressionList != null && orderExpressionList.size() != 0) {
			pageSql.append(" order by");
			for (int i = 0; i < orderExpressionList.size(); i++) {
				if (i > 0)
					pageSql.append(",");
				pageSql.append(
						" " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
			}
		}
		pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
				+ pageCriteria.getPageSize());
		List<User> list = jdbcTemplate.query(pageSql.toString(), new UserRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<User>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				list.toArray(new User[] {}));
	}

	@Override
	public Pagination<User> pageByRoleIds(PageCriteria pageCriteria) {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringSearch = new StringBuilder();
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();

		stringBuilder.append(FIND_USER_COUNT_PAGE);
		if (searchExpressionList != null && pageCriteria.getPredicate() != null) {
			String predicate = pageCriteria.getPredicate().getOperator();
			if (searchExpressionList.size() >= 1 && predicate != null) {
				stringSearch.append(" AND ");
				for (int i = 0; i < searchExpressionList.size() - 1; i++) {
					stringSearch.append(searchExpressionList.get(i).getPropertyName() + " "
							+ searchExpressionList.get(i).getOperation().getOperator() + " '"
							+ searchExpressionList.get(i).getValue() + "' " + predicate + " ");
				}
			}
			stringSearch.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " "
					+ searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator() + " '"
					+ searchExpressionList.get(searchExpressionList.size() - 1).getValue() + "' ");
		} else if (searchExpressionList.get(0) != null) {
			stringSearch.append(" AND " + searchExpressionList.get(0).getPropertyName() + " "
					+ searchExpressionList.get(0).getOperation().getOperator() + " '"
					+ searchExpressionList.get(0).getValue() + "' ");

		}
		stringSearch.append(" WHERE u.`is_delete` = 0 ");
		int totalCount = jdbcTemplate.queryForObject(FIND_USER_COUNT + " " + stringSearch.toString(), int.class);
		Pagination<User> pagination = new Pagination<User>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), null);

		if (totalCount == 0) {
			return pagination;
		}

		Integer currentPageNo = pagination.getCurrentPageNo();
		Integer pageSize = pagination.getPageSize();
		stringBuilder.append(stringSearch);
		if (orderExpressionList != null) {
			stringBuilder.append(" ORDER BY ");
			for (int i = 0; i < orderExpressionList.size() - 1; i++) {
				stringBuilder.append(
						orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp() + ", ");
			}
			stringBuilder.append(orderExpressionList.get(orderExpressionList.size() - 1).getPropertyName() + " "
					+ orderExpressionList.get(orderExpressionList.size() - 1).getOp());
		}
		if (currentPageNo != null && pageSize != null) {
			Integer start = (currentPageNo - 1) * pageSize;
			stringBuilder.append(" limit " + start + "," + pageSize);
		}
		List<User> users = jdbcTemplate.query(stringBuilder.toString(), new UserRowMapper());
		pagination.setDataArray(users.toArray(new User[] {}));

		return pagination;

	}

	@Override
	public Pagination<User> pageByRoleSysIds(PageCriteria pageCriteria) {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringSearch = new StringBuilder();
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();

		stringBuilder.append(ROLE_SYS_ID);
		if (searchExpressionList != null) {
			if (searchExpressionList.get(0) != null) {
				stringSearch.append(" AND b.`role_id`IN (SELECT c.id FROM common_role c WHERE c.`system_id`= "
						+ searchExpressionList.get(0).getValue() + ")");
			}
		}

		int totalCount = jdbcTemplate.queryForObject(ROLE_SYS_ID_COUNT + " " + stringSearch.toString(), int.class);
		Pagination<User> pagination = new Pagination<User>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), null);

		if (totalCount == 0) {
			return pagination;
		}

		Integer currentPageNo = pagination.getCurrentPageNo();
		Integer pageSize = pagination.getPageSize();
		stringBuilder.append(stringSearch);

		if (orderExpressionList != null) {
			stringBuilder.append(" ORDER BY ");
			for (int i = 0; i < orderExpressionList.size() - 1; i++) {
				stringBuilder.append(
						orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp() + ", ");
			}
			stringBuilder.append(orderExpressionList.get(orderExpressionList.size() - 1).getPropertyName() + " "
					+ orderExpressionList.get(orderExpressionList.size() - 1).getOp());
		}

		if (currentPageNo != null && pageSize != null) {
			Integer start = (currentPageNo - 1) * pageSize;
			stringBuilder.append(" limit " + start + "," + pageSize);
		}

		List<User> users = jdbcTemplate.query(stringBuilder.toString(), new UserRowMapper());
		pagination.setDataArray(users.toArray(new User[] {}));

		return pagination;
	}

	@Override
	public Pagination<User> sspUserPage(PageCriteria pageCriteria) {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringSearch = new StringBuilder();
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		int systemId = 0;
		if (searchExpressionList != null) {
			if (searchExpressionList.size() >= 1) {
				if ("systemId".equals(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName())) {
					systemId = Integer
							.valueOf(searchExpressionList.get(searchExpressionList.size() - 1).getValue().toString());
				}
			}
		}
		stringBuilder.append(SSP_FIND_USER_PAGE);
		if (searchExpressionList != null) {
			if (searchExpressionList.size() >= 1) {
				for (int i = 0; i < searchExpressionList.size() - 1; i++) {
					if ("assignmentStatus".equals(searchExpressionList.get(i).getPropertyName())) {
						// 全部
						/*
						 * if
						 * ("0".equals(searchExpressionList.get(i).getValue()))
						 * { }
						 */
						// 已分配
						if ("1".equals(searchExpressionList.get(i).getValue())) {
							stringSearch.append(" AND r.system_id=" + systemId + " ");
						}
						// 未分配
						if ("2".equals(searchExpressionList.get(i).getValue())) {
							stringSearch.append(" AND ISNULL(r.system_id) ");
						}

					} else if ("roleName".equals(searchExpressionList.get(i).getPropertyName())) {
						// 按角色搜索
						if (!"0".equals(searchExpressionList.get(i).getValue())) {
							stringSearch.append(" AND ur.role_id='" + searchExpressionList.get(i).getValue() + "' ");
						}
					} else if ("updateTime".equals(searchExpressionList.get(i).getPropertyName())) {
						// 按时间搜索
						if (!StringUtils.isBlank(String.valueOf(searchExpressionList.get(i).getValue()))) {
							String[] time = String.valueOf(searchExpressionList.get(i).getValue()).split("/");
							stringSearch.append(" AND u.update_time BETWEEN '" + time[0] + "' AND date_add('" + time[1]
									+ "',interval 1 day) ");
						}

					} else if ("loginName".equals(searchExpressionList.get(i).getPropertyName())) {
						// 按账户名称搜索
						if (!StringUtils.isBlank(String.valueOf(searchExpressionList.get(i).getValue()))) {
							stringSearch
									.append(" AND u.login_name LIKE '%" + searchExpressionList.get(i).getValue() + "%' ");
						}
					}
				}
			}
		}
		stringSearch.append(" and s.id=" + systemId + " ");
		int totalCount = jdbcTemplate.queryForObject(SSP_FIND_USER_COUNT + " " + stringSearch.toString(), int.class);
		Pagination<User> pagination = new Pagination<User>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), null);

		if (totalCount == 0) {
			return pagination;
		}

		Integer currentPageNo = pagination.getCurrentPageNo()<1?1:pagination.getCurrentPageNo();
		Integer pageSize = pagination.getPageSize();
		stringBuilder.append(stringSearch);

		if (orderExpressionList != null) {
			stringBuilder.append(" ORDER BY ");
			for (int i = 0; i < orderExpressionList.size() - 1; i++) {
				stringBuilder.append(
						orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp() + ", ");
			}
			stringBuilder.append(orderExpressionList.get(orderExpressionList.size() - 1).getPropertyName() + " "
					+ orderExpressionList.get(orderExpressionList.size() - 1).getOp());
		}

		if (currentPageNo != null && pageSize != null) {
			Integer start = (currentPageNo - 1) * pageSize;
			stringBuilder.append(" limit " + start + "," + pageSize);
		}

		List<User> users = jdbcTemplate.query(stringBuilder.toString(), new UserRowMapper());
		pagination.setDataArray(users.toArray(new User[] {}));

		return pagination;
	}
	@Override
	public User findById(Long id) throws DaoException{
		if (id != null) {
			List<User> query = jdbcTemplate.query(QUERY_FOR_OBJECT + " WHERE id = " + id, new UserRowMapper());
			if(CommonUtil.isNotNull(query)&&!query.isEmpty())
				return query.get(0);
			return null;
		}
		return null;
	}

	@Override
	public User[] findByRole(Role role) {
		if (role != null) {
			if (role.getSystemId() != null) {
				List<User> query = jdbcTemplate
						.query(ROLE_SYS_ID + " AND b.`role_id`IN (SELECT c.id FROM common_role c WHERE c.`system_id`= "
								+ role.getSystemId() + ")", new UserRowMapper());
				return query.toArray(new User[] {});
			}
		}
		return null;

	}

	@Override
	public UserCount findUserCount() {
		UserCount uc = new UserCount();
		Integer adxAllUserCount = jdbcTemplate.queryForObject(QUERYADXALLUSERCOUNT, Integer.class);
		Integer adxQualifiedUserCount = jdbcTemplate.queryForObject(QUERYADXALLUSERCOUNT + " AND u.`state`= 1 ",
				Integer.class);
		Integer sspAllUserCount = jdbcTemplate.queryForObject(QUERYSSPALLUSERCOUNT, Integer.class);
		Integer sspQualifiedUserCount = jdbcTemplate.queryForObject(QUERYSSPALLUSERCOUNT + " AND u.`state`= 1 ",
				Integer.class);
		Integer dspAllUserCount = jdbcTemplate.queryForObject(QUERYDSPALLUSERCOUNT, Integer.class);
		Integer dspQualifiedUserCount = jdbcTemplate.queryForObject(QUERYDSPALLUSERCOUNT + " AND u.`state`= 1 ",
				Integer.class);
		uc.setAdxAllUserCount(adxAllUserCount);
		uc.setAdxQualifiedUserCount(adxQualifiedUserCount);
		uc.setSspAllUserCount(sspAllUserCount);
		uc.setSspQualifiedUserCount(sspQualifiedUserCount);
		uc.setDspAllUserCount(dspAllUserCount);
		uc.setDspQualifiedUserCount(dspQualifiedUserCount);
		return uc;
	}

	@Override
	public User[] findUserByUser(User user) {
		StringBuilder stringBuilder = new StringBuilder(QUERY_FOR_OBJECT);
		stringBuilder.append(" where 1 = 1 ");
		if (user.getLoginName() != null) {
			stringBuilder.append(" and login_name = '" + user.getLoginName() + "' ");
		}
		if (user.getUsername() != null) {
			stringBuilder.append(" and username = '" + user.getUsername() + "' ");
		}
		if (user.getPhone() != null) {
			stringBuilder.append(" and phone = '" + user.getPhone() + "' ");
		}
		if (user.getEmail() != null) {
			stringBuilder.append(" and email = '" + user.getEmail() + "' ");
		}
		if (user.getState() != null) {
			stringBuilder.append(" and state = '" + user.getState() + "' ");
		}
		if (user.getUserType() != null) {
			stringBuilder.append(" and user_type = '" + user.getUserType() + "' ");
		}
		if (user.getIsDelete() != null) {
			stringBuilder.append(" and is_delete = '" + user.getIsDelete() + "' ");
		}
		if (user.getIsVerify() != null) {
			stringBuilder.append(" and is_verify = '" + user.getIsVerify() + "' ");
		}
		if (user.getContact() != null) {
			stringBuilder.append(" and contact = '" + user.getContact() + "' ");
		}
		if (user.getParentId() != null) {
			stringBuilder.append(" and parent_id = '" + user.getParentId() + "' ");
		}
		if (user.getValidataCode() != null) {
			stringBuilder.append(" and validata_code = '" + user.getValidataCode() + "' ");
		}
		// if (user.getRegisterTime() != null) {
		// stringBuilder.append(" and register_time = '" +
		// user.getRegisterTime() + "' ");
		// }
		List<User> list = jdbcTemplate.query(stringBuilder.toString(), new UserRowMapper());
		return list.toArray(new User[] {});
	}

	/**
	 * 查询全部
	 */
	@Override
	public User[] findAll() throws DaoException {
		List<User> list = jdbcTemplate.query(QUERY_FOR_OBJECT, new UserRowMapper());
		// User[] users = new User[list.size()];
		// for (int i = 0; i < list.size(); i++) {
		// users[i] = list.get(i);
		// }
		return list.toArray(new User[list.size()]);
	}

	/**
	 * 查找
	 */
	@Override
	public User[] find(Long id) throws DaoException {
		if (id == null) {
			List<User> list = jdbcTemplate.query(QUERY_FOR_OBJECT, new UserRowMapper());
			return list.toArray(new User[list.size()]);

		}
		User user = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where id = ?", new UserRowMapper(), id);
		User[] users = new User[] { user };
		return users;
	}

	/**
	 * 根据登录账号查找
	 */
	@Override
	public User findByString(String loginName) throws DaoException {
		User user = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where login_name = ?", new UserRowMapper(),
				loginName);
		return user;
	}

	@Override
	public int countAll() {
		Integer sum = jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
		return sum;
	}

	/**
	 * 添加多个
	 *
	 * @param entitys
	 * @throws DaoException
	 * @author zhangzhe
	 * @date 2016年10月9日 上午10:20:54
	 */

	@Override
	public Long[] batchAdd(User[] entitys) {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(INSERT_FOR_USER,
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (User user : entitys) {
				pstmt.setString(1, user.getLoginName());
				pstmt.setString(2, user.getLoginPassword());
				pstmt.setString(3, user.getUsername());
				pstmt.setString(4, user.getPhone());
				pstmt.setString(5, user.getEmail());
				if (user.getState() != null)
					pstmt.setInt(6, user.getState());
				else
					pstmt.setNull(6, Types.INTEGER);
				if (user.getUserType() != null)
					pstmt.setInt(7, user.getUserType());
				else
					pstmt.setNull(7, Types.INTEGER);
				if (user.getIsDelete() != null)
					pstmt.setInt(8, user.getIsDelete());
				else
					pstmt.setNull(8, Types.INTEGER);
				pstmt.setTimestamp(9, new Timestamp(new Date().getTime()));
				pstmt.setTimestamp(10, new Timestamp(new Date().getTime()));
				pstmt.setString(11, user.getRemark());
				if (user.getIsVerify() != null)
					pstmt.setInt(12, user.getIsVerify());
				else
					pstmt.setNull(12, Types.INTEGER);
				pstmt.setString(13, user.getContact());
				if (user.getParentId() != null)
					pstmt.setLong(14, user.getParentId());
				else
					pstmt.setNull(14, Types.BIGINT);
				if (user.getRegisterTime() != null)
					pstmt.setTimestamp(15, new Timestamp(user.getRegisterTime().getTime()));
				else
					pstmt.setNull(15, Types.TIMESTAMP);

				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			ResultSet rs = pstmt.getGeneratedKeys();
			list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
			connection.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.toArray(new Long[list.size()]);
	}

	/**
	 * 删除
	 */
	@Override
	public int deleteById(Long id) throws DaoException {
		int num = jdbcTemplate.update(DELETE_FOR_USER + " WHERE id = " + id);

		return num;

	}

	// =========================================================================================

	/**
	 * 批量删除
	 */
	@Override
	public int deleteById(Long[] ids) throws DaoException {

		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DELETE_FOR_USER + " where id = ?", batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;

	}

	/**
	 * 通过userId查询role
	 */

	public Role[] findRoleIdByUserId(Long id) {

		List<Role> list = jdbcTemplate.query(LEFT_JOIN + id, new RoleRowMapper());

		return list.toArray(new Role[list.size()]);
	}

	public User findByUsername(String username) throws DaoException {
		User user = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where username = ?", new UserRowMapper(),
				username);
		return user;
	}

	public User findByEmail(String email) throws DaoException {
		User user = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where email = ?", new UserRowMapper(), email);
		return user;
	}

	public int deleteUserRoleByUserId(Long id) throws DaoException {
		int num = jdbcTemplate.update(DELETE_FOR_USER_ROLE + " WHERE user_id = " + id);

		return num;
	}

	public int deleteUserRoleByRoleId(Long id) throws DaoException {
		int num = jdbcTemplate.update(DELETE_FOR_USER_ROLE + " WHERE role_id = " + id);

		return num;
	}

	public Long[] batchAddUserRole(UserRole[] entitys) throws DaoException {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(INSERT_FOR_USERROLE,
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (UserRole userRole : entitys) {
				pstmt.setString(1, userRole.getUserRoleId());
				if (userRole.getRoleId() != null)
					pstmt.setLong(2, userRole.getRoleId());
				else
					pstmt.setNull(2, Types.BIGINT);
				if (userRole.getUserId() != null)
					pstmt.setLong(3, userRole.getUserId());
				else
					pstmt.setNull(3, Types.BIGINT);
				pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
				pstmt.setTimestamp(5, new Timestamp(new Date().getTime()));
				pstmt.setString(6, userRole.getRemark());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			ResultSet rs = pstmt.getGeneratedKeys();
			list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
			connection.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.toArray(new Long[list.size()]);
	}

	public int batchDeleteUserRoleByRoleId(Long[] ids) throws DaoException {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DELETE_FOR_USER_ROLE + " where role_id = ?", batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	public int batchAddOneUesrIdAndManyRoleId(Long uesrId, Long[] roleIds) throws DaoException {
		int x = 0;
		for (int i = 0; i < roleIds.length; i++) {
			String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");

			Object[] obj = new Object[] { uuid, roleIds[i], uesrId, new Date(), new Date(), null };
			int num = jdbcTemplate.update(INSERT_FOR_USER_ROLE, obj);
			x += num;

		}

		return x;
	}

	public int batchAddManyUesrIdAndOneRoleId(Long roleId, Long[] uesrIds) throws DaoException {
		int x = 0;
		for (int i = 0; i < uesrIds.length; i++) {
			String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");

			Object[] obj = new Object[] { uuid, roleId, uesrIds[i], new Date(), new Date(), null };
			int num = jdbcTemplate.update(INSERT_FOR_USER_ROLE, obj);
			x += num;

		}

		return x;
	}

	@Override
	public int updateByEmail(User user) throws DaoException {
		List<Object> args = new ArrayList<>();
		args.add(user.getLoginPassword());
		args.add(user.getUpdateTime());
		args.add(user.getEmail());
		return jdbcTemplate.update(UPDATE_PWD_FOR_USER + " and email=? ", args.toArray());
	}

	@Override
	public Role[] findRoleByUserId(Long id) throws DaoException {
		return findRoleIdByUserId(id);
	}

	@Override
	public User checkbyLoginPWR(String loginname, String password, Long roleid) throws DaoException {
		User user = jdbcTemplate.queryForObject(CHECK_BY_LOGINPWR, new UserRowMapper(), roleid, loginname, password);
		return user;

	}

	public static final class LongRowMapper implements RowMapper<Long> {

		@Override
		public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Long(rs.getLong("role_id"));
		}

	}

	/**
	 * 封装结果集
	 */

	public final class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getLong("id"), rs.getString("login_name"), rs.getString("login_password"),
					rs.getString("username"), rs.getString("phone"),rs.getDouble("balance"), rs.getString("email"), rs.getInt("state"),
					rs.getInt("is_delete"), rs.getTimestamp("update_time"), rs.getTimestamp("create_time"),
					rs.getString("remark"), rs.getInt("user_type"), Arrays.asList(findRoleIdByUserId(rs.getLong("id"))),
					rs.getInt("is_verify"), rs.getString("contact"), rs.getLong("parent_id"),
					rs.getTimestamp("register_time"), rs.getString("validata_code"));
		}

	}

	/**
	 * 结果集封装
	 */

	public final class RoleRowMapper implements RowMapper<Role> {

		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Role(rs.getLong("id"), rs.getInt("system_id"), rs.getString("name"), rs.getString("description"),
					rs.getTimestamp("create_time"), rs.getString("remark"), rs.getTimestamp("update_time"), null);
		}

	}

	/********************************* UserSystem部分方法 *************************************/
	@Override
	public Long[] batchAddUserSystem(UserSystem[] userSystems) throws DaoException {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(INSERT_FOR_USERSYSTEM,
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (UserSystem userSystem : userSystems) {
				pstmt.setString(1, userSystem.getUserSystemId());
				if (userSystem.getSystemId() != null)
					pstmt.setLong(2, userSystem.getSystemId());
				else
					pstmt.setNull(2, Types.BIGINT);
				if (userSystem.getUserId() != null)
					pstmt.setLong(3, userSystem.getUserId());
				else
					pstmt.setNull(3, Types.BIGINT);
				pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
				pstmt.setTimestamp(5, new Timestamp(new Date().getTime()));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			ResultSet rs = pstmt.getGeneratedKeys();
			list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
			connection.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.toArray(new Long[list.size()]);
	}

	@Override
	public int batchDeleteUserSystemByUserId(Long[] ids) throws DaoException {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DELETE_FOR_USER_SYSTEM + " where user_id = ?", batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	@Override
	public User[] findUserByUserSystem(User user) throws DaoException {
		StringBuilder stringBuilder = new StringBuilder(SELECT_FOR_USER_SYSTEM);
		stringBuilder.append(" where 1 = 1 ");
		if (user.getLoginName() != null) {
			stringBuilder.append(" and u.login_name = '" + user.getLoginName() + "' ");
		}
		if (user.getUsername() != null) {
			stringBuilder.append(" and u.username = '" + user.getUsername() + "' ");
		}
		if (user.getPhone() != null) {
			stringBuilder.append(" and u.phone = '" + user.getPhone() + "' ");
		}
		if (user.getEmail() != null) {
			stringBuilder.append(" and u.email = '" + user.getEmail() + "' ");
		}
		if (user.getState() != null) {
			stringBuilder.append(" and u.state = '" + user.getState() + "' ");
		}
		if (user.getUserType() != null) {
			stringBuilder.append(" and u.user_type = '" + user.getUserType() + "' ");
		}
		if (user.getIsDelete() != null) {
			stringBuilder.append(" and u.is_delete = '" + user.getIsDelete() + "' ");
		}
		if (user.getIsVerify() != null) {
			stringBuilder.append(" and u.is_verify = '" + user.getIsVerify() + "' ");
		}
		if (user.getContact() != null) {
			stringBuilder.append(" and u.contact = '" + user.getContact() + "' ");
		}
		if (user.getParentId() != null) {
			stringBuilder.append(" and u.parent_id = '" + user.getParentId() + "' ");
		}
		if (user.getValidataCode() != null) {
			stringBuilder.append(" and u.validata_code = '" + user.getValidataCode() + "' ");
		}
		if (user.getRemark() != null) {
			// system暂存于remark
			stringBuilder.append(" and s.system_name = '" + user.getRemark() + "' ");
		}
		List<User> list = jdbcTemplate.query(stringBuilder.toString(), new UserRowMapper());
		return list.toArray(new User[] {});
	}

	// /**
	// * 分页查找
	// */
	// public Pagination<User> paginationAll(Integer currentPageNo, Integer
	// pageSize) throws DaoException {
	// int totalCount = countAll();
	// int maxPage = PaginationUtil.calTotalPage(totalCount, pageSize);
	// User[] dataArray = null;
	// pagination = new Pagination<>(totalCount, pageSize, maxPage,
	// currentPageNo, dataArray);
	// int currentPageNo2 = pagination.getCurrentPageNo();
	// int start = (currentPageNo2 - 1) * pageSize;
	// List<User> list = jdbcTemplate.query(QUERY_FOR_OBJECT + " limit " + start
	// + "," + pageSize,
	// new UserRowMapper());
	// User[] s = new User[list.size()];
	// for (int i = 0; i < list.size(); i++) {
	// s[i] = list.get(i);
	// }
	// pagination.setDataArray(s);
	// return pagination;
	// }
	//

	/**
	 * // * 条件分页查找 //
	 */
	// @Override
	// public Pagination<User> page(User user, Integer currentPageNo, Integer
	// pageSize) {
	// StringBuilder stringBuilder = new StringBuilder(QUERY_FOR_OBJECT);
	// List<Object> list = new ArrayList<>();
	//
	// if (user.getId() != null) {
	// stringBuilder.append(" where id = ?");
	// list.add(user.getId());
	// }
	//
	// if (user.getLoginName() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and login_name like ?");
	// else
	// stringBuilder.append(" where login_name like ?");
	// list.add("%" + user.getLoginName() + "%");
	// }
	// if (user.getLoginPassword() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and login_password = ?");
	// else
	// stringBuilder.append(" where login_password = ?");
	// list.add(user.getLoginPassword());
	// }
	// if (user.getUsername() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and username like ?");
	// else
	// stringBuilder.append(" where username like ?");
	// list.add("%" + user.getUsername() + "%");
	// }
	// if (user.getPhone() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and phone = ?");
	// else
	// stringBuilder.append(" where phone = ?");
	// list.add(user.getPhone());
	// }
	// if (user.getEmail() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and email = ?");
	// else
	// stringBuilder.append(" where email = ?");
	// list.add(user.getEmail());
	// }
	// if (user.getState() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and state = ?");
	// else
	// stringBuilder.append(" where state = ?");
	// list.add(user.getState());
	// }
	// if (user.getUserType() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and user_type = ?");
	// else
	// stringBuilder.append(" where user_type = ?");
	// list.add(user.getUserType());
	// }
	// if (user.getIsDelete() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and is_delete = ?");
	// else
	// stringBuilder.append(" where is_delete = ?");
	// list.add(user.getIsDelete());
	// }
	// if (user.getUpdateTime() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and update_time = ?");
	// else
	// stringBuilder.append(" where update_time = ?");
	// list.add(user.getUpdateTime());
	// }
	// if (user.getCreateTime() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and create_time = ?");
	// else
	// stringBuilder.append(" where create_time = ?");
	// list.add(user.getCreateTime());
	// }
	// if (user.getRemark() != null) {
	// String beginningIndex =
	// QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
	// if ("?".equals(beginningIndex))
	// stringBuilder.append(" and remark = ?");
	// else
	// stringBuilder.append(" where remark = ?");
	// list.add("%" + user.getRemark() + "%");
	// }
	//
	// if (currentPageNo != null && pageSize != null) {
	// Integer start = (currentPageNo - 1) * pageSize;
	// stringBuilder.append(" limit ?,?");
	// list.add(start);
	// list.add(pageSize);
	// }
	// List<User> users = jdbcTemplate.query(stringBuilder.toString(), new
	// UserRowMapper(), list.toArray());
	// int totalCount = jdbcTemplate.queryForObject(COUNT_ALL, int.class);
	// return new Pagination<User>(totalCount, pageSize, currentPageNo,
	// users.toArray(new User[]{}));
	//
	// }

	// public final class RoleRowMapper implements RowMapper<Role> {
	//
	// @Override
	// public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
	// return new Role(rs.getLong("id"), rs.getInt("system_id"),
	// rs.getString("name"),
	// rs.getString("description"), rs.getTimestamp("create_time"),
	// rs.getString("remark"), null);
	// }
	//
	// }

	// @Override
	// public void save(List<User> users) throws DaoException {
	//
	// for (int i = 0; i < users.size(); i++) {
	// User user = users.get(i);
	// Object[] obj = new Object[] { user.getLoginName(),
	// user.getLoginPassword(), user.getUsername(),
	// user.getPhone(), user.getEmail(), user.getState(), user.getUserType(),
	// user.getIsDelete(),
	// user.getUpdateTime(), user.getCreateTime(), user.getRemark() };
	// jdbcTemplate.update(INSERT_FOR_USER, obj);
	//
	// }
	//
	// }

	// /**
	// * 修改
	// *
	// */
	//
	// public static final String UPDATE_NOT_NULL_FIELD = "update common_user
	// set update_time = ?";
	//
	// @Override
	// public int updateNotNullFieldAndReturn(User user) throws DaoException {
	// /*
	// * Object[] obj = new Object[] { user.getLoginName(),
	// * user.getLoginPassword(), user.getUsername(), user.getPhone(),
	// * user.getEmail(), user.getState(), user.getUserType(),
	// * user.getIsDelete(), user.getUpdateTime(), user.getCreateTime(),
	// * user.getRemark(), user.getId() }; jdbcTemplate.update(UPDATE_FOR_USER
	// * + " WHERE id =?", obj);
	// */
	//
	// StringBuilder stringBuilder = new StringBuilder(UPDATE_NOT_NULL_FIELD);
	// List<Object> args = new ArrayList<>();
	// user.setUpdateTime(new Date());
	// args.add(new Date());
	//
	// if (user.getLoginName() != null) {
	// stringBuilder.append(",login_name = ?");
	// args.add(user.getLoginName());
	// }
	// if (user.getLoginPassword() != null) {
	// stringBuilder.append(",login_password = ?");
	// args.add(user.getLoginPassword());
	// }
	// if (user.getUsername() != null) {
	// stringBuilder.append(",username = ?");
	// args.add(user.getUsername());
	// }
	// if (user.getPhone() != null) {
	// stringBuilder.append(",phone = ?");
	// args.add(user.getPhone());
	// }
	// if (user.getEmail() != null) {
	// stringBuilder.append(",email = ?");
	// args.add(user.getEmail());
	// }
	// if (user.getState() != null) {
	// stringBuilder.append(",state = ?");
	// args.add(user.getState());
	// }
	// if (user.getUserType() != null) {
	// stringBuilder.append(",user_type = ?");
	// args.add(user.getUserType());
	// }
	// if (user.getIsDelete() != null) {
	// stringBuilder.append(",is_delete = ?");
	// args.add(user.getIsDelete());
	// }
	//
	// if (user.getCreateTime() != null) {
	// stringBuilder.append(",create_time = ?");
	// args.add(user.getCreateTime());
	// }
	// if (user.getRemark() != null) {
	// stringBuilder.append(",remark = ?");
	// args.add(user.getRemark());
	// }
	//
	// stringBuilder.append(" where id = ?");
	// args.add(user.getId());
	// int num = jdbcTemplate.update(stringBuilder.toString(), args.toArray());
	// return num;
	//
	// }
	// @Override
	// public Long save(User user) throws DaoException {
	//
	// /*
	// * LOG.info("timeInDao user:{}", user);
	// *
	// * Object[] obj = new Object[] { user.getId(),user.getLoginName(),
	// * user.getLoginPassword(), user.getUsername(), user.getPhone(),
	// * user.getEmail(), user.getState(), user.getUserType(),
	// * user.getIsDelete(), user.getUpdateTime(), user.getCreateTime(),
	// * user.getRemark() }; int num = jdbcTemplate.update(INSERT_FOR_USER,
	// * obj);
	// *
	// * LOG.info("timeInDao num:{}", num);
	// *
	// * return (long) num;
	// */
	// return insertAndReturnId(user);
	// }
	//
	// private long insertAndReturnId(User user) {
	// SimpleJdbcInsert jdbcInsert = new
	// SimpleJdbcInsert(jdbcTemplate).withTableName("common_user");
	// jdbcInsert.setGeneratedKeyName("id");
	// Map<String, Object> args = new HashMap<String, Object>();
	// args.put("login_name", user.getLoginName());
	// args.put("login_password", user.getLoginPassword());
	// args.put("username", user.getUsername());
	// args.put("phone", user.getPhone());
	// args.put("email", user.getEmail());
	// args.put("state", user.getState());
	// args.put("user_type", user.getUserType());
	// args.put("is_delete", user.getIsDelete());
	// args.put("update_time", new Date());
	// args.put("create_time", new Date());
	// args.put("remark", user.getRemark());
	// long id = jdbcInsert.executeAndReturnKey(args).longValue();
	// return id;
	// }

}
