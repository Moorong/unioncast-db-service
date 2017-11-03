package com.unioncast.db.rdbms.common.dao;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.Operation;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.user.model.*;
import com.unioncast.db.rdbms.common.dao.mapper.MyRowMapper;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommonGeneralDao<T extends Serializable, ID extends Serializable>
		extends AbstractGeneralDao<T, ID>implements GeneralDao<T, ID> {

	@Autowired
	@Qualifier("commonJdbcTemplate")
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public T[] findByUserId(Long id) throws DaoException {
		return null;
	}

	public T[] findByRoleId(Long id) {
		return null;
	}

	public T[] findBySystemId(Long id) throws DaoException {
		return null;
	}
	
	public T[] findByIdAndName(Long id, String name) {
		return null;
	}

	public T[] findModuleBySystemId(Long id) {
		return null;
	}

	public T[] findByNameAndSystem(String name, Long id) {
		return null;
	}

	public T findByString(String str) throws DaoException {
		return null;
	}

	public T findBySysId(Long id) {
		return null;
	}

	public int delRoleModById(Long[] ids) throws DaoException {
		return 0;
	}

	public int updateNotNullFieldAndReturn(User user) throws DaoException {
		return 0;
	}

	@Override
	public int updateAndReturnNum(T entity) throws IllegalArgumentException, IllegalAccessException {
		List<Object> args = new ArrayList<>();
		Class<?> clazz = entity.getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		// StringBuilder sb = new
		// StringBuilder(SqlBuild.updateNotNullField(myTable.value()));
		StringBuilder sb = new StringBuilder("update " + myTable.value());
		MyColumn myColumn = null;
		MyId myId = null;
		String idColumunName = null;
		Object idColumnValue = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				if (field.get(entity) != null) {
					myId = field.getDeclaredAnnotation(MyId.class);
					if (myId == null) {
						myColumn = field.getDeclaredAnnotation(MyColumn.class);
						if (myColumn != null) {
							if ("?".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("," + myColumn.value() + " = ?");
							else
								sb.append(" set " + myColumn.value() + " = ?");
							args.add(field.get(entity));
						}
					} else {
						idColumunName = myId.value();
						idColumnValue = field.get(entity);
					}
				}
			}
		}
		sb.append(" where " + idColumunName + " = ?");
		args.add(idColumnValue);
		return jdbcTemplate.update(sb.toString(), args.toArray());
	}

	public T[] find(T entity, RowMapper<T> rowMapper, Class claz) throws DaoException, IllegalAccessException {
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
						sb.append("and " + myColumn.value() + " = ? ");
						args.add(field.get(entity));
					}
				}
			}
		}
		List<T> query = jdbcTemplate.query(sb.toString(), rowMapper, args.toArray());
		return (T[]) query.toArray((T[]) Array.newInstance(claz, query.size()));

	}

	@Override
	public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		List<Object> args = new ArrayList<>();
		Class<?> clazz = entity.getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		// StringBuilder sb = new
		// StringBuilder(SqlBuild.updateNotNullField(myTable.value()));
		StringBuilder sb = new StringBuilder("update " + myTable.value());
		MyColumn myColumn = null;
		MyId myId = null;
		String idColumunName = null;
		Object idColumnValue = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				if (field.get(entity) != null) {
					myId = field.getDeclaredAnnotation(MyId.class);
					if (myId == null) {
						myColumn = field.getDeclaredAnnotation(MyColumn.class);
						if (myColumn != null) {
							if ("?".equals(sb.subSequence(sb.length() - 1, sb.length())))
								sb.append("," + myColumn.value() + " = ?");
							else
								sb.append(" set " + myColumn.value() + " = ?");
							args.add(field.get(entity));
						}
					} else {
						idColumunName = myId.value();
						idColumnValue = field.get(entity);
					}
				}
			}
		}
		sb.append(" where " + idColumunName + " = ?");
		args.add(idColumnValue);
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
					if (myColumn != null && StringUtils.isNotBlank(myColumn.value()))
						args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
				}
			}
		}
		return (ID) jdbcInsert.executeAndReturnKey(args);
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

	public int batchAddOneUesrIdAndManyRoleId(Long uesrId, Long[] roleIds) throws DaoException {
		return 0;
	}

	public int batchAddManyUesrIdAndOneRoleId(Long roleId, Long[] uesrIds) throws DaoException {
		return 0;
	}

	public int deleteUserRoleByRoleId(Long id) throws DaoException {
		return 0;
	}

	public int batchDeleteUserRoleByRoleId(Long[] ids) throws DaoException {
		return 0;
	}

	public Long[] batchAddUserRole(UserRole[] userRoles) throws DaoException {
		return null;
	}

	public int updateNotNullFieldAndReturn(Authentication authentications) throws DaoException {
		return 0;
	}

	public int deleteAuthApiInfoByAuthId(Long id) throws DaoException {
		return 0;
	}

	public int batchDeleteAuthApiInfoByApiInfoId(Long[] ids) throws DaoException {
		return 0;
	}

	public int deleteAuthApiInfoByApiInfoId(Long id) throws DaoException {
		return 0;
	}

	public int batchDeleteAuthApiInfoByAuthId(Long[] ids) throws DaoException {
		return 0;
	}

	public Long[] batchAddAuthenticationApiInfo(AuthenticationApiInfo[] authenticationApiInfos) throws DaoException {
		return null;
	}

	public Role[] findRoleByUserId(Long id) throws DaoException {
		return null;
	}

	public User checkbyLoginPWR(String loginname, String password, Long roleid) throws DaoException {
		return null;
	}

	public Long[] batchAdd(T[] entitys) throws DaoException {
		return null;
	}

	public int batchDelete(Long[] ids) throws DaoException {
		return 0;
	}

	@Override
	public Pagination<T> page(PageCriteria pageCriteria) throws DaoException {
		return null;
	}

	public T[] find(ID id) throws DaoException {
		return null;

	}

	public ApiInfo[] findApiInfoByAuthId(Long id) throws DaoException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException {
		ArrayList<Object> arrayList = getFindSqlAndList(pageCriteria);
		StringBuilder sql = (StringBuilder) arrayList.get(0);
		List<Object> list = (List<Object>) arrayList.get(1);
		List<T> results = jdbcTemplate.query(sql.toString(), new MyRowMapper<T>(pageCriteria.getEntityClass()),
				list.toArray());
		return (T[]) results.toArray((T[]) Array.newInstance(pageCriteria.getEntityClass(), results.size()));

	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<T> generalPage(PageCriteria pageCriteria)
			throws DaoException, InstantiationException, IllegalAccessException {
		ArrayList<Object> arrayList = getFindSqlAndList(pageCriteria);
		StringBuilder sql = (StringBuilder) arrayList.get(0);
		List<Object> list = (List<Object>) arrayList.get(1);
		int start = 0;
		start = (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize();
		sql.append(" limit " + start + "," + pageCriteria.getPageSize());
		List<T> results = jdbcTemplate.query(sql.toString(), new MyRowMapper<T>(pageCriteria.getEntityClass()),
				list.toArray());
		return new Pagination<T>(1, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				results.toArray((T[]) Array.newInstance(pageCriteria.getEntityClass(), results.size())));
	}

	/**
	 * @author juchaochao
	 * @date 2016年11月10日 下午7:21:31
	 *
	 * @param pageCriteria
	 * @return
	 */
	protected ArrayList<Object> getFindSqlAndList(PageCriteria pageCriteria) {
		Class<?> clazz = pageCriteria.getEntityClass();
		MyTable myTable = clazz.getDeclaredAnnotation(MyTable.class);
		List<SearchExpression> searchExpression = pageCriteria.getSearchExpressionList();
		StringBuilder sql = new StringBuilder("select * from " + myTable.value() + " where ");
		List<Object> list = new ArrayList<>();
		for (SearchExpression searchExpression1 : searchExpression) {
			String propertyName = searchExpression1.getPropertyName();
			Object value = searchExpression1.getValue();
			Operation operation = searchExpression1.getOperation();
			Field field = null;
			try {
				field = clazz.getDeclaredField(propertyName);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			MyColumn myColumn = field.getDeclaredAnnotation(MyColumn.class);
			sql.append(myColumn.value() + " " + operation.getOperator() + " ?");
			switch (operation) {
			case LIKE:
				list.add("%" + value + "%");
				break;
			default:
				list.add(value);
				break;
			}
		}
		ArrayList<Object> result = new ArrayList<>();
		result.add(sql);
		result.add(list);
		return result;
	}

	@Override
	public Long[] add(T[] entitys) throws DaoException, SQLException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = entitys[0].getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		StringBuilder sqlSb = new StringBuilder("insert into " + myTable.value());
		StringBuilder key = new StringBuilder("(");
		StringBuilder placeholder = new StringBuilder(" values(");
		MyId myId = null;
		MyColumn myColumn = null;
		Field[] classFields = clazz.getDeclaredFields();
		List<Field> needFields = new ArrayList<>();
		for (Field field : classFields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				myId = field.getAnnotation(MyId.class);
				if (myId == null) {
					needFields.add(field);
					myColumn = field.getDeclaredAnnotation(MyColumn.class);
					if (myColumn != null) {
						key.append(myColumn.value() + ",");
						placeholder.append("?,");
					}
				}
			}
		}
		String keyStr = key.substring(0, key.length() - 1);
		keyStr += ") ";
		String placeholderStr = placeholder.substring(0, placeholder.length() - 1);
		placeholderStr += ") ";
		sqlSb.append(keyStr).append(placeholderStr).toString();
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		connection.setAutoCommit(false);
		PreparedStatement pstmt = connection.prepareStatement(sqlSb.toString(),
				PreparedStatement.RETURN_GENERATED_KEYS);
		for (T entity : entitys) {
			int parameterIndex = 1;
			for (Field field : needFields) {
				pstmt.setObject(parameterIndex++, field.get(entity));
			}
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		connection.commit();
		ResultSet rs = pstmt.getGeneratedKeys();
		List<Long> list = new ArrayList<>();
		while (rs.next()) {
			list.add(rs.getLong(1));
		}
		connection.close();
		pstmt.close();
		rs.close();
		return list.toArray(new Long[] {});
	}

	@Override
	public int delete(Class<?> clazz, ID[] ids) throws DaoException {
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		StringBuilder sqlSb = new StringBuilder("delete from " + myTable.value() + " where id = ?");
		List<Object[]> batchArgs = new ArrayList<>();
		for (ID id : ids) {
			Object[] objects = new Object[] { id };
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
	public int updateNotNullField(T[] entities)
			throws DaoException, IllegalArgumentException, IllegalAccessException, SQLException {
		List<Object> args = new ArrayList<>();
		Class<?> clazz = entities[0].getClass();
		MyTable myTable = clazz.getAnnotation(MyTable.class);
		StringBuilder builder = new StringBuilder("update " + myTable.value());
		MyColumn myColumn = null;
		MyId myId = null;
		String idColumunName = null;
		Object idColumnValue = null;
		Field[] fields = clazz.getDeclaredFields();
		List<Field> needFields = new ArrayList<>();
		Field idField = null;
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				if (field.get(entities[0]) != null) {
					myId = field.getDeclaredAnnotation(MyId.class);
					if (myId == null) {
						needFields.add(field);
						myColumn = field.getDeclaredAnnotation(MyColumn.class);
						if ("?".equals(builder.subSequence(builder.length() - 1, builder.length())))
							builder.append("," + myColumn.value() + " = ?");
						else
							builder.append(" set " + myColumn.value() + " = ?");
						args.add(field.get(entities[0]));
					} else {
						idField = field;
						idColumunName = myId.value();
						idColumnValue = field.get(entities[0]);
					}
				}
			}
		}
		builder.append(" where " + idColumunName + " = ?");
		args.add(idColumnValue);
		final Field finalIdFiel = idField;
		int[] arrayInt = jdbcTemplate.batchUpdate(builder.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				T entity = entities[i];
				try {
					int parameterIndex = 1;
					for (Field field : needFields) {
						ps.setObject(parameterIndex++, field.get(entity));
					}
					ps.setObject(parameterIndex, finalIdFiel.get(entity));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			@Override
			public int getBatchSize() {
				return 0;
			}
		});
		int count = 0;
		for (int i : arrayInt) {
			count += i;
		}
		return count;
	}

	public Pagination<User> pageByRoleIds(PageCriteria pageCriteria) {
		return null;
	}

	public UserCount findUserCount() {
		return null;
	}

}
