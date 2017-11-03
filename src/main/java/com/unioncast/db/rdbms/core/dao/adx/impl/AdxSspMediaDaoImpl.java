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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.unioncast.common.adx.model.AdxSspMedia;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.user.model.User;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspMediaDao;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxSspMediaDao")
public class AdxSspMediaDaoImpl extends AdxGeneralDao<AdxSspMedia, Long> implements AdxSspMediaDao {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	// 关于查询
	public static String FIND_ALL = SqlBuild.select(AdxSspMedia.TABLE_NAME, AdxSspMedia.PROPERTIES);
	public static final String COUNT_ALL = SqlBuild.countAll(AdxSspMedia.TABLE_NAME);

	@Override
	public AdxSspMedia[] find(Long id) throws DaoException {
		List<AdxSspMedia> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxSspMediaRowMapper(), id));
			return list.toArray(new AdxSspMedia[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new AdxSspMediaRowMapper());
		return list.toArray(new AdxSspMedia[] {});
	}

	@Override
	public Pagination<AdxSspMedia> page(PageCriteria pageCriteria) {
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
		Pagination<AdxSspMedia> pagination = new Pagination<AdxSspMedia>(totalCount, pageCriteria.getPageSize(),
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

		List<AdxSspMedia> medias = jdbcTemplate.query(stringBuilder.toString(), new AdxSspMediaRowMapper());
		pagination.setDataArray(medias.toArray(new AdxSspMedia[] {}));

		return pagination;
	}

	// 关于增加
	public static String BATCH_ADD = "insert into adx_ssp_media(app_or_web_name,domain_or_package_name,media_type,app_or_web_describe,industry_id,app_platform,user_id,audit_status,audit_date_time,access_type,download_url,keywords,create_time,app_or_web_id,remarks,update_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public Long save(AdxSspMedia entity) throws DaoException {
		return insertAndReturnId(entity);
	}

	@Override
	public Long[] batchAdd(AdxSspMedia[] entitys) {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxSspMedia adxSspMedia : entitys) {
				pstmt.setString(1, adxSspMedia.getAppOrWebName());
				pstmt.setString(2, adxSspMedia.getDomainOrPackageName());
				if (adxSspMedia.getMediaType() != null) {
					pstmt.setInt(3, adxSspMedia.getMediaType());
				} else {
					pstmt.setNull(3, Types.INTEGER);
				}
				pstmt.setString(4, adxSspMedia.getAppOrWebDescribe());
				if (adxSspMedia.getIndustryId() != null) {
					pstmt.setInt(5, adxSspMedia.getIndustryId());
				} else {
					pstmt.setNull(5, Types.INTEGER);
				}
				pstmt.setString(6, adxSspMedia.getAppPlatform());
				if (adxSspMedia.getUser() != null) {
					pstmt.setLong(7, adxSspMedia.getUser().getId());
				} else {
					pstmt.setNull(7, Types.NULL);
				}
				if (adxSspMedia.getAuditStatus() != null) {
					pstmt.setInt(8, adxSspMedia.getAuditStatus());
				} else {
					pstmt.setNull(8, Types.INTEGER);
				}
				if (adxSspMedia.getAuditDateTime() != null) {
					pstmt.setTimestamp(9, new Timestamp(adxSspMedia.getAuditDateTime().getTime()));
				} else {
					pstmt.setNull(9, Types.TIMESTAMP);
				}
				if (adxSspMedia.getAccessType() != null) {
					pstmt.setInt(10, adxSspMedia.getAccessType());
				} else {
					pstmt.setNull(10, Types.INTEGER);
				}
				pstmt.setString(11, adxSspMedia.getDownloadUrl());
				pstmt.setString(12, adxSspMedia.getKeywords());
				if (adxSspMedia.getCreateTime() != null) {
					pstmt.setTimestamp(13, new Timestamp(adxSspMedia.getCreateTime().getTime()));
				} else {
					pstmt.setNull(13, Types.TIMESTAMP);
				}
				pstmt.setString(14, adxSspMedia.getAppOrWebId());
				pstmt.setString(15, adxSspMedia.getRemarks());
				if (adxSspMedia.getUpdateTime() != null) {
					pstmt.setTimestamp(16, new Timestamp(adxSspMedia.getUpdateTime().getTime()));
				} else {
					pstmt.setNull(16, Types.TIMESTAMP);
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
	public static String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxSspMedia.TABLE_NAME);

	@Override
	public int updateAndReturnNum(AdxSspMedia entity) {
		StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
		List<Object> args = new ArrayList<>();
		entity.setUpdateTime(new Date());
		args.add(new Date());
		if (entity.getAppOrWebName() != null) {
			stringBuffer.append(",app_or_web_name = ?");
			args.add(entity.getAppOrWebName());
		}
		if (entity.getDomainOrPackageName() != null) {
			stringBuffer.append(",domain_or_package_name = ?");
			args.add(entity.getDomainOrPackageName());
		}
		if (entity.getMediaType() != null) {
			stringBuffer.append(",media_type = ?");
			args.add(entity.getMediaType());
		}
		if (entity.getAppOrWebDescribe() != null) {
			stringBuffer.append(",app_or_web_describe = ?");
			args.add(entity.getAppOrWebDescribe());
		}
		if (entity.getIndustryId() != null) {
			stringBuffer.append(",industry_id = ?");
			args.add(entity.getIndustryId());
		}
		if (entity.getAppPlatform() != null) {
			stringBuffer.append(",app_platform = ?");
			args.add(entity.getAppPlatform());
		}
		if (entity.getUser() != null) {
			stringBuffer.append(",user_id = ?");
			args.add(entity.getUser().getId());
		}
		if (entity.getAuditStatus() != null) {
			stringBuffer.append(",audit_status = ?");
			args.add(entity.getAuditStatus());
		}
		if (entity.getAuditDateTime() != null) {
			stringBuffer.append(",audit_date_time = ?");
			args.add(entity.getAuditDateTime());
		}
		if (entity.getAccessType() != null) {
			stringBuffer.append(",access_type = ?");
			args.add(entity.getAccessType());
		}
		if (entity.getDownloadUrl() != null) {
			stringBuffer.append(",download_url = ?");
			args.add(entity.getDownloadUrl());
		}
		if (entity.getKeywords() != null) {
			stringBuffer.append(",keywords = ?");
			args.add(entity.getKeywords());
		}
		if (entity.getCreateTime() != null) {
			stringBuffer.append(",create_time = ?");
			args.add(entity.getCreateTime());
		}
		if (entity.getAppOrWebId() != null) {
			stringBuffer.append(",app_or_web_id = ?");
			args.add(entity.getAppOrWebId());
		}
		if (entity.getRemarks() != null) {
			stringBuffer.append(",remarks = ?");
			args.add(entity.getRemarks());
		}
		stringBuffer.append(" where id = ?");
		args.add(entity.getId());
		return jdbcTemplate.update(stringBuffer.toString(), args.toArray());
	}

	// 关于删除
	public static String DELETE_BY_ID = SqlBuild.delete(AdxSspMedia.TABLE_NAME);
	public static String BATCH_DELETE = SqlBuild.delete(AdxSspMedia.TABLE_NAME);

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

	private Long insertAndReturnId(AdxSspMedia adxSspMedia) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AdxSspMedia.TABLE_NAME);
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("app_or_web_name", adxSspMedia.getAppOrWebName() == null ? null : adxSspMedia.getAppOrWebName());
		args.put("domain_or_package_name",
				adxSspMedia.getDomainOrPackageName() == null ? null : adxSspMedia.getDomainOrPackageName());
		args.put("media_type", adxSspMedia.getMediaType() == null ? null : adxSspMedia.getMediaType());
		args.put("app_or_web_describe",
				adxSspMedia.getAppOrWebDescribe() == null ? null : adxSspMedia.getAppOrWebDescribe());
		args.put("industry_id", adxSspMedia.getIndustryId() == null ? null : adxSspMedia.getIndustryId());
		args.put("app_platform", adxSspMedia.getAppPlatform() == null ? null : adxSspMedia.getAppPlatform());
		args.put("user_id", adxSspMedia.getUser() == null ? null : adxSspMedia.getUser().getId());
		args.put("audit_status", adxSspMedia.getAuditStatus() == null ? null : adxSspMedia.getAuditStatus());
		args.put("audit_date_time", adxSspMedia.getAuditDateTime() == null ? null : adxSspMedia.getAuditDateTime());
		args.put("access_type", adxSspMedia.getAccessType() == null ? null : adxSspMedia.getAccessType());
		args.put("download_url", adxSspMedia.getDownloadUrl() == null ? null : adxSspMedia.getDownloadUrl());
		args.put("keywords", adxSspMedia.getKeywords() == null ? null : adxSspMedia.getKeywords());
		args.put("create_time", new Date());
		args.put("app_or_web_id", adxSspMedia.getAppOrWebId() == null ? null : adxSspMedia.getAppOrWebId());
		args.put("remarks", adxSspMedia.getRemarks() == null ? null : adxSspMedia.getRemarks());
		args.put("update_time", new Date());
		long id = jdbcInsert.executeAndReturnKey(args).longValue();
		return id;
	}

	private final class AdxSspMediaRowMapper implements RowMapper<AdxSspMedia> {
		public AdxSspMedia mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdxSspMedia adxSspMedia = new AdxSspMedia();
			adxSspMedia.setId(rs.getLong("id"));
			adxSspMedia.setAppOrWebName(rs.getString("app_or_web_name"));
			adxSspMedia.setDomainOrPackageName(rs.getString("domain_or_package_name"));
			adxSspMedia.setMediaType(rs.getInt("media_type"));
			adxSspMedia.setAppOrWebDescribe(rs.getString("app_or_web_describe"));
			adxSspMedia.setIndustryId(rs.getInt("industry_id"));
			adxSspMedia.setAppPlatform(rs.getString("app_platform"));
			User user = null;
			try {
				user = userDao.findById(rs.getLong("user_id"));
			} catch (DaoException e) {
				e.printStackTrace();
			}
			adxSspMedia.setUser(user);
			adxSspMedia.setAuditStatus(rs.getInt("audit_status"));
			adxSspMedia.setAuditDateTime(rs.getTimestamp("audit_date_time"));
			adxSspMedia.setAccessType(rs.getInt("access_type"));
			adxSspMedia.setDownloadUrl(rs.getString("download_url"));
			adxSspMedia.setKeywords(rs.getString("keywords"));
			adxSspMedia.setCreateTime(rs.getTimestamp("create_time"));
			adxSspMedia.setAppOrWebId(rs.getString("app_or_web_id"));
			adxSspMedia.setRemarks(rs.getString("remarks"));
			adxSspMedia.setUpdateTime(rs.getTimestamp("update_time"));
			return adxSspMedia;
		}
	}

	private static String QUERY_FOR_OBJECT = "select id,app_or_web_name,domain_or_package_name,media_type,app_or_web_describe,industry_id,app_platform,user_id,audit_status,audit_date_time,access_type,download_url,keywords,create_time,app_or_web_id,remarks,update_time from adx_ssp_media";

	@Override
	public AdxSspMedia findByAppOrWebId(Long id) {
		AdxSspMedia adxSspMedia = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + "where app_or_web_id =" + id,
				new AdxSspMediaRowMapper());
		return adxSspMedia;
	}

}
