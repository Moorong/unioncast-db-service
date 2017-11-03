package com.unioncast.db.rdbms.core.dao.adx.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.adx.model.AdxDspBiddingParamCriteria;
import com.unioncast.common.adx.model.AdxDspBiddingParams;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspBiddingParamsDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxDspBiddingParamsDao")
public class AdxDspBiddingParamsDaoImpl extends AdxGeneralDao<AdxDspBiddingParams, Long>
		implements AdxDspBiddingParamsDao {

	public static final class AdxDspBiddingParamsRowMapper implements RowMapper<AdxDspBiddingParams> {
		/**
		 * 结果集封装 (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		@Override
		public AdxDspBiddingParams mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AdxDspBiddingParams(rs.getLong("id"), rs.getInt("flow_type"), rs.getLong("dsp_id"),
					rs.getLong("can_send_request_num"), rs.getLong("actual_send_num"), rs.getLong("do_bidding_num"),
					rs.getLong("bidding_success_num"), rs.getLong("bidding_timeout_num"), rs.getLong("parse_error_num"),
					rs.getString("day_time"), rs.getTimestamp("create_time"), rs.getTimestamp("update_time"),
					rs.getString("remarks"));
		}

	}

	private static String DELETE_BY_ID = SqlBuild.delete(AdxDspBiddingParams.TABLE_NAME);
	private static String FIND_ALL = SqlBuild.select(AdxDspBiddingParams.TABLE_NAME, AdxDspBiddingParams.PROPERTIES);
	private static String ADD = "insert into " + AdxDspBiddingParams.TABLE_NAME + "(" + AdxDspBiddingParams.PROPERTIES
			+ ") values(null,?,?,?, ?,?,?,?, ?,?,?,?, ?)";
	private static String COUNT_ALL = SqlBuild.countAll(AdxDspBiddingParams.TABLE_NAME);

	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}

	@Override
	public AdxDspBiddingParams[] find(Long id) throws DaoException {
		List<AdxDspBiddingParams> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspBiddingParamsRowMapper(), id));
			return list.toArray(new AdxDspBiddingParams[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxDspBiddingParamsRowMapper());
		return list.toArray(new AdxDspBiddingParams[] {});
	}

	@Override
	public Long[] batchAdd(AdxDspBiddingParams[] entitys) throws DaoException {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxDspBiddingParams adxDspBiddingParams : entitys) {
				if (adxDspBiddingParams.getFlowType() != null) {
					pstmt.setInt(1, adxDspBiddingParams.getFlowType());
				} else {
					pstmt.setNull(1, Types.INTEGER);
				}
				if (adxDspBiddingParams.getDspId() != null) {
					pstmt.setLong(2, adxDspBiddingParams.getDspId());
				} else {
					pstmt.setNull(2, Types.BIGINT);
				}
				if (adxDspBiddingParams.getCanSendRequestNum() != 0) {
					pstmt.setLong(3, adxDspBiddingParams.getCanSendRequestNum());
				} else {
					pstmt.setNull(3, Types.BIGINT);
				}
				if (adxDspBiddingParams.getActualSendNum() != 0) {
					pstmt.setLong(4, adxDspBiddingParams.getActualSendNum());
				} else {
					pstmt.setNull(4, Types.BIGINT);
				}
				if (adxDspBiddingParams.getDoBiddingNum() != 0) {
					pstmt.setLong(5, adxDspBiddingParams.getDoBiddingNum());
				} else {
					pstmt.setNull(5, Types.BIGINT);
				}
				if (adxDspBiddingParams.getBiddingSuccessNum() != 0) {
					pstmt.setLong(6, adxDspBiddingParams.getBiddingSuccessNum());
				} else {
					pstmt.setNull(6, Types.BIGINT);
				}
				if (adxDspBiddingParams.getParseErrorNum() != 0) {
					pstmt.setLong(7, adxDspBiddingParams.getParseErrorNum());
				} else {
					pstmt.setNull(7, Types.BIGINT);
				}
				pstmt.setString(8, adxDspBiddingParams.getDayTime());
				if (adxDspBiddingParams.getCreateTime() != null) {
					pstmt.setTimestamp(9, new Timestamp(adxDspBiddingParams.getCreateTime().getTime()));
				} else {
					pstmt.setTimestamp(9, new Timestamp(new Date().getTime()));
				}
				pstmt.setString(10, adxDspBiddingParams.getRemarks());
				pstmt.setNull(11, Types.TIMESTAMP);
				if (adxDspBiddingParams.getBiddingTimeOutNum() != 0) {
					pstmt.setLong(12, adxDspBiddingParams.getBiddingTimeOutNum());
				} else {
					pstmt.setNull(12, Types.BIGINT);
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
	public int batchDelete(List<Long> ids) {
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

	/*
	 * SELECT SUM(can_send_request_num), SUM(actual_send_num), SUM(do_bidding_num), SUM(bidding_success_num),
	 * SUM(parse_error_num), SUM(bidding_timeout_num) FROM adx_dsp_bidding_params WHERE LEFT(day_time,8) BETWEEN
	 * 20160808 AND 20160812 AND flow_type = 1 AND dsp_id = 1
	 */
	AdxDspBiddingParams adxDspBiddingParams = null;
	Map<String, AdxDspBiddingParams> adxDspBiddingParamsMap = new HashMap<>();

	@Override
	public AdxDspBiddingParams getSumDataByBE(AdxDspBiddingParamCriteria criteria) {
		String getSumDataByBE = "select sum(can_send_request_num) total_can_send, sum(actual_send_num) total_actual_send, sum(do_bidding_num) total_do_bidding,"
				+ " sum(bidding_success_num) total_bidding_success, sum(parse_error_num) total_parse_error, sum(bidding_timeout_num) total_bidding_timeout"
				+ " from adx_dsp_bidding_params" + " where left(day_time,8) between "
				+ Long.parseLong(criteria.getBeginTime()) + " and " + Long.parseLong(criteria.getEndTime())
				+ " and flow_type = " + criteria.getFlowType() + " and dsp_id = " + criteria.getDspId();

		jdbcTemplate.query(getSumDataByBE, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				adxDspBiddingParams = new AdxDspBiddingParams(null, criteria.getFlowType(), criteria.getDspId(),
						rs.getLong("total_can_send"), rs.getLong("total_actual_send"), rs.getLong("total_do_bidding"),
						rs.getLong("total_bidding_success"), rs.getLong("total_bidding_timeout"),
						rs.getLong("total_parse_error"), null, null, null, null);
			}
		});
		return adxDspBiddingParams;
	}

	@Override
	public Map<String, AdxDspBiddingParams> getDataByBE(AdxDspBiddingParamCriteria criteria) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = dateFormat.parse(criteria.getBeginTime());
		Date endDate = dateFormat.parse(criteria.getEndTime());
		Calendar calendar = Calendar.getInstance();
		adxDspBiddingParamsMap.clear();
		for (calendar.setTime(beginDate); calendar.getTime().getTime() <= endDate.getTime(); calendar.add(Calendar.DAY_OF_YEAR,
				1)) {
			String currentDay = dateFormat.format(calendar.getTime());
			String getSumDataByBE = "select sum(can_send_request_num) total_can_send, sum(actual_send_num) total_actual_send, sum(do_bidding_num) total_do_bidding,"
					+ " sum(bidding_success_num) total_bidding_success, sum(parse_error_num) total_parse_error, sum(bidding_timeout_num) total_bidding_timeout"
					+ " from adx_dsp_bidding_params" + " where left(day_time,8) = " + currentDay + " and flow_type = "
					+ criteria.getFlowType() + " and dsp_id = " + criteria.getDspId();
			jdbcTemplate.query(getSumDataByBE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					adxDspBiddingParams = new AdxDspBiddingParams(null, criteria.getFlowType(), criteria.getDspId(),
							rs.getLong("total_can_send"), rs.getLong("total_actual_send"),
							rs.getLong("total_do_bidding"), rs.getLong("total_bidding_success"),
							rs.getLong("total_bidding_timeout"), rs.getLong("total_parse_error"), null, null, null,
							null);

					adxDspBiddingParamsMap.put(currentDay, adxDspBiddingParams);
				}
			});
		}
		return adxDspBiddingParamsMap;
	}

	@Override
	public Pagination<AdxDspBiddingParams> page(PageCriteria pageCriteria) {
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1");
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = (String) searchExpressionList.get(i).getValue();
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator()))
					value = "'%" + value + "%'";
				String criteriaSql = " " + pageCriteria.getPredicate() + " "
						+ searchExpressionList.get(i).getPropertyName() + " "
						+ searchExpressionList.get(i).getOperation().getOperator() + " " + value;
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
		List<AdxDspBiddingParams> list = jdbcTemplate.query(pageSql.toString(), new AdxDspBiddingParamsRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<AdxDspBiddingParams>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new AdxDspBiddingParams[] {}));
	}

}
