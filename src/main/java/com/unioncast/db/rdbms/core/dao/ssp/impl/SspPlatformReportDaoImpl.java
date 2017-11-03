package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.*;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlatformReportDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
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

@Repository("sspPlatformReportDao")
public class SspPlatformReportDaoImpl extends SspGeneralDao<SspPlatformReport, Long> implements SspPlatformReportDao {

    private static String FIND_ALL = SqlBuild.select(SspPlatformReport.TABLE_NAME,
            SspPlatformReport.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspPlatformReport.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspPlatformReport.TABLE_NAME);

    @Override
    public SspPlatformReport[] find(Long id) throws DaoException {
        List<SspPlatformReport> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspPlatformReportDaoImpl.SspPlatformReportRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspPlatformReportDaoImpl.SspPlatformReportRowMapper());
        }

        return list.toArray(new SspPlatformReport[]{});
    }

    @Override
    public SspPlatformReport[] findT(SspPlatformReport s) throws DaoException, IllegalAccessException {
        List<SspPlatformReport> list = new ArrayList<>();
        if (s != null) {
            SspPlatformReport[] sspOrders = find(s, new SspPlatformReportRowMapper(), SspPlatformReport.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspPlatformReportRowMapper());
        }
        return list.toArray(new SspPlatformReport[]{});
    }

    @Override
    public int updateAndReturnNum(SspPlatformReport entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        if (myColumn.value().equals("advertiser_id")) {
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
                        } else if (myColumn.value().equals("plan_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspPlan().getId());
                        } else if (myColumn.value().equals("creative_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspCreative().getId());
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
    public Pagination<SspPlatformReport> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspPlatformReport.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspPlatformReportDaoImpl.SspPlatformReportRowMapper(), "account_id", userId);

    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspPlatformReport.class, ids);
    }


    @Override
    public Long save(SspPlatformReport entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

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
                            args.put("advertiser_id", entity.getSspAdvertiser() == null ? null : entity.getSspAdvertiser().getId());
                        } else if (myColumn.value().equals("city_info_id")) {
                            args.put("order_id", entity.getSspOrder() == null ? null : entity.getSspOrder().getId());
                        } else if (myColumn.value().equals("city_info_id")) {
                            args.put("plan_id", entity.getSspPlan() == null ? null : entity.getSspPlan().getId());
                        } else if (myColumn.value().equals("city_info_id")) {
                            args.put("creative_id", entity.getSspCreative() == null ? null : entity.getSspCreative().getId());
                        } else {
                            args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                        }
                    }
                }
            }
        }
        return jdbcInsert.executeAndReturnKey(args).longValue();
    }


    public static final class SspPlatformReportRowMapper implements RowMapper<SspPlatformReport> {
        @Override
        public SspPlatformReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long vertiser = rs.getLong("advertiser_id");
            SspAdvertiser spAdvertiser = null;
            if (vertiser != null) {
                spAdvertiser = new SspAdvertiser();
                spAdvertiser.setId(vertiser);
            }

            Long rder = rs.getLong("order_id");
            SspOrder spOrder = null;
            if (rder != null) {
                spOrder = new SspOrder();
                spOrder.setId(rder);
            }

            Long lan = rs.getLong("plan_id");
            SspPlan spPlan = null;
            if (lan != null) {
                spPlan = new SspPlan();
                spPlan.setId(lan);
            }

            Long ative = rs.getLong("creative_id");
            SspCreative spCreative = null;
            if (ative != null) {
                spCreative = new SspCreative();
                spCreative.setId(ative);
            }

            return new SspPlatformReport(rs.getLong("id"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"), rs.getString("platform"), spAdvertiser, spOrder, spPlan, spCreative, rs.getLong("show_times"), rs.getDouble("income"), rs.getLong("cpm"), rs.getLong("click_times"), rs.getDouble("click_ratio"), rs.getLong("cpc"), rs.getLong("arrival"), rs.getDouble("transform"), rs.getLong("cpa"), rs.getLong("second_jump"), rs.getDouble("second_jump_ratio"), rs.getDouble("arrival_ratio"), rs.getLong("click_transform"), rs.getDouble("click_transform_ratio"), rs.getTimestamp("update_time"));
        }
    }
}
