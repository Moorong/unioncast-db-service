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

import com.unioncast.common.adx.model.AdxDspAccessSettings;
import com.unioncast.common.adx.model.AdxDspFlowAccessSetting;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspAccessSettingsDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxDspAccessSettingsDao")
public class AdxDspAccessSettingsDaoImpl extends AdxGeneralDao<AdxDspAccessSettings, Long>
		implements AdxDspAccessSettingsDao {

	public static final class AdxDspAccessSettingsRowMapper implements RowMapper<AdxDspAccessSettings> {
		/**
		 * 结果集封装 (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		@Override
		public AdxDspAccessSettings mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AdxDspAccessSettings(rs.getLong("id"), rs.getLong("dsp_id"), rs.getString("token"),
					rs.getString("ip"), rs.getString("current_key"), rs.getString("check_key"),
					rs.getInt("dsp_qps_web"), rs.getInt("bes_qps_web"), rs.getInt("dsp_qps_mobile"),
					rs.getInt("bes_qps_mobile"), rs.getInt("dsp_qps_video"), rs.getInt("bes_qps_video"),
					rs.getString("remarks"), rs.getTimestamp("update_time"), rs.getString("cookie_mapping_url"),
					rs.getString("rtb_url"), rs.getString("click_monitoring"), rs.getString("exposure_monitoring"),
					rs.getString("delivery_status"));
		}
	}

	private static String FIND_ALL = SqlBuild.select(AdxDspAccessSettings.TABLE_NAME, AdxDspAccessSettings.PROPERTIES);
	private static String ADD = "insert into " + AdxDspAccessSettings.TABLE_NAME + "("
			+ AdxDspAccessSettings.PROPERTIES + ") values (null,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?)";
	private static String DELETE_BY_ID = SqlBuild.delete(AdxDspAccessSettings.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(AdxDspAccessSettings.TABLE_NAME);
	
	
	@Override
	public AdxDspAccessSettings[] find(Long id) throws DaoException {
		List<AdxDspAccessSettings> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspAccessSettingsRowMapper(), id));
			return list.toArray(new AdxDspAccessSettings[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxDspAccessSettingsRowMapper());
		return list.toArray(new AdxDspAccessSettings[] {});
	}

	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}

	

	@Override
	public Long[] batchAdd(AdxDspAccessSettings[] entitys) throws DaoException {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxDspAccessSettings adxDspAccessSettings : entitys) {
				if (adxDspAccessSettings.getDspId() != 0) {
					pstmt.setLong(1, adxDspAccessSettings.getDspId());
				} else {
					pstmt.setNull(1, Types.DOUBLE);
				}
				pstmt.setString(2, adxDspAccessSettings.getToken());
				pstmt.setString(3, adxDspAccessSettings.getIp());
				pstmt.setString(4, adxDspAccessSettings.getCurrentKey());
				pstmt.setString(5, adxDspAccessSettings.getCheckKey());
				if (adxDspAccessSettings.getDspQpsWeb() != 0) {
					pstmt.setInt(6, adxDspAccessSettings.getDspQpsWeb());
				} else {
					pstmt.setNull(6, Types.INTEGER);
				}
				if (adxDspAccessSettings.getBesQpsWeb() != 0) {
					pstmt.setInt(7, adxDspAccessSettings.getBesQpsWeb());
				} else {
					pstmt.setNull(7, Types.INTEGER);
				}
				if (adxDspAccessSettings.getDspQpsMobile() != 0) {
					pstmt.setInt(8, adxDspAccessSettings.getDspQpsMobile());
				} else {
					pstmt.setNull(8, Types.INTEGER);
				}
				if (adxDspAccessSettings.getBesQpsMobile() != 0) {
					pstmt.setInt(9, adxDspAccessSettings.getBesQpsMobile());
				} else {
					pstmt.setNull(9, Types.INTEGER);
				}
				if (adxDspAccessSettings.getDspQpsVideo() != 0) {
					pstmt.setInt(10, adxDspAccessSettings.getDspQpsVideo());
				} else {
					pstmt.setNull(10, Types.INTEGER);
				}
				if (adxDspAccessSettings.getBesQpsVideo() != 0) {
					pstmt.setInt(11, adxDspAccessSettings.getBesQpsVideo());
				} else {
					pstmt.setNull(11, Types.INTEGER);
				}
				pstmt.setString(12, adxDspAccessSettings.getRemarks());
				pstmt.setString(13, adxDspAccessSettings.getCookieMappingUrl());
				pstmt.setString(14, adxDspAccessSettings.getRtbUrl());
				pstmt.setString(15, adxDspAccessSettings.getClickMonitoring());
				pstmt.setString(16, adxDspAccessSettings.getExposureMonitoring());
				pstmt.setString(17, adxDspAccessSettings.getDeliveryStatus());
				if (adxDspAccessSettings.getUpdateTime() != null) {
					pstmt.setTimestamp(18, new Timestamp(adxDspAccessSettings.getUpdateTime().getTime()));
				} else {
					pstmt.setTimestamp(18, new Timestamp(new Date().getTime()));
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

	@Override
	public AdxDspAccessSettings findByDspId(long dspId) {
		return jdbcTemplate.queryForObject(FIND_ALL + " where dsp_id = ?", new AdxDspAccessSettingsRowMapper(), dspId);
	}

	@Override
	public Pagination<AdxDspAccessSettings> page(PageCriteria pageCriteria) throws DaoException {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + "where 1=1");
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
		List<AdxDspAccessSettings> list = jdbcTemplate.query(pageSql.toString(), new AdxDspAccessSettingsRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<AdxDspAccessSettings>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new AdxDspAccessSettings[] {}));
	}

	@Override
	public AdxDspAccessSettings procedure(Long id) throws SQLException {
		String callableSql = "{call `p_select*fromAdxDspAccessSettings`(?)}";
		// CallableStatement cs = jdbcTemplate.getDataSource().getConnection().prepareCall(callableSql);
		// cs.setLong(1, id);
		// ResultSet resultSet = cs.executeQuery();

		/*
		 * //创建存储过程的对象 CallableStatement c=conn.prepareCall("{call getsum(?,?)}");
		 * 
		 * //给存储过程的第一个参数设置值 c.setInt(1,100);
		 * 
		 * //注册存储过程的第二个参数 c.registerOutParameter(2,java.sql.Types.INTEGER);
		 * 
		 * //执行存储过程 c.execute();
		 * 
		 * //得到存储过程的输出参数值 System.out.println (c.getInt(2)); conn.close();
		 */

		//
		// 若是调用存储过程，需要重新封装结果集
		return jdbcTemplate.queryForObject(callableSql, new AdxDspAccessSettingsRowMapper(), id);
	}

	private static final String FIND_BY_FLOW_TYPE = "select " + AdxDspAccessSettings.PROPERTIES + " from " + AdxDspAccessSettings.TABLE_NAME + " where dsp_id = (select dsp_id from " + AdxDspFlowAccessSetting.TABLE_NAME + " where flow_type = ?)";
	
	@Override
	public AdxDspAccessSettings[] findByFlowType(Integer flowType) {
		List<AdxDspAccessSettings> adxDspAccessSettingsList = jdbcTemplate.query(FIND_BY_FLOW_TYPE, new AdxDspAccessSettingsRowMapper(), flowType);
		return adxDspAccessSettingsList.toArray(new AdxDspAccessSettings[]{});
	}
}
