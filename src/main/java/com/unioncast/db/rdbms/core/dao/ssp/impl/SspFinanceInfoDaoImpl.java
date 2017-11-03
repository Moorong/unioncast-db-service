package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.common.ssp.model.SspFinanceInfo;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspFinanceInfoDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.UserService;
import com.unioncast.db.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sspFinanceInfoDao")
public class SspFinanceInfoDaoImpl extends SspGeneralDao<SspFinanceInfo, Long> implements SspFinanceInfoDao {

    private static String FIND_ALL = SqlBuild.select(SspFinanceInfo.TABLE_NAME,
            SspFinanceInfo.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspFinanceInfo.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspFinanceInfo.TABLE_NAME);

    @Override
    public SspFinanceInfo[] find(Long id) throws DaoException {
        List<SspFinanceInfo> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspFinanceInfoDaoImpl.SspFinanceInfoRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspFinanceInfoDaoImpl.SspFinanceInfoRowMapper());
        }

        return list.toArray(new SspFinanceInfo[]{});
    }

    @Override
    public SspFinanceInfo[] findT(SspFinanceInfo s) throws DaoException, IllegalAccessException {
        List<SspFinanceInfo> list = new ArrayList<>();
        if (s != null) {
            SspFinanceInfo[] sspOrders = find(s, new SspFinanceInfoRowMapper(), SspFinanceInfo.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspFinanceInfoRowMapper());
        }
        return list.toArray(new SspFinanceInfo[]{});
    }
    @Override
    public SspFinanceInfo findById(Long id) throws DaoException {
        if(null!=id){
            SspFinanceInfo[] sspOrders = find(id);
            if(null!=sspOrders&&sspOrders.length>0)
                return sspOrders[0];
        }
        return null;
    }
    @Override
    public int updateAndReturnNum(SspFinanceInfo entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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

                        } else if (myColumn.value().equals("city_info_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspCityInfo().getId());
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
    public Pagination<SspFinanceInfo> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspFinanceInfo.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspFinanceInfoDaoImpl.SspFinanceInfoRowMapper(), "account_id", userId);

    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspFinanceInfo.class, ids);
    }


    @Override
    public Long save(SspFinanceInfo entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

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
                        } else if (myColumn.value().equals("city_info_id")) {
                            args.put("city_info_id", entity.getSspCityInfo() == null ? null : entity.getSspCityInfo().getId());
                        } else {
                            args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                        }
                    }
                }
            }
        }
        return jdbcInsert.executeAndReturnKey(args).longValue();
    }


    public static final class SspFinanceInfoRowMapper implements RowMapper<SspFinanceInfo> {
        @Override
        public SspFinanceInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
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

            Long advertiserId = rs.getLong("city_info_id");
            SspCityInfo sspAdvertiser = null;
            if (advertiserId != null) {
                sspAdvertiser = new SspCityInfo();
                sspAdvertiser.setId(advertiserId);
            }


            return new SspFinanceInfo(rs.getLong("id"), rs.getInt("finance_type"), sspAdvertiser, rs.getString("recipient_bank"), rs.getString("bank_account"), rs.getInt("invoice_type"), rs.getString("tax_num"), rs.getString("recipient_name"), rs.getString("recipient_identity_num"), rs.getTimestamp("create_time"), rs.getTimestamp("update_time"), rs.getInt("state"), user, rs.getInt("delete_state"));
        }
    }
}
