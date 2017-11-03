package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspDictAccessWay;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAccessWayDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictAccessWayDao")
public class SspDictAccessWayDaoImpl extends SspGeneralDao<SspDictAccessWay, Long> implements SspDictAccessWayDao {


	public final class SspDictAccessWayRowMapper implements RowMapper<SspDictAccessWay> {

		@Override
		public SspDictAccessWay mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictAccessWay(rs.getLong("id"), rs.getInt("code"), rs.getString("name"),
					null);
		}
		
	}
	
	private static String FIND_ALL = SqlBuild.select(SspDictAccessWay.TABLE_NAME, SspDictAccessWay.PROPERTIES);
//	private static String ADD = "insert into " + SspDictAccessWay.TABLE_NAME + "("
//			+ SspDictAccessWay.PROPERTIES + ") values (null,?,?,?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspDictAccessWay.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictAccessWay.TABLE_NAME);
	/*
	@Override
	public SspDictAccessWay[] find(Long id) throws DaoException {
		List<SspDictAccessWay> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspDictAccessWayRowMapper(), id));
			return list.toArray(new SspDictAccessWay[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspDictAccessWayRowMapper());
		return list.toArray(new SspDictAccessWay[] {});
	}
	
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	

    @Override
    public SspDictAccessWay[] findT(SspDictAccessWay s) throws DaoException, IllegalAccessException {
        List<SspDictAccessWay> list = new ArrayList<>();
        if (s != null) {
        	SspDictAccessWay[] sspDictAccessWays = find(s, new SspDictAccessWayRowMapper(), SspDictAccessWay.class);
            return sspDictAccessWays;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictAccessWayRowMapper());
        }
        return list.toArray(new SspDictAccessWay[]{});
    }
	
	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictAccessWay.class, ids);
    }
	
	@Override
	public Pagination<SspDictAccessWay> page(PageCriteria pageCriteria) throws DaoException {
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
		List<SspDictAccessWay> list = jdbcTemplate.query(pageSql.toString(), new SspDictAccessWayRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspDictAccessWay>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspDictAccessWay[] {}));
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
