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

import com.unioncast.common.adx.model.AdxSspReportFormHour;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspReportFormHourDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxSspReportFormHourDao")
public class AdxSspReportFormHourDaoImpl extends AdxGeneralDao<AdxSspReportFormHour, Long>
		implements AdxSspReportFormHourDao {

	// 关于查询
	public static String FIND_ALL = SqlBuild.select(AdxSspReportFormHour.TABLE_NAME, AdxSspReportFormHour.PROPERTIES);
	public static final String COUNT_ALL = SqlBuild.countAll(AdxSspReportFormHour.TABLE_NAME);

	@Override
	public AdxSspReportFormHour[] find(Long id) throws DaoException {
		List<AdxSspReportFormHour> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxSspReportFormHourRowMapper(), id));
			return list.toArray(new AdxSspReportFormHour[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxSspReportFormHourRowMapper());
		return list.toArray(new AdxSspReportFormHour[] {});
	}

	@Override
	public Pagination<AdxSspReportFormHour> page(PageCriteria pageCriteria) {
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
		Pagination<AdxSspReportFormHour> pagination = new Pagination<AdxSspReportFormHour>(totalCount,
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

		List<AdxSspReportFormHour> hours = jdbcTemplate.query(stringBuilder.toString(),
				new AdxSspReportFormHourRowMapper());
		pagination.setDataArray(hours.toArray(new AdxSspReportFormHour[] {}));

		return pagination;
	}

	// 关于增加
	private static String BATCH_ADD = "insert into adx_ssp_report_form_hour(media_type,size,show_num,click_rate,thousand_show_price,average_click_price,income,date,remarks,update_time) values(?,?,?,?,?,?,?,?,?,?)";

	@Override
	public Long save(AdxSspReportFormHour entity) throws DaoException {
		return insertAndReturnId(entity);
	}

	@Override
	public Long[] batchAdd(AdxSspReportFormHour[] entitys) throws DaoException {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxSspReportFormHour adxSspReportFormHour : entitys) {
				if (adxSspReportFormHour.getMediaType() != null)
					pstmt.setInt(1, adxSspReportFormHour.getMediaType());
				else
					pstmt.setNull(1, Types.INTEGER);
				pstmt.setString(2, adxSspReportFormHour.getSize());
				pstmt.setString(3, adxSspReportFormHour.getShowNum());
				if (adxSspReportFormHour.getClickRate() != null)
					pstmt.setDouble(4, adxSspReportFormHour.getClickRate());
				else
					pstmt.setNull(4, Types.DOUBLE);
				if (adxSspReportFormHour.getThousandShowPrice() != null)
					pstmt.setDouble(5, adxSspReportFormHour.getThousandShowPrice());
				else
					pstmt.setNull(5, Types.DOUBLE);
				if (adxSspReportFormHour.getAverageClickPrice() != null)
					pstmt.setDouble(6, adxSspReportFormHour.getAverageClickPrice());
				else
					pstmt.setNull(6, Types.DOUBLE);
				if (adxSspReportFormHour.getIncome() != null)
					pstmt.setDouble(7, adxSspReportFormHour.getIncome());
				else
					pstmt.setNull(7, Types.DOUBLE);
				if (adxSspReportFormHour.getDate() != null)
					pstmt.setTimestamp(8, new Timestamp(adxSspReportFormHour.getDate().getTime()));
				else
					pstmt.setNull(8, Types.TIMESTAMP);
				pstmt.setString(9, adxSspReportFormHour.getRemarks());
				if (adxSspReportFormHour.getUpdateTime() != null) {
					pstmt.setTimestamp(10, new Timestamp(adxSspReportFormHour.getUpdateTime().getTime()));
				} else {
					pstmt.setNull(10, Types.TIMESTAMP);
				}
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

	// 关于更新
	public static String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullField(AdxSspReportFormHour.TABLE_NAME);

	@Override
	public void updateNotNullField(AdxSspReportFormHour entity) throws DaoException {
		StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
		List<Object> args = new ArrayList<>();
		entity.setUpdateTime(new Date());
		args.add(new Date());
		if (entity.getMediaType() != null) {
			stringBuffer.append(",media_type = ?");
			args.add(entity.getMediaType());
		}
		if (entity.getSize() != null) {
			stringBuffer.append(",size = ?");
			args.add(entity.getSize());
		}
		if (entity.getShowNum() != null) {
			stringBuffer.append(",show_num = ?");
			args.add(entity.getShowNum());
		}
		if (entity.getClickRate() != null) {
			stringBuffer.append(",click_rate = ?");
			args.add(entity.getClickRate());
		}
		if (entity.getThousandShowPrice() != null) {
			stringBuffer.append(",thousand_show_price = ?");
			args.add(entity.getThousandShowPrice());
		}
		if (entity.getAverageClickPrice() != null) {
			stringBuffer.append(",average_click_price = ?");
			args.add(entity.getAverageClickPrice());
		}
		if (entity.getIncome() != null) {
			stringBuffer.append(",income = ?");
			args.add(entity.getIncome());
		}
		if (entity.getDate() != null) {
			stringBuffer.append(",date = ?");
			args.add(entity.getDate());
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
	public static String DELETE_BY_ID = SqlBuild.delete(AdxSspReportFormHour.TABLE_NAME);
	public static String BATCH_DELETE = SqlBuild.delete(AdxSspReportFormHour.TABLE_NAME);

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

	private Long insertAndReturnId(AdxSspReportFormHour adxSspReportFormHour) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AdxSspReportFormHour.TABLE_NAME);
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("media_type",
				adxSspReportFormHour.getMediaType() == null ? null : adxSspReportFormHour.getMediaType());
		args.put("size", adxSspReportFormHour.getSize() == null ? null : adxSspReportFormHour.getSize());
		args.put("show_num", adxSspReportFormHour.getShowNum() == null ? null : adxSspReportFormHour.getShowNum());
		args.put("click_rate",
				adxSspReportFormHour.getClickRate() == null ? null : adxSspReportFormHour.getClickRate());
		args.put("thousand_show_price", adxSspReportFormHour.getThousandShowPrice() == null ? null
				: adxSspReportFormHour.getThousandShowPrice());
		args.put("average_click_price", adxSspReportFormHour.getAverageClickPrice() == null ? null
				: adxSspReportFormHour.getAverageClickPrice());
		args.put("income", adxSspReportFormHour.getIncome() == null ? null : adxSspReportFormHour.getIncome());
		args.put("date", adxSspReportFormHour.getDate() == null ? null : adxSspReportFormHour.getDate());
		args.put("remarks", adxSspReportFormHour.getRemarks() == null ? null : adxSspReportFormHour.getRemarks());
		args.put("update_time", new Date());
		long id = jdbcInsert.executeAndReturnKey(args).longValue();
		return id;
	}

	private final class AdxSspReportFormHourRowMapper implements RowMapper<AdxSspReportFormHour> {
		public AdxSspReportFormHour mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdxSspReportFormHour adxSspReportFormHour = new AdxSspReportFormHour();
			adxSspReportFormHour.setId(rs.getLong("id"));
			adxSspReportFormHour.setMediaType(rs.getInt("media_type"));
			adxSspReportFormHour.setSize(rs.getString("size"));
			adxSspReportFormHour.setShowNum(rs.getString("show_num"));
			adxSspReportFormHour.setClickRate(rs.getDouble("click_rate"));
			adxSspReportFormHour.setThousandShowPrice(rs.getDouble("thousand_show_price"));
			adxSspReportFormHour.setAverageClickPrice(rs.getDouble("average_click_price"));
			adxSspReportFormHour.setIncome(rs.getDouble("income"));
			adxSspReportFormHour.setDate(rs.getTimestamp("date"));
			adxSspReportFormHour.setRemarks(rs.getString("remarks"));
			adxSspReportFormHour.setUpdateTime(rs.getTimestamp("update_time"));
			return adxSspReportFormHour;
		}
	}

}
