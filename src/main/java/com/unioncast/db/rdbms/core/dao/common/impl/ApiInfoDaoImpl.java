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
import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.core.dao.common.ApiInfoDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("apiInfoDao")
public class ApiInfoDaoImpl extends CommonGeneralDao<ApiInfo, Long> implements ApiInfoDao {

	// 关于查询
	private static String FIND_ALL = "select id,system_id,system_name,prefix,remark,create_time,update_time,ip_white_list from common_apiInfo";
	private static final String COUNT_ALL = "select count(*) from common_apiInfo";

	@Override
	public ApiInfo[] find(Long id) throws DaoException {
		List<ApiInfo> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new ApiInfoRowMapper(), id));
			return list.toArray(new ApiInfo[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new ApiInfoRowMapper());
		return list.toArray(new ApiInfo[] {});
	}
	
	@Override
	public ApiInfo[] findBySystemId(Long systemId) throws DaoException{
		List<ApiInfo> list = jdbcTemplate.query(FIND_ALL + " where system_id = " + systemId, new ApiInfoRowMapper());
		return list.toArray(new ApiInfo[list.size()]);
	}
	
	@Override
	public ApiInfo[] findByIdAndName(Long systemId, String systemName) {
		List<ApiInfo> list = jdbcTemplate.query(FIND_ALL + " where system_id = " + systemId + " and system_name = ?", new ApiInfoRowMapper(), systemName);
		return list.toArray(new ApiInfo[list.size()]);
	}

	@Override
	public Pagination<ApiInfo> page(PageCriteria pageCriteria) {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringSearch = new StringBuilder();
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();

		stringBuilder.append(FIND_ALL);
		if (searchExpressionList != null && pageCriteria.getPredicate() != null) {
			String predicate = pageCriteria.getPredicate().getOperator();
			if (searchExpressionList.size() >= 1 && predicate != null) {
				stringSearch.append(" where ");
				for (int i = 0; i < searchExpressionList.size() - 1; i++) {
					if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
						stringSearch.append(searchExpressionList.get(i).getPropertyName() + " "
								+ searchExpressionList.get(i).getOperation().getOperator() + " '%"
								+ searchExpressionList.get(i).getValue() + "%' " + predicate + " ");
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
					stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " "
							+ searchExpressionList.get(0).getOperation().getOperator() + " '%"
							+ searchExpressionList.get(0).getValue() + "%' ");
				} else {
					stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " "
							+ searchExpressionList.get(0).getOperation().getOperator() + " '"
							+ searchExpressionList.get(0).getValue() + "' ");
				}
			}
		}
		int totalCount = jdbcTemplate.queryForObject(COUNT_ALL + " " + stringSearch.toString(), int.class);
		Pagination<ApiInfo> pagination = new Pagination<ApiInfo>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), null);
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

		List<ApiInfo> apiInfos = jdbcTemplate.query(stringBuilder.toString(), new ApiInfoRowMapper());
		pagination.setDataArray(apiInfos.toArray(new ApiInfo[] {}));

		return pagination;
	}

	// 关于增加
	public static final String SAVE = "insert into common_apiInfo(id,system_id,system_name,prefix,remark,create_time,update_time,ip_white_list) values(?,?,?,?,?,?,?,?)";
	public static final String BATCH_ADD = "insert into common_apiInfo(system_id,system_name,prefix,remark,create_time,update_time,ip_white_list) values(?,?,?,?,?,?,?)";

	@Override
	public Long[] batchAdd(ApiInfo[] entitys) {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (ApiInfo apiInfo : entitys) {
				if (apiInfo.getSystemId() != null)
					pstmt.setInt(1, apiInfo.getSystemId());
				else
					pstmt.setNull(1, Types.INTEGER);
				pstmt.setString(2, apiInfo.getSystemName());
				pstmt.setString(3, apiInfo.getPrefix());
				pstmt.setString(4, apiInfo.getRemark());
				if (apiInfo.getCreateTime() != null)
					pstmt.setTimestamp(5, new Timestamp(apiInfo.getCreateTime().getTime()));
				else
					pstmt.setNull(5, Types.TIMESTAMP);
				if (apiInfo.getUpdateTime() != null)
					pstmt.setTimestamp(6, new Timestamp(apiInfo.getUpdateTime().getTime()));
				else
					pstmt.setNull(6, Types.TIMESTAMP);
				pstmt.setString(7, apiInfo.getIpWhiteList());
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
	public static final String UPDATE_NOT_NULL_FIELD = "update common_apiInfo set update_time = ?";

	@Override
	public void updateNotNullField(ApiInfo entity) throws DaoException {
		StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
		List<Object> args = new ArrayList<>();
		entity.setUpdateTime(new Date());
		args.add(new Date());
		if (entity.getSystemId() != null) {
			stringBuffer.append(",system_id = ?");
			args.add(entity.getSystemId());
		}
		if (entity.getSystemName() != null) {
			stringBuffer.append(",system_name = ?");
			args.add(entity.getSystemName());
		}
		if (entity.getPrefix() != null) {
			stringBuffer.append(",prefix = ?");
			args.add(entity.getPrefix());
		}
		if (entity.getRemark() != null) {
			stringBuffer.append(",remark = ?");
			args.add(entity.getRemark());
		}
		if (entity.getCreateTime() != null) {
			stringBuffer.append(",create_time = ?");
			args.add(entity.getCreateTime());
		}
		if (entity.getIpWhiteList() != null) {
			stringBuffer.append(",ip_white_list = ?");
			args.add(entity.getIpWhiteList());
		}
		stringBuffer.append(" where id = ?");
		args.add(entity.getId());
		jdbcTemplate.update(stringBuffer.toString(), args.toArray());
	}

	// 关于删除
	public static final String DELETE_BY_ID = "delete from common_apiInfo where id = ?";
	public static final String BATCH_DELETE = "delete from common_apiInfo where id = ?";

	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}

	@Override
	public int batchDelete(Long[] ids) {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(BATCH_DELETE, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	public static final class ApiInfoRowMapper implements RowMapper<ApiInfo> {
		public ApiInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setId(rs.getLong("id"));
			apiInfo.setSystemId(rs.getInt("system_id"));
			apiInfo.setSystemName(rs.getString("system_name"));
			apiInfo.setPrefix(rs.getString("prefix"));
			apiInfo.setRemark(rs.getString("remark"));
			apiInfo.setCreateTime(rs.getTimestamp("create_time"));
			apiInfo.setUpdateTime(rs.getTimestamp("update_time"));
			apiInfo.setIpWhiteList(rs.getString("ip_white_list"));
			return apiInfo;
		}
	}

}
