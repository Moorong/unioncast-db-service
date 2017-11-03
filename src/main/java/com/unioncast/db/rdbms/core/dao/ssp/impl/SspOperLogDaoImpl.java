package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspDictLabel;
import com.unioncast.common.ssp.model.SspOperLog;
import com.unioncast.common.ssp.model.SspOrder;
import com.unioncast.common.ssp.model.SspPlan;
import com.unioncast.common.ssp.model.SspPlanTargetCondition;
import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspOperLogDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("sspOperLogDao")
public class SspOperLogDaoImpl extends SspGeneralDao<SspOperLog, Long> implements SspOperLogDao {

    @Autowired
    UserDao userDao;

    private static String FIND_ALL = SqlBuild.select(SspOperLog.TABLE_NAME,SspOperLog.PROPERTIES);

    private static String DELETE_BY_ID = SqlBuild.delete(SspOperLog.TABLE_NAME);

    private static String COUNT_ALL = SqlBuild.countAll(SspOperLog.TABLE_NAME);
    //用户
    private static String QUERY_ACCOUNT = "SELECT * FROM  common_user WHERE 1 = 1 ";
    //订单
    private static String QUERY_ORDER = SqlBuild.select(SspOrder.TABLE_NAME,SspOrder.PROPERTIES);
    //计划
    private static String QUERY_PLAN = SqlBuild.select(SspPlan.TABLE_NAME,SspPlan.PROPERTIES);
    //广告主
    private static String QUERY_SSPADVERTISER =SqlBuild.select(SspAdvertiser.TABLE_NAME,SspAdvertiser.PROPERTIES);
    //创意
    private static String QUERY_SSPCREATIVE = SqlBuild.select(SspCreative.TABLE_NAME,SspCreative.PROPERTIES);

    private  static  String ID_EQ = " WHERE 1= 1 and id = ?";

