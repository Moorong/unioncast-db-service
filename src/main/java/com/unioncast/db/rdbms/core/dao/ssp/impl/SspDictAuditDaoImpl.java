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
import com.unioncast.common.ssp.model.SspDictAudit;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictAuditDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("sspDictAuditDao")
public class SspDictAuditDaoImpl extends SspGeneralDao<SspDictAudit, Long> implements SspDictAuditDao {


	public final class SspDictAuditRowMapper implements RowMapper<SspDictAudit> {

		@Override
		public SspDictAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictAudit(rs.getLong("id"), rs.getInt("code"), rs.getString("name"), rs.getDate("update_time"));
		}
		
	}
	

	
	private static String FIND_ALL = SqlBuild.select(SspDictAudit.TABLE_NAME, SspDictAudit.PROPERTIES);
//	private static String ADD = "insert into " + SspAdPositionInfo.TABLE_NAME + "("
//			+ SspDictAudit.PROPERTIES + ") values (null,?,?,?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspDictAudit.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictAudit.TABLE_NAME);
	/*
	@Override
	public SspDictAudit[] find(Long id) throws DaoException {
		List<SspDictAudit> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspDictAuditRowMapper(), id));
			return list.toArray(new SspDictAudit[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspDictAuditRowMapper());
		return list.toArray(new SspDictAudit[] {});
	}
	
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	

    @Override
    public SspDictAudit[] findT(SspDictAudit s) throws DaoException, IllegalAccessException {
        List<SspDictAudit> list = new ArrayList<>();
        if (s != null) {
        	SspDictAudit[] sspDictAudits = find(s, new SspDictAuditRowMapper(), SspDictAudit.class);
            return sspDictAudits;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictAuditRowMapper());
        }
        return list.toArray(new SspDictAudit[]{});
    }
	
	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictAudit.class, ids);
    }
	
	@Override
	public Pagination<SspDictAudit> page(PageCriteria pageCriteria) throws DaoException {
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
		List<SspDictAudit> list = jdbcTemplate.query(pageSql.toString(), new SspDictAuditRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspDictAudit>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspDictAudit[] {}));
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
