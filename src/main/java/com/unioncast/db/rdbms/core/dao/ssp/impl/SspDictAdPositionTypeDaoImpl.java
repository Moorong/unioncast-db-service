package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspDictAdPositionType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAdPositionTypeDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictAdPositionTypeDao")
public class SspDictAdPositionTypeDaoImpl extends SspGeneralDao<SspDictAdPositionType, Long> implements SspDictAdPositionTypeDao {


	public final class SspDictAdPositionTypeRowMapper implements RowMapper<SspDictAdPositionType> {

		@Override
		public SspDictAdPositionType mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictAdPositionType(rs.getLong("id"), rs.getInt("code"), rs.getString("name"),
					rs.getString("img_url"),null);
		}
		
	}
	

	
	private static String FIND_ALL = SqlBuild.select(SspDictAdPositionType.TABLE_NAME, SspDictAdPositionType.PROPERTIES);
//	private static String ADD = "insert into " + SspDictAdPositionType.TABLE_NAME + "("
//			+ SspDictAdPositionType.PROPERTIES + ") values (null,?,?,?, ?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspDictAdPositionType.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictAdPositionType.TABLE_NAME);
	/*
	@Override
	public SspDictAdPositionType[] find(Long id) throws DaoException {
		List<SspDictAdPositionType> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspDictAdPositionTypeRowMapper(), id));
			return list.toArray(new SspDictAdPositionType[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspDictAdPositionTypeRowMapper());
		return list.toArray(new SspDictAdPositionType[] {});
	}
	
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	

    @Override
    public SspDictAdPositionType[] findT(SspDictAdPositionType s) throws DaoException, IllegalAccessException {
        List<SspDictAdPositionType> list = new ArrayList<>();
        if (s != null) {
        	SspDictAdPositionType[] sspDictAdPositionTypes = find(s, new SspDictAdPositionTypeRowMapper(), SspDictAdPositionType.class);
            return sspDictAdPositionTypes;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictAdPositionTypeRowMapper());
        }
        return list.toArray(new SspDictAdPositionType[]{});
    }
	
	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictAdPositionType.class, ids);
    }
	
	@Override
	public Pagination<SspDictAdPositionType> page(PageCriteria pageCriteria) throws DaoException {
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
		List<SspDictAdPositionType> list = jdbcTemplate.query(pageSql.toString(), new SspDictAdPositionTypeRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspDictAdPositionType>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspDictAdPositionType[] {}));
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
