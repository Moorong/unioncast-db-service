package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.ssp.model.SspAdPositionInfo;
import com.unioncast.common.ssp.model.SspAppInfo;
import com.unioncast.common.ssp.model.SspPricingGranularity;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspPricingGranularityDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspAppInfoService;
import com.unioncast.db.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther wangyao
 * @date 2017-04-24 19:34
 */
@Repository("sspPricingGranularityDao")
public class SspPricingGranularityDaoImpl extends SspGeneralDao<SspPricingGranularity, Long>
		implements SspPricingGranularityDao {
	public final class SspAdPositionInfoRowMapper implements RowMapper<SspPricingGranularity> {

		@Override
		public SspPricingGranularity mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspPricingGranularity(rs.getLong("id"), findAppInfoById(rs.getLong("app_info_id")),
					findAdPositionInfoById(rs.getLong("ad_position_id")), rs.getLong("kpi"), rs.getLong("price_type"),
					rs.getTimestamp("price_start_time"), rs.getTimestamp("price_end_time"),
					rs.getDouble("unified_price"), rs.getDouble("ad_position_price"), rs.getInt("delete_state"),
					rs.getTimestamp("create_time"), rs.getTimestamp("update_time"));
		}

	}

	private static String FIND_ALL = SqlBuild.select(SspPricingGranularity.TABLE_NAME,
			SspPricingGranularity.PROPERTIES);
	// private static String ADD = "insert into " + SspAdPositionInfo.TABLE_NAME
	// + "("
	// + SspAdPositionInfo.PROPERTIES + ") values (null,?,?,?, ?,?,?,?, ?,?,?)";
	// private static String DELETE_BY_ID =
	// SqlBuild.delete(SspAdPositionInfo.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspPricingGranularity.TABLE_NAME);

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

	private final SspAdPositionInfo findAdPositionInfoById(Long id) {
		//TODO
		SspAdPositionInfo sspAdPositionInfo = new SspAdPositionInfo();
		sspAdPositionInfo.setId(id);
		return sspAdPositionInfo;
	}

	@Override
	public Long save(SspPricingGranularity entity)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
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
						} else if (myColumn.value().equals("ad_position_id")) {

							args.put("ad_position_id", entity.getSspAdPositionInfo() == null ? null
									: entity.getSspAdPositionInfo().getId());
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
	public SspPricingGranularity[] findT(SspPricingGranularity s) throws DaoException, IllegalAccessException {
		List<SspPricingGranularity> list = new ArrayList<>();
		if (s != null) {
			SspPricingGranularity[] sspAdPositionInfos = find(s, new SspAdPositionInfoRowMapper(), SspAdPositionInfo.class);
			return sspAdPositionInfos;
		} else {
			list = jdbcTemplate.query(FIND_ALL, new SspAdPositionInfoRowMapper());
		}
		return list.toArray(new SspPricingGranularity[] {});
	}
}
