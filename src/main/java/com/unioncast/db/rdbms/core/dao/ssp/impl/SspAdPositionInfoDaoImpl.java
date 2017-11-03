package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspAdPositionInfo;
import com.unioncast.common.ssp.model.SspAppInfo;
import com.unioncast.common.ssp.model.SspDictAdPositionType;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspAdPositionInfoDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspAppInfoService;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAdPositionTypeService;
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

@Repository("sspAdPositionInfoDao")
public class SspAdPositionInfoDaoImpl extends SspGeneralDao<SspAdPositionInfo, Long> implements SspAdPositionInfoDao {

	private static final String  PAGE_AD_POSITION="select ad.id,ad.ad_position_id,ad.name,ad.app_info_id,ad.dict_ad_position_type_list,ad.state,ad.cheat_level,ad.create_time,ad.update_time,ad.creater_id,ad.delete_state from ssp_ad_position_info ad inner join ssp_app_info app on ad.app_info_id=app.id where 1=1 and ad.delete_state=1";
	private static final String  PAGE_COUNT_AD_POSITION="select count(*) from ssp_ad_position_info ad inner join ssp_app_info app on ad.app_info_id=app.id where 1=1 and ad.delete_state=1";

	public final class SspAdPositionInfoRowMapper implements RowMapper<SspAdPositionInfo> {

