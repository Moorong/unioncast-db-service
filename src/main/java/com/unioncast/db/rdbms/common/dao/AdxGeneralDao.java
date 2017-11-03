package com.unioncast.db.rdbms.common.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.unioncast.common.adx.model.AdxDspDeliverySetting;
import com.unioncast.common.adx.model.AdxDspFlowAccessSetting;
import com.unioncast.common.adx.model.AdxDspPanter;
import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.AuthenticationApiInfo;
import com.unioncast.common.user.model.User;
import com.unioncast.common.user.model.UserRole;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.exception.DaoException;

public abstract class AdxGeneralDao<T extends Serializable, ID extends Serializable> extends AbstractGeneralDao<T, ID>
		implements GeneralDao<T, ID> {

	@Autowired
	@Qualifier("adxJdbcTemplate")
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public T findByAppOrWebId(Long id) {
		return null;
	}

	public List<T> findByUserId(Long id) {
		return null;
	}

	public T[] findBySystemId(Long id) throws DaoException {
		return null;
	}

	public T findByString(String str) throws DaoException {
		return null;
	}

	public T findBySysId(Long id) {
		return null;
	}

	@Override
	public int updateAndReturnNum(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
						if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
							sb.append("set " + myColumn.value() + " = ?");
						else
							sb.append("," + myColumn.value() + " = ?");
						args.add(field.get(entity));
					}
				}
			}
		}
		sb.append(" where " + strId + " = ?");
		args.add(objId);
		return jdbcTemplate.update(sb.toString(), args.toArray());
	}

	@Override
	public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
						if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
							sb.append("set " + myColumn.value() + " = ?");
						else
							sb.append("," + myColumn.value() + " = ?");
						args.add(field.get(entity));
					}
				}
			}
		}
		sb.append(" where " + strId + " = ?");
		args.add(objId);
		jdbcTemplate.update(sb.toString(), args.toArray());
	}

	@Override
	public ID save(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		return insertAndReturnId(entity);
	}

	@SuppressWarnings("unchecked")
	private ID insertAndReturnId(T entity) throws IllegalArgumentException, IllegalAccessException {
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
						args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
					}
				}
			}
		}
		return (ID) jdbcInsert.executeAndReturnKey(args);
	}

	public int updateNotNullFieldAndReturn(User user) throws DaoException {
		return 0;
	}

	public User findByUsername(String username) throws DaoException {
		return null;
	}

	public User findByEmail(String email) throws DaoException {
		return null;
	}

	public int deleteUserRoleByUserId(Long id) throws DaoException {
		return 0;
	}

	public int batchAddOneUesrIdAndManyRoleId(Long uesrId, List<Long> roleIds) throws DaoException {
		return 0;
	}

	public int batchAddManyUesrIdAndOneRoleId(Long roleId, List<Long> uesrIds) throws DaoException {
		return 0;
	}

	public int deleteUserRoleByRoleId(Long id) throws DaoException {
		return 0;
	}

	public int batchDeleteUserRoleByRoleId(List<Long> ids) throws DaoException {
		return 0;
	}

	public List<Long> batchAddUserRole(List<UserRole> userRoles) throws DaoException {
		return null;
	}

	public int updateNotNullFieldAndReturn(Authentication authentications) throws DaoException {
		return 0;
	}

	public int deleteAuthApiInfoByAuthId(Long id) throws DaoException {
		return 0;
	}

	public int deleteAuthApiInfoByApiInfoId(Long id) throws DaoException {
		return 0;
	}

	public int batchDeleteAuthApiInfoByAuthId(List<Long> ids) throws DaoException {
		return 0;
	}

	public List<Long> batchAddAuthenticationApiInfo(List<AuthenticationApiInfo> authenticationApiInfos)
			throws DaoException {
		return null;
	}

	public AdxDspDeliverySetting[] findByAdxOrSspId(Long adxOrSspId) throws DaoException {
		return null;
	}

	public AdxDspPanter[] findAdxDspPanterByIdstype(Long flowType, Long[] ids) throws DaoException {
		return null;
	}

	;

	public Long[] batchAdd(T[] entitys) throws DaoException {
		// if(ArrayUtils.isNotEmpty(entitys)){
		//
		// Class<?> clazzT = entitys[0].getClass();
		// MyTable myTable = clazzT.getAnnotation(MyTable.class);
		//
		// String BATCH_ADD = SqlBuild.batchAdd(myTable.value(),AdxDspAccessSettings.PROPERTIES);
		//
		// List<Long> list = null;
		// try {
		// Connection connection = jdbcTemplate.getDataSource().getConnection();
		// connection.setAutoCommit(false);
		//
		//
		// PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
		// for (T entity : entitys) {
		//
		// Class<?> clazz = entity.getClass();
		//
		//
		//
		// if (qualification.getUser() != null)
		// pstmt.setLong(1, qualification.getUser().getId());
		// else
		// pstmt.setNull(1, Types.NULL);
		// pstmt.setString(2, qualification.getName());
		// if (qualification.getFiletype() != null)
		// pstmt.setInt(3, qualification.getFiletype());
		// else
		// pstmt.setNull(3, Types.INTEGER);
		// pstmt.setString(4, qualification.getFilevalidatecode());
		// pstmt.setString(5, qualification.getUrl());
		// pstmt.setString(6, qualification.getScope());
		// if (qualification.getStarttime() != null)
		// pstmt.setTimestamp(7, new Timestamp(qualification.getStarttime().getTime()));
		// else
		// pstmt.setNull(7, Types.TIMESTAMP);
		// if (qualification.getEndtime() != null)
		// pstmt.setTimestamp(8, new Timestamp(qualification.getEndtime().getTime()));
		// else
		// pstmt.setNull(8, Types.TIMESTAMP);
		// pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
		// pstmt.addBatch();
		// }
		// pstmt.executeBatch();
		// connection.commit();
		// ResultSet rs = pstmt.getGeneratedKeys();
		// list = new ArrayList<>();
		// while (rs.next()) {
		// list.add(rs.getLong(1));
		// }
		// connection.close();
		// pstmt.close();
		// rs.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// return list;
		//
		//
		// }
		return null;
	}

	public int batchDelete(Long[] ids) throws DaoException {
		return 0;
	}

	public AdxDspFlowAccessSetting[] findByIds(Long[] ids) throws DaoException {
		return null;
	}

	@Override
	public Pagination<T> page(PageCriteria pageCriteria) throws DaoException {
		return null;
	}

	public T[] find(ID id) throws DaoException {
		return null;

	}

	public AdxDspFlowAccessSetting[] findByDspIds(Long[] ids) {
		return null;
	}

	@Override
	public Long[] add(T[] entitys) throws SQLException, DaoException, IllegalArgumentException, IllegalAccessException {
		return super.add(entitys);
	}

	@Override
	public T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException {
		return super.find(pageCriteria);
	}

	@Override
	public Pagination<T> generalPage(PageCriteria pageCriteria)
			throws DaoException, InstantiationException, IllegalAccessException {
		return super.generalPage(pageCriteria);
	}

	@Override
	public int delete(Class<?> clazz, ID[] ids) throws DaoException {
		return super.delete(clazz, ids);
	}

	@Override
	public int updateNotNullField(T[] entities)
			throws DaoException, IllegalArgumentException, IllegalAccessException, SQLException {
		return super.updateNotNullField(entities);
	}

}
