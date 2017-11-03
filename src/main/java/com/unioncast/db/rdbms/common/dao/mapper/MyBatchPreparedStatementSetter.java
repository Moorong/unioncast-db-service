package com.unioncast.db.rdbms.common.dao.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

/**
 * 为批量更新设置参数
 * 
 * @author juchaochao
 * @param <T>
 * @date 2016年11月11日 下午1:45:38
 *
 */
public class MyBatchPreparedStatementSetter<T> implements BatchPreparedStatementSetter {

	private T[] entities;

	public MyBatchPreparedStatementSetter() {
		super();
	}

	public MyBatchPreparedStatementSetter(T[] entities) {
		super();
		this.setEntities(entities);
	}

	@Override
	public void setValues(PreparedStatement ps, int i) throws SQLException {

	}

	@Override
	public int getBatchSize() {
		return 0;
	}

	public T[] getEntities() {
		return entities;
	}

	public void setEntities(T[] entities) {
		this.entities = entities;
	}

}
