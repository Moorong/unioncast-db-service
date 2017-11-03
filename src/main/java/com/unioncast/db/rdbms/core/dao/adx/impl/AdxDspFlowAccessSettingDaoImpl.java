package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspFlowAccessSetting;
import com.unioncast.common.adx.model.AdxDspPanter;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspFlowAccessSettingDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspFlowAccessSettingDao")
public class AdxDspFlowAccessSettingDaoImpl extends AdxGeneralDao<AdxDspFlowAccessSetting, Long>
        implements AdxDspFlowAccessSettingDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspFlowAccessSetting.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspFlowAccessSetting.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspFlowAccessSetting.TABLE_NAME,
            AdxDspFlowAccessSetting.PROPERTIES);
    public static final String FIND_DSP_ID = "SELECT `dsp_id` FROM `adx_dsp_flow_access_setting` WHERE `flow_type`= ";
    public static final String FIND_ALL_FROM_ADXDSPPANTER = SqlBuild.select(AdxDspPanter.TABLE_NAME,
            AdxDspPanter.PROPERTIES);


    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspFlowAccessSetting.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspFlowAccessSetting.TABLE_NAME + "(" + AdxDspFlowAccessSetting.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspFlowAccessSetting.TABLE_NAME);


    public Pagination<AdxDspFlowAccessSetting> page(PageCriteria pageCriteria) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringSearch = new StringBuilder();
        List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        String predicate = pageCriteria.getPredicate().getOperator();

        stringBuilder.append(FIND_ALL);
        if (searchExpressionList != null) {
            if (searchExpressionList.size() >= 1 && predicate != null) {
                stringSearch.append(" where ");
                for (int i = 0; i < searchExpressionList.size() - 1; i++) {
                    if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
                        stringSearch.append(searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator() + " '%" + searchExpressionList.get(i).getValue() + "%' " + predicate + " ");
                    } else {
                        stringSearch.append(searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator() + " '" + searchExpressionList.get(i).getValue() + "' " + predicate + " ");
                    }
                }
                if ("like".equalsIgnoreCase(searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator())) {
                    stringSearch.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " " + searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator() + " '%" + searchExpressionList.get(searchExpressionList.size() - 1).getValue() + "%' ");

                } else {

                    stringSearch.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " " + searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator() + " '" + searchExpressionList.get(searchExpressionList.size() - 1).getValue() + "' ");
                }
            } else if (searchExpressionList.get(0) != null) {
                if ("like".equalsIgnoreCase(searchExpressionList.get(0).getOperation().getOperator())) {
                    stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " " + searchExpressionList.get(0).getOperation().getOperator() + " '%" + searchExpressionList.get(0).getValue() + "%' ");
                } else {
                    stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " " + searchExpressionList.get(0).getOperation().getOperator() + " '" + searchExpressionList.get(0).getValue() + "' ");
                }
            }
        }

        int totalCount = jdbcTemplate.queryForObject(COUNT_ALL + " " + stringSearch.toString(), int.class);
        Pagination<AdxDspFlowAccessSetting> pagination = new Pagination<AdxDspFlowAccessSetting>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
        Integer currentPageNo = pagination.getCurrentPageNo();
        Integer pageSize = pagination.getPageSize();
        stringBuilder.append(stringSearch);

        if (orderExpressionList != null) {
            stringBuilder.append(" ORDER BY ");
            for (int i = 0; i < orderExpressionList.size() - 1; i++) {
                stringBuilder.append(orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp() + ", ");
            }
            stringBuilder.append(orderExpressionList.get(orderExpressionList.size() - 1).getPropertyName() + " " + orderExpressionList.get(orderExpressionList.size() - 1).getOp());
        }

        if (currentPageNo != null && pageSize != null) {
            Integer start = (currentPageNo - 1) * pageSize;
            stringBuilder.append(" limit " + start + "," + pageSize);
        }

        List<AdxDspFlowAccessSetting> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspFlowAccessSettingRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspFlowAccessSetting[]{}));

        return pagination;
    }


    @Override
    public AdxDspFlowAccessSetting[] findByDspIds(Long[] ids) {
        List<AdxDspFlowAccessSetting> list = new ArrayList<>();
        if (ids != null && ids.length != 0) {
            for (Long id : ids) {
                StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
                stringBuffer.append(" where dsp_id = ?");
                list.addAll(jdbcTemplate.query(stringBuffer.toString(),
                        new AdxDspFlowAccessSettingRowMapper(), id));
            }
            return list.toArray(new AdxDspFlowAccessSetting[list.size()]);
        }
        list.addAll(jdbcTemplate.query(FIND_ALL,
                new AdxDspFlowAccessSettingRowMapper()));
        return list.toArray(new AdxDspFlowAccessSetting[list.size()]);
    }

    /**
     * 查找
     */
    @Override
    public AdxDspFlowAccessSetting[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspFlowAccessSetting> list = jdbcTemplate.query(FIND_ALL, new AdxDspFlowAccessSettingRowMapper());
            return list.toArray(new AdxDspFlowAccessSetting[list.size()]);

        }
        AdxDspFlowAccessSetting adxDspFlowAccessSetting = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspFlowAccessSettingRowMapper(), id);
        AdxDspFlowAccessSetting[] adxDspFlowAccessSettings = new AdxDspFlowAccessSetting[]{adxDspFlowAccessSetting};
        return adxDspFlowAccessSettings;
    }


    @Override
    public Long[] batchAdd(AdxDspFlowAccessSetting[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspFlowAccessSetting adxDspFlowAccessSetting : entitys) {
                pstmt.setString(1, adxDspFlowAccessSetting.getTimeInterval());
                pstmt.setString(2, adxDspFlowAccessSetting.getAdvertisingPosition());
                pstmt.setString(3, adxDspFlowAccessSetting.getAdvertisingSize());
                pstmt.setString(4, adxDspFlowAccessSetting.getPageType());
                pstmt.setString(5, adxDspFlowAccessSetting.getWebShield());
                pstmt.setString(6, adxDspFlowAccessSetting.getRegion());
                pstmt.setString(7, adxDspFlowAccessSetting.getInterest());
                pstmt.setString(8, adxDspFlowAccessSetting.getEquipment());
                pstmt.setString(9, adxDspFlowAccessSetting.getCookie());
                pstmt.setString(10, adxDspFlowAccessSetting.getAdvertisingType());
                pstmt.setString(11, adxDspFlowAccessSetting.getAppType());
                pstmt.setString(12, adxDspFlowAccessSetting.getChannel());
                pstmt.setString(13, adxDspFlowAccessSetting.getRemarks());
                if (adxDspFlowAccessSetting.getFlowType() != null)
                    pstmt.setInt(14, adxDspFlowAccessSetting.getFlowType());
                else
                    pstmt.setNull(14, Types.INTEGER);
                if (adxDspFlowAccessSetting.getDspId() != null)
                    pstmt.setLong(15, adxDspFlowAccessSetting.getDspId());
                else
                    pstmt.setNull(15, Types.BIGINT);
                pstmt.setTimestamp(16, new Timestamp(System.currentTimeMillis()));
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
    public int batchDelete(Long[] ids) throws DaoException {
        List<Object[]> batchArgs = new ArrayList<>();
        for (Long id : ids) {
            Object[] objects = new Object[]{id};
            batchArgs.add(objects);
        }
        int[] intArray = jdbcTemplate.batchUpdate(BATCH_DELETE, batchArgs);
        int j = 0;
        for (int i : intArray) {
            j += i;
        }
        return j;
    }


    @Override
    public AdxDspFlowAccessSetting findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspFlowAccessSetting) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxDspFlowAccessSettingRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspFlowAccessSetting entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getTimeInterval() != null) {
            stringBuffer.append(",time_interval = ?");
            args.add(entity.getTimeInterval());
        }
        if (entity.getAdvertisingPosition() != null) {
            stringBuffer.append(",advertising_position = ?");
            args.add(entity.getAdvertisingPosition());
        }
        if (entity.getAdvertisingSize() != null) {
            stringBuffer.append(",advertising_size = ?");
            args.add(entity.getAdvertisingSize());
        }
        if (entity.getPageType() != null) {
            stringBuffer.append(",page_type = ?");
            args.add(entity.getPageType());
        }
        if (entity.getWebShield() != null) {
            stringBuffer.append(",web_shield = ?");
            args.add(entity.getWebShield());
        }
        if (entity.getRegion() != null) {
            stringBuffer.append(",region = ?");
            args.add(entity.getRegion());
        }
        if (entity.getInterest() != null) {
            stringBuffer.append(",interest = ?");
            args.add(entity.getInterest());
        }
        if (entity.getEquipment() != null) {
            stringBuffer.append(",equipment = ?");
            args.add(entity.getEquipment());
        }
        if (entity.getCookie() != null) {
            stringBuffer.append(",cookie = ?");
            args.add(entity.getCookie());
        }
        if (entity.getAdvertisingType() != null) {
            stringBuffer.append(",advertising_type = ?");
            args.add(entity.getAdvertisingType());
        }
        if (entity.getAppType() != null) {
            stringBuffer.append(",app_type = ?");
            args.add(entity.getAppType());
        }
        if (entity.getChannel() != null) {
            stringBuffer.append(",channel = ?");
            args.add(entity.getChannel());
        }
        if (entity.getRemarks() != null) {
            stringBuffer.append(",remarks = ?");
            args.add(entity.getRemarks());
        }
        if (entity.getFlowType() != null) {
            stringBuffer.append(",flow_type = ?");
            args.add(entity.getFlowType());
        }
        if (entity.getDspId() != null) {
            stringBuffer.append(",dsp_id = ?");
            args.add(entity.getDspId());
        }
        stringBuffer.append(" where id = ?");
        args.add(entity.getId());
        jdbcTemplate.update(stringBuffer.toString(), args.toArray());
    }

    @Override
    public Long save(AdxDspFlowAccessSetting entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public AdxDspFlowAccessSetting[] findByIds(Long[] ids) throws DaoException {
        List<AdxDspFlowAccessSetting> list = new ArrayList<>();
        for (Long id : ids) {
            StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
            stringBuffer.append(" where id = ?");
            list.add((AdxDspFlowAccessSetting) jdbcTemplate.queryForObject(stringBuffer.toString(),
                    new AdxDspFlowAccessSettingRowMapper(), id));

        }
        return list.toArray(new AdxDspFlowAccessSetting[list.size()]);

    }

//    @Override
//    public AdxDspPanter[] findAdxDspPanterByIdstype(Long flowType, Long[] longs) throws DaoException {
//        List<AdxDspPanter> list = new ArrayList<>();
//        List<Long> listIds = jdbcTemplate.queryForList(FIND_DSP_ID + flowType, Long.class);
//        List<Long> ids = CollectionUtil.intersection(listIds, Arrays.asList(longs));
//        HashSet<Long> hs = new HashSet<Long>(ids);
//        List<Long> listId = new ArrayList<>(hs);
//        System.out.println(listId);
//        for (int i = 0; i < listId.size(); i++) {
//            StringBuffer stringBuffer = new StringBuffer(FIND_ALL_FROM_ADXDSPPANTER);
//            stringBuffer.append(" where dsp_id = ? and status = 1");
//            list.addAll(jdbcTemplate.query(stringBuffer.toString(),
//                    new AdxDspPanterRowMapper(), listId.get(i)));
//
//        }
//        return list.toArray(new AdxDspPanter[list.size()]);
//    }

    private Long insertAndReturnId(AdxDspFlowAccessSetting adxDspFlowAccessSetting) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AdxDspFlowAccessSetting.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("time_interval",
                adxDspFlowAccessSetting.getTimeInterval() == null ? null : adxDspFlowAccessSetting.getTimeInterval());
        args.put("advertising_position", adxDspFlowAccessSetting.getAdvertisingPosition() == null ? null
                : adxDspFlowAccessSetting.getAdvertisingPosition());
        args.put("advertising_size", adxDspFlowAccessSetting.getAdvertisingSize() == null ? null
                : adxDspFlowAccessSetting.getAdvertisingSize());
        args.put("page_type",
                adxDspFlowAccessSetting.getPageType() == null ? null : adxDspFlowAccessSetting.getPageType());
        args.put("web_shield",
                adxDspFlowAccessSetting.getWebShield() == null ? null : adxDspFlowAccessSetting.getWebShield());
        args.put("region", adxDspFlowAccessSetting.getRegion() == null ? null : adxDspFlowAccessSetting.getRegion());
        args.put("interest",
                adxDspFlowAccessSetting.getInterest() == null ? null : adxDspFlowAccessSetting.getInterest());
        args.put("equipment",
                adxDspFlowAccessSetting.getEquipment() == null ? null : adxDspFlowAccessSetting.getEquipment());
        args.put("cookie", adxDspFlowAccessSetting.getCookie() == null ? null : adxDspFlowAccessSetting.getCookie());
        args.put("advertising_type", adxDspFlowAccessSetting.getAdvertisingType() == null ? null
                : adxDspFlowAccessSetting.getAdvertisingType());
        args.put("app_type",
                adxDspFlowAccessSetting.getAppType() == null ? null : adxDspFlowAccessSetting.getAppType());
        args.put("channel", adxDspFlowAccessSetting.getChannel() == null ? null : adxDspFlowAccessSetting.getChannel());
        args.put("remarks", adxDspFlowAccessSetting.getRemarks() == null ? null : adxDspFlowAccessSetting.getRemarks());
        args.put("flow_type",
                adxDspFlowAccessSetting.getFlowType() == null ? null : adxDspFlowAccessSetting.getFlowType());
        args.put("dsp_id", adxDspFlowAccessSetting.getDspId() == null ? null : adxDspFlowAccessSetting.getDspId());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspFlowAccessSettingRowMapper implements RowMapper<AdxDspFlowAccessSetting> {
        public AdxDspFlowAccessSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspFlowAccessSetting adxDspFlowAccessSetting = new AdxDspFlowAccessSetting();
            adxDspFlowAccessSetting.setId(rs.getLong("id"));
            adxDspFlowAccessSetting.setTimeInterval(rs.getString("time_interval"));
            adxDspFlowAccessSetting.setAdvertisingPosition(rs.getString("advertising_position"));
            adxDspFlowAccessSetting.setAdvertisingSize(rs.getString("advertising_size"));
            adxDspFlowAccessSetting.setPageType(rs.getString("page_type"));
            adxDspFlowAccessSetting.setWebShield(rs.getString("web_shield"));
            adxDspFlowAccessSetting.setRegion(rs.getString("region"));
            adxDspFlowAccessSetting.setInterest(rs.getString("interest"));
            adxDspFlowAccessSetting.setEquipment(rs.getString("equipment"));
            adxDspFlowAccessSetting.setCookie(rs.getString("cookie"));
            adxDspFlowAccessSetting.setAdvertisingType(rs.getString("advertising_type"));
            adxDspFlowAccessSetting.setAppType(rs.getString("app_type"));
            adxDspFlowAccessSetting.setChannel(rs.getString("channel"));
            adxDspFlowAccessSetting.setRemarks(rs.getString("remarks"));
            adxDspFlowAccessSetting.setFlowType(rs.getInt("flow_type"));
            adxDspFlowAccessSetting.setDspId(rs.getLong("dsp_id"));
            adxDspFlowAccessSetting.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspFlowAccessSetting;
        }
    }

    private final class AdxDspPanterRowMapper implements RowMapper<AdxDspPanter> {
        public AdxDspPanter mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspPanter adxDspPanter = new AdxDspPanter();
            adxDspPanter.setId(rs.getLong("id"));
            adxDspPanter.setDspId(rs.getLong("dsp_id"));
            adxDspPanter.setName(rs.getString("name"));
            adxDspPanter.setBiddingUrl(rs.getString("bidding_url"));
            adxDspPanter.setMaxFlowPSecond(rs.getInt("max_flow_psecond"));
            adxDspPanter.setStatus(rs.getInt("status"));
            adxDspPanter.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspPanter;
        }
    }


}
