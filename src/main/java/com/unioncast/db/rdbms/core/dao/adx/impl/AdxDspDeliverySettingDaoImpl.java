package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspDeliverySetting;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspDeliverySettingDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspDeliverySettingDao")
public class AdxDspDeliverySettingDaoImpl extends AdxGeneralDao<AdxDspDeliverySetting, Long>
        implements AdxDspDeliverySettingDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspDeliverySetting.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspDeliverySetting.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspDeliverySetting.TABLE_NAME,
            AdxDspDeliverySetting.PROPERTIES);
    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspDeliverySetting.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspDeliverySetting.TABLE_NAME + "(" + AdxDspDeliverySetting.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?)";


    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspDeliverySetting.TABLE_NAME);



    public Pagination<AdxDspDeliverySetting> page(PageCriteria pageCriteria) {
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
        Pagination<AdxDspDeliverySetting> pagination = new Pagination<AdxDspDeliverySetting>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<AdxDspDeliverySetting> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspDeliverySettingRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspDeliverySetting[]{}));

        return pagination;
    }



    /**
     * 查找
     */
    @Override
    public AdxDspDeliverySetting[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspDeliverySetting> list = jdbcTemplate.query(FIND_ALL, new AdxDspDeliverySettingRowMapper());
            return list.toArray(new AdxDspDeliverySetting[list.size()]);

        }
        AdxDspDeliverySetting adxDspDeliverySetting = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspDeliverySettingRowMapper(), id);
        AdxDspDeliverySetting[] adxDspDeliverySettings = new AdxDspDeliverySetting[]{adxDspDeliverySetting};
        return adxDspDeliverySettings;
    }


    @Override
    public Long[] batchAdd(AdxDspDeliverySetting[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspDeliverySetting adxDspDeliverySetting : entitys) {
                if (adxDspDeliverySetting.getAdxOrSspUserId() != null)
                    pstmt.setLong(1, adxDspDeliverySetting.getAdxOrSspUserId());
                else
                    pstmt.setNull(1, Types.BIGINT);
                pstmt.setString(2, adxDspDeliverySetting.getMediaId());
                if (adxDspDeliverySetting.getDspId() != null)
                    pstmt.setLong(3, adxDspDeliverySetting.getDspId());
                else
                    pstmt.setNull(3, Types.BIGINT);
                if (adxDspDeliverySetting.getFlowPercentage() != null)
                    pstmt.setDouble(4, adxDspDeliverySetting.getFlowPercentage());
                else
                    pstmt.setNull(4, Types.DOUBLE);
                if (adxDspDeliverySetting.getIsNotForward() != null)
                    pstmt.setInt(5, adxDspDeliverySetting.getIsNotForward());
                else
                    pstmt.setNull(5, Types.INTEGER);
                if (adxDspDeliverySetting.getAdxManagerId() != null)
                    pstmt.setLong(6, adxDspDeliverySetting.getAdxManagerId());
                else
                    pstmt.setNull(6, Types.BIGINT);
                pstmt.setString(7, adxDspDeliverySetting.getRemarks());
                pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
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
    public AdxDspDeliverySetting findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspDeliverySetting) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxDspDeliverySettingRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspDeliverySetting entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getAdxOrSspUserId() != null) {
            stringBuffer.append(",adx_or_ssp_id = ?");
            args.add(entity.getAdxOrSspUserId());
        }
        if (entity.getMediaId() != null) {
            stringBuffer.append(",media_id = ?");
            args.add(entity.getMediaId());
        }
        if (entity.getDspId() != null) {
            stringBuffer.append(",dsp_id = ?");
            args.add(entity.getDspId());
        }
        if (entity.getFlowPercentage() != null) {
            stringBuffer.append(",flow_percentage = ?");
            args.add(entity.getFlowPercentage());
        }
        if (entity.getIsNotForward() != null) {
            stringBuffer.append(",is_not_forward = ?");
            args.add(entity.getIsNotForward());
        }
        if (entity.getAdxManagerId() != null) {
            stringBuffer.append(",adx_manager_id = ?");
            args.add(entity.getAdxManagerId());
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
    public Long save(AdxDspDeliverySetting entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public AdxDspDeliverySetting[] findByAdxOrSspId(Long adxOrSspId) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where adx_or_ssp_id = ?");
        List<AdxDspDeliverySetting> lists = jdbcTemplate.query(stringBuffer.toString(),
                new AdxDspDeliverySettingRowMapper(), adxOrSspId);
        AdxDspDeliverySetting[] adxDspDeliverySettings = lists.toArray(new AdxDspDeliverySetting[lists.size()]);
        return adxDspDeliverySettings;

    }

    private Long insertAndReturnId(AdxDspDeliverySetting adxDspDeliverySetting) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AdxDspDeliverySetting.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("adx_or_ssp_id",
                adxDspDeliverySetting.getAdxOrSspUserId() == null ? null : adxDspDeliverySetting.getAdxOrSspUserId());
        args.put("media_id", adxDspDeliverySetting.getMediaId() == null ? null : adxDspDeliverySetting.getMediaId());
        args.put("dsp_id", adxDspDeliverySetting.getDspId() == null ? null : adxDspDeliverySetting.getDspId());
        args.put("flow_percentage",
                adxDspDeliverySetting.getFlowPercentage() == null ? null : adxDspDeliverySetting.getFlowPercentage());
        args.put("is_not_forward",
                adxDspDeliverySetting.getIsNotForward() == null ? null : adxDspDeliverySetting.getIsNotForward());
        args.put("adx_manager_id",
                adxDspDeliverySetting.getAdxManagerId() == null ? null : adxDspDeliverySetting.getAdxManagerId());
        args.put("remarks", adxDspDeliverySetting.getRemarks() == null ? null : adxDspDeliverySetting.getRemarks());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspDeliverySettingRowMapper implements RowMapper<AdxDspDeliverySetting> {
        public AdxDspDeliverySetting mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspDeliverySetting adxDspDeliverySetting = new AdxDspDeliverySetting();
            adxDspDeliverySetting.setId(rs.getLong("id"));
            adxDspDeliverySetting.setAdxOrSspUserId(rs.getLong("adx_or_ssp_id"));
            adxDspDeliverySetting.setMediaId(rs.getString("media_id"));
            adxDspDeliverySetting.setDspId(rs.getLong("dsp_id"));
            adxDspDeliverySetting.setFlowPercentage(rs.getDouble("flow_percentage"));
            adxDspDeliverySetting.setIsNotForward(rs.getInt("is_not_forward"));
            adxDspDeliverySetting.setAdxManagerId(rs.getLong("adx_manager_id"));
            adxDspDeliverySetting.setRemarks(rs.getString("remarks"));
            adxDspDeliverySetting.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspDeliverySetting;
        }
    }

}
