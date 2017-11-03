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

import com.unioncast.common.adx.model.AdxDspAdvertisers;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspAdvertisersDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxDspAdvertisersDao")
public class AdxDspAdvertisersDaoImpl extends AdxGeneralDao<AdxDspAdvertisers, Long> implements AdxDspAdvertisersDao {

	public static final class AdxDspAdvertisersRowMapper implements RowMapper<AdxDspAdvertisers>{
		/** 
		 * 结果集封装
		 * (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		@Override
		public AdxDspAdvertisers mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AdxDspAdvertisers(rs.getLong("id"), rs.getString("name"), rs.getString("client_main_qua_name"),
					rs.getString("web_name"), rs.getString("phone"), rs.getString("address"),
					rs.getTimestamp("registration_time"), rs.getTimestamp("audit_time"), rs.getInt("audit_status"),
					rs.getString("reject_details"), rs.getLong("dsp_user_id"), rs.getLong("qualifications_id"),
					rs.getTimestamp("create_date"), rs.getString("remarks"), rs.getTimestamp("update_time"));
		}
		
	}

	private static String DELETE_BY_ID = SqlBuild.delete(AdxDspAdvertisers.TABLE_NAME);
	private static String FIND_ALL = SqlBuild.select(AdxDspAdvertisers.TABLE_NAME, AdxDspAdvertisers.PROPERTIES);
	private static String COUNT_ALL = SqlBuild.countAll(AdxDspAdvertisers.TABLE_NAME);
	private static String ADD = "insert into " + AdxDspAdvertisers.TABLE_NAME + "("
			+ AdxDspAdvertisers.PROPERTIES + ") values (null,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?)";
	
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	
	@Override
	public AdxDspAdvertisers[] find(Long id) throws DaoException {
		List<AdxDspAdvertisers> list = new ArrayList<>();
		if(id != null){
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspAdvertisersRowMapper(), id));
			return list.toArray(new AdxDspAdvertisers[]{});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxDspAdvertisersRowMapper());
		return list.toArray(new AdxDspAdvertisers[]{});
	}
	

	@Override
	public Pagination<AdxDspAdvertisers> page(PageCriteria pageCriteria) {
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1");
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = (String) searchExpressionList.get(i).getValue();
				if("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
				String criteriaSql = " " + pageCriteria.getPredicate() + " " + searchExpressionList.get(i).getPropertyName()
						+ " " + searchExpressionList.get(i).getOperation().getOperator() + " " + value;
				pageSql.append(criteriaSql);
				countAll.append(criteriaSql);
			}
		}
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		if (orderExpressionList != null && orderExpressionList.size() != 0) {
			pageSql.append(" order by");
			for (int i = 0; i < orderExpressionList.size(); i++) {
				if (i > 0) pageSql.append(",");
				pageSql.append(" " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
			}
		}
		pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
				+ pageCriteria.getPageSize());
		List<AdxDspAdvertisers> list = jdbcTemplate.query(pageSql.toString(), new AdxDspAdvertisersRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<AdxDspAdvertisers>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				list.toArray(new AdxDspAdvertisers[] {}));
	}

	
	@Override
	public Long[] batchAdd(AdxDspAdvertisers[] entitys) throws DaoException {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxDspAdvertisers adxDspAdvertisers : entitys) {
				pstmt.setString(1, adxDspAdvertisers.getName());
				pstmt.setString(2, adxDspAdvertisers.getClientMainQuaName());
				pstmt.setString(3, adxDspAdvertisers.getWebName());
				pstmt.setString(4, adxDspAdvertisers.getPhone());
				pstmt.setString(5, adxDspAdvertisers.getAddress());
				if (adxDspAdvertisers.getRegistrationTime() != null) {
					pstmt.setTimestamp(6, new Timestamp(adxDspAdvertisers.getRegistrationTime().getTime()));
				} else {
					pstmt.setNull(6, Types.TIMESTAMP);
				}
				if (adxDspAdvertisers.getAuditTime() != null) {
					pstmt.setTimestamp(7, new Timestamp(adxDspAdvertisers.getAuditTime().getTime()));
				} else {
					pstmt.setNull(7, Types.TIMESTAMP);
				}
				if (adxDspAdvertisers.getAuditStatus() != 0) {
					pstmt.setInt(8, adxDspAdvertisers.getAuditStatus());
				} else {
					pstmt.setNull(8, Types.INTEGER);
				}
				pstmt.setString(9, adxDspAdvertisers.getRejectDetails());
				if (adxDspAdvertisers.getDspUserId() != null){
					pstmt.setLong(10, adxDspAdvertisers.getDspUserId());
				} else {
					pstmt.setNull(10, Types.BIGINT);
				}
				if (adxDspAdvertisers.getQualificationsId() != null){
					pstmt.setLong(11, adxDspAdvertisers.getQualificationsId());
				} else {
					pstmt.setNull(11, Types.BIGINT);
				}
				if (adxDspAdvertisers.getCreateDate() != null){
					pstmt.setTimestamp(12, new Timestamp(adxDspAdvertisers.getCreateDate().getTime()));
				} else {
					pstmt.setTimestamp(12, new Timestamp(new Date().getTime()));;
				}
				pstmt.setString(13, adxDspAdvertisers.getRemarks());
				pstmt.setNull(14, Types.TIMESTAMP);
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
	
}
