package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.*;
import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.common.ssp.model.SspOrder;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspOrderDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.UserService;
import com.unioncast.db.rdbms.core.service.ssp.SspAdvertiserService;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanService;
import com.unioncast.db.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sspOrderDao")
public class SspOrderDaoImpl extends SspGeneralDao<SspOrder, Long> implements SspOrderDao {
    @Autowired
    private SspPlanService planService;

    private static String FIND_ALL = SqlBuild.select(SspOrder.TABLE_NAME,
            SspOrder.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspOrder.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspOrder.TABLE_NAME);

    @Override
    public SspOrder[] findT(SspOrder s) throws DaoException, IllegalAccessException {
        List<SspOrder> list = new ArrayList<>();
        if (s != null) {
            SspOrder[] sspOrders = find(s, new SspOrderRowMapper(), SspOrder.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspOrderRowMapper());
        }
//        List<SspOrder> list = new ArrayList<>();
//        if (id != null) {
//            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
//                    new SspOrderRowMapper(), id));
//        } else {
//            list = jdbcTemplate.query(FIND_ALL, new SspOrderRowMapper());
//        }

        //return list.toArray(new SspOrder[]{});
        return list.toArray(new SspOrder[]{});
    }


    @Override
    public SspOrder[] find(SspOrder entity, RowMapper<SspOrder> rowMapper, Class claz) throws DaoException, IllegalAccessException {
        List<Object> args = new ArrayList<>();
        Class<?> clazz = entity.getClass();
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        StringBuilder sb = new StringBuilder(SqlBuild.select(myTable.value()));
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
                        if (myColumn.value().equals("advertiser_id")) {
                            sb.append(" and advertiser_id" + " = " + entity.getSspAdvertiser().getId());
                        } else {
                            sb.append(" and " + myColumn.value() + " = ? ");
                            args.add(field.get(entity));
                        }
                    }
                }
            }
        }
        List<SspOrder> query = jdbcTemplate.query(sb.toString(), rowMapper, args.toArray());
        return query.toArray((SspOrder[]) Array.newInstance(claz, query.size()));
    }

    @Override
    public int updateAndReturnNum(SspOrder entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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

                        } else if (myColumn.value().equals("advertiser_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspAdvertiser().getId());
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
        return jdbcTemplate.update(sb.toString(), args.toArray());
    }

    @Override
    public Pagination<SspOrder> page(PageCriteria pageCriteria)
            throws DaoException {
        Long userId = 1L;
        pageCriteria.setEntityClass(SspOrder.class);
       /* return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspOrderRowMapper());*/
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1 and delete_state =1 ");
        StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 and delete_state =1 ");
        if (searchExpressionList != null && searchExpressionList.size() != 0) {
            for (int i = 0; i < searchExpressionList.size(); i++) {
                String value = (String) searchExpressionList.get(i).getValue();
                /*if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator()))
                    value = "'%" + value + "%'";*/
                //TODO  上面注释代码，比如传时间查询，没有拼接引号
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
        int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);

        List<SspOrder> list = jdbcTemplate.query(pageSql.toString(), new SspOrderRowMapper());
        if(list.size() > 0){
            list.stream().forEach(orders ->{
             Map<String,Object> results = planService.findPlanCountByOrderId(orders.getId());
             if(!results.isEmpty()){
                 for (String key : results.keySet()) {
                     orders.setOpenStatePlanAmount(Integer.parseInt(results.get("openStatePlan").toString()));
                     orders.setCountPlanAmount(Integer.parseInt(results.get("countPlanAmount").toString()));
                 }
             }
            });
        }
        return new Pagination<SspOrder>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),  list.toArray(new SspOrder[]{}));


    /*    List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1");
        StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1");
        if (searchExpressionList != null && searchExpressionList.size() != 0) {
            for (int i = 0; i < searchExpressionList.size(); i++) {
                String value = (String) searchExpressionList.get(i).getValue();
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
        int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);

        List<SspOrder> list = jdbcTemplate.query(pageSql.toString(), new SspOrderRowMapper());

        return new Pagination<SspOrder>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), list.toArray(new SspOrder[]{}));
*/
    }



    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspOrder.class, ids);
    }


    @Override
    public Long save(SspOrder entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

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
                        } else if (myColumn.value().equals("advertiser_id")) {
                            args.put("advertiser_id", entity.getSspAdvertiser() == null ? null : entity.getSspAdvertiser().getId());
                        } else {
                            args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                        }
                    }
                }
            }
        }
        return jdbcInsert.executeAndReturnKey(args).longValue();
    }


    public static final class SspOrderRowMapper implements RowMapper<SspOrder> {
        @Override
        public SspOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long accountId = rs.getLong("account_id");
            User user = null;
            if (accountId != null) {
                try {
                    UserService userService = SpringUtils.getBean(UserService.class);
                    user = userService.findById(accountId);
                } catch (Exception e) {
                   user = new User();
                   user.setId(accountId);
                }
                if(CommonUtil.isNull(user)){
                    user = new User();
                    user.setId(accountId);
                }
            }

            SspAdvertiser sspAdvertiser = null;
            Long advertiserId = rs.getLong("advertiser_id");
            if (advertiserId != null) {
                try {
                    SspAdvertiserService sspAdvertiserService = SpringUtils.getBean(SspAdvertiserService.class);
                    sspAdvertiser = sspAdvertiserService.findById(advertiserId);
                } catch (Exception e) {
                    sspAdvertiser = new SspAdvertiser();
                    sspAdvertiser.setId(accountId);
                }
                if(CommonUtil.isNull(sspAdvertiser)){
                    sspAdvertiser = new SspAdvertiser();
                    sspAdvertiser.setId(accountId);
                }
            }


            return new SspOrder(rs.getLong("id"), user, sspAdvertiser, rs.getString("order_identifying"),
                    rs.getString("name"), rs.getDouble("budget"), rs.getDouble("total_amount"), rs.getDouble("balance"),
                    rs.getString("contract_id"), rs.getInt("put_time_state"), rs.getTimestamp("put_start_time"), rs.getTimestamp("put_end_time"),
                    rs.getInt("settlement_type"), rs.getDouble("settlement_fee"), rs.getDouble("service_fee_ratio"),
                    rs.getDouble("service_fee"), rs.getDouble("agent_fee_ratio"), rs.getDouble("agent_fee"),
                    rs.getLong("single_period_show_times"), rs.getInt("show_calculation_type"), rs.getLong("single_show_times"), rs.getInt("click_calculation_type"),
                    rs.getLong("single_click_times"), rs.getDouble("daily_budget_cap"), rs.getLong("daily_show_cap"), rs.getLong("daily_click_cap"), rs.getInt("kpi"),
                    rs.getDouble("kpi_fee"), rs.getString("keywords"), rs.getString("comment"), rs.getTimestamp("create_time"), rs.getTimestamp("update_time"),
                    rs.getInt("delete_state"), rs.getInt("ad_put_state"),rs.getDouble("consumption_amount"),rs.getInt("open_state_plan_amount"),rs.getInt("count_plan_amount"),rs.getLong("click_num"),rs.getLong("exposure_num"));
        }
    }
    
    @Override
	public Integer countOrders(PageCriteria pageCriteria) {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 and delete_state = 1");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = searchExpressionList.get(i).getValue().toString();
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
				
				String criteriaSql = " and "
						+ searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator()
						+ " " + value;
				countAll.append(criteriaSql);
			}
		}
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return totalCount;
	}

    @Override
    public List<SspOrder> findOrderAll() {
        StringBuffer sql = new StringBuffer(FIND_ALL + " where 1=1 and delete_state = 1 and ad_put_state = 1 ");
        List<SspOrder> list = jdbcTemplate.query(sql.toString(), new SspOrderRowMapper());
        return list;
    }


	@Override
	public Pagination<SspOrder> AdStatePage( AdvertiserOrderModel params ) {
		 String sql = "select o.* from ssp_order o left join  ssp_advertiser a on o.advertiser_id=a.id where 1=1 and o.delete_state=1 and a.delete_state = 1 ";
		String countsql = "select count(*) from ssp_order o left join  ssp_advertiser a on o.advertiser_id=a.id where 1=1 and o.delete_state=1 and a.delete_state = 1";
		 //订单状态
		 if(params.getAdPutState()!=null){
			 sql+=" and o.ad_put_state = "+params.getAdPutState();
			 countsql+=" and o.ad_put_state = "+params.getAdPutState();
		 }
		 //订单名称
		 if(params.getOrderName()!=null){
			 sql+=" and o.name like "+"'%"+params.getOrderName()+"%'";
			 countsql +=" and o.name like" +"'%"+params.getOrderName()+"%'";
		 }
		 //订单更新开始时间
		 if(params.getPushStartTime()!=null){
			 sql+=" and o.update_time >="+"'"+params.getPushStartTime()+"'";
			 countsql+=" and o.update_time >="+"'"+params.getPushStartTime()+"'";
		 }
		 //订单更新结束时间
		 if(params.getPushEndTime()!=null){
			 sql+=" and o.update_time <= "+"'"+params.getPushEndTime()+"'";
			 countsql+=" and o.update_time <= "+"'"+params.getPushEndTime()+"'";
		 }
		 //订单关联的广告主的状态
		 if(params.getAdvertiserState()!=null){
			 sql+=" and a.put_allowed_state = "+params.getAdvertiserState();
			 countsql +=" and a.put_allowed_state = "+params.getAdvertiserState();
		 }
         sql+=" order by o.update_time desc ";
         
         sql+=" limit "+(params.getCurrentPageNo()-1)*params.getPageSize()+","+params.getPageSize();
         
         System.out.println("最终的sql语句是"+sql);
         
        List<SspOrder> list = jdbcTemplate.query(sql, new SspOrderRowMapper());
        int totalCount = jdbcTemplate.queryForObject(countsql, int.class);
        return new Pagination<SspOrder>(totalCount, params.getPageSize(), params.getCurrentPageNo(), list.toArray(new SspOrder[]{}));
	}


	@Override
	public SspOrder[] findByAdId(Long advertiserId) {
		String sql = FIND_ALL + " where advertiser_id = ? order by create_time";
		List<SspOrder> sspOrders = jdbcTemplate.query(sql, new SspOrderRowMapper(), advertiserId);
		return sspOrders.toArray(new SspOrder[] {});
	}
	
	@Override
    public SspOrder findById (Long id) throws DaoException{
        if (id != null) {
            return jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspOrderRowMapper(), id);
        }
        return null;
    }

}
