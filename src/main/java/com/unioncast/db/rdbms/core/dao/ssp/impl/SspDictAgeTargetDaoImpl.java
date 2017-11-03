package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.common.ssp.model.SspDictAgeTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAgeTargetDao;
import com.unioncast.db.rdbms.core.dao.ssp.impl.SspCityInfoDaoImpl.SspCityInfoRowMapper;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("sspDictAgeTargetDao")
public class SspDictAgeTargetDaoImpl extends SspGeneralDao<SspDictAgeTarget, Long> implements SspDictAgeTargetDao {


	public final class SspDictAgeTargetRowMapper implements RowMapper<SspDictAgeTarget> {

		@Override
		public SspDictAgeTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictAgeTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),rs.getLong("level"));
		}
		
	}
	

	
	private static String FIND_ALL = SqlBuild.select(SspDictAgeTarget.TABLE_NAME, SspDictAgeTarget.PROPERTIES);
//	private static String ADD = "insert into " + SspDictAgeTarget.TABLE_NAME + "("
//			+ SspDictAgeTarget.PROPERTIES + ") values (null,?,?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspDictAgeTarget.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictAgeTarget.TABLE_NAME);
	/*
	@Override
	public SspDictAgeTarget[] find(Long id) throws DaoException {
		List<SspDictAgeTarget> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspDictAgeTargetRowMapper(), id));
			return list.toArray(new SspDictAgeTarget[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspDictAgeTargetRowMapper());
		return list.toArray(new SspDictAgeTarget[] {});
	}
	
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	

    @Override
    public SspDictAgeTarget[] findT(SspDictAgeTarget s) throws DaoException, IllegalAccessException {
        List<SspDictAgeTarget> list = new ArrayList<>();
        if (s != null) {
        	SspDictAgeTarget[] sspDictAgeTargets = find(s, new SspDictAgeTargetRowMapper(), SspDictAgeTarget.class);
            return sspDictAgeTargets;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictAgeTargetRowMapper());
        }
        return list.toArray(new SspDictAgeTarget[]{});
    }
	
	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictAgeTarget.class, ids);
    }
	
	@Override
	public Pagination<SspDictAgeTarget> page(PageCriteria pageCriteria) throws DaoException {
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
		List<SspDictAgeTarget> list = jdbcTemplate.query(pageSql.toString(), new SspDictAgeTargetRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspDictAgeTarget>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspDictAgeTarget[] {}));
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

	@Override
	public SspDictAgeTarget[] batchFindbyCodes(String[] codes) {
		 List<SspDictAgeTarget> list = new ArrayList<>();
	        if (codes!=null && codes.length!=0) {
	            for (int i = 0; i < codes.length; i++) {
	                List<SspDictAgeTarget> query = jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ", new SspDictAgeTargetRowMapper());
	                list.addAll(query);
	            }
	        }
	        return list.toArray(new SspDictAgeTarget[]{});
	}
}
