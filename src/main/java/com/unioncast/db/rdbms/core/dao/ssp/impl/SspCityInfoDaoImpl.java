package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspCityInfoDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository("sspCityInfoDao")
public class SspCityInfoDaoImpl extends SspGeneralDao<SspCityInfo, Long> implements SspCityInfoDao {

	private static final String INSERT_FOR_CITY= "INSERT INTO ssp_city_info(code,name,update_time) VALUES(?,?,?)";

	@Override
	public SspCityInfo[] batchFindbyCodes(String[] codes) {
        List<SspCityInfo> list = new ArrayList<>();
        if (codes!=null && codes.length!=0) {
            for (int i = 0; i < codes.length; i++) {
                List<SspCityInfo> query = jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ", new SspCityInfoRowMapper());
                list.addAll(query);
            }
        }
        return list.toArray(new SspCityInfo[]{});
	}

	public final class SspCityInfoRowMapper implements RowMapper<SspCityInfo> {

		@Override
		public SspCityInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspCityInfo(rs.getLong("id"), rs.getString("code"), rs.getString("name"),
					rs.getDate("update_time"));
		}
		
	}
	

	
	private static String FIND_ALL = SqlBuild.select(SspCityInfo.TABLE_NAME, SspCityInfo.PROPERTIES);
//	private static String ADD = "insert into " + SspCityInfo.TABLE_NAME + "("
//			+ SspCityInfo.PROPERTIES + ") values (null,?,?,?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspCityInfo.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspCityInfo.TABLE_NAME);
	
	/*
	@Override
	public SspCityInfo[] find(Long id) throws DaoException {
		List<SspCityInfo> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspCityInfoRowMapper(), id));
			return list.toArray(new SspCityInfo[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspCityInfoRowMapper());
		return list.toArray(new SspCityInfo[] {});
	}
	*/
	/*
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/

	@Override
    public SspCityInfo[] findT(SspCityInfo s) throws DaoException, IllegalAccessException {
        List<SspCityInfo> list = new ArrayList<>();
        if (s != null) {
        	SspCityInfo[] sspCityInfos = find(s, new SspCityInfoRowMapper(), SspCityInfo.class);
            return sspCityInfos;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspCityInfoRowMapper());
        }
        return list.toArray(new SspCityInfo[]{});
    }
	
	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspCityInfo.class, ids);
    }
	
	@Override
	public Pagination<SspCityInfo> page(PageCriteria pageCriteria) throws DaoException {
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
		List<SspCityInfo> list = jdbcTemplate.query(pageSql.toString(), new SspCityInfoRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspCityInfo>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspCityInfo[] {}));
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
	public Long[] batchAdd(SspCityInfo[] entitys) throws DaoException {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(INSERT_FOR_CITY,
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (SspCityInfo sspCityInfo : entitys) {
				pstmt.setString(1, sspCityInfo.getCode());
				pstmt.setString(2, sspCityInfo.getName());
				pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
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
}




