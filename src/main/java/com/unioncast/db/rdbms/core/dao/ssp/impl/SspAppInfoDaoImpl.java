package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.*;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspAppInfoDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAccessWayService;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAuditService;
import com.unioncast.db.rdbms.core.service.ssp.SspDictIndustryService;
import com.unioncast.db.rdbms.core.service.ssp.SspDictPlatformService;
import com.unioncast.db.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sspAppInfoDao")
public class SspAppInfoDaoImpl extends SspGeneralDao<SspAppInfo, Long> implements SspAppInfoDao {

	public final class SspAppInfoRowMapper implements RowMapper<SspAppInfo> {

		@Override
		public SspAppInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspAppInfo(rs.getLong("id"), rs.getString("app_id"), rs.getString("name"),
					findIndustryById(rs.getLong("dict_industry_id")), findPlatformById(rs.getLong("dict_platform_id")),
					findAccessWayById(rs.getLong("dict_access_way_id")), rs.getString("download_url"),
					rs.getString("package_name"), rs.getString("itunes_id"), rs.getString("app_keywords"),
					rs.getString("app_desc"), findAuditById(rs.getLong("dict_audit_id")), rs.getTimestamp("create_time"),
					rs.getTimestamp("update_time"), rs.getDouble("ratio"), rs.getString("audit_info"),
					findUserByUserId(rs.getLong("creater_id")), rs.getInt("delete_state"),rs.getLong("kpi"),rs.getTimestamp("price_start_time"),rs.getTimestamp("price_end_time"),rs.getDouble("unified_price"));
		}

	}

	private static String FIND_ALL = SqlBuild.select(SspAppInfo.TABLE_NAME, SspAppInfo.PROPERTIES);
	// private static String ADD = "insert into " + SspAppInfo.TABLE_NAME + "("
	// + SspAppInfo.PROPERTIES + ") values (null,?,?,?, ?,?,?,?, ?,?,?,?,
	// ?,?,?,?, ?,?)";
	// private static String DELETE_BY_ID =
	// SqlBuild.delete(SspAppInfo.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspAppInfo.TABLE_NAME);

	@Resource(name = "userDao")
	UserDao userDao;

	private final User findUserByUserId(Long id) {
		User user = null;
		try {
			User[] users = userDao.find(id);
			if (users != null && users.length != 0) {
				user = users[0];
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return user;
	}

	private final SspDictIndustry findIndustryById(Long id) {
		SspDictIndustryService sspDictIndustryService = SpringUtils.getBean(SspDictIndustryService.class);
		SspDictIndustry sspDictIndustry = new SspDictIndustry();
		sspDictIndustry.setCode(id);
		try {
			SspDictIndustry[] sspDictIndustries = sspDictIndustryService.findT(sspDictIndustry);
			if (CommonUtil.isNotNull(sspDictIndustries)) {
				sspDictIndustry = sspDictIndustries[0];
			}
		} catch (Exception e) {

		}
		return sspDictIndustry;
	}

	private final SspDictPlatform findPlatformById(Long id) {
		SspDictPlatformService sspDictPlatformService = SpringUtils.getBean(SspDictPlatformService.class);
		SspDictPlatform sspDictPlatform = new SspDictPlatform();
		sspDictPlatform.setCode(id);
		try {
			SspDictPlatform[] sspDictPlatforms = sspDictPlatformService.findT(sspDictPlatform);
			if (CommonUtil.isNotNull(sspDictPlatforms)) {
				sspDictPlatform = sspDictPlatforms[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sspDictPlatform;
	}

	private final SspDictAccessWay findAccessWayById(Long id) {
		SspDictAccessWayService sspDictAccessWayService = SpringUtils.getBean(SspDictAccessWayService.class);
		SspDictAccessWay sspDictAccessWay = new SspDictAccessWay();
		sspDictAccessWay.setCode(Integer.parseInt(String.valueOf(id)));
		try {
			SspDictAccessWay[] sspDictAccessWays = sspDictAccessWayService.findT(sspDictAccessWay);
			if (CommonUtil.isNotNull(sspDictAccessWays)) {
				sspDictAccessWay = sspDictAccessWays[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sspDictAccessWay;
	}

	private final SspDictAudit findAuditById(Long id) {
		SspDictAuditService sspDictAuditService = SpringUtils.getBean(SspDictAuditService.class);
		SspDictAudit sspDictAudit = new SspDictAudit();
		sspDictAudit.setCode(Integer.parseInt(String.valueOf(id)));
		try {
			SspDictAudit[] sspDictAudits = sspDictAuditService.findT(sspDictAudit);
			if (CommonUtil.isNotNull(sspDictAudits)) {
				sspDictAudit = sspDictAudits[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sspDictAudit;
	}

	@Override
	public SspAppInfo[] findT(SspAppInfo s) throws DaoException, IllegalAccessException {
		List<SspAppInfo> list = new ArrayList<>();
		if (s != null) {
			SspAppInfo[] sspAppInfos = find(s, new SspAppInfoRowMapper(), SspAppInfo.class);
			return sspAppInfos;
		} else {
			list = jdbcTemplate.query(FIND_ALL, new SspAppInfoRowMapper());
		}
		return list.toArray(new SspAppInfo[] {});
	}

	/*
	 * @Override public SspAppInfo[] find(Long id) throws DaoException {
	 * List<SspAppInfo> list = new ArrayList<>(); if (id != null) {
	 * list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new
	 * SspAppInfoRowMapper(), id)); return list.toArray(new SspAppInfo[] {}); }
	 * list = jdbcTemplate.query(FIND_ALL, new SspAppInfoRowMapper()); return
	 * list.toArray(new SspAppInfo[] {}); }
	 */
	/*
	 * @Override public int deleteById(Long id) throws DaoException { return
	 * jdbcTemplate.update(DELETE_BY_ID, id); }
	 */
	@Override
	public Pagination<SspAppInfo> page(PageCriteria pageCriteria) throws DaoException {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1 and delete_state=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 and delete_state=1");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = String.valueOf(searchExpressionList.get(i).getValue());
				String propertyName = searchExpressionList.get(i).getPropertyName();
				String criteriaSql = " ";
				if (StringUtils.isNotBlank(value)) {
					if ("dict_platform_id".equals(propertyName)) {
						value = "'" + value + "%'";
						criteriaSql = " " + pageCriteria.getPredicate() + " " + propertyName + " " + " like " + value;
					} else {
						if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator()))
							value = "'%" + value + "%'";

						criteriaSql = " " + pageCriteria.getPredicate() + " " + propertyName + " "
								+ searchExpressionList.get(i).getOperation().getOperator() + " " + value;
					}
				}
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
		List<SspAppInfo> list = jdbcTemplate.query(pageSql.toString(), new SspAppInfoRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspAppInfo>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				list.toArray(new SspAppInfo[] {}));
	}

	/*
	 * @Override public int batchDelete(Long[] ids) { List<Object[]> batchArgs =
	 * new ArrayList<>(); for (Long id : ids) { Object[] objects = new Object[]
	 * { id }; batchArgs.add(objects); } int[] intArray =
	 * jdbcTemplate.batchUpdate(DELETE_BY_ID, batchArgs); int j = 0; for (int i
	 * : intArray) { j += i; } return j; }
	 */
	@Override
	public int batchDelete(Long[] ids) throws DaoException {
		return delete(SspAppInfo.class, ids);
	}

	@Override
	public Long save(SspAppInfo entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = entity.getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(myTable.value());
		Map<String, Object> args = new HashMap<String, Object>();
		MyColumn myColumn = null;
		MyId myId = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				myId = field.getAnnotation(MyId.class);
				if (myId != null) {
					jdbcInsert.setGeneratedKeyName(myId.value());
				} else {
					myColumn = field.getDeclaredAnnotation(MyColumn.class);
					if (myColumn != null && StringUtils.isNotBlank(myColumn.value())) {
						if (myColumn.value().equals("dict_industry_id")) {
							args.put("dict_industry_id",
									entity.getSspDictIndustry() == null ? null : entity.getSspDictIndustry().getCode());
						} else if (myColumn.value().equals("dict_platform_id")) {
							args.put("dict_platform_id",
									entity.getSspDictPlatform() == null ? null : entity.getSspDictPlatform().getCode());
						} else if (myColumn.value().equals("dict_access_way_id")) {
							args.put("dict_access_way_id", entity.getSspDictAccessWay() == null ? null
									: entity.getSspDictAccessWay().getCode());
						} else if (myColumn.value().equals("creater_id")) {
							args.put("creater_id", entity.getUser() == null ? null : entity.getUser().getId());
						}else if (myColumn.value().equals("dict_audit_id")) {
							args.put("dict_audit_id", entity.getSspDictAudit() == null ? null : entity.getSspDictAudit().getCode());
						} else {
							args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
						}
					}
				}
			}
		}
		return jdbcInsert.executeAndReturnKey(args).longValue();
	}

	@Override
	public int updateAndReturnNum(SspAppInfo entity)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		List<Object> args = new ArrayList<>();
		Class<?> clazz = entity.getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		StringBuilder sb = new StringBuilder(SqlBuild.updateNotNullField(myTable.value()));
		MyColumn myColumn = null;
		MyId myId = null;
		String strId = null;
		Object objId = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				if (field.get(entity) != null) {
					myColumn = field.getDeclaredAnnotation(MyColumn.class);
					if (myColumn != null && StringUtils.isNotBlank(myColumn.value())) {
						if (field.getDeclaredAnnotation(MyId.class) != null) {
							myId = field.getDeclaredAnnotation(MyId.class);
							strId = myId.value();
							objId = field.get(entity);
							continue;
						}
						if (myColumn.value().equals("creater_id")) {
							if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("set " + myColumn.value() + " = ?");
							else
								sb.append("," + myColumn.value() + " = ?");
							args.add(entity.getUser().getId());

						} else if (myColumn.value().equals("dict_industry_id")) {
							if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("set " + myColumn.value() + " = ?");
							else
								sb.append("," + myColumn.value() + " = ?");
							args.add(entity.getSspDictIndustry().getCode());
						} else if (myColumn.value().equals("dict_platform_id")) {
							if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("set " + myColumn.value() + " = ?");
							else
								sb.append("," + myColumn.value() + " = ?");
							args.add(entity.getSspDictPlatform().getCode());
						} else if (myColumn.value().equals("dict_access_way_id")) {
							if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("set " + myColumn.value() + " = ?");
							else
								sb.append("," + myColumn.value() + " = ?");
							args.add(entity.getSspDictAccessWay().getCode());
						} else {
							if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("set " + myColumn.value() + " = ?");
							else
								sb.append("," + myColumn.value() + " = ?");
							args.add(field.get(entity));
						}
					}
				}
			}
		}
		sb.append(" where " + strId + " = ?");
		args.add(objId);
		return jdbcTemplate.update(sb.toString(), args.toArray());
	}

	@Override
	public int delete(Class<?> clazz, Long[] ids) throws DaoException {
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		StringBuilder sqlSb = new StringBuilder("update " + myTable.value() + " set delete_state=2 where id = ?");
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[]{id};
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(sqlSb.toString(), batchArgs);
		int deleteCount = 0;
		for (int i : intArray) {
			deleteCount += i;
		}
		return deleteCount;
	}

	@Override
	public SspAppInfo findById(Long id) throws DaoException {
		if (id != null) {
			return jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspAppInfoRowMapper(), id);
		}
		return null;
	}
}
