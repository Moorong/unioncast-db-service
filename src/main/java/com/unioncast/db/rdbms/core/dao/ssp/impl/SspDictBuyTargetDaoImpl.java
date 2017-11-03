package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictBuyTargetDao;
import com.unioncast.db.rdbms.core.dao.ssp.impl.SspDictCrowdSexDaoImpl.SspDictCrowdSexTypeRowMapper;
import com.unioncast.db.rdbms.core.exception.DaoException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictBuyTargetDao")
public class SspDictBuyTargetDaoImpl extends SspGeneralDao<SspDictBuyTarget, Long> implements SspDictBuyTargetDao {


	public final class SspDictBuyTargetRowMapper implements RowMapper<SspDictBuyTarget> {

		@Override
		public SspDictBuyTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictBuyTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),rs.getLong("level"));
		}
		
	}
	

	
	private static String FIND_ALL = SqlBuild.select(SspDictBuyTarget.TABLE_NAME, SspDictBuyTarget.PROPERTIES);
//	private static String ADD = "insert into " + SspDictBuyTarget.TABLE_NAME + "("
//			+ SspDictBuyTarget.PROPERTIES + ") values (null,?,?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspDictBuyTarget.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictBuyTarget.TABLE_NAME);
	/*
	@Override
	public SspDictBuyTarget[] find(Long id) throws DaoException {
		List<SspDictBuyTarget> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspDictBuyTargetRowMapper(), id));
			return list.toArray(new SspDictBuyTarget[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspDictBuyTargetRowMapper());
		return list.toArray(new SspDictBuyTarget[] {});
	}
	
	
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	

    @Override
    public SspDictBuyTarget[] findT(SspDictBuyTarget s) throws DaoException, IllegalAccessException {
        List<SspDictBuyTarget> list = new ArrayList<>();
        if (s != null) {
        	SspDictBuyTarget[] sspDictBuyTargets = find(s, new SspDictBuyTargetRowMapper(), SspDictBuyTarget.class);
            return sspDictBuyTargets;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictBuyTargetRowMapper());
        }
        return list.toArray(new SspDictBuyTarget[]{});
    }
	
	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictBuyTarget.class, ids);
    }
	
	@Override
	public Pagination<SspDictBuyTarget> page(PageCriteria pageCriteria) throws DaoException {
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
		List<SspDictBuyTarget> list = jdbcTemplate.query(pageSql.toString(), new SspDictBuyTargetRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspDictBuyTarget>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspDictBuyTarget[] {}));
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
	public Long[] batchAdd(SspDictBuyTarget[] entitys) throws DaoException {
		List<Long> list = null;
		String sql ="insert into ssp_dict_buy_target(code,name) values(?,?)";
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (SspDictBuyTarget buyTarget : entitys) {
				pstmt.setLong(1, buyTarget.getCode());
				pstmt.setString(2, buyTarget.getName());
				/*pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));*/
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			ResultSet rs = pstmt.getGeneratedKeys();
			list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
			connection.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.toArray(new Long[list.size()]);

	}

	@Override
	public SspDictBuyTarget[] batchFindbyCodes(String[] codes) {
			List<SspDictBuyTarget> list = new ArrayList<SspDictBuyTarget>();
			if(codes!=null&&codes.length!=0){
				 for (int i = 0; i < codes.length; i++) {
					 String quer = FIND_ALL + " where code = '" + codes[i] + "' ";
					 System.out.println("查询语句是--"+quer);
					 List<SspDictBuyTarget> query= jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ",new SspDictBuyTargetRowMapper());
					 list.addAll(query);
				 }
			}
			return list.toArray(new SspDictBuyTarget[]{});
		}
}
