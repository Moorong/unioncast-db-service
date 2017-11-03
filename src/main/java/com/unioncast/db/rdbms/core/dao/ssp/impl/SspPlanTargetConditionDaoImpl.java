package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.common.ssp.model.SspDictAdType;
import com.unioncast.common.ssp.model.SspDictAgeTarget;
import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.common.ssp.model.SspDictIncomeTarget;
import com.unioncast.common.ssp.model.SspDictInterestsTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.common.ssp.model.SspDictMediaType;
import com.unioncast.common.ssp.model.SspDictMobileBrandType;
import com.unioncast.common.ssp.model.SspDictSysOperationType;
import com.unioncast.common.ssp.model.SspPlanTargetCondition;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlanTargetConditionDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAdTypeService;
import com.unioncast.db.util.SpringUtils;

@Repository("sspPlanTargetConditionDao")
public class SspPlanTargetConditionDaoImpl extends SspGeneralDao<SspPlanTargetCondition, Long> implements
		SspPlanTargetConditionDao {
	private static final Logger LOG = LogManager.getLogger(SspPlanTargetConditionDaoImpl.class);
	private static final String queryForSspCityInfo = SqlBuild.select(SspCityInfo.TABLE_NAME, SspCityInfo.PROPERTIES);
	private static final String queryForSspDictSysOperationType = SqlBuild.select(SspDictSysOperationType.TABLE_NAME,
			SspDictSysOperationType.PROPERTIES);
	private static final String queryForSspDictMobileBrandType = SqlBuild.select(SspDictMobileBrandType.TABLE_NAME,
			SspDictMobileBrandType.PROPERTIES);
	private static final String queryForSspDictMediaType = SqlBuild.select(SspDictMediaType.TABLE_NAME,
			SspDictMediaType.PROPERTIES);
	private static final String queryForSspDictAgeTarget = SqlBuild.select(SspDictAgeTarget.TABLE_NAME,
			SspDictAgeTarget.PROPERTIES);
	private static final String queryForSspDictEducationTarget = SqlBuild.select(SspDictEducationTarget.TABLE_NAME,
			SspDictEducationTarget.PROPERTIES);
	private static final String queryForSspDictInterestsTarget = SqlBuild.select(SspDictInterestsTarget.TABLE_NAME,
			SspDictInterestsTarget.PROPERTIES);
	private static final String queryForSspDictBuyTarget = SqlBuild.select(SspDictBuyTarget.TABLE_NAME,
			SspDictBuyTarget.PROPERTIES);
	private static final String queryForSspDictCrowdSex = SqlBuild.select(SspDictCrowdSexType.TABLE_NAME,
			SspDictCrowdSexType.PROPERTIES);
	private static final String queryForSspDictMarriageTarget = SqlBuild.select(SspDictMarriageTarget.TABLE_NAME,
			SspDictMarriageTarget.PROPERTIES);
	private static final String queryForSspDictIncomeTarget = SqlBuild.select(SspDictIncomeTarget.TABLE_NAME,
			SspDictIncomeTarget.PROPERTIES);
	private static final String idIn = " WHERE code IN(?) ";
	private static String FIND_ALL = SqlBuild.select(SspPlanTargetCondition.TABLE_NAME,
			SspPlanTargetCondition.PROPERTIES);
	private static String DELETE_BY_ID = SqlBuild.delete(SspPlanTargetCondition.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspPlanTargetCondition.TABLE_NAME);
	@Autowired
	SspCityInfoDaoImpl sspCityInfoDaoImpl;

	@Override
	public SspPlanTargetCondition[] find(Long id) throws DaoException {
		List<SspPlanTargetCondition> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
					new SspPlanTargetConditionDaoImpl.SspPlanTargetConditionRowMapper(), id));
		} else {
			list = jdbcTemplate.query(FIND_ALL, new SspPlanTargetConditionDaoImpl.SspPlanTargetConditionRowMapper());
		}

		return list.toArray(new SspPlanTargetCondition[] {});
	}

	@Override
	public SspPlanTargetCondition[] findT(SspPlanTargetCondition s) throws DaoException, IllegalAccessException {
		List<SspPlanTargetCondition> list = new ArrayList<>();
		if (s != null) {
			SspPlanTargetCondition[] SspPlanTargetCondition = this.find(s, new SspPlanTargetConditionRowMapper(),
					SspPlanTargetCondition.class);
			return SspPlanTargetCondition;
		} else {
			list = jdbcTemplate.query(FIND_ALL, new SspPlanTargetConditionRowMapper());
			System.out.println("查询到的结果是--" + list.toString());
		}
		return list.toArray(new SspPlanTargetCondition[] {});
	}

	private void buildSql(String columnName, Object columnValue, int argType, StringBuilder sqlBuilder,
			List<Object> args, List<Integer> argTypes) {
		if (columnValue == null) {
			return;
		}

		if (args.size() == 0) {
			sqlBuilder.append("set " + columnName + " = ?");
		} else {
			sqlBuilder.append(", " + columnName + " = ?");
		}

		args.add(columnValue);

		argTypes.add(argType);
	}

	private void buildSql(String columnName, List<Object> valueList, int argType, StringBuilder sqlBuilder,
			List<Object> args, List<Integer> argTypes) {
		String columnValue = "";

		if (CollectionUtils.isNotEmpty(valueList)) {
			StringBuilder stb = new StringBuilder();

			stb.append(valueList.get(0).toString());

			for (int i = 1; i < valueList.size(); i++) {
				stb.append(",");
				stb.append(valueList.get(i).toString());

			}

			columnValue = stb.toString();
		}

		buildSql(columnName, columnValue, argType, sqlBuilder, args, argTypes);
	}

	@Override
	public int updateAndReturnNum(SspPlanTargetCondition entity) throws DaoException {
		System.out.println("-------------updateAndReturnNum entity:" + entity);

		Class<?> clazz = entity.getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);

		System.out.println("-------------updateAndReturnNum myTable:" + myTable.value());

		// 获取ID的值
		String idColumn = null;
		Object idValue = null;

		Field[] fields = clazz.getDeclaredFields();

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(SqlBuild.updateNotNullField(myTable.value()));

		List<Object> args = new ArrayList<>();
		List<Integer> argTypes = new ArrayList<>();

		System.out.println("-------------updateAndReturnNum fields-------------" + fields.length);

		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			field.setAccessible(true);

			Object fieldValue = null;
			try {
				fieldValue = field.get(entity);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

			if (fieldValue == null) {
				continue;
			}

			MyColumn myColumn = field.getDeclaredAnnotation(MyColumn.class);

			if (myColumn == null) {
				continue;
			}

			String columnName = myColumn.value();
			if (StringUtils.isBlank(columnName)) {
				continue;
			}

			System.out.println("-------------updateAndReturnNum fields-------------" + columnName);

			if (columnName.equals("id")) {
				MyId myId = field.getDeclaredAnnotation(MyId.class);

				idColumn = myId.value();

				try {
					idValue = field.get(entity);
				} catch (Exception e) {
					idValue = new Long(-1);

					LOG.error(e.getMessage(), e);
				}

				System.out.println("-------------updateAndReturnNum idColumn:" + idColumn + "--->" + idValue);
			} else if (columnName.equals("city_code")) {
				List<Object> valueList = new ArrayList<>();

				SspCityInfo[] cityInfoArr = entity.getCityInfoArr();

				for (SspCityInfo sspe : cityInfoArr) {
					// Long id = sspe.getId();
					String code = sspe.getCode();

					valueList.add(code);
				}

				buildSql(columnName, valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("dict_sys_operation_type")) {
				List<Object> valueList = new ArrayList<>();

				SspDictSysOperationType[] cityInfoArr = entity.getDictSysOperationTypeArr();

				for (SspDictSysOperationType sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(columnName, valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("dict_mobile_brand_type")) {
				List<Object> valueList = new ArrayList<>();

				SspDictMobileBrandType[] cityInfoArr = entity.getDictMobileBrandTypeArr();

				for (SspDictMobileBrandType sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("dict_media_type")) {
				List<Object> valueList = new ArrayList<>();

				SspDictMediaType[] cityInfoArr = entity.getDictMediaTypeArr();

				for (SspDictMediaType sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			}
			// 以下是人群定向
			else if (columnName.equals("dict_crowd_sex_type")) {// 性别
				List<Object> valueList = new ArrayList<>();

				SspDictCrowdSexType[] cityInfoArr = entity.getDictCrowdSexTypeArr();

				for (SspDictCrowdSexType sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("age")) {// 年龄定向
				List<Object> valueList = new ArrayList<>();

				SspDictAgeTarget[] cityInfoArr = entity.getAgeTargetArr();

				for (SspDictAgeTarget sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("education")) {// 学历定向
				List<Object> valueList = new ArrayList<>();

				SspDictEducationTarget[] cityInfoArr = entity.getEducationTargetArr();

				for (SspDictEducationTarget sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("marriage")) {// 婚姻状况
				List<Object> valueList = new ArrayList<>();

				SspDictMarriageTarget[] cityInfoArr = entity.getSspDictMarriageTargetArr();

				for (SspDictMarriageTarget sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("income")) {// 收入状况
				List<Object> valueList = new ArrayList<>();

				SspDictIncomeTarget[] cityInfoArr = entity.getSspDictIncomeTargetArr();

				for (SspDictIncomeTarget sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("interests")) {// 兴趣爱好
				List<Object> valueList = new ArrayList<>();

				SspDictInterestsTarget[] cityInfoArr = entity.getInterestsTargetArr();

				for (SspDictInterestsTarget sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("buy")) {// 购买倾向
				List<Object> valueList = new ArrayList<>();

				SspDictBuyTarget[] cityInfoArr = entity.getBuyTargetArr();

				for (SspDictBuyTarget sspe : cityInfoArr) {
					Long id = sspe.getId();

					valueList.add(id);
				}

				buildSql(myColumn.value(), valueList, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else if (columnName.equals("dict_ad_type_id")) {
				Long columnValue = entity.getSspDictAdType().getId();

				buildSql(columnName, columnValue, Types.VARCHAR, sqlBuilder, args, argTypes);
			} else {
				buildSql(myColumn.value(), fieldValue, Types.VARCHAR, sqlBuilder, args, argTypes);
			}
		}

		sqlBuilder.append(" where " + idColumn + " = ?");
		args.add(idValue);

		System.out.println("更新时sql语句是-------" + sqlBuilder.toString());
		System.out.println("更新时传递的参数为----" + args);

		int result = jdbcTemplate.update(sqlBuilder.toString(), args.toArray());

		System.out.println("更新result----" + result);

		return result;
	}

	@Override
	public Pagination<SspPlanTargetCondition> page(PageCriteria pageCriteria, Long userId) throws DaoException {
		pageCriteria.setEntityClass(SspPlanTargetCondition.class);
		return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspPlanTargetConditionRowMapper(), "account_id", userId);

	}

	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}

	@Override
	public int batchDelete(Long[] ids) throws DaoException {
		return delete(SspPlanTargetCondition.class, ids);
	}

	@Override
	public Long save(SspPlanTargetCondition entity) throws DaoException, IllegalArgumentException,
			IllegalAccessException {

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
						if (myColumn.value().equals("dict_ad_type_id")) {
							args.put("dict_ad_type_id", entity.getSspDictAdType() == null ? null : entity
									.getSspDictAdType().getId());

						} else if (myColumn.value().equals("city_code")) {
							if (entity.getCityInfoArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspCityInfo sspe : entity.getCityInfoArr()) {
									String code = sspe.getCode();
									// Long id = sspe.getId();
									stb.append(code);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("city_code", substring);

							} else {
								args.put("city_code", null);
							}

						} else if (myColumn.value().equals("dict_sys_operation_type")) {
							if (entity.getDictSysOperationTypeArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictSysOperationType sspe : entity.getDictSysOperationTypeArr()) {
									Long id = sspe.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("dict_sys_operation_type", substring);

							} else {
								args.put("dict_sys_operation_type", null);
							}

						}
						// 移动设备品牌定向
						else if (myColumn.value().equals("dict_mobile_brand_type")) {
							if (entity.getDictMobileBrandTypeArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictMobileBrandType sspe : entity.getDictMobileBrandTypeArr()) {
									Long id = sspe.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("dict_mobile_brand_type", substring);
							} else {
								args.put("dict_mobile_brand_type", null);
							}

						} else if (myColumn.value().equals("dict_media_type")) {
							if (entity.getDictMediaTypeArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictMediaType sspe : entity.getDictMediaTypeArr()) {
									Long id = sspe.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("dict_media_type", substring);

							} else {
								args.put("dict_media_type", null);
							}

						}

						// **************以下是人群定向**********************//
						// 性别
						else if (myColumn.value().equals("dict_crowd_sex_type")) {
							if (entity.getDictCrowdSexTypeArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictCrowdSexType sex : entity.getDictCrowdSexTypeArr()) {
									Long id = sex.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("dict_crowd_sex_type", substring);
							} else {
								args.put("dict_crowd_sex_type", null);
							}
						}
						// 年龄
						else if (myColumn.value().equals("age")) {
							if (entity.getAgeTargetArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictAgeTarget sspe : entity.getAgeTargetArr()) {
									Long id = sspe.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("age", substring);
							} else {
								args.put("age", null);
							}

						}
						// 教育
						else if (myColumn.value().equals("education")) {
							if (entity.getEducationTargetArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictEducationTarget sspe : entity.getEducationTargetArr()) {
									Long id = sspe.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("education", substring);

							} else {
								args.put("education", null);
							}

						}
						// 兴趣爱好
						else if (myColumn.value().equals("interests")) {
							if (entity.getInterestsTargetArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictInterestsTarget sspe : entity.getInterestsTargetArr()) {
									Long id = sspe.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("interests", substring);

							} else {
								args.put("interests", null);
							}

						}
						// 婚姻状况
						else if (myColumn.value().equals("marriage")) {
							if (entity.getSspDictMarriageTargetArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictMarriageTarget marriage : entity.getSspDictMarriageTargetArr()) {
									Long id = marriage.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("marriage", substring);
							} else {
								args.put("marriage", null);
							}
						}

						// 收入
						else if (myColumn.value().equals("income")) {
							if (entity.getSspDictIncomeTargetArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictIncomeTarget income : entity.getSspDictIncomeTargetArr()) {
									Long id = income.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("income", substring);
							} else {
								args.put("income", null);
							}

						}

						// 购买倾向
						else if (myColumn.value().equals("buy")) {
							if (entity.getBuyTargetArr() != null) {
								StringBuilder stb = new StringBuilder();
								for (SspDictBuyTarget sspe : entity.getBuyTargetArr()) {
									Long id = sspe.getId();
									stb.append(id);
									stb.append(",");
								}
								String substring = stb.substring(0, stb.length() - 1);
								args.put("buy", substring);

							} else {
								args.put("buy", null);
							}

						} else {
							args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
						}
					}
				}
			}
		}
		return jdbcInsert.executeAndReturnKey(args).longValue();
	}

	public SspCityInfo[] findSspCityInfos(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspCityInfo + " where 1=1 AND code in(" + vals + ")",
				new SspCityInfoRowMapper()).toArray(new SspCityInfo[] {});
		/*
		 * return jdbcTemplate.query(queryForSspCityInfo + idIn, new
		 * SspCityInfoRowMapper(), vals).toArray(new SspCityInfo[]{});
		 */
	}

	public SspDictSysOperationType[] findSspDictSysOperationType(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictSysOperationType + " where 1=1 AND code in(" + vals + ")",
				new SspDictSysOperationTypeRowMapper()).toArray(new SspDictSysOperationType[] {});
		/*
		 * return jdbcTemplate.query(queryForSspDictSysOperationType + idIn, new
		 * SspDictSysOperationTypeRowMapper(), str).toArray(new
		 * SspDictSysOperationType[]{});
		 */
	}

	public SspDictMobileBrandType[] findSspDictMobileBrandType(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictMobileBrandType + " where 1=1 AND code in(" + vals + ")",
				new SspDictMobileBrandTypeRowMapper()).toArray(new SspDictMobileBrandType[] {});
		/*
		 * return jdbcTemplate.query(queryForSspDictMobileBrandType + idIn, new
		 * SspDictMobileBrandTypeRowMapper(), str).toArray(new
		 * SspDictMobileBrandType[]{});
		 */
	}

	public SspDictMediaType[] findSspDictMediaType(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictMediaType + " where 1=1 AND code in(" + vals + ")",
				new SspDictMediaTypeRowMapper()).toArray(new SspDictMediaType[] {});
		/*
		 * return jdbcTemplate.query(queryForSspDictMediaType + idIn, new
		 * SspDictMediaTypeRowMapper(), str).toArray(new SspDictMediaType[]{});
		 */
	}

	public SspDictAgeTarget[] findSspDictAgeTarget(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictAgeTarget + " where 1=1 AND code in(" + vals + ")",
				new SspDictAgeTargetRowMapper()).toArray(new SspDictAgeTarget[] {});
	}

	public SspDictMarriageTarget[] findSspDictMarriageTarget(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictMarriageTarget + " where 1=1 AND code in(" + vals + ")",
				new SspDictMarriageTargetRowMapper()).toArray(new SspDictMarriageTarget[] {});
	}

	public SspDictEducationTarget[] findSspDictEducationTarget(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}

		return jdbcTemplate.query(queryForSspDictEducationTarget + " where 1=1 AND code in(" + vals + ")",
				new SspDictEducationTargetRowMapper()).toArray(new SspDictEducationTarget[] {});
	}

	public SspDictInterestsTarget[] findSspDictInterestsTarget(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictInterestsTarget + " where 1=1 AND code in(" + vals + ")",
				new SspDictInterestsTargetRowMapper()).toArray(new SspDictInterestsTarget[] {});
	}

	public SspDictBuyTarget[] findSspDictBuyTarget(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictBuyTarget + " where 1=1 AND code in(" + vals + ")",
				new SspDictBuyTargetRowMapper()).toArray(new SspDictBuyTarget[] {});
	}

	public SspDictCrowdSexType[] findSspDictCrowdSex(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictCrowdSex + " where 1=1 AND code in(" + vals + ")",
				new SspDictCrowdSexTypeRowMapper()).toArray(new SspDictCrowdSexType[] {});
	}

	public SspDictIncomeTarget[] findSspDictIncomeTarget(String str) {
		StringBuilder val = new StringBuilder();
		String vals = "";
		if (CommonUtil.isNotNull(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				val.append("'" + strs[i] + "',");
			}
			vals = val.substring(0, val.length() - 1).toString();
		}
		if (vals == "") {
			return null;
		}
		return jdbcTemplate.query(queryForSspDictIncomeTarget + " where 1=1 AND code in(" + vals + ")",
				new SspDictIncomeTargetRowMapper()).toArray(new SspDictIncomeTarget[] {});
	}

	public final class SspDictIncomeTargetRowMapper implements RowMapper<SspDictIncomeTarget> {

		@Override
		public SspDictIncomeTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictIncomeTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),
					rs.getLong("level"));
		}

	}

	public final class SspDictCrowdSexTypeRowMapper implements RowMapper<SspDictCrowdSexType> {

		@Override
		public SspDictCrowdSexType mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictCrowdSexType(rs.getLong("id"), rs.getLong("code"), rs.getLong("level"),
					rs.getString("name"));
		}

	}

	public final class SspDictBuyTargetRowMapper implements RowMapper<SspDictBuyTarget> {

		@Override
		public SspDictBuyTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictBuyTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"), rs.getLong("level"));
		}

	}

	public final class SspDictInterestsTargetRowMapper implements RowMapper<SspDictInterestsTarget> {
		@Override
		public SspDictInterestsTarget mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new SspDictInterestsTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),
					rs.getLong("level"));
		}
	}

	public final class SspDictEducationTargetRowMapper implements RowMapper<SspDictEducationTarget> {
		@Override
		public SspDictEducationTarget mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new SspDictEducationTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),
					rs.getLong("level"));
		}
	}

	public final class SspDictAgeTargetRowMapper implements RowMapper<SspDictAgeTarget> {

		@Override
		public SspDictAgeTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictAgeTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"), rs.getLong("level"));
		}

	}

	public final class SspDictMediaTypeRowMapper implements RowMapper<SspDictMediaType> {
		@Override
		public SspDictMediaType mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new SspDictMediaType(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"), rs.getString("name"));
		}
	}

	public final class SspDictSysOperationTypeRowMapper implements RowMapper<SspDictSysOperationType> {
		@Override
		public SspDictSysOperationType mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new SspDictSysOperationType(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"),
					rs.getString("name"));
		}
	}

	public final class SspDictMobileBrandTypeRowMapper implements RowMapper<SspDictMobileBrandType> {
		@Override
		public SspDictMobileBrandType mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new SspDictMobileBrandType(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"),
					rs.getString("name"));
		}
	}

	public final class SspCityInfoRowMapper implements RowMapper<SspCityInfo> {

		@Override
		public SspCityInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspCityInfo(rs.getLong("id"), rs.getString("code"), rs.getString("name"), null);
		}

	}

	public final class SspDictMarriageTargetRowMapper implements RowMapper<SspDictMarriageTarget> {

		@Override
		public SspDictMarriageTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictMarriageTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),
					rs.getLong("level"));
		}

	}

	public final class SspPlanTargetConditionRowMapper implements RowMapper<SspPlanTargetCondition> {
		@Override
		public SspPlanTargetCondition mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long ctAdType = rs.getLong("dict_ad_type_id");
			SspDictAdType spDictAdType = new SspDictAdType();
			if (CommonUtil.isNotNull(ctAdType)) {
				SspDictAdTypeService sspDictAdTypeService = SpringUtils.getBean(SspDictAdTypeService.class);
				SspDictAdType sspDictAdType = new SspDictAdType();
				sspDictAdType.setCode(ctAdType);
				try {
					SspDictAdType[] sspDictAdTypes = sspDictAdTypeService.findT(sspDictAdType);
					if (CommonUtil.isNotNull(sspDictAdTypes) && sspDictAdTypes.length > 0) {
						spDictAdType = sspDictAdTypes[0];
					}
				} catch (DaoException e) {
					spDictAdType.setCode(ctAdType);
				} catch (IllegalAccessException e) {
					spDictAdType.setCode(ctAdType);
				}
				if (CommonUtil.isNull(spDictAdType.getCode())) {
					spDictAdType.setCode(ctAdType);
				}
			}
			SspCityInfo[] sspCityInfos = findSspCityInfos(rs.getString("city_code"));
			SspDictSysOperationType[] sspDictSysOperationTypes = findSspDictSysOperationType(rs
					.getString("dict_sys_operation_type"));
			SspDictMobileBrandType[] sspDictMobileBrandTypes = findSspDictMobileBrandType(rs
					.getString("dict_mobile_brand_type"));
			SspDictMediaType[] sspDictMediaTypes = findSspDictMediaType(rs.getString("dict_media_type"));
			SspDictCrowdSexType[] sspDictCrowdSexTypes = findSspDictCrowdSex(rs.getString("dict_crowd_sex_type"));
			SspDictAgeTarget[] sspDictAgeTargets = findSspDictAgeTarget(rs.getString("age"));
			SspDictEducationTarget[] sspDictEducationTargets = findSspDictEducationTarget(rs.getString("education"));
			SspDictMarriageTarget[] sspDictMarriageTargets = findSspDictMarriageTarget(rs.getString("marriage"));
			SspDictIncomeTarget[] sspDictIncomeTargets = findSspDictIncomeTarget(rs.getString("income"));
			SspDictInterestsTarget[] sspDictInterestsTargets = findSspDictInterestsTarget(rs.getString("interests"));
			SspDictBuyTarget[] sspDictBuyTargets = findSspDictBuyTarget(rs.getString("buy"));
			SspPlanTargetCondition condition = new SspPlanTargetCondition(rs.getLong("id"),
					rs.getString("time_target"), spDictAdType, rs.getLong("put_rhythm"),
					rs.getLong("area_target_state"), sspCityInfos, rs.getString("lbs_info"),
					rs.getLong("dict_sys_operation_state"), sspDictSysOperationTypes,
					rs.getLong("dict_mobile_brand_state"), sspDictMobileBrandTypes, rs.getString("network_type"),
					rs.getLong("device_type_state"), rs.getString("device_type_idfa"), rs.getString("device_type_mac"),
					rs.getString("device_type_imei"), rs.getLong("media_state"), sspDictMediaTypes,
					rs.getString("media_ids"), rs.getLong("dict_crowd_sex_state"), sspDictCrowdSexTypes,
					rs.getLong("dict_age_state"), sspDictAgeTargets, rs.getLong("dict_education_state"),
					sspDictEducationTargets, rs.getLong("dict_marriage_state"), sspDictMarriageTargets,
					rs.getLong("dict_income_state"), sspDictIncomeTargets, rs.getLong("dict_interests_state"),
					sspDictInterestsTargets, rs.getLong("dict_buy_state"), sspDictBuyTargets,
					rs.getTimestamp("create_time"), rs.getTimestamp("update_time"), rs.getLong("delete_state"));
			System.out.println("查到的结果是 ---" + condition);
			return condition;

		}

	}

	public SspPlanTargetCondition[] find(SspPlanTargetCondition entity, RowMapper<SspPlanTargetCondition> rowMapper,
			Class claz) throws DaoException, IllegalAccessException {
		List<Object> args = new ArrayList<>();
		Class<?> clazz = entity.getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		StringBuilder sb = new StringBuilder(SqlBuild.select(myTable.value()));
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
						sb.append(" and " + myColumn.value() + " = ? ");
						args.add(field.get(entity));
					}
				}
			}
		}
		List<SspPlanTargetCondition> query = jdbcTemplate.query(sb.toString(), rowMapper, args.toArray());
		return query.toArray((SspPlanTargetCondition[]) Array.newInstance(claz, query.size()));
	}

}
