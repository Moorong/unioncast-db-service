package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspQualifications;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspQualificationsDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspQualificationsDao")
public class AdxDspQualificationsDaoImpl extends AdxGeneralDao<AdxDspQualifications, Long>
        implements AdxDspQualificationsDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspQualifications.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspQualifications.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspQualifications.TABLE_NAME, AdxDspQualifications.PROPERTIES);
    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspQualifications.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspQualifications.TABLE_NAME + "(" + AdxDspQualifications.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspQualifications.TABLE_NAME);


    public Pagination<AdxDspQualifications> page(PageCriteria pageCriteria) {
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
        Pagination<AdxDspQualifications> pagination = new Pagination<AdxDspQualifications>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<AdxDspQualifications> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspQualificationsRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspQualifications[]{}));

        return pagination;
    }



    /**
     * 查找
     */
    @Override
    public AdxDspQualifications[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspQualifications> list = jdbcTemplate.query(FIND_ALL, new AdxDspQualificationsRowMapper());
            return list.toArray(new AdxDspQualifications[list.size()]);

        }
        AdxDspQualifications adxDspQualifications = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspQualificationsRowMapper(), id);
        AdxDspQualifications[] adxDspQualificationss = new AdxDspQualifications[]{adxDspQualifications};
        return adxDspQualificationss;
    }


    @Override
    public Long[] batchAdd(AdxDspQualifications[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspQualifications adxDspQualifications : entitys) {

                pstmt.setString(1, adxDspQualifications.getQualificationDocNumber());

                pstmt.setString(2, adxDspQualifications.getQualificationFileName());
                pstmt.setString(3, adxDspQualifications.getQualificationFile());

                if (adxDspQualifications.getBeginDate() != null)
                    pstmt.setTimestamp(4, new Timestamp(adxDspQualifications.getBeginDate().getTime()));
                else
                    pstmt.setNull(4, Types.TIMESTAMP);

                if (adxDspQualifications.getEndDate() != null)
                    pstmt.setTimestamp(5, new Timestamp(adxDspQualifications.getEndDate().getTime()));
                else
                    pstmt.setNull(5, Types.TIMESTAMP);

                pstmt.setString(6, adxDspQualifications.getLegalpersionName());
                pstmt.setString(7, adxDspQualifications.getEnterpriseType());

                pstmt.setString(8, adxDspQualifications.getBusinessAddress());
                pstmt.setString(9, adxDspQualifications.getBusinessScope());

                if (adxDspQualifications.getRegisteredCapital() != null)
                    pstmt.setDouble(10, adxDspQualifications.getRegisteredCapital());
                else
                    pstmt.setNull(10, Types.INTEGER);

                pstmt.setString(11, adxDspQualifications.getIssuingAuthority());

                if (adxDspQualifications.getEstablishDate() != null)
                    pstmt.setTimestamp(12, new Timestamp(adxDspQualifications.getEstablishDate().getTime()));
                else
                    pstmt.setNull(12, Types.TIMESTAMP);

                if (adxDspQualifications.getBusinessBeginDate() != null)
                    pstmt.setTimestamp(13, new Timestamp(adxDspQualifications.getBusinessBeginDate().getTime()));
                else
                    pstmt.setNull(13, Types.TIMESTAMP);

                if (adxDspQualifications.getBusinessEndDate() != null)
                    pstmt.setTimestamp(14, new Timestamp(adxDspQualifications.getBusinessEndDate().getTime()));
                else
                    pstmt.setNull(14, Types.TIMESTAMP);

                if (adxDspQualifications.getIssueOfDate() != null)
                    pstmt.setTimestamp(15, new Timestamp(adxDspQualifications.getIssueOfDate().getTime()));
                else
                    pstmt.setNull(15, Types.TIMESTAMP);
                pstmt.setString(16, adxDspQualifications.getRemarks());
                pstmt.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
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
    public AdxDspQualifications findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspQualifications) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxDspQualificationsRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspQualifications entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getQualificationDocNumber() != null) {
            stringBuffer.append(",qualification_doc_number = ?");
            args.add(entity.getQualificationDocNumber());
        }
        if (entity.getQualificationFileName() != null) {
            stringBuffer.append(",qualification_file_name = ?");
            args.add(entity.getQualificationFileName());
        }
        if (entity.getQualificationFile() != null) {
            stringBuffer.append(",qualification_file = ?");
            args.add(entity.getQualificationFile());
        }
        if (entity.getBeginDate() != null) {
            stringBuffer.append(",begin_date = ?");
            args.add(entity.getBeginDate());
        }
        if (entity.getEndDate() != null) {
            stringBuffer.append(",end_date = ?");
            args.add(entity.getEndDate());
        }
        if (entity.getLegalpersionName() != null) {
            stringBuffer.append(",legalpersion_name = ?");
            args.add(entity.getLegalpersionName());
        }
        if (entity.getEnterpriseType() != null) {
            stringBuffer.append(",enterprise_type = ?");
            args.add(entity.getEnterpriseType());
        }
        if (entity.getBusinessAddress() != null) {
            stringBuffer.append(",business_address = ?");
            args.add(entity.getBusinessAddress());
        }
        if (entity.getBusinessScope() != null) {
            stringBuffer.append(",business_scope = ?");
            args.add(entity.getBusinessScope());
        }
        if (entity.getRegisteredCapital() != null) {
            stringBuffer.append(",registered_capital = ?");
            args.add(entity.getRegisteredCapital());
        }
        if (entity.getIssuingAuthority() != null) {
            stringBuffer.append(",issuing_authority = ?");
            args.add(entity.getIssuingAuthority());
        }
        if (entity.getEstablishDate() != null) {
            stringBuffer.append(",establish_date = ?");
            args.add(entity.getEstablishDate());
        }
        if (entity.getBusinessBeginDate() != null) {
            stringBuffer.append(",business_begin_date = ?");
            args.add(entity.getBusinessBeginDate());
        }
        if (entity.getBusinessEndDate() != null) {
            stringBuffer.append(",business_end_date = ?");
            args.add(entity.getBusinessEndDate());
        }
        if (entity.getIssueOfDate() != null) {
            stringBuffer.append(",issue_of_date = ?");
            args.add(entity.getIssueOfDate());
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
    public Long save(AdxDspQualifications entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private Long insertAndReturnId(AdxDspQualifications adxDspQualifications) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AdxDspQualifications.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("qualification_doc_number", adxDspQualifications.getQualificationDocNumber() == null ? null
                : adxDspQualifications.getQualificationDocNumber());
        args.put("qualification_file_name", adxDspQualifications.getQualificationFileName() == null ? null
                : adxDspQualifications.getQualificationFileName());
        args.put("qualification_file", adxDspQualifications.getQualificationFile() == null ? null
                : adxDspQualifications.getQualificationFile());
        args.put("begin_date",
                adxDspQualifications.getBeginDate() == null ? null : adxDspQualifications.getBeginDate());
        args.put("end_date", adxDspQualifications.getEndDate() == null ? null : adxDspQualifications.getEndDate());
        args.put("legalpersion_name",
                adxDspQualifications.getLegalpersionName() == null ? null : adxDspQualifications.getLegalpersionName());
        args.put("enterprise_type",
                adxDspQualifications.getEnterpriseType() == null ? null : adxDspQualifications.getEnterpriseType());
        args.put("business_address",
                adxDspQualifications.getBusinessAddress() == null ? null : adxDspQualifications.getBusinessAddress());
        args.put("business_scope",
                adxDspQualifications.getBusinessScope() == null ? null : adxDspQualifications.getBusinessScope());
        args.put("registered_capital", adxDspQualifications.getRegisteredCapital() == null ? null
                : adxDspQualifications.getRegisteredCapital());
        args.put("issuing_authority",
                adxDspQualifications.getIssuingAuthority() == null ? null : adxDspQualifications.getIssuingAuthority());
        args.put("establish_date",
                adxDspQualifications.getEstablishDate() == null ? null : adxDspQualifications.getEstablishDate());
        args.put("business_begin_date", adxDspQualifications.getBusinessBeginDate() == null ? null
                : adxDspQualifications.getBusinessBeginDate());
        args.put("business_end_date",
                adxDspQualifications.getBusinessEndDate() == null ? null : adxDspQualifications.getBusinessEndDate());
        args.put("issue_of_date",
                adxDspQualifications.getIssueOfDate() == null ? null : adxDspQualifications.getIssueOfDate());
        args.put("remarks", adxDspQualifications.getRemarks() == null ? null : adxDspQualifications.getRemarks());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspQualificationsRowMapper implements RowMapper<AdxDspQualifications> {
        public AdxDspQualifications mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspQualifications adxDspQualifications = new AdxDspQualifications();
            adxDspQualifications.setId(rs.getLong("id"));
            adxDspQualifications.setQualificationDocNumber(rs.getString("qualification_doc_number"));
            adxDspQualifications.setQualificationFileName(rs.getString("qualification_file_name"));
            adxDspQualifications.setQualificationFile(rs.getString("qualification_file"));
            adxDspQualifications.setBeginDate(rs.getTimestamp("begin_date"));
            adxDspQualifications.setEndDate(rs.getTimestamp("end_date"));
            adxDspQualifications.setLegalpersionName(rs.getString("legalpersion_name"));
            adxDspQualifications.setEnterpriseType(rs.getString("enterprise_type"));
            adxDspQualifications.setBusinessAddress(rs.getString("business_address"));
            adxDspQualifications.setBusinessScope(rs.getString("business_scope"));
            adxDspQualifications.setRegisteredCapital(rs.getDouble("registered_capital"));
            adxDspQualifications.setIssuingAuthority(rs.getString("issuing_authority"));
            adxDspQualifications.setEstablishDate(rs.getTimestamp("establish_date"));
            adxDspQualifications.setBusinessBeginDate(rs.getTimestamp("business_begin_date"));
            adxDspQualifications.setBusinessEndDate(rs.getTimestamp("business_end_date"));
            adxDspQualifications.setIssueOfDate(rs.getTimestamp("issue_of_date"));
            adxDspQualifications.setRemarks(rs.getString("remarks"));
            adxDspQualifications.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspQualifications;
        }
    }

}
