package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspAdPositionInfo;
import com.unioncast.common.ssp.model.SspAdPositionReport;
import com.unioncast.common.ssp.model.SspAppInfo;
import com.unioncast.common.user.model.User;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspAdPositionReportDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("sspAdPositionReportDao")
public class SspAdPositionReportDaoImpl extends SspGeneralDao<SspAdPositionReport, Long> implements SspAdPositionReportDao {


	public final class SspAdPositionReportRowMapper implements RowMapper<SspAdPositionReport> {

		@Override
		public SspAdPositionReport mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspAdPositionReport(rs.getLong("id"), rs.getDate("start_time"), rs.getDate("end_time"),
					findUserByUserId(rs.getLong("developer_id")), findByAppId(rs.getLong("app_id")),
					findByPositionId(rs.getLong("ad_position_id")), rs.getLong("show_times"),
					rs.getDouble("income"), rs.getLong("cpm"), rs.getLong("click_times"),
					rs.getDouble("click_ratio"), rs.getLong("cpc"), rs.getLong("arrival"),
					rs.getDouble("transform"), rs.getLong("cpa"), rs.getLong("second_jump"),
					rs.getDouble("second_jump_ratio"), rs.getDouble("arrival_ratio"), rs.getLong("click_transform"),
					rs.getDouble("click_trandform_ratio"), rs.getDouble("fill_ratio"), rs.getLong("epcm"),
					rs.getDate("update_time"));
		}
		
	}
	

	
	private static String FIND_ALL = SqlBuild.select(SspAdPositionReport.TABLE_NAME, SspAdPositionReport.PROPERTIES);
//	private static String ADD = "insert into " + SspAdPositionReport.TABLE_NAME + "("
//			+ SspAdPositionReport.PROPERTIES + ") values (null,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspAdPositionReport.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspAdPositionReport.TABLE_NAME);
	
	
	
	@Resource(name="userDao")
	UserDao userDao;
	
	private final User findUserByUserId(Long id) {
		User user = null;
		try {
			User[] users =  userDao.find(id);
			if(users != null && users.length != 0){
				user = users[0];
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	public final SspAppInfo findByAppId(Long id){
		
		//TODO 根据appid查找
		return null;
	}
	
	public final SspAdPositionInfo findByPositionId(Long id){
		//TODO 根据位置id查找位置信息
		return null;
	}
	
	@Override
    public SspAdPositionReport[] findT(SspAdPositionReport s) throws DaoException, IllegalAccessException {
        List<SspAdPositionReport> list = new ArrayList<>();
        if (s != null) {
        	SspAdPositionReport[] sspAdPositionReports = find(s, new SspAdPositionReportRowMapper(), SspAdPositionReport.class);
            return sspAdPositionReports;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspAdPositionReportRowMapper());
        }
        return list.toArray(new SspAdPositionReport[]{});
    }
	
	/*
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	@Override
	public Pagination<SspAdPositionReport> page(PageCriteria pageCriteria) throws DaoException {
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
		List<SspAdPositionReport> list = jdbcTemplate.query(pageSql.toString(), new SspAdPositionReportRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspAdPositionReport>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspAdPositionReport[] {}));
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
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspAdPositionReport.class, ids);
    }
}
