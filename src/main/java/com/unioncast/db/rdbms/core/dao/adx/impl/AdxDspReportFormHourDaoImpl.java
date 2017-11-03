package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspReportFormHour;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspReportFormHourDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspReportFormHourDao")
public class AdxDspReportFormHourDaoImpl extends AdxGeneralDao<AdxDspReportFormHour, Long>
        implements AdxDspReportFormHourDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspReportFormHour.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspReportFormHour.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspReportFormHour.TABLE_NAME,
            AdxDspReportFormHour.PROPERTIES);

    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspReportFormHour.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspReportFormHour.TABLE_NAME + "(" + AdxDspReportFormHour.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspReportFormHour.TABLE_NAME);



    public Pagination<AdxDspReportFormHour> page(PageCriteria pageCriteria) {
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
        Pagination<AdxDspReportFormHour> pagination = new Pagination<AdxDspReportFormHour>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<AdxDspReportFormHour> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspReportFormHourRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspReportFormHour[]{}));

        return pagination;
    }


    /**
     * 查找
     */
    @Override
    public AdxDspReportFormHour[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspReportFormHour> list = jdbcTemplate.query(FIND_ALL, new AdxDspReportFormHourRowMapper());
            return list.toArray(new AdxDspReportFormHour[list.size()]);

        }
        AdxDspReportFormHour adxDspReportFormHour = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspReportFormHourRowMapper(), id);
        AdxDspReportFormHour[] adxDspReportFormHours = new AdxDspReportFormHour[]{adxDspReportFormHour};
        return adxDspReportFormHours;
    }


    @Override
    public Long[] batchAdd(AdxDspReportFormHour[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspReportFormHour adxDspReportFormHour : entitys) {
                if (adxDspReportFormHour.getFlowType() != null)
                    pstmt.setLong(1, adxDspReportFormHour.getFlowType());
                else
                    pstmt.setNull(1, Types.BIGINT);
                pstmt.setString(2, adxDspReportFormHour.getSize());
                if (adxDspReportFormHour.getThousandShowPrice() != null)
                    pstmt.setDouble(3, adxDspReportFormHour.getThousandShowPrice());
                else
                    pstmt.setNull(3, Types.DOUBLE);
                pstmt.setString(4, adxDspReportFormHour.getClickNum());
                if (adxDspReportFormHour.getClickRate() != null)
                    pstmt.setDouble(5, adxDspReportFormHour.getClickRate());
                else
                    pstmt.setNull(5, Types.DOUBLE);
                if (adxDspReportFormHour.getThousandShowPrice() != null)
                    pstmt.setDouble(6, adxDspReportFormHour.getThousandShowPrice());
                else
                    pstmt.setNull(6, Types.DOUBLE);
                pstmt.setString(7, adxDspReportFormHour.getAdvertisers());
                pstmt.setString(8, adxDspReportFormHour.getOriginality());
                if (adxDspReportFormHour.getUserId() != null)
                    pstmt.setLong(9, adxDspReportFormHour.getUserId());
                else
                    pstmt.setNull(9, Types.BIGINT);
                if (adxDspReportFormHour.getDateTime() != null)
                    pstmt.setTimestamp(10, new Timestamp(adxDspReportFormHour.getDateTime().getTime()));
                else
                    pstmt.setNull(10, Types.TIMESTAMP);

                pstmt.setString(11, adxDspReportFormHour.getRemarks());
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
    public AdxDspReportFormHour findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspReportFormHour) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxDspReportFormHourRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspReportFormHour entity) throws DaoException {
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
        if (entity.getDateTime() != null) {
            stringBuffer.append(",date_time = ?");
            args.add(entity.getDateTime());
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
    public Long save(AdxDspReportFormHour entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private Long insertAndReturnId(AdxDspReportFormHour adxDspReportFormHour) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AdxDspReportFormHour.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("flow_type", adxDspReportFormHour.getFlowType() == null ? null : adxDspReportFormHour.getFlowType());
        args.put("size", adxDspReportFormHour.getSize() == null ? null : adxDspReportFormHour.getSize());
        args.put("show_num", adxDspReportFormHour.getShowNum() == null ? null : adxDspReportFormHour.getShowNum());
        args.put("click_num", adxDspReportFormHour.getClickNum() == null ? null : adxDspReportFormHour.getClickNum());
        args.put("click_rate",
                adxDspReportFormHour.getClickRate() == null ? null : adxDspReportFormHour.getClickRate());
        args.put("thousand_show_price", adxDspReportFormHour.getThousandShowPrice() == null ? null
                : adxDspReportFormHour.getThousandShowPrice());
        args.put("advertisers",
                adxDspReportFormHour.getAdvertisers() == null ? null : adxDspReportFormHour.getAdvertisers());
        args.put("originality",
                adxDspReportFormHour.getOriginality() == null ? null : adxDspReportFormHour.getOriginality());
        args.put("user_id", adxDspReportFormHour.getUserId() == null ? null : adxDspReportFormHour.getUserId());
        args.put("date_time", adxDspReportFormHour.getDateTime() == null ? null : adxDspReportFormHour.getDateTime());
        args.put("remarks", adxDspReportFormHour.getRemarks() == null ? null : adxDspReportFormHour.getRemarks());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspReportFormHourRowMapper implements RowMapper<AdxDspReportFormHour> {
        public AdxDspReportFormHour mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspReportFormHour adxDspReportFormHour = new AdxDspReportFormHour();
            adxDspReportFormHour.setId(rs.getLong("id"));
            adxDspReportFormHour.setFlowType(rs.getInt("flow_type"));
            adxDspReportFormHour.setSize(rs.getString("size"));
            adxDspReportFormHour.setShowNum(rs.getString("show_num"));
            adxDspReportFormHour.setClickNum(rs.getString("click_num"));
            adxDspReportFormHour.setClickRate(rs.getDouble("click_rate"));
            adxDspReportFormHour.setThousandShowPrice(rs.getDouble("thousand_show_price"));
            adxDspReportFormHour.setAdvertisers(rs.getString("advertisers"));
            adxDspReportFormHour.setOriginality(rs.getString("originality"));
            adxDspReportFormHour.setUserId(rs.getLong("user_id"));
            adxDspReportFormHour.setDateTime(rs.getTimestamp("date_time"));
            adxDspReportFormHour.setRemarks(rs.getString("remarks"));
            adxDspReportFormHour.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspReportFormHour;
        }
    }

}
