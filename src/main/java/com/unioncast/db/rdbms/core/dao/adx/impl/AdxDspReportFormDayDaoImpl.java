package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspReportFormDay;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspReportFormDayDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspReportFormDayDao")
public class AdxDspReportFormDayDaoImpl extends AdxGeneralDao<AdxDspReportFormDay, Long>
        implements AdxDspReportFormDayDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspReportFormDay.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspReportFormDay.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspReportFormDay.TABLE_NAME,
            AdxDspReportFormDay.PROPERTIES);
    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspReportFormDay.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspReportFormDay.TABLE_NAME + "(" + AdxDspReportFormDay.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?,?,?,?)";


    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspReportFormDay.TABLE_NAME);


    public Pagination<AdxDspReportFormDay> page(PageCriteria pageCriteria) {
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
        Pagination<AdxDspReportFormDay> pagination = new Pagination<AdxDspReportFormDay>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<AdxDspReportFormDay> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspReportFormDayRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspReportFormDay[]{}));

        return pagination;
    }


    /**
     * 查找
     */
    @Override
    public AdxDspReportFormDay[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspReportFormDay> list = jdbcTemplate.query(FIND_ALL, new AdxDspReportFormDayRowMapper());
            return list.toArray(new AdxDspReportFormDay[list.size()]);

        }
        AdxDspReportFormDay adxDspReportFormDay = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspReportFormDayRowMapper(), id);
        AdxDspReportFormDay[] adxDspReportFormDays = new AdxDspReportFormDay[]{adxDspReportFormDay};
        return adxDspReportFormDays;
    }


    @Override
    public Long[] batchAdd(AdxDspReportFormDay[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspReportFormDay adxDspReportFormDay : entitys) {
                if (adxDspReportFormDay.getFlowType() != null)
                    pstmt.setLong(1, adxDspReportFormDay.getFlowType());
                else
                    pstmt.setNull(1, Types.BIGINT);
                pstmt.setString(2, adxDspReportFormDay.getSize());
                pstmt.setString(3, adxDspReportFormDay.getShowNum());
                pstmt.setString(4, adxDspReportFormDay.getClickNum());
                if (adxDspReportFormDay.getClickRate() != null)
                    pstmt.setDouble(5, adxDspReportFormDay.getClickRate());
                else
                    pstmt.setNull(5, Types.DOUBLE);
                if (adxDspReportFormDay.getThousandShowPrice() != null)
                    pstmt.setDouble(6, adxDspReportFormDay.getThousandShowPrice());
                else
                    pstmt.setNull(6, Types.DOUBLE);
                pstmt.setString(7, adxDspReportFormDay.getAdvertisers());
                pstmt.setString(8, adxDspReportFormDay.getOriginality());
                if (adxDspReportFormDay.getUserId() != null)
                    pstmt.setLong(9, adxDspReportFormDay.getUserId());
                else
                    pstmt.setNull(9, Types.BIGINT);
                if (adxDspReportFormDay.getDate() != null)
                    pstmt.setTimestamp(10, new Timestamp(adxDspReportFormDay.getDate().getTime()));
                else
                    pstmt.setNull(10, Types.TIMESTAMP);

                pstmt.setString(11, adxDspReportFormDay.getRemarks());
                pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
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
    public AdxDspReportFormDay findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspReportFormDay) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxDspReportFormDayRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspReportFormDay entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getFlowType() != null) {
            stringBuffer.append(",flow_type = ?");
            args.add(entity.getFlowType());
        }
        if (entity.getSize() != null) {
            stringBuffer.append(",size = ?");
            args.add(entity.getSize());
        }
        if (entity.getShowNum() != null) {
            stringBuffer.append(",show_num = ?");
            args.add(entity.getShowNum());
        }
        if (entity.getClickNum() != null) {
            stringBuffer.append(",click_num = ?");
            args.add(entity.getClickNum());
        }
        if (entity.getClickRate() != null) {
            stringBuffer.append(",click_rate = ?");
            args.add(entity.getClickRate());
        }
        if (entity.getThousandShowPrice() != null) {
            stringBuffer.append(",thousand_show_price = ?");
            args.add(entity.getThousandShowPrice());
        }
        if (entity.getAdvertisers() != null) {
            stringBuffer.append(",advertisers = ?");
            args.add(entity.getAdvertisers());
        }
        if (entity.getOriginality() != null) {
            stringBuffer.append(",originality = ?");
            args.add(entity.getOriginality());
        }
        if (entity.getUserId() != null) {
            stringBuffer.append(",user_id = ?");
            args.add(entity.getUserId());
        }
        if (entity.getDate() != null) {
            stringBuffer.append(",date = ?");
            args.add(entity.getDate());
        }
        if (entity.getRemarks() != null) {
            stringBuffer.append(",remarks = ?");
            args.add(entity.getRemarks());
        }
        stringBuffer.append(" where id = ?");
        args.add(entity.getId());
        jdbcTemplate.update(stringBuffer.toString(), args.toArray());
    }

    @Override
    public Long save(AdxDspReportFormDay entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private Long insertAndReturnId(AdxDspReportFormDay adxDspReportFormDay) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AdxDspReportFormDay.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("flow_type", adxDspReportFormDay.getFlowType() == null ? null : adxDspReportFormDay.getFlowType());
        args.put("size", adxDspReportFormDay.getSize() == null ? null : adxDspReportFormDay.getSize());
        args.put("show_num", adxDspReportFormDay.getShowNum() == null ? null : adxDspReportFormDay.getShowNum());
        args.put("click_num", adxDspReportFormDay.getClickNum() == null ? null : adxDspReportFormDay.getClickNum());
        args.put("click_rate", adxDspReportFormDay.getClickRate() == null ? null : adxDspReportFormDay.getClickRate());
        args.put("thousand_show_price",
                adxDspReportFormDay.getThousandShowPrice() == null ? null : adxDspReportFormDay.getThousandShowPrice());
        args.put("advertisers",
                adxDspReportFormDay.getAdvertisers() == null ? null : adxDspReportFormDay.getAdvertisers());
        args.put("originality",
                adxDspReportFormDay.getOriginality() == null ? null : adxDspReportFormDay.getOriginality());
        args.put("user_id", adxDspReportFormDay.getUserId() == null ? null : adxDspReportFormDay.getUserId());
        args.put("date", adxDspReportFormDay.getDate() == null ? null : adxDspReportFormDay.getDate());
        args.put("remarks", adxDspReportFormDay.getRemarks() == null ? null : adxDspReportFormDay.getRemarks());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspReportFormDayRowMapper implements RowMapper<AdxDspReportFormDay> {
        public AdxDspReportFormDay mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspReportFormDay adxDspReportFormDay = new AdxDspReportFormDay();
            adxDspReportFormDay.setId(rs.getLong("id"));
            adxDspReportFormDay.setFlowType(rs.getInt("flow_type"));
            adxDspReportFormDay.setSize(rs.getString("size"));
            adxDspReportFormDay.setShowNum(rs.getString("show_num"));
            adxDspReportFormDay.setClickNum(rs.getString("click_num"));
            adxDspReportFormDay.setClickRate(rs.getDouble("click_rate"));
            adxDspReportFormDay.setThousandShowPrice(rs.getDouble("thousand_show_price"));
            adxDspReportFormDay.setAdvertisers(rs.getString("advertisers"));
            adxDspReportFormDay.setOriginality(rs.getString("originality"));
            adxDspReportFormDay.setUserId(rs.getLong("user_id"));
            adxDspReportFormDay.setDate(rs.getTimestamp("date"));
            adxDspReportFormDay.setRemarks(rs.getString("remarks"));
            adxDspReportFormDay.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspReportFormDay;
        }
    }

}
