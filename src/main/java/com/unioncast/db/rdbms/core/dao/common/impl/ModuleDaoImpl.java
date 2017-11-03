package com.unioncast.db.rdbms.core.dao.common.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.user.model.Module;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.core.dao.common.ModuleDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("moduleDao")
public class ModuleDaoImpl extends CommonGeneralDao<Module, Long> implements ModuleDao {

	// 关于查询
	private static String FIND_ALL = "select id,system_id,name,icon,element_url,description,level,parent_id,state,create_time,update_time,sort,remark from common_module where state = 0";
	private static String FIND_BY_ROLE_ID = "select m.id,m.system_id,m.name,m.icon,m.element_url,m.description,m.level,m.parent_id,m.state,m.create_time,m.update_time,m.sort,m.remark "
			+ "from common_module m,common_role_module r where r.module_id = m.id and r.role_id = ";
	private static final String COUNT_ALL = "select count(*) from common_module where state = 0";
	private static String FIND_BY_SYSTEM_ID = "select id,system_id,name,icon,element_url,description,level,parent_id,state,create_time,update_time,sort,remark from common_module where state = 0 and system_id =";

	@Override
	public Module[] find(Long id) throws DaoException {
		List<Module> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " and id = ?", new ModuleRowMapper(), id));
			return list.toArray(new Module[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new ModuleRowMapper());
		return list.toArray(new Module[] {});
	}

	@Override
	public Module[] findByRoleId(Long id) {
		List<Module> list = jdbcTemplate.query(FIND_BY_ROLE_ID + id, new ModuleRowMapper());
		return list.toArray(new Module[list.size()]);
	}

	@Override
	public Module[] findModuleBySystemId(Long systemId) {
		List<Module> list = jdbcTemplate.query(FIND_BY_SYSTEM_ID + systemId, new ModuleRowMapper());
		return list.toArray(new Module[list.size()]);
	}

	@Override
	public Module[] findByNameAndSystem(String moduleName, Long systemId) {
		List<Module> list = jdbcTemplate.query(FIND_BY_SYSTEM_ID + systemId + " and name = ?", new ModuleRowMapper(),
				moduleName);
		return list.toArray(new Module[list.size()]);
	}

	@Override
	public Pagination<Module> page(PageCriteria pageCriteria) {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringSearch = new StringBuilder();
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();

		stringBuilder.append(FIND_ALL);
		if (searchExpressionList != null && pageCriteria.getPredicate() != null) {
			String predicate = pageCriteria.getPredicate().getOperator();
			if (searchExpressionList.size() >= 1 && predicate != null) {
				stringSearch.append(" and ");
				for (int i = 0; i < searchExpressionList.size() - 1; i++) {
					if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
						stringSearch.append(searchExpressionList.get(i).getPropertyName() + " "
								+ searchExpressionList.get(i).getOperation().getOperator() + " '%"
								+ searchExpressionList.get(i).getValue() + "%' " + predicate + " ");
					}
					if ("update_time".equals(searchExpressionList.get(i).getPropertyName())) {
						String[] time = String.valueOf(searchExpressionList.get(i).getValue()).split("/");
						stringSearch.append(searchExpressionList.get(i).getPropertyName() + " between '" + time[0]
								+ "' AND date_add('" + time[1] + "',interval 1 day) " + predicate + " ");
					} else {
						stringSearch.append(searchExpressionList.get(i).getPropertyName() + " "
								+ searchExpressionList.get(i).getOperation().getOperator() + " '"
								+ searchExpressionList.get(i).getValue() + "' " + predicate + " ");
					}
				}
				if ("like".equalsIgnoreCase(
						searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator())) {
					stringSearch
							.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " "
									+ searchExpressionList.get(searchExpressionList.size() - 1).getOperation()
											.getOperator()
									+ " '%" + searchExpressionList.get(searchExpressionList.size() - 1).getValue()
									+ "%' ");

				} else if ("update_time".equalsIgnoreCase(
						searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName())) {
					String[] time = String.valueOf(searchExpressionList.get(searchExpressionList.size() - 1).getValue())
							.split("/");
					stringSearch.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName()
							+ " between '" + time[0] + "' AND date_add('" + time[1] + "',interval 1 day) ");
				} else {

					stringSearch
							.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " "
									+ searchExpressionList.get(searchExpressionList.size() - 1).getOperation()
											.getOperator()
									+ " '" + searchExpressionList.get(searchExpressionList.size() - 1).getValue()
									+ "' ");
				}
			} else if (searchExpressionList.get(0) != null) {
				if ("like".equalsIgnoreCase(searchExpressionList.get(0).getOperation().getOperator())) {
					stringSearch.append(" and " + searchExpressionList.get(0).getPropertyName() + " "
							+ searchExpressionList.get(0).getOperation().getOperator() + " '%"
							+ searchExpressionList.get(0).getValue() + "%' ");
				} else {
					stringSearch.append(" and " + searchExpressionList.get(0).getPropertyName() + " "
							+ searchExpressionList.get(0).getOperation().getOperator() + " '"
							+ searchExpressionList.get(0).getValue() + "' ");
				}
			}
		}

		int totalCount = jdbcTemplate.queryForObject(COUNT_ALL + " " + stringSearch.toString(), int.class);
		Pagination<Module> pagination = new Pagination<Module>(totalCount, pageCriteria.getPageSize(),
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

		List<Module> modules = jdbcTemplate.query(stringBuilder.toString(), new ModuleRowMapper());
		pagination.setDataArray(modules.toArray(new Module[] {}));

		return pagination;
	}

	// 关于增加
	public static final String BATCH_ADD = "insert into common_module(system_id,name,icon,element_url,description,level,parent_id,state,create_time,update_time,sort,remark) values(?,?,?,?,?,?,?,?,?,?)";

	@Override
	public Long[] batchAdd(Module[] entitys) {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (Module module : entitys) {
				if (module.getSystemId() != null)
					pstmt.setLong(1, module.getSystemId());
				else
					pstmt.setNull(1, Types.NULL);
				pstmt.setString(2, module.getName());
				pstmt.setString(3, module.getIcon());
				pstmt.setString(4, module.getElementUrl());
				pstmt.setString(5, module.getDescription());
				if (module.getLevel() != null)
					pstmt.setInt(6, module.getLevel());
				else
					pstmt.setNull(6, Types.INTEGER);
				if (module.getParentId() != null)
					pstmt.setLong(7, module.getParentId());
				else
					pstmt.setNull(7, Types.NULL);
				if (module.getState() != null)
					pstmt.setInt(8, module.getState());
				else
					pstmt.setNull(8, Types.INTEGER);
				if (module.getCreateTime() != null)
					pstmt.setTimestamp(9, new Timestamp(module.getCreateTime().getTime()));
				else
					pstmt.setNull(9, Types.TIMESTAMP);
				if (module.getUpdateTime() != null)
					pstmt.setTimestamp(10, new Timestamp(module.getUpdateTime().getTime()));
				else
					pstmt.setNull(10, Types.TIMESTAMP);
				if (module.getSort() != null)
					pstmt.setInt(11, module.getSort());
				else
					pstmt.setNull(11, Types.INTEGER);
				pstmt.setString(12, module.getRemark());
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

	// 关于修改
	public static final String UPDATE_NOT_NULL_FIELD = "update common_module set update_time = ?";

	@Override
	public void updateNotNullField(Module entity) throws DaoException {
		StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
		List<Object> args = new ArrayList<>();
		entity.setUpdateTime(new Date());
		args.add(new Date());
		if (entity.getSystemId() != null) {
			stringBuffer.append(",system_id = ?");
			args.add(entity.getSystemId());
		}
		if (entity.getName() != null) {
			stringBuffer.append(",name = ?");
			args.add(entity.getName());
		}
		if (entity.getIcon() != null) {
			stringBuffer.append(",icon = ?");
			args.add(entity.getIcon());
		}
		if (entity.getElementUrl() != null) {
			stringBuffer.append(",element_url = ?");
			args.add(entity.getElementUrl());
		}
		if (entity.getDescription() != null) {
			stringBuffer.append(",description = ?");
			args.add(entity.getDescription());
		}
		if (entity.getLevel() != null) {
			stringBuffer.append(",level = ?");
			args.add(entity.getLevel());
		}
		if (entity.getParentId() != null) {
			stringBuffer.append(",parent_id = ?");
			args.add(entity.getParentId());
		}
		if (entity.getState() != null) {
			stringBuffer.append(",state = ?");
			args.add(entity.getState());
		}
		if (entity.getCreateTime() != null) {
			stringBuffer.append(",create_time = ?");
			args.add(entity.getCreateTime());
		}
		if (entity.getUpdateTime() != null) {
			stringBuffer.append(",update_time = ?");
			args.add(entity.getUpdateTime());
		}
		if (entity.getSort() != null) {
			stringBuffer.append(",sort = ?");
			args.add(entity.getSort());
		}
		if (entity.getRemark() != null) {
			stringBuffer.append(",remark = ?");
			args.add(entity.getRemark());
		}
		stringBuffer.append(" where id = ?");
		args.add(entity.getId());
		jdbcTemplate.update(stringBuffer.toString(), args.toArray());
	}

	// 关于删除
	public static final String DELETE_BY_ID = "delete from common_module where id = ?";
	public static final String BATCH_DELETE = "delete from common_module where id = ?";
	public static final String DEL_ROLE_MOD_BY_ID = "delete from common_role_module where module_id = ?";
	// 伪删除
	public static final String DELETE_UPDATE_BY_ID = "update common_module set state = 1 where id = ?";
	public static final String BATCH_DELETE_UPDATE = "update common_module set state = 1 where id = ?";

	@Override
	public int delRoleModById(Long[] ids) throws DaoException {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DEL_ROLE_MOD_BY_ID, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_UPDATE_BY_ID, id);
	}

	@Override
	public int batchDelete(Long[] ids) {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(BATCH_DELETE_UPDATE, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	public static final class ModuleRowMapper implements RowMapper<Module> {
		public Module mapRow(ResultSet rs, int rowNum) throws SQLException {
			Module module = new Module();
			module.setId(rs.getLong("id"));
			module.setSystemId(rs.getLong("system_id"));
			module.setName(rs.getString("name"));
			module.setIcon(rs.getString("icon"));
			module.setElementUrl(rs.getString("element_url"));
			module.setDescription(rs.getString("description"));
			module.setLevel(rs.getInt("level"));
			module.setParentId(rs.getLong("parent_id"));
			module.setState(rs.getInt("state"));
			module.setCreateTime(rs.getTimestamp("create_time"));
			module.setUpdateTime(rs.getTimestamp("update_time"));
			module.setSort(rs.getInt("sort"));
			module.setRemark(rs.getString("remark"));
			return module;
		}
	}

}