		@Override
		public SspAdPositionInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspAdPositionInfo(rs.getLong("id"), rs.getString("ad_position_id"), rs.getString("name"),
					findAppInfoById(rs.getLong("app_info_id")),
					findPositionTypeListByIds(rs.getInt("dict_ad_position_type_list")), rs.getInt("state"),
					rs.getInt("cheat_level"), rs.getTimestamp("create_time"), rs.getTimestamp("update_time"),
					findUserByUserId(rs.getLong("creater_id")), rs.getInt("delete_state"));
		}

	}

	private static String FIND_ALL = SqlBuild.select(SspAdPositionInfo.TABLE_NAME, SspAdPositionInfo.PROPERTIES);
	// private static String ADD = "insert into " + SspAdPositionInfo.TABLE_NAME
	// + "("
	// + SspAdPositionInfo.PROPERTIES + ") values (null,?,?,?, ?,?,?,?, ?,?,?)";
	// private static String DELETE_BY_ID =
	// SqlBuild.delete(SspAdPositionInfo.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspAdPositionInfo.TABLE_NAME);

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

	private final SspAppInfo findAppInfoById(Long id) {
		SspAppInfoService sspAppInfoService = SpringUtils.getBean(SspAppInfoService.class);
		SspAppInfo sspAppInfo = new SspAppInfo();
		sspAppInfo.setId(id);
		try {
			SspAppInfo[] sspAppInfos = sspAppInfoService.findT(sspAppInfo);
			if (CommonUtil.isNotNull(sspAppInfos)) {
				sspAppInfo = sspAppInfos[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sspAppInfo;
	}

	private final SspDictAdPositionType findPositionTypeListByIds(Integer id) {
		SspDictAdPositionTypeService sspDictAdPositionTypeService = SpringUtils
				.getBean(SspDictAdPositionTypeService.class);
		SspDictAdPositionType sspDictAdPositionType = new SspDictAdPositionType();
		sspDictAdPositionType.setCode(id);
		try {
			SspDictAdPositionType[] sspDictAdPositionTypes = sspDictAdPositionTypeService.findT(sspDictAdPositionType);
			if (CommonUtil.isNotNull(sspDictAdPositionTypes)) {
				sspDictAdPositionType = sspDictAdPositionTypes[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sspDictAdPositionType;
	}

	/*
	 * @Override public SspAdPositionInfo[] find(Long id) throws DaoException {
	 * List<SspAdPositionInfo> list = new ArrayList<>(); if (id != null) {
	 * list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new
	 * SspAdPositionInfoRowMapper(), id)); return list.toArray(new
	 * SspAdPositionInfo[] {}); } list = jdbcTemplate.query(FIND_ALL, new
	 * SspAdPositionInfoRowMapper()); return list.toArray(new
	 * SspAdPositionInfo[] {}); }
	 */
	@Override
	public SspAdPositionInfo[] findT(SspAdPositionInfo s) throws DaoException, IllegalAccessException {
		List<SspAdPositionInfo> list = new ArrayList<>();
		if (s != null) {
			SspAdPositionInfo[] sspAdPositionInfos = find(s, new SspAdPositionInfoRowMapper(), SspAdPositionInfo.class);
			return sspAdPositionInfos;
		} else {
			list = jdbcTemplate.query(FIND_ALL, new SspAdPositionInfoRowMapper());
		}
		return list.toArray(new SspAdPositionInfo[] {});
	}

	/*
	 * @Override public int deleteById(Long id) throws DaoException { return
	 * jdbcTemplate.update(DELETE_BY_ID, id); }
	 */
	@Override
	public Pagination<SspAdPositionInfo> page(PageCriteria pageCriteria) throws DaoException {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(PAGE_AD_POSITION);
		StringBuilder countAll = new StringBuilder(PAGE_COUNT_AD_POSITION);
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = String.valueOf(searchExpressionList.get(i).getValue());
				String propertyName = searchExpressionList.get(i).getPropertyName();
				String criteriaSql = " ";
				if (StringUtils.isNotBlank(value)) {
					if ("dict_platform_id".equals(propertyName)) {
						value = "'" + value + "%'";
						criteriaSql = " " + pageCriteria.getPredicate() + " app." + propertyName + " " + " like " + value;
					} else {
						if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator()))
							value = "'%" + value + "%'";
						else {
							value = " '" + value + "' ";
						}
						criteriaSql = " " + pageCriteria.getPredicate() + " ad." + propertyName + " "
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
						" ad." + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
			}
		}
		pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
				+ pageCriteria.getPageSize());
		List<SspAdPositionInfo> list = jdbcTemplate.query(pageSql.toString(), new SspAdPositionInfoRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspAdPositionInfo>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspAdPositionInfo[] {}));
	}

	@Override
	public int batchDelete(Long[] ids) throws DaoException {
		return delete(SspAdPositionInfo.class, ids);
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
	public Long save(SspAdPositionInfo entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
						if (myColumn.value().equals("app_info_id")) {
							args.put("app_info_id",
									entity.getSspAppInfo() == null ? null : entity.getSspAppInfo().getId());
						} else if (myColumn.value().equals("dict_ad_position_type_list")) {

							args.put("dict_ad_position_type_list", entity.getSspDictAdPositionTypeList() == null ? null
									: entity.getSspDictAdPositionTypeList().getCode());
						} else if (myColumn.value().equals("creater_id")) {
							args.put("creater_id", entity.getUser() == null ? null : entity.getUser().getId());
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
	public int updateAndReturnNum(SspAdPositionInfo entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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

						} else if (myColumn.value().equals("app_info_id")) {
							if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("set " + myColumn.value() + " = ?");
							else
								sb.append("," + myColumn.value() + " = ?");
							args.add(entity.getSspAppInfo().getId());
						} else if (myColumn.value().equals("dict_ad_position_type_list")) {
							if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("set " + myColumn.value() + " = ?");
							else
								sb.append("," + myColumn.value() + " = ?");
							args.add(entity.getSspDictAdPositionTypeList().getCode());
						}  else {
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
	public SspAdPositionInfo findById(Long id) throws DaoException {
		if (id != null) {
			return jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspAdPositionInfoRowMapper(), id);
		}
		return null;
	}

/*
	 * @Override public int batchDelete(Long[] ids) { List<Object[]> batchArgs =
	 * new ArrayList<>(); for (Long id : ids) { Object[] objects = new Object[]
	 * { id }; batchArgs.add(objects); } int[] intArray =
	 * jdbcTemplate.batchUpdate(DELETE_BY_ID, batchArgs); int j = 0; for (int i
	 * : intArray) { j += i; } return j; }
	 */
}
