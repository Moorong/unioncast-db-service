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

import com.unioncast.common.adx.model.AdxDspAdcreative;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspAdcreativeDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxDspAdcreativeDao")
public class AdxDspAdcreativeDaoImpl extends AdxGeneralDao<AdxDspAdcreative, Long> implements AdxDspAdcreativeDao {

	public static final class AdxDspAdcreativeRowMapper implements RowMapper<AdxDspAdcreative> {
		/**
		 * 结果集封装 (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		@Override
		public AdxDspAdcreative mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AdxDspAdcreative(rs.getLong("id"), rs.getString("title"), rs.getString("description"),
					rs.getInt("flow_type"), rs.getString("industry_type"), rs.getString("operating_system"),
					rs.getString("click_landing_page"), rs.getString("creative_type"), rs.getString("size"),
					rs.getString("creative_url"), rs.getLong("advert_person_id"),
					rs.getString("advertising_position_type"), rs.getString("resolution"), rs.getString("play_time"),
					rs.getLong("user_id"), rs.getInt("check_status"), rs.getTimestamp("check_time"),
					rs.getString("refuse_details"), rs.getTimestamp("create_time"), rs.getTimestamp("update_time"),
					rs.getString("remarks"));
		}
	}

	private static String FIND_ALL = SqlBuild.select(AdxDspAdcreative.TABLE_NAME, AdxDspAdcreative.PROPERTIES);
	private static String DELETE_BY_ID = SqlBuild.delete(AdxDspAdcreative.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(AdxDspAdcreative.TABLE_NAME);
	private static String ADD = "insert into " + AdxDspAdcreative.TABLE_NAME + "("
			+ AdxDspAdcreative.PROPERTIES + ") values(null,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?)";

	@Override
	public AdxDspAdcreative[] find(Long id) throws DaoException {
		List<AdxDspAdcreative> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspAdcreativeRowMapper(), id));
			return list.toArray(new AdxDspAdcreative[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxDspAdcreativeRowMapper());
		return list.toArray(new AdxDspAdcreative[] {});
	}

	

	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}

	@Override
	public Pagination<AdxDspAdcreative> page(PageCriteria pageCriteria) {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = (String) searchExpressionList.get(i).getValue();
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
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
		List<AdxDspAdcreative> list = jdbcTemplate.query(pageSql.toString(), new AdxDspAdcreativeRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<AdxDspAdcreative>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				list.toArray(new AdxDspAdcreative[] {}));
	}

	

	@Override
	public Long[] batchAdd(AdxDspAdcreative[] entitys) throws DaoException {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxDspAdcreative adxDspAdcreative : entitys) {
				pstmt.setString(1, adxDspAdcreative.getTitle());
				pstmt.setString(2, adxDspAdcreative.getDescription());
				if (adxDspAdcreative.getFlowType() != 0) {
					pstmt.setInt(3, adxDspAdcreative.getFlowType());
				} else {
					pstmt.setNull(3, Types.INTEGER);
				}
				pstmt.setString(4, adxDspAdcreative.getIndustryType());
				pstmt.setString(5, adxDspAdcreative.getOperatingSystem());
				pstmt.setString(6, adxDspAdcreative.getClickLandingPage());
				pstmt.setString(7, adxDspAdcreative.getCreativeType());
				pstmt.setString(8, adxDspAdcreative.getSize());
				pstmt.setString(9, adxDspAdcreative.getCreativeUrl());
				if (adxDspAdcreative.getAdvertPersonId() != null) {
					pstmt.setLong(10, adxDspAdcreative.getAdvertPersonId());
				} else {
					pstmt.setNull(10, Types.BIGINT);
				}
				pstmt.setString(11, adxDspAdcreative.getAdvertisingPositionType());
				pstmt.setString(12, adxDspAdcreative.getResolution());
				pstmt.setString(13, adxDspAdcreative.getPlayTime());
				if (adxDspAdcreative.getUserId() != null) {
					pstmt.setLong(14, adxDspAdcreative.getUserId());
				} else {
					pstmt.setNull(14, Types.BIGINT);
				}
				if (adxDspAdcreative.getCheckStatus() != null) {
					pstmt.setInt(15, adxDspAdcreative.getCheckStatus());
				} else {
					pstmt.setNull(15, Types.INTEGER);
				}
				if (adxDspAdcreative.getCheckTime() != null) {
					pstmt.setTimestamp(16, new Timestamp(adxDspAdcreative.getCheckTime().getTime()));
				} else {
					pstmt.setNull(16, Types.TIMESTAMP);
				}
				pstmt.setString(17, adxDspAdcreative.getRefuseDetails());
				if (adxDspAdcreative.getCreateTime() != null) {
					pstmt.setTimestamp(18, new Timestamp(adxDspAdcreative.getCreateTime().getTime()));
				} else {
					pstmt.setTimestamp(18, new Timestamp(new Date().getTime()));
				}
				pstmt.setString(19, adxDspAdcreative.getRemarks());
				pstmt.setNull(20, Types.TIMESTAMP);
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
		return ids.toArray(new Long[] {});
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

}