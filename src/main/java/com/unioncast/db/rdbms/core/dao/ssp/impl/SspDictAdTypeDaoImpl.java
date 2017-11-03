package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspDictAdType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAdTypeDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictAdTypeDao")
public class SspDictAdTypeDaoImpl extends SspGeneralDao<SspDictAdType, Long> implements SspDictAdTypeDao {


	public final class SspDictAdTypeRowMapper implements RowMapper<SspDictAdType> {

		@Override
		public SspDictAdType mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictAdType(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"),
					rs.getString("name"), null);
		}
		
	}
	

	
	private static String FIND_ALL = SqlBuild.select(SspDictAdType.TABLE_NAME, SspDictAdType.PROPERTIES);
//	private static String ADD = "insert into " + SspDictAdType.TABLE_NAME + "("
//			+ SspDictAdType.PROPERTIES + ") values (null,?,?,?, ?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspDictAdType.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictAdType.TABLE_NAME);
	/*
	@Override
	public SspDictAdType[] find(Long id) throws DaoException {
		List<SspDictAdType> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspDictAdTypeRowMapper(), id));
			return list.toArray(new SspDictAdType[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspDictAdTypeRowMapper());
		return list.toArray(new SspDictAdType[] {});
	}
	
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	

    @Override
    public SspDictAdType[] findT(SspDictAdType s) throws DaoException, IllegalAccessException {
        List<SspDictAdType> list = new ArrayList<>();
        if (s != null) {
        	SspDictAdType[] sspDictAdTypes = find(s, new SspDictAdTypeRowMapper(), SspDictAdType.class);
            return sspDictAdTypes;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictAdTypeRowMapper());
        }
        return list.toArray(new SspDictAdType[]{});
    }
	
	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictAdType.class, ids);
    }
	
	@Override
	public Pagination<SspDictAdType> page(PageCriteria pageCriteria) throws DaoException {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + "where 1=1");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = (String) searchExpressionList.get(i).getValue();
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
				String criteriaSql = " " + pageCriteria.getPredicate() + " "
						+ searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator()
						+ " " + value;
				pageSql.append(criteriaSql);
				countAll.append(criteriaSql);
			}
		}
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		if (orderExpressionList != null && orderExpressionList.size() != 0) {
			pageSql.append(" order by");
			for (int i = 0; i < orderExpressionList.size(); i++) {
				if (i > 0) pageSql.append(",");
				pageSql.append(
						" " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
			}
		}
		pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
				+ pageCriteria.getPageSize());
		List<SspDictAdType> list = jdbcTemplate.query(pageSql.toString(), new SspDictAdTypeRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspDictAdType>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspDictAdType[] {}));
	}
	/*
	@Override
	public int batchDelete(Long[] ids) {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(DELETE_BY_ID, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}
	*/
}
