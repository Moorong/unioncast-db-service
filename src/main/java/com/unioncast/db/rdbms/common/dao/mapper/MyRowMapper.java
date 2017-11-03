/**
 * 
 */
package com.unioncast.db.rdbms.common.dao.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.unioncast.common.annotation.JoinColumn;
import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;

/**
 * @author juchaochao
 * @param <T>
 * @date 2016年11月9日 下午7:20:00
 *
 */
public final class MyRowMapper<T> implements RowMapper<T> {
	private Class<?> clazz;

	public MyRowMapper(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		Object entity = null;
		try {
			entity = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				// 处理非static的属性
				if (!Modifier.isStatic(field.getModifiers())) {
					field.setAccessible(true);
					MyColumn myColumn = field.getDeclaredAnnotation(MyColumn.class);
					if (myColumn != null) {
						String fieldType = field.getType().getSimpleName();
						String columnName = myColumn.value();
						switch (fieldType) {
						case "String":
							field.set(entity, rs.getString(columnName));
							break;
						case "int":
							field.set(entity, rs.getInt(columnName));
							break;
						case "Integer":
							field.set(entity, rs.getInt(columnName));
							break;
						case "long":
							field.set(entity, rs.getLong(columnName));
							break;
						case "Long":
							field.set(entity, rs.getLong(columnName));
							break;
						case "Date":
							field.set(entity, rs.getTimestamp(columnName));
							break;
						default:
							break;
						}
					} else {
						// 处理外键
						JoinColumn joinColumn = field.getDeclaredAnnotation(JoinColumn.class);
//						field.get(obj)
						if (joinColumn != null) {
							Class<?> fieldType = field.getType();
							if (fieldType.equals(List.class)) {

							} else if (fieldType.isArray()) {

							} else if (fieldType.equals(clazz)) {
								// 获得外键对应的实体
								Object refValue = setIdForRefClass(field, rs);
								// 将返回的外键实体设置到当前的实体属性中
								field.set(entity, refValue);
							} else {
								Object refValue = setIdForRefClass(field, rs);
								field.set(entity, refValue);
							}
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return (T) entity;
	}

	/**
	 * 为外键关联类设置id
	 * 
	 * @author juchaochao
	 * @date 2016年11月16日 下午2:35:45
	 *
	 * @param refField
	 * @param rs
	 * @return
	 */
	private Object setIdForRefClass(Field refField, ResultSet rs) {
		// 外键对应的实体 refInstance
		Object refInstance = null;
		Field[] refClassfields = refField.getType().getDeclaredFields();
		for (Field field : refClassfields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				MyId id = field.getDeclaredAnnotation(MyId.class);
				if (id != null) {
					try {
						refInstance = refField.getType().newInstance();
						String className = field.getType().getSimpleName();
						Object refInstanceIdValue = null;
						// 为外键对应的实体设置id
						refInstanceIdValue = rs.getObject(refField.getDeclaredAnnotation(JoinColumn.class).value());
						if(refInstanceIdValue == null){
							return null;
						}
						switch (className) {
						case "Long":
							refInstanceIdValue = rs.getLong(refField.getDeclaredAnnotation(JoinColumn.class).value());
							break;
						default:
							break;
						}
						field.set(refInstance, refInstanceIdValue);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return refInstance;
	}

}