    @Override
    public SspOperLog[] find(Long id) throws DaoException {
        List<SspOperLog> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspOperLogDaoImpl.SspOperLogRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspOperLogDaoImpl.SspOperLogRowMapper());
        }

        return list.toArray(new SspOperLog[]{});
    }

    @Override
    public SspOperLog[] findT(SspOperLog s) throws DaoException, IllegalAccessException {
        List<SspOperLog> list = new ArrayList<>();
        if (s != null) {
            SspOperLog[] sspOrders = find(s, new SspOperLogRowMapper(), SspOperLog.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspOperLogRowMapper());
        }
        return list.toArray(new SspOperLog[]{});
    }

    @Override
    public int updateAndReturnNum(SspOperLog entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        } else if (myColumn.value().equals("plan_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspPlan().getId());
                        } else if (myColumn.value().equals("creative")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspCreative().getId());
                        } else if (myColumn.value().equals("order_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspOrder().getId());
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
    public Pagination<SspOperLog> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspOperLog.class);
     /*   return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspOperLogDaoImpl.SspOperLogRowMapper(), "account_id", userId);*/
        return page(pageCriteria);

    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspOperLog.class, ids);
    }


    @Override
    public Long save(SspOperLog entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

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
                        } else if (myColumn.value().equals("plan_id")) {
                            args.put("plan_id", entity.getSspPlan() == null ? null : entity.getSspPlan().getId());
                        } else if (myColumn.value().equals("creative")) {
                            args.put("creative", entity.getSspCreative() == null ? null : entity.getSspCreative().getId());
                        } else if (myColumn.value().equals("order_id")) {
                            args.put("order_id", entity.getSspOrder() == null ? null : entity.getSspOrder().getId());
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
    public Pagination<SspOperLog> page(PageCriteria pageCriteria)
            throws DaoException {
        Long userId = 1L;
        pageCriteria.setEntityClass(SspOrder.class);
       /* return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspOrderRowMapper());*/
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1 ");
        StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 ");
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

        List<SspOperLog> list = jdbcTemplate.query(pageSql.toString(), new SspOperLogRowMapper());
        return new Pagination<SspOperLog>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),  list.toArray(new SspOperLog[]{}));
    }



    // 查询订单
    private SspOrder getOrder(Long id) {
        List<SspOrder> orderlist = jdbcTemplate.query(QUERY_ORDER + ID_EQ,
                new SspOrderRowMapper(), id);
        return orderlist.get(0);
    }

    // 查询user

    private User getUser(Long id) throws DaoException {
        User[] user = userDao.find(id);
        if(user.length>0 && CommonUtil.isNotNull(user)){
            return  user[0];
        }
        return null;
    }

    // 查询创意
    private SspCreative getCreative(Long id) {
        List<SspCreative> sspCreativeList = jdbcTemplate.query(
                QUERY_SSPCREATIVE + ID_EQ, new SspCreativeRowMapper(), id);
        return sspCreativeList.get(0);
    }

    // 查询计划
    private SspPlan getPlan(Long id) {
        List<SspPlan> sspPlanList = jdbcTemplate.query(QUERY_PLAN + ID_EQ,
                new SspPlanRowMapper(), id);
        return sspPlanList.get(0);
    }

    // 查询广告主
    private SspAdvertiser getAdvertiser(Long id) {
        List<SspAdvertiser> sspAdvertiserlist = jdbcTemplate.query(QUERY_SSPADVERTISER + ID_EQ,
                new SspAdvertiserRowMapper(), id);
        return sspAdvertiserlist.get(0);
    }



    public final class SspOperLogRowMapper implements RowMapper<SspOperLog> {
        @Override
        public SspOperLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long accountId = rs.getLong("account_id");
            User user = null;
            if (accountId != null) {
                user = new User();
                user.setId(accountId);
                try {
                    user = getUser(rs.getLong("account_id"));
                } catch (DaoException e) {
                    e.printStackTrace();
                }
            }
            Long advertiserId = rs.getLong("advertiser_id");
            SspAdvertiser sspAdvertiser = null;
            if (advertiserId != null) {
                sspAdvertiser = new SspAdvertiser();
                sspAdvertiser.setId(advertiserId);
                sspAdvertiser =  getAdvertiser(rs.getLong("advertiser_id"));
            }
            Long de = rs.getLong("order_id");
            SspOrder spOrder = null;
            if (de != null) {
                spOrder = new SspOrder();
                spOrder.setId(de);
                spOrder = getOrder(rs.getLong("order_id"));
            }
            Long lan = rs.getLong("plan_id");
            SspPlan spPlan = null;
            if (lan != null) {
                spPlan = new SspPlan();
                spPlan.setId(lan);
                spPlan = getPlan(rs.getLong("plan_identifying"));
            }
            Long tive = rs.getLong("creative");
            SspCreative spCreative = null;
            if (tive != null) {
                spCreative = new SspCreative();
                spCreative.setId(tive);
                spCreative = getCreative(rs.getLong("creative"));
            }

            return new SspOperLog(rs.getLong("id"), rs.getString("module"),
                   user,sspAdvertiser,
                    rs.getString("advertiser_identifying"),
                    spOrder,
                    rs.getString("order_identifying"),
                    spPlan,
                    rs.getString("plan_identifying"),
                    spCreative,
                    rs.getString("operation"),
                    rs.getTimestamp("create_time"));
        }
    }


    //订单映射
    public static final class SspOrderRowMapper implements RowMapper<SspOrder> {
        @Override
        public SspOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long accountId = rs.getLong("account_id");
            User user = null;
            if (accountId != null) {
                user = new User();
                user.setId(accountId);
            }

            SspAdvertiser sspAdvertiser = null;
            Long advertiserId = rs.getLong("advertiser_id");
            if (advertiserId != null) {
                sspAdvertiser = new SspAdvertiser();
                sspAdvertiser.setId(advertiserId);
            }

            return new SspOrder(rs.getLong("id"), user, sspAdvertiser,
                    rs.getString("order_identifying"),
                    rs.getString("name"), rs.getDouble("budget"),
                    rs.getDouble("total_amount"), rs.getDouble("balance"),
                    rs.getString("contract_id"), rs.getInt("put_time_state"),
                    rs.getTimestamp("put_start_time"),
                    rs.getTimestamp("put_end_time"),
                    rs.getInt("settlement_type"), rs.getDouble("settlement_fee"),
                    rs.getDouble("service_fee_ratio"),
                    rs.getDouble("service_fee"),
                    rs.getDouble("agent_fee_ratio"), rs.getDouble("agent_fee"),
                    rs.getLong("single_period_show_times"),
                    rs.getInt("show_calculation_type"),
                    rs.getLong("single_show_times"),
                    rs.getInt("click_calculation_type"),
                    rs.getLong("single_click_times"),
                    rs.getDouble("daily_budget_cap"),
                    rs.getLong("daily_show_cap"), rs.getLong("daily_click_cap"),
                    rs.getInt("kpi"),
                    rs.getDouble("kpi_fee"), rs.getString("keywords"),
                    rs.getString("comment"), rs.getTimestamp("create_time"),
                    rs.getTimestamp("update_time"),
                    rs.getInt("delete_state"), rs.getInt("ad_put_state"),
                    rs.getDouble("consumption_amount"),
                    rs.getInt("open_state_plan_amount"),
                    rs.getInt("count_plan_amount"),rs.getLong("click_num"),rs.getLong("exposure_num"));
        }
    }

    //策略映射
    public static final class SspPlanRowMapper implements RowMapper<SspPlan> {
        // 结果集封装
        @Override
        public SspPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long accountId = rs.getLong("account_id");
            User user = null;
            if (accountId != null) {
                user = new User();
                user.setId(accountId);
            }

            SspAdvertiser sspAdvertiser = null;
            Long advertiserId = rs.getLong("advertiser_id");
            if (advertiserId != null) {
                sspAdvertiser = new SspAdvertiser();
                sspAdvertiser.setId(advertiserId);
            }

            SspOrder sspOrder = null;
            Long orderId = rs.getLong("order_id");
            if (orderId != null) {
                sspOrder = new SspOrder();
                sspOrder.setId(orderId);
            }
            SspPlan parentPlan = null;
            Long parentPlanId = rs.getLong("parent_plan_id");
            if (parentPlanId != null) {
                parentPlan = new SspPlan();
                parentPlan.setId(parentPlanId);
            }
            SspPlanTargetCondition sspPlanTargetCondition = null;
            Long planTargetConditionId = rs.getLong("plan_target_condition_id");
            if (planTargetConditionId != null) {
                sspPlanTargetCondition = new SspPlanTargetCondition();
                sspPlanTargetCondition.setId(planTargetConditionId);
            }
            return new SspPlan(rs.getLong("id"), user, sspAdvertiser, sspOrder,
                    rs.getString("name"),
                    rs.getString("plan_identifying"), rs.getLong("plan_type"),
                    rs.getLong("put_time_state"),
                    rs.getTimestamp("put_start_time"),
                    rs.getTimestamp("put_end_time"), rs.getLong("budget_state"),
                    rs.getDouble("budget"), rs.getString("contract_id"),
                    rs.getLong("single_period_show_times"),
                    rs.getInt("show_calculation_type"),
                    rs.getLong("single_show_times"),
                    rs.getInt("click_calculation_type"),
                    rs.getLong("single_click_times"),
                    rs.getDouble("daily_budget_cap"),
                    rs.getLong("daily_show_cap"), rs.getLong("daily_click_cap"),
                    rs.getInt("put_rhythm"),
                    rs.getInt("kpi"), rs.getDouble("highest_cpm"),
                    rs.getString("keywords"), rs.getString("comment"),
                    rs.getInt("is_plan_group"),
                    parentPlan, sspPlanTargetCondition,
                    rs.getTimestamp("create_time"),
                    rs.getTimestamp("update_time"),
                    rs.getInt("delete_state"), rs.getInt("level"),
                    rs.getInt("state"), rs.getInt("children_num"),
                    rs.getInt("child_state_count"),
                    rs.getDouble("consumption_amount"));
        }
    }


    // user映射
    public final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<Role> roleList = new ArrayList<>();
            return new User(rs.getLong("id"), rs.getString("login_name"),
                    rs.getString("login_password"),
                    rs.getString("username"), rs.getString("phone"), rs.getDouble("balance"),
                    rs.getString("email"), rs.getInt("state"),
                    rs.getInt("is_delete"), rs.getTimestamp("update_time"),
                    rs.getTimestamp("create_time"),
                    rs.getString("remark"), rs.getInt("user_type"), roleList,
                    rs.getInt("is_verify"), rs.getString("contact"),
                    rs.getLong("parent_id"),
                    rs.getTimestamp("register_time"),
                    rs.getString("validata_code"));
        }
    }

    // 创意映射
    public final class SspCreativeRowMapper implements RowMapper<SspCreative> {

        @Override
        public SspCreative mapRow(ResultSet rs, int rowNum)
                throws SQLException {

            SspAdvertiser spAdvertiser = new SspAdvertiser();
            User user = new User();
            SspDictLabel[] sspDictLabel = new SspDictLabel[1];
            return new SspCreative(rs.getLong("id"), user,
                    spAdvertiser, rs.getInt("creative_type"),
                    rs.getString("creative_name"), sspDictLabel,
                    rs.getInt("creative_state"),
                    rs.getString("creative_label_new"),
                    rs.getString("creative_size"),
                    rs.getString("pic_name"), rs.getDouble("pic_size"),
                    rs.getString("creative_format"),
                    rs.getLong("video_duration"), rs.getString("creative_url"),
                    rs.getString("creative_click_address"),
                    rs.getString("creative_monitor_address"),
                    rs.getString("adv_title"), rs.getTimestamp("create_time"),
                    rs.getTimestamp("update_time"), rs.getInt("height"),
                    rs.getInt("width"),
                    rs.getString("creative_source"), rs.getInt("delete_state"));
        }

    }

    public final class SspAdvertiserRowMapper implements RowMapper<SspAdvertiser> {

        @Override
        public SspAdvertiser mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SspAdvertiser(rs.getLong("id"), new User(), rs.getString("advertiser_identifying"),
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


}
