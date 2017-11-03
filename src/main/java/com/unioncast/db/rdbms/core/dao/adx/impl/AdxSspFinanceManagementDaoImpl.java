package com.unioncast.db.rdbms.core.dao.adx.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.unioncast.common.adx.model.AdxSspFinanceManagement;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspFinanceManagementDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxSspFinanceManagementDao")
public class AdxSspFinanceManagementDaoImpl extends AdxGeneralDao<AdxSspFinanceManagement, Long>
		implements AdxSspFinanceManagementDao {

	// 关于查询
	public static String FIND_ALL = SqlBuild.select(AdxSspFinanceManagement.TABLE_NAME,
			AdxSspFinanceManagement.PROPERTIES);
	public static final String COUNT_ALL = SqlBuild.countAll(AdxSspFinanceManagement.TABLE_NAME);

	@Override
	public AdxSspFinanceManagement[] find(Long id) throws DaoException {
		List<AdxSspFinanceManagement> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxSspFinanceManagementRowMapper(),
					id));
			return list.toArray(new AdxSspFinanceManagement[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxSspFinanceManagementRowMapper());
		return list.toArray(new AdxSspFinanceManagement[] {});
	}

	@Override
	public Pagination<AdxSspFinanceManagement> page(PageCriteria pageCriteria) {
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
		Pagination<AdxSspFinanceManagement> pagination = new Pagination<AdxSspFinanceManagement>(totalCount,
				pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

		List<AdxSspFinanceManagement> managements = jdbcTemplate.query(stringBuilder.toString(),
				new AdxSspFinanceManagementRowMapper());
		pagination.setDataArray(managements.toArray(new AdxSspFinanceManagement[] {}));

		return pagination;
	}

	// 关于增加
	private static String BATCH_ADD = "insert into adx_ssp_finance_management(wd_amount,wd_date_time,wd_status,wd_account_number,company_personal_name,bank_account,remarks,update_time) values(?,?,?,?,?,?,?,?)";

	@Override
	public Long save(AdxSspFinanceManagement entity) throws DaoException {
		return insertAndReturnId(entity);
	}

	@Override
	public Long[] batchAdd(AdxSspFinanceManagement[] entitys) {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxSspFinanceManagement adxSspFinanceManagement : entitys) {
				if (adxSspFinanceManagement.getWdAmount() != null) {
					pstmt.setDouble(1, adxSspFinanceManagement.getWdAmount());
				} else {
					pstmt.setNull(1, Types.DOUBLE);
				}
				if (adxSspFinanceManagement.getWdDateTime() != null) {
					pstmt.setTimestamp(2, new Timestamp(adxSspFinanceManagement.getWdDateTime().getTime()));
				} else {
					pstmt.setNull(2, Types.TIMESTAMP);
				}
				if (adxSspFinanceManagement.getWdStatus() != null) {
					pstmt.setInt(3, adxSspFinanceManagement.getWdStatus());
				} else {
					pstmt.setNull(3, Types.INTEGER);
				}
				pstmt.setString(4, adxSspFinanceManagement.getWdAccountNumber());
				if (adxSspFinanceManagement.getCompanyPersonalName() != null) {
					pstmt.setInt(5, adxSspFinanceManagement.getCompanyPersonalName());
				} else {
					pstmt.setNull(5, Types.INTEGER);
				}
				pstmt.setString(6, adxSspFinanceManagement.getBankAccount());
				pstmt.setString(7, adxSspFinanceManagement.getRemarks());
				if (adxSspFinanceManagement.getUpdateTime() != null) {
					pstmt.setTimestamp(8, new Timestamp(adxSspFinanceManagement.getUpdateTime().getTime()));
				} else {
					pstmt.setNull(8, Types.TIMESTAMP);
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			ResultSet rs = pstmt.getGeneratedKeys();
			ids = new ArrayList<>();
			while (rs.next()) {
				ids.add(rs.getLong(1));
			}
			connection.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids.toArray(new Long[ids.size()]);
	}

	// 关于更新
	public static String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullField(AdxSspFinanceManagement.TABLE_NAME);

	@Override
	public void updateNotNullField(AdxSspFinanceManagement entity) throws DaoException {
		StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
		List<Object> args = new ArrayList<>();
		entity.setUpdateTime(new Date());
		args.add(new Date());
		if (entity.getWdAmount() != null) {
			stringBuffer.append(",wd_amount = ?");
			args.add(entity.getWdAmount());
		}
		if (entity.getWdDateTime() != null) {
			stringBuffer.append(",wd_date_time = ?");
			args.add(entity.getWdDateTime());
		}
		if (entity.getWdStatus() != null) {
			stringBuffer.append(",wd_status = ?");
			args.add(entity.getWdStatus());
		}
		if (entity.getWdAccountNumber() != null) {
			stringBuffer.append(",wd_account_number = ?");
			args.add(entity.getWdAccountNumber());
		}
		if (entity.getCompanyPersonalName() != null) {
			stringBuffer.append(",company_personal_name = ?");
			args.add(entity.getCompanyPersonalName());
		}
		if (entity.getBankAccount() != null) {
			stringBuffer.append(",bank_account = ?");
			args.add(entity.getBankAccount());
		}
		if (entity.getRemarks() != null) {
			stringBuffer.append(",remarks = ?");
			args.add(entity.getRemarks());
		}
		stringBuffer.append(" where id = ?");
		args.add(entity.getId());
		jdbcTemplate.update(stringBuffer.toString(), args.toArray());
	}

	// 关于删除
	public static String DELETE_BY_ID = SqlBuild.delete(AdxSspFinanceManagement.TABLE_NAME);
	public static String BATCH_DELETE = SqlBuild.delete(AdxSspFinanceManagement.TABLE_NAME);

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

	private Long insertAndReturnId(AdxSspFinanceManagement adxSspFinanceManagement) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName(AdxSspFinanceManagement.TABLE_NAME);
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("wd_amount",
				adxSspFinanceManagement.getWdAmount() == null ? null : adxSspFinanceManagement.getWdAmount());
		args.put("wd_date_time",
				adxSspFinanceManagement.getWdDateTime() == null ? null : adxSspFinanceManagement.getWdDateTime());
		args.put("wd_status",
				adxSspFinanceManagement.getWdStatus() == null ? null : adxSspFinanceManagement.getWdStatus());
		args.put("wd_account_number", adxSspFinanceManagement.getWdAccountNumber() == null ? null
				: adxSspFinanceManagement.getWdAccountNumber());
		args.put("company_personal_name", adxSspFinanceManagement.getCompanyPersonalName() == null ? null
				: adxSspFinanceManagement.getCompanyPersonalName());
		args.put("bank_account",
				adxSspFinanceManagement.getBankAccount() == null ? null : adxSspFinanceManagement.getBankAccount());
		args.put("remarks", adxSspFinanceManagement.getRemarks() == null ? null : adxSspFinanceManagement.getRemarks());
		args.put("update_time", new Date());
		long id = jdbcInsert.executeAndReturnKey(args).longValue();
		return id;
	}

	private final class AdxSspFinanceManagementRowMapper implements RowMapper<AdxSspFinanceManagement> {
		public AdxSspFinanceManagement mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdxSspFinanceManagement adxSspFinanceManagement = new AdxSspFinanceManagement();
			adxSspFinanceManagement.setId(rs.getLong(AdxSspFinanceManagement.ID));
			adxSspFinanceManagement.setWdAmount(rs.getDouble(AdxSspFinanceManagement.WD_AMOUNT));
			adxSspFinanceManagement.setWdDateTime(rs.getTimestamp(AdxSspFinanceManagement.WD_DATE_TIME));
			adxSspFinanceManagement.setWdStatus(rs.getInt(AdxSspFinanceManagement.WD_STATUS));
			adxSspFinanceManagement.setWdAccountNumber(rs.getString(AdxSspFinanceManagement.WD_ACCOUNT_NUMBER));
			adxSspFinanceManagement.setCompanyPersonalName(rs.getInt(AdxSspFinanceManagement.COMPANY_PERSONAL_NAME));
			adxSspFinanceManagement.setBankAccount(rs.getString(AdxSspFinanceManagement.BANK_ACCOUNT));
			adxSspFinanceManagement.setRemarks(rs.getString(AdxSspFinanceManagement.REMARKS));
			adxSspFinanceManagement.setUpdateTime(rs.getTimestamp(AdxSspFinanceManagement.UPDATE_TIME));
			return adxSspFinanceManagement;
		}
	}

}
