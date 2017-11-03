package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspOperationRecord;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspOperationRecordDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspOperationRecordDao")
public class AdxDspOperationRecordDaoImpl extends AdxGeneralDao<AdxDspOperationRecord, Long>
        implements AdxDspOperationRecordDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspOperationRecord.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspOperationRecord.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspOperationRecord.TABLE_NAME, AdxDspOperationRecord.PROPERTIES);

    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspOperationRecord.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspOperationRecord.TABLE_NAME + "(" + AdxDspOperationRecord.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?,?,?)";


    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspOperationRecord.TABLE_NAME);



    public Pagination<AdxDspOperationRecord> page(PageCriteria pageCriteria) {
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
        Pagination<AdxDspOperationRecord> pagination = new Pagination<AdxDspOperationRecord>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<AdxDspOperationRecord> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspOperationRecordRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspOperationRecord[]{}));

        return pagination;
    }



    /**
     * 查找
     */
    @Override
    public AdxDspOperationRecord[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspOperationRecord> list = jdbcTemplate.query(FIND_ALL, new AdxDspOperationRecordRowMapper());
            return list.toArray(new AdxDspOperationRecord[list.size()]);

        }
        AdxDspOperationRecord adxDspOperationRecord = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspOperationRecordRowMapper(), id);
        AdxDspOperationRecord[] adxDspOperationRecords = new AdxDspOperationRecord[]{adxDspOperationRecord};
        return adxDspOperationRecords;
    }


    @Override
    public Long[] batchAdd(AdxDspOperationRecord[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspOperationRecord adxDspOperationRecord : entitys) {
                if (adxDspOperationRecord.getOperationTime() != null)
                    pstmt.setTimestamp(1, new Timestamp(adxDspOperationRecord.getOperationTime().getTime()));
                else
                    pstmt.setNull(1, Types.TIMESTAMP);
                pstmt.setString(2, adxDspOperationRecord.getOperationObject());
                pstmt.setString(3, adxDspOperationRecord.getFlowType());
                pstmt.setString(4, adxDspOperationRecord.getOperationContent());
                pstmt.setString(5, adxDspOperationRecord.getBeforeOperation());
                pstmt.setString(6, adxDspOperationRecord.getAfterOperation());
                pstmt.setString(7, adxDspOperationRecord.getOperationPersion());
                pstmt.setString(8, adxDspOperationRecord.getOperationSource());
                pstmt.setString(9, adxDspOperationRecord.getOperationIp());
                pstmt.setString(10, adxDspOperationRecord.getRemarks());
                pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
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
    public AdxDspOperationRecord findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspOperationRecord) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxDspOperationRecordRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspOperationRecord entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getOperationTime() != null) {
            stringBuffer.append(",operation_time = ?");
            args.add(entity.getOperationTime());
        }
        if (entity.getOperationObject() != null) {
            stringBuffer.append(",operation_object = ?");
            args.add(entity.getOperationObject());
        }
        if (entity.getFlowType() != null) {
            stringBuffer.append(",flow_type = ?");
            args.add(entity.getFlowType());
        }
        if (entity.getOperationContent() != null) {
            stringBuffer.append(",operation_content = ?");
            args.add(entity.getOperationContent());
        }
        if (entity.getBeforeOperation() != null) {
            stringBuffer.append(",before_operation = ?");
            args.add(entity.getBeforeOperation());
        }
        if (entity.getAfterOperation() != null) {
            stringBuffer.append(",after_operation = ?");
            args.add(entity.getAfterOperation());
        }
        if (entity.getOperationPersion() != null) {
            stringBuffer.append(",operation_persion = ?");
            args.add(entity.getOperationPersion());
        }
        if (entity.getOperationSource() != null) {
            stringBuffer.append(",operation_source = ?");
            args.add(entity.getOperationSource());
        }
        if (entity.getOperationIp() != null) {
            stringBuffer.append(",operation_ip = ?");
            args.add(entity.getOperationIp());
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
    public Long save(AdxDspOperationRecord entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private Long insertAndReturnId(AdxDspOperationRecord adxDspOperationRecord) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AdxDspOperationRecord.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("operation_time",
                adxDspOperationRecord.getOperationTime() == null ? null : adxDspOperationRecord.getOperationTime());
        args.put("operation_object",
                adxDspOperationRecord.getOperationObject() == null ? null : adxDspOperationRecord.getOperationObject());
        args.put("flow_type", adxDspOperationRecord.getFlowType() == null ? null : adxDspOperationRecord.getFlowType());
        args.put("operation_content", adxDspOperationRecord.getOperationContent() == null ? null
                : adxDspOperationRecord.getOperationContent());
        args.put("before_operation",
                adxDspOperationRecord.getBeforeOperation() == null ? null : adxDspOperationRecord.getBeforeOperation());
        args.put("after_operation",
                adxDspOperationRecord.getAfterOperation() == null ? null : adxDspOperationRecord.getAfterOperation());
        args.put("operation_persion", adxDspOperationRecord.getOperationPersion() == null ? null
                : adxDspOperationRecord.getOperationPersion());
        args.put("operation_source",
                adxDspOperationRecord.getOperationSource() == null ? null : adxDspOperationRecord.getOperationSource());
        args.put("operation_ip",
                adxDspOperationRecord.getOperationIp() == null ? null : adxDspOperationRecord.getOperationIp());
        args.put("remarks", adxDspOperationRecord.getRemarks() == null ? null : adxDspOperationRecord.getRemarks());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspOperationRecordRowMapper implements RowMapper<AdxDspOperationRecord> {
        public AdxDspOperationRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspOperationRecord adxDspOperationRecord = new AdxDspOperationRecord();
            adxDspOperationRecord.setId(rs.getLong("id"));
            adxDspOperationRecord.setOperationTime(rs.getTimestamp("operation_time"));
            adxDspOperationRecord.setOperationObject(rs.getString("operation_object"));
            adxDspOperationRecord.setFlowType(rs.getString("flow_type"));
            adxDspOperationRecord.setOperationContent(rs.getString("operation_content"));
            adxDspOperationRecord.setBeforeOperation(rs.getString("before_operation"));
            adxDspOperationRecord.setAfterOperation(rs.getString("after_operation"));
            adxDspOperationRecord.setOperationPersion(rs.getString("operation_persion"));
            adxDspOperationRecord.setOperationSource(rs.getString("operation_source"));
            adxDspOperationRecord.setOperationIp(rs.getString("operation_ip"));
            adxDspOperationRecord.setRemarks(rs.getString("remarks"));
            adxDspOperationRecord.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspOperationRecord;
        }
    }

}
