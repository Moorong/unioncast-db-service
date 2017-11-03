/**
 * 
 */
package com.unioncast.db.rdbms.common.dao.sqlBuild;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 生成sql的工具类
 * 
 * @author juchaochao
 * @date 2016年10月21日 下午3:03:29
 *
 */
public class SqlBuild {

	public static String countAll(String tableName) {
		return "select count(*) from " + tableName;
	}

	public static String select(String tableName, String properties) {
		return "select " + properties + " from " + tableName;
	}

	/**
	 * @param tableName
	 * @param properties
	 * @return
	 * 
	 * @Tip 只有前11个字段(带id)，后面有差的需要手动补充
	 */
	public static String batchAdd(String tableName, String properties) {
		return "insert into " + tableName + "(" + properties + ") values(null,?,?,?,?,?,?,?,?,?,?)";
	}

	public static <T> String batchAdd(T entity) {
		// return "insert into " + tableName + "(" + properties + ") values(null,?,?,?,?,?,?,?,?,?,?)";PERTIES);
		Class<?> clazzT = entity.getClass();
		MyTable myTable = clazzT.getAnnotation(MyTable.class);
		StringBuilder sb = new StringBuilder("insert into " + myTable.value());
		StringBuilder key = new StringBuilder("(");
		StringBuilder placeholder = new StringBuilder(" values(");
		MyId myId = null;
		MyColumn myColumn = null;
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				myId = field.getAnnotation(MyId.class);
				if (myId == null) {
					myColumn = field.getDeclaredAnnotation(MyColumn.class);
					key.append(myColumn.value() + ",");
					placeholder.append("?,");
				}
			}
		}
		String keyStr = key.substring(0, sb.length() - 1);
		keyStr += ") ";
		String placeholderStr = key.substring(0, sb.length() - 1);
		placeholderStr += ") ";
		return sb.append(keyStr).append(placeholderStr).toString();
	}

	public static String updateNotNullField(String tableName) {
		// return "update " + tableName + " set update_time = ?";
		return "update " + tableName + " ";
	}

	public static String select(String tableName) {
		// return "update " + tableName + " set update_time = ?";
		return "select * from  " + tableName + " where 1=1 " ;
	}

	public static String updateNotNullFieldSet(String tableName) {
		return "update " + tableName + " set update_time = ?";
	}

	public static String delete(String tableName) {
		return "delete from " + tableName + " where id = ?";
	}

	// public static String add(String tableName, String properties) {
	// return "insert into " + tableName + " from " + tableName + "";
	// }
	//
	// public static String modify(String tableName, String properties) {
	// return "select " + properties + " from " + tableName + "";
	// }
	//
	// public static String delete(String tableName, String properties) {
	// return "select " + properties + " from " + tableName + "";
	// }

	public static <T> String findByID(Class<?> clazz) {
		MyTable myTable = clazz.getDeclaredAnnotation(MyTable.class);
		StringBuilder sqlBuilder = new StringBuilder("select * from " + myTable.value() + " where ");
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				MyId myId = field.getAnnotation(MyId.class);
				MyColumn myColumn = field.getDeclaredAnnotation(MyColumn.class);
				if (myId != null) {
					sqlBuilder.append(myColumn.value() + "=?");
				}
			}
		}
		return sqlBuilder.toString();
	}

}
