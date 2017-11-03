package com.unioncast.db.rdbms.core.dao.ssp.impl;




import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.*;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlanDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

import com.unioncast.db.rdbms.core.service.common.UserService;
import com.unioncast.db.rdbms.core.service.ssp.SspAdvertiserService;
import com.unioncast.db.rdbms.core.service.ssp.SspOrderService;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanService;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanTargetConditionService;
import com.unioncast.db.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;









import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sspPlanDao")
public class SspPlanDaoImpl extends SspGeneralDao<SspPlan, Long> implements
        SspPlanDao {

    private static String FIND_ALL = SqlBuild.select(SspPlan.TABLE_NAME,
            SspPlan.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspPlan.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspPlan.TABLE_NAME);

    @Override
    public SspPlan[] find(Long id, Long userId) {
        List<SspPlan> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspPlanRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL + " where account_id = ?", new SspPlanRowMapper(), userId);
        }

        return list.toArray(new SspPlan[]{});
    }
    @Override
    public boolean validateSspPlanName(String name,Long advertiserId){
        if (CommonUtil.isNotNull(name)) {
            SspPlan cp = new SspPlan();
            cp.setName(name);
            if(CommonUtil.isNotNull(advertiserId)){
                SspAdvertiser sad = new SspAdvertiser();
                sad.setId(advertiserId);
                cp.setSspAdvertiser(sad);
            }
            cp.setDeleteState(1);
            try {
                SspPlan[] sspPlans = find(cp,new SspPlanRowMapper(),SspPlan.class);
                return sspPlans.length==0;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SspPlan findById (Long id) throws DaoException{
        if (id != null) {
            return jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspPlanRowMapper(), id);
        }
        return null;
    }


    @Override
    public SspPlan[] find(Long id) {
        List<SspPlan> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspPlanRowMapper(), id));
            return list.toArray(new SspPlan[]{});
        }
        list = jdbcTemplate.query(FIND_ALL, new SspPlanRowMapper());
        return list.toArray(new SspPlan[]{});

    }

    @Override
    public Long save(SspPlan entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        } else if (myColumn.value().equals("order_id")) {
                            args.put("order_id", entity.getSspOrder() == null ? null : entity.getSspOrder().getId());
                        } else if (myColumn.value().equals("parent_plan_id")) {
                            args.put("parent_plan_id", entity.getParentPlan() == null ? null : entity.getParentPlan().getId());
                        } else if (myColumn.value().equals("plan_target_condition_id")) {
                            args.put("plan_target_condition_id", entity.getSspPlanTargetCondition() == null ? null : entity.getSspPlanTargetCondition().getId());
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
    public int updateAndReturnNum(SspPlan entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        } else if (myColumn.value().equals("order_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspOrder().getId());
                        } else if (myColumn.value().equals("parent_plan_id")) {
                            Long parentPlanId = entity.getParentPlan().getId();
                            if(CommonUtil.isNotNull(parentPlanId)&&0l!=parentPlanId){
                                if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                    sb.append("set " + myColumn.value() + " = ?");
                                else
                                    sb.append("," + myColumn.value() + " = ?");
                                args.add(parentPlanId);
                            }
                        } else if (myColumn.value().equals("plan_target_condition_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspPlanTargetCondition().getId());
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
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspPlan.class, ids);
    }

    @Override
    public SspOperLog[] findLogsByPlanId(Long id) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SspCreative[] findCreativesByPlanId(Long id) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pagination<SspPlan> page(PageCriteria pageCriteria) throws DaoException {
        //account_id 该表中用户id字段
      /*  pageCriteria.setEntityClass(SspPlan.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspPlanRowMapper());*/
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1 and delete_state = 1");
        StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 and delete_state = 1");
        if (searchExpressionList != null && searchExpressionList.size() != 0) {
            for (int i = 0; i < searchExpressionList.size(); i++) {

                String value = searchExpressionList.get(i).getValue().toString();
                System.out.println(value);
                //if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
                if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
                    value = " '%" + value + "%' ";
                }else if("is".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())){
             	   value = null;
                }else{
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
        List<SspPlan> list = jdbcTemplate.query(pageSql.toString(), new SspPlanRowMapper());
        System.out.println("查询到的数据时==="+list);
        //在这里加上 ChildCount行吗
        if(list!=null&& list.size()>0){
        	for(int i=0;i<list.size();i++){
        		Long id = list.get(i).getId();
        		Integer state = list.get(i).getState();
        		int ChildCount =jdbcTemplate.queryForObject("select count(*) from ssp_plan where delete_state = 1 and parent_plan_id="+id,int.class) ;
        		int childStateCount = jdbcTemplate.queryForObject("select count(*) from ssp_plan where delete_state = 1 and state=1 and parent_plan_id="+id,int.class) ;
        		//剩余金额=预算-消费金额（所有的子计划的消费金额的和）
        		//select sum(consumption_amount)from ssp_plan where delete_state = 1 and parent_plan_id = 14;
        		Double consumptionAmount = jdbcTemplate.queryForObject("select sum(consumption_amount)from ssp_plan where delete_state = 1 and parent_plan_id ="+id,double.class) ;
        		list.get(i).setChildrenNum(ChildCount);
        		list.get(i).setChildStateCount(childStateCount);
        		//计划组的总消费金额
        		if(consumptionAmount!=null){
        			list.get(i).setConsumptionAmount(consumptionAmount);
        		}else{
        			//不是计划组的计划
        			Double planAmount = jdbcTemplate.queryForObject("select consumption_amount from ssp_plan where delete_state = 1 and id ="+id,double.class) ;
        			list.get(i).setConsumptionAmount(planAmount);
        		}
     	}
        }
        int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
        return new Pagination<SspPlan>(totalCount, pageCriteria.getPageSize(),
                pageCriteria.getCurrentPageNo(), list.toArray(new SspPlan[]{}));
    }

    @Override
    public void updateState(Long id, int state) {
        // TODO Auto-generated method stub

    }

    /**
     * 将跟订单id相关的计划删掉
     */
    @Override
    public int deleteByOrderId(Long id) throws DaoException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 将跟父id下的子计划删掉相关的计划删掉
     */
    @Override
    public int deleteByParentId(Long id) throws DaoException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public SspPlan[] findByIsPlanGroup(Integer isPlanGroup) {
        List<SspPlan> list = new ArrayList<>();
        if (isPlanGroup != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where is_plan_group = ?",
                    new SspPlanRowMapper(), isPlanGroup));
            return list.toArray(new SspPlan[]{});
        }
        list = jdbcTemplate.query(FIND_ALL, new SspPlanRowMapper());
        return list.toArray(new SspPlan[]{});
    }

    @Override
    public SspPlan[] findByOrderId(Long id) {
        List<SspPlan> list = new ArrayList<>();
        if (id != null) {
            list = jdbcTemplate.query(FIND_ALL + " where delete_state = '1' and order_id = ? ", new SspPlanRowMapper(), id);
        }
        return list.toArray(new SspPlan[]{});
    }

    public static final class SspPlanRowMapper implements RowMapper<SspPlan> {
        // 结果集封装
        @Override
        public SspPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
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
                    sspAdvertiser.setId(advertiserId);
                }
                if(CommonUtil.isNull(sspAdvertiser)){
                    sspAdvertiser = new SspAdvertiser();
                    sspAdvertiser.setId(advertiserId);
                }
            }

            SspOrder sspOrder = null;
            Long orderId = rs.getLong("order_id");
            if (orderId != null) {
                try {
                    SspOrderService sspOrderService = SpringUtils.getBean(SspOrderService.class);
                    sspOrder = sspOrderService.findById(orderId);
                } catch (Exception e) {
                    sspOrder = new SspOrder();
                    sspOrder.setId(orderId);
                }
                if(CommonUtil.isNull(sspOrder)){
                    sspOrder = new SspOrder();
                    sspOrder.setId(orderId);
                }
            }

            SspPlan parentPlan = null;
            Long parentPlanId = rs.getLong("parent_plan_id");
            if (parentPlanId != null) {
                try {
                    SspPlanService sspPlanService = SpringUtils.getBean(SspPlanService.class);
                    parentPlan = sspPlanService.findById(parentPlanId);
                } catch (Exception e) {
                    parentPlan = new SspPlan();
                    parentPlan.setId(parentPlanId);
                }
                if(CommonUtil.isNull(parentPlan)){
                    parentPlan = new SspPlan();
                    parentPlan.setId(parentPlanId);
                }
            }

            SspPlanTargetCondition sspPlanTargetCondition = null;
            Long planTargetConditionId = rs.getLong("plan_target_condition_id");
            if (planTargetConditionId != null) {
                try {
                    SspPlanTargetConditionService sspPlanTargetConditionService = SpringUtils.getBean(SspPlanTargetConditionService.class);
                    sspPlanTargetCondition = sspPlanTargetConditionService.findById(planTargetConditionId);
                } catch (Exception e) {
                    sspPlanTargetCondition = new SspPlanTargetCondition();
                    sspPlanTargetCondition.setId(planTargetConditionId);
                }
                if(CommonUtil.isNull(sspPlanTargetCondition)){
                    sspPlanTargetCondition = new SspPlanTargetCondition();
                    sspPlanTargetCondition.setId(planTargetConditionId);
                }
            }

            return new SspPlan(rs.getLong("id"), user, sspAdvertiser, sspOrder, rs.getString("name"),
                    rs.getString("plan_identifying"), rs.getLong("plan_type"), rs.getLong("put_time_state"), rs.getTimestamp("put_start_time"),
                    rs.getTimestamp("put_end_time"), rs.getLong("budget_state"), rs.getDouble("budget"), rs.getString("contract_id"),
                    rs.getLong("single_period_show_times"), rs.getInt("show_calculation_type"),
                    rs.getLong("single_show_times"), rs.getInt("click_calculation_type"), rs.getLong("single_click_times"),
                    rs.getDouble("daily_budget_cap"), rs.getLong("daily_show_cap"), rs.getLong("daily_click_cap"), rs.getInt("put_rhythm"),
                    rs.getInt("kpi"), rs.getDouble("highest_cpm"), rs.getString("keywords"), rs.getString("comment"), rs.getInt("is_plan_group"),
                    parentPlan, sspPlanTargetCondition, rs.getTimestamp("create_time"), rs.getTimestamp("update_time"),
                    rs.getInt("delete_state"), rs.getInt("level"), rs.getInt("state"),rs.getInt("children_num"),rs.getInt("child_state_count"),rs.getDouble("consumption_amount"));
        }
    }

	@Override
	public Map<Long, Integer> findChildPlans(List<Long> ids) {
		Map<Long, Integer> childCount = new HashMap<Long, Integer>();
		String sql = "select * from ssp_plan where parent_plan_id = ?";
		if(ids!=null&&ids.size()>0){
			for(Long id:ids){
				Object [] params  = new Object[]{id};
				int [] types = new int[]{Types.BIGINT};
				List<SspPlan> result = jdbcTemplate.query(sql, params,types,new SspPlanRowMapper());
				if(result!=null&&result.size()>0){
					childCount.put(id, result.size());
				}else{
					childCount.put(id, 0);
				}
			}

		}
		return childCount;
	}

    //查询所有未删除的状态为开启的计划
	public List<SspPlan> findStateAndDelete() {
		String sql = "select * from ssp_plan where state =1 and delete_state = 1";
		List<SspPlan>  result = jdbcTemplate.query(sql,new SspPlanRowMapper());
		return result;
	}

    @Override
    public Map<String,Object> findPlanCountByOrderId(Long orderId) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("(SELECT COUNT(*) from ssp_plan where state = 1 and delete_state = 1 and order_id = '"+orderId+"') as openStatePlan");
        sql.append(",count(*)  as countPlanAmount");
        sql.append(" from ssp_plan where delete_state = 1 and order_id = '"+orderId+"' ");
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql.toString());
        return resultMap;
    }
	@Override
	public List<SspPlanCreativeRelation> findAllPlanCreatives() {
		List<Map<String , Object>> list = jdbcTemplate.queryForList("select plan_id,creater_id from ssp_plan_creative_relation");
		if(list != null && list.size() > 0) {
			List<SspPlanCreativeRelation> relationList = new ArrayList<SspPlanCreativeRelation>();
			for(Map<String , Object> map : list) {
				if(map != null && map.size() > 0) {
					SspPlanCreativeRelation relation = new SspPlanCreativeRelation();
					Long planId = (Long) map.get("plan_id");
					Long creativeId = (Long) map.get("creater_id");
					SspPlan plan = new SspPlan();
					plan.setId(planId);
					SspCreative creative = new SspCreative();
					creative.setId(creativeId);
					relation.setSspPlan(plan);
					relation.setSspCreative(creative);

					relationList.add(relation);
				}
			}
			return relationList;
		}
		return null;
	}


}
