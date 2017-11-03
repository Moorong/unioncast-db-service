package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.common.user.model.User;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspAdvertiserDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sspAdvertiserDao")
public class SspAdvertiserDaoImpl extends SspGeneralDao<SspAdvertiser, Long> implements SspAdvertiserDao {

	public final class SspAdvertiserRowMapper implements RowMapper<SspAdvertiser> {

		@Override
		public SspAdvertiser mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new SspAdvertiser(rs.getLong("id"), findUserByUserId(rs.getLong("account_id")), rs.getString("advertiser_identifying"),
					rs.getString("name"), rs.getString("company_reg_name"), rs.getString("contract_id"),
					rs.getLong("put_allowed_state"), rs.getLong("put_already_state"), rs.getString("url"),
					rs.getLong("corner_mark_state"), rs.getString("contacts"), rs.getString("phone"),
					rs.getString("email"), rs.getString("business_license_pic"), rs.getString("business_reg_certificate_pic"),
					rs.getString("legal_person_certificate_pic"), rs.getString("organization_code"),
					rs.getString("organization_pic"), rs.getString("icp_pic"), rs.getString("tax_certificate_pic"),
					rs.getString("telecom_oper_license_pic"), rs.getString("logo_name"), rs.getString("logo_pic"),
					rs.getString("keywords"), rs.getDouble("service_fee"), rs.getTimestamp("create_time"),
					rs.getTimestamp("update_time"), rs.getLong("delete_state"));
		}

	}



	private static String FIND_ALL = SqlBuild.select(SspAdvertiser.TABLE_NAME, SspAdvertiser.PROPERTIES);
//	private static String ADD = "insert into " + SspAdvertiser.TABLE_NAME + "("
//			+ SspAdvertiser.PROPERTIES + ") values (null,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?)";
//	private static String DELETE_BY_ID = SqlBuild.delete(SspAdvertiser.TABLE_NAME);
	private static String COUNT_ALL = SqlBuild.countAll(SspAdvertiser.TABLE_NAME);



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

	/*
	@Override
	public SspAdvertiser[] find(Long id) throws DaoException {
		List<SspAdvertiser> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspAdvertiserRowMapper(), id));
			return list.toArray(new SspAdvertiser[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new SspAdvertiserRowMapper());
		return list.toArray(new SspAdvertiser[] {});
	}
	*/
	@Override
    public SspAdvertiser[] findT(SspAdvertiser s) throws DaoException, IllegalAccessException {
        List<SspAdvertiser> list = new ArrayList<>();
        if (s != null) {
        	SspAdvertiser[] sspAdvertisers = find(s, new SspAdvertiserRowMapper(), SspAdvertiser.class);
            return sspAdvertisers;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspAdvertiserRowMapper());
        }
        return list.toArray(new SspAdvertiser[]{});
    }
	/*
	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}
	*/
	@Override
	public Pagination<SspAdvertiser> page(PageCriteria pageCriteria) throws DaoException {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1 and delete_state = 1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 and delete_state = 1");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = searchExpressionList.get(i).getValue().toString();
//				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
		            value = " '%" + value + "%' ";
		        } else {
		            value = " '" + value + "' ";
		        }
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
		System.out.println(pageSql.toString());
		List<SspAdvertiser> list = jdbcTemplate.query(pageSql.toString(), new SspAdvertiserRowMapper());

		//查询订单数
		String countOrderSql = "select count(1) from ssp_order where advertiser_id = ? and delete_state = 1";
		if(list != null && list.size() != 0){
			for(int i=0; i<list.size(); i++){
				int orderNum = jdbcTemplate.queryForObject(countOrderSql, int.class, list.get(i).getId());
				list.get(i).setOrderNumber(orderNum);
			}
		}

		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspAdvertiser>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), list.toArray(new SspAdvertiser[] {}));
	}

	@Override
    public Long save(SspAdvertiser entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

        Class<?> clazz = entity.getClass();
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(myTable.value());
        Map<String, Object> args = new HashMap<String, Object>();
        MyColumn myColumn = null;
        MyId myId = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                myId = field.getAnnotation(MyId.class);
                if (myId != null) {
                    jdbcInsert.setGeneratedKeyName(myId.value());
                } else {
                    myColumn = field.getDeclaredAnnotation(MyColumn.class);
                    if (myColumn != null && StringUtils.isNotBlank(myColumn.value())) {
                        if (myColumn.value().equals("account_id")) {
                            args.put("account_id", entity.getUser() == null ? null : entity.getUser().getId());
                        } else {
                            args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                        }
                    }
                }
            }
        }
        return jdbcInsert.executeAndReturnKey(args).longValue();
    }

	@Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspAdvertiser.class, ids);
    }

	@Override
    public int updateAndReturnNum(SspAdvertiser entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
        List<Object> args = new ArrayList<>();
        Class<?> clazz = entity.getClass();
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        StringBuilder sb = new StringBuilder(SqlBuild.updateNotNullField(myTable.value()));
        MyColumn myColumn = null;
        MyId myId = null;
        String strId = null;
        Object objId = null;
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    myColumn = field.getDeclaredAnnotation(MyColumn.class);
                    if (myColumn != null && StringUtils.isNotBlank(myColumn.value())) {
                        if (field.getDeclaredAnnotation(MyId.class) != null) {
                            myId = field.getDeclaredAnnotation(MyId.class);
                            strId = myId.value();
                            objId = field.get(entity);
                            continue;
                        }
                        if (myColumn.value().equals("account_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getUser().getId());

                        } else {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(field.get(entity));
                        }
                    }
                }
            }
        }
        sb.append(" where " + strId + " = ?");
        args.add(objId);
       // System.out.println("sql语句是：==="+sb);
        return jdbcTemplate.update(sb.toString(), args.toArray());
    }

	@Override
    public SspAdvertiser findById (Long id) throws DaoException{
        if (id != null) {
            return jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspAdvertiserRowMapper(), id);
        }
        return null;
    }

    @Override
    public SspAdvertiser[] findByUserId(SspAdvertiser sspAdvertiser) {
        List<SspAdvertiser> list = new ArrayList<>();
        if (sspAdvertiser != null && sspAdvertiser.getUser()!=null) {
            Long id = sspAdvertiser.getUser().getId();
            list = jdbcTemplate.query(FIND_ALL+" where delete_state ='1' and account_id = " + id, new SspAdvertiserRowMapper());
        }
        return list.toArray(new SspAdvertiser[]{});
    }
}
