package com.unioncast.db.rdbms.core.dao.common.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.page.*;
import com.unioncast.common.user.model.Module;
import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.RoleModule;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.ModuleDao;
import com.unioncast.db.rdbms.core.dao.common.RoleDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

/**
 * @author： 刘蓉 @date： 2016年10月9日 上午11:33:31 RoleDao实现
 */
@Repository("roleDao")
public class RoleDaoImpl extends CommonGeneralDao<Role, Long> implements RoleDao {

	Pagination<Role> pagination;

	private static String QUERY_FOR_OBJECT = SqlBuild.select(Role.TABLE_NAME, Role.PROPERTIES);
	private static String COUNT_ALL = SqlBuild.countAll(Role.TABLE_NAME);
	private static String INSERT_FOR_ROLE = "insert into " + Role.TABLE_NAME + "(" + Role.PROPERTIES
			+ ") values(null,?,?,?,?,?,?)";
	private static String DELETE_FOR_ROLE = SqlBuild.delete(Role.TABLE_NAME);

	public final class RoleRowMapper implements RowMapper<Role> {

		/**
		 * 结果集封装 (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
		 *      int)
		 */
		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Role(rs.getLong("id"), rs.getInt("system_id"), rs.getString("name"), rs.getString("description"),
					rs.getTimestamp("create_time"), rs.getString("remark"), rs.getTimestamp("update_time"),
					Arrays.asList(findModuleByRoleId(rs.getLong("id"))));
		}
	}

	@Override
	public Role[] find(Long id) throws DaoException {
		List<Role> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where id = ?", new RoleRowMapper(), id));
			return list.toArray(new Role[] {});
		}
		list = jdbcTemplate.query(QUERY_FOR_OBJECT, new RoleRowMapper());
		return list.toArray(new Role[] {});
	}

	@Resource
	ModuleDao moduleDao;

	public Module[] findModuleByRoleId(Long id) {
		return moduleDao.findByRoleId(id);
	}

	/**
	 * 通过systemId查询
	 * 
	 * @author 刘蓉
	 * @date 2016年10月9日 下午1:39:16
	 * @param systemId
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Role[] findBySystemId(Long systemId) throws DaoException {
		return jdbcTemplate.query(QUERY_FOR_OBJECT + " where system_id = ?", new RoleRowMapper(), systemId)
				.toArray(new Role[] {});
	}

	public Module[] findModulesByRoleId(long id) {
		ModuleDaoImpl moduleDaoImpl = new ModuleDaoImpl();
		return moduleDaoImpl.findByRoleId(id);
	}

	/**
	 * 分页查找
	 * 
	 * @author 刘蓉
	 * @date 2016年10月9日 下午1:50:27
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws DaoException
	 */
	public Pagination<Role> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException {
		int totalCount = countAll();
		int maxPage = PaginationUtil.calTotalPage(totalCount, pageSize);
		Role[] dataArray = null;
		pagination = new Pagination<Role>(totalCount, pageSize, maxPage, currentPageNo, dataArray);
		int currentPageNo2 = pagination.getCurrentPageNo();
		int start = (currentPageNo2 - 1) * pageSize;
		List<Role> list = jdbcTemplate.query(QUERY_FOR_OBJECT + " limit " + start + "," + pageSize,
				new RoleRowMapper());
		Role[] roles = list.toArray(new Role[] {});
		pagination.setDataArray(roles);
		return pagination;
	}

	@Override
	public Pagination<Role> page(PageCriteria pageCriteria) {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(QUERY_FOR_OBJECT + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 ");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = (String) searchExpressionList.get(i).getValue();
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator()))
					value = "'%" + value + "%'";
				String timeSql = " ";
				String criteriaSql = "";
				if ("update_time".equals(searchExpressionList.get(i).getPropertyName())) {
					String[] time = String.valueOf(searchExpressionList.get(i).getValue()).split("/");
					timeSql = " " + pageCriteria.getPredicate() + " " + searchExpressionList.get(i).getPropertyName()
							+ " between '" + time[0] + "' AND date_add('" + time[1] + "',interval 1 day) ";
				} else {
					criteriaSql = " " + pageCriteria.getPredicate() + " "
							+ searchExpressionList.get(i).getPropertyName() + " "
							+ searchExpressionList.get(i).getOperation().getOperator() + " " + value;
				}
				pageSql.append(criteriaSql + timeSql);
				countAll.append(criteriaSql + timeSql);
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
		List<Role> list = jdbcTemplate.query(pageSql.toString(), new RoleRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<Role>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				list.toArray(new Role[] {}));
	}

	/**
	 * 查询总数
	 * 
	 * @author 刘蓉
	 * @date 2016年10月9日 下午2:03:04
	 * @return
	 * @throws DaoException
	 */
	@Override
	public int countAll() throws DaoException {
		return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
	}

	/**
	 * 批量添加
	 * 
	 * @author 刘蓉
	 * @date 2016年10月9日 下午2:12:20
	 * @param roles
	 * @return 新增记录的id集合
	 * @throws DaoException
	 */
	@Override
	public Long[] batchAdd(Role[] entitys) {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(INSERT_FOR_ROLE,
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (Role role : entitys) {
				if (role.getSystemId() != null)
					pstmt.setLong(1, role.getSystemId());
				else
					pstmt.setNull(1, Types.NULL);
				pstmt.setString(2, role.getName());
				pstmt.setString(3, role.getDescription());
				if (role.getCreateTime() != null)
					pstmt.setTimestamp(4, new Timestamp(role.getCreateTime().getTime()));
				else
					pstmt.setNull(4, Types.TIMESTAMP);
				if (role.getUpdateTime() != null)
					pstmt.setTimestamp(5, new Timestamp(role.getUpdateTime().getTime()));
				else
					pstmt.setNull(5, Types.TIMESTAMP);
				pstmt.setString(6, role.getRemark());
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
		return list.toArray(new Long[] {});
	}

	/**
	 * 根据id删除
	 * 
	 * @author 刘蓉
	 * @date 2016年10月9日 下午2:26:15
	 * @param id
	 * @return 删除的行数(1行)
	 * @throws DaoException
	 */
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_FOR_ROLE, id);
	}

	/**
	 * 批量删除
	 * 
	 * @author 刘蓉
	 * @date 2016年10月10日 下午1:56:12
	 * @param ids
	 * @return 删除的行数
	 */
	@Override
	public int batchDelete(Long[] ids) {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DELETE_FOR_ROLE, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	private static String DELETE_ROLE_MODULE = "DELETE FROM `common_role_module` WHERE role_id = ?";

	/**
	 * 根据角色id删除角色模块表数据
	 * 
	 * @author 刘蓉
	 * @date 2016年10月10日 下午6:45:22
	 * @param roleId
	 * @return 影响的行数
	 */
	@Override
	public int deleteRoleModule(Long roleId) {
		return jdbcTemplate.update(DELETE_ROLE_MODULE, roleId);
	}

	/**
	 * 根据角色id的集合批量删除角色模块表数据
	 * 
	 * @author 刘蓉
	 * @date 2016年10月10日 下午6:50:58
	 * @param roleId
	 * @return 影响的行数
	 */
	@Override
	public int batchDeleteRoleModule(Long[] roleIds) {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long roleId : roleIds) {
			Object[] objects = new Object[] { roleId };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DELETE_ROLE_MODULE, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	private static String INSERT_ROLE_MODULE = "insert into common_role_module(role_module_id, role_id, module_id, create_time, update_time, sort, remark) values(?,?,?,?,?,?,?)";

	/**
	 * 批量插入角色模块数据
	 * 
	 * @author 刘蓉
	 * @date 2016年10月10日 下午7:05:05
	 * @param roleModules
	 * @return 影响的行数
	 */
	@Override
	public int batchAddRoleModule(RoleModule[] roleModules) {
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (RoleModule roleModule : roleModules) {
			Object[] args = new Object[] { roleModule.getRoleModuleId(), roleModule.getRoleId(),
					roleModule.getModuleId(), roleModule.getCreateTime(), roleModule.getUpdateTime(),
					roleModule.getSort(), roleModule.getRemark() };
			batchArgs.add(args);
		}
		int[] intArray = jdbcTemplate.batchUpdate(INSERT_ROLE_MODULE, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	// 只返回第一条数据，表需要设置唯一约束
	@Override
	public Role findByName(String name) {
		return jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where name = ? limit 0,1", new RoleRowMapper(), name);
	}

	@Override
	public Role findByNameAndSystemId(String name, Integer systemId) {
		return jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where name = ? and system_id = ? limit 0,1",
				new RoleRowMapper(), name, systemId);
	}

}
