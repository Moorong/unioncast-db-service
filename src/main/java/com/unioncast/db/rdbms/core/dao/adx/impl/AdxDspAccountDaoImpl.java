package com.unioncast.db.rdbms.core.dao.adx.impl;

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

import com.unioncast.common.adx.model.AdxDspAccount;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspAccountDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxDspAccountDao")
public class AdxDspAccountDaoImpl extends AdxGeneralDao<AdxDspAccount, Long> implements AdxDspAccountDao {

	public static final class AdxDspAccountRowMapper implements RowMapper<AdxDspAccount> {
		/**
		 * 结果集封装 (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		@Override
		public AdxDspAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AdxDspAccount(rs.getInt("id"), rs.getString("account_name"), rs.getString("dsp_full_name"),
					rs.getTimestamp("registration_time"), rs.getString("contacts"), rs.getString("fixed_telephone"),
					rs.getString("mobile"), rs.getString("email"), rs.getString("company_name"),
					rs.getString("company_address"), rs.getString("company_code"), rs.getString("company_fax"),
					rs.getString("remarks"), rs.getTimestamp("update_time"));
		}

	}

	private static String DELETE_BY_ID = SqlBuild.delete(AdxDspAccount.TABLE_NAME);
	private static String FIND_ALL = SqlBuild.select(AdxDspAccount.TABLE_NAME, AdxDspAccount.PROPERTIES);
	private static String ADD = "insert into " + AdxDspAccount.TABLE_NAME + "(" + AdxDspAccount.PROPERTIES
			+ ") values (null,?,?,?, ?,?,?,?, ?,?,?,?, ?,?)";
	private static String COUNT_ALL = SqlBuild.countAll(AdxDspAccount.TABLE_NAME);
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}

	@Override
	public AdxDspAccount[] find(Long id) throws DaoException {
		List<AdxDspAccount> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspAccountRowMapper(), id));
			return list.toArray(new AdxDspAccount[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxDspAccountRowMapper());
		return list.toArray(new AdxDspAccount[] {});
	}

	
	@Override
	public Long[] batchAdd(AdxDspAccount[] entitys) throws DaoException {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxDspAccount adxDspAccount : entitys) {
				pstmt.setString(1, adxDspAccount.getAccountName());
				pstmt.setString(2, adxDspAccount.getDspFullName());
				if (adxDspAccount.getRegistrationTime() != null) {
					pstmt.setTimestamp(3, new Timestamp(adxDspAccount.getRegistrationTime().getTime()));
				} else {
					pstmt.setNull(3, Types.TIMESTAMP);
				}
				pstmt.setString(4, adxDspAccount.getContacts());
				pstmt.setString(5, adxDspAccount.getFixedTelephone());
				pstmt.setString(6, adxDspAccount.getMobile());
				pstmt.setString(7, adxDspAccount.getEmail());
				pstmt.setString(8, adxDspAccount.getCompanyName());
				pstmt.setString(9, adxDspAccount.getCompanyAddress());
				pstmt.setString(10, adxDspAccount.getCompanyCode());
				pstmt.setString(11, adxDspAccount.getCompanyFax());
				pstmt.setString(12, adxDspAccount.getRemarks());
				pstmt.setTimestamp(13, new Timestamp(new Date().getTime()));
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
		return ids.toArray(new Long[]{});
	}

	

	@Override
	public int batchDelete(Long[] ids) {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DELETE_BY_ID, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	@Override
	public Pagination<AdxDspAccount> page(PageCriteria pageCriteria) {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + "where 1=1");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = (String) searchExpressionList.get(i).getValue();
				if("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
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
				if (i > 0) pageSql.append(",");
				pageSql.append(
						" " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
			}
		}
		pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
				+ pageCriteria.getPageSize());
		List<AdxDspAccount> list = jdbcTemplate.query(pageSql.toString(), new AdxDspAccountRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<AdxDspAccount>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				list.toArray(new AdxDspAccount[] {}));
	}

}
