package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspFinancialAccount;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspFinancialAccountDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspFinancialAccountDao")
public class AdxDspFinancialAccountDaoImpl extends AdxGeneralDao<AdxDspFinancialAccount, Long>
        implements AdxDspFinancialAccountDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspFinancialAccount.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspFinancialAccount.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspFinancialAccount.TABLE_NAME,
            AdxDspFinancialAccount.PROPERTIES);

    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspFinancialAccount.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspFinancialAccount.TABLE_NAME + "(" + AdxDspFinancialAccount.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?)";


    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspFinancialAccount.TABLE_NAME);


    public Pagination<AdxDspFinancialAccount> page(PageCriteria pageCriteria) {
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
        Pagination<AdxDspFinancialAccount> pagination = new Pagination<AdxDspFinancialAccount>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<AdxDspFinancialAccount> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspFinancialAccountRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspFinancialAccount[]{}));

        return pagination;
    }


    /**
     * 查找
     */
    @Override
    public AdxDspFinancialAccount[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspFinancialAccount> list = jdbcTemplate.query(FIND_ALL, new AdxDspFinancialAccountRowMapper());
            return list.toArray(new AdxDspFinancialAccount[list.size()]);

        }
        AdxDspFinancialAccount adxDspFinancialAccount = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspFinancialAccountRowMapper(), id);
        AdxDspFinancialAccount[] adxDspFinancialAccounts = new AdxDspFinancialAccount[]{adxDspFinancialAccount};
        return adxDspFinancialAccounts;
    }


    @Override
    public Long[] batchAdd(AdxDspFinancialAccount[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspFinancialAccount adxDspFinancialAccount : entitys) {
                if (adxDspFinancialAccount.getConsumeOrRecharge() != null)
                    pstmt.setDouble(1, adxDspFinancialAccount.getConsumeOrRecharge());
                else
                    pstmt.setNull(1, Types.DOUBLE);
                pstmt.setString(2, adxDspFinancialAccount.getSerialNumber());
                if (adxDspFinancialAccount.getDateTime() != null)
                    pstmt.setTimestamp(3, new Timestamp(adxDspFinancialAccount.getDateTime().getTime()));
                else
                    pstmt.setNull(3, Types.TIMESTAMP);
                if (adxDspFinancialAccount.getState() != null)
                    pstmt.setInt(4, adxDspFinancialAccount.getState());
                else
                    pstmt.setNull(4, Types.INTEGER);
                if (adxDspFinancialAccount.getBalance() != null)
                    pstmt.setDouble(5, adxDspFinancialAccount.getBalance());
                else
                    pstmt.setNull(5, Types.DOUBLE);
                if (adxDspFinancialAccount.getUserId() != null)
                    pstmt.setLong(6, adxDspFinancialAccount.getUserId());
                else
                    pstmt.setNull(6, Types.BIGINT);
                pstmt.setString(7, adxDspFinancialAccount.getRemarks());
                pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                if (adxDspFinancialAccount.getAmountOfMoney() != null)
                    pstmt.setDouble(9, adxDspFinancialAccount.getAmountOfMoney());
                else
                    pstmt.setNull(9, Types.DOUBLE);
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
    public AdxDspFinancialAccount findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspFinancialAccount) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxDspFinancialAccountRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspFinancialAccount entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getConsumeOrRecharge() != null) {
            stringBuffer.append(",consume_or_recharge = ?");
            args.add(entity.getConsumeOrRecharge());
        }
        if (entity.getSerialNumber() != null) {
            stringBuffer.append(",serial_number = ?");
            args.add(entity.getSerialNumber());
        }
        if (entity.getDateTime() != null) {
            stringBuffer.append(",date_time = ?");
            args.add(entity.getDateTime());
        }
        if (entity.getState() != null) {
            stringBuffer.append(",state = ?");
            args.add(entity.getState());
        }
        if (entity.getBalance() != null) {
            stringBuffer.append(",balance = ?");
            args.add(entity.getBalance());
        }
        if (entity.getUserId() != null) {
            stringBuffer.append(",user_id = ?");
            args.add(entity.getUserId());
        }
        if (entity.getRemarks() != null) {
            stringBuffer.append(",remarks = ?");
            args.add(entity.getRemarks());
        }
        if (entity.getAmountOfMoney() != null) {
            stringBuffer.append(",amount_of_money = ?");
            args.add(entity.getAmountOfMoney());
        }
        stringBuffer.append(" where id = ?");
        args.add(entity.getId());
        jdbcTemplate.update(stringBuffer.toString(), args.toArray());
    }

    @Override
    public Long save(AdxDspFinancialAccount entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private Long insertAndReturnId(AdxDspFinancialAccount adxDspFinancialAccount) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AdxDspFinancialAccount.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("consume_or_recharge", adxDspFinancialAccount.getConsumeOrRecharge() == null ? null
                : adxDspFinancialAccount.getConsumeOrRecharge());
        args.put("serial_number",
                adxDspFinancialAccount.getSerialNumber() == null ? null : adxDspFinancialAccount.getSerialNumber());
        args.put("date_time",
                adxDspFinancialAccount.getDateTime() == null ? null : adxDspFinancialAccount.getDateTime());
        args.put("state", adxDspFinancialAccount.getState() == null ? null : adxDspFinancialAccount.getState());
        args.put("balance", adxDspFinancialAccount.getBalance() == null ? null : adxDspFinancialAccount.getBalance());
        args.put("user_id", adxDspFinancialAccount.getUserId() == null ? null : adxDspFinancialAccount.getUserId());
        args.put("remarks", adxDspFinancialAccount.getRemarks() == null ? null : adxDspFinancialAccount.getRemarks());
        args.put("amount_of_money", adxDspFinancialAccount.getAmountOfMoney() == null ? null : adxDspFinancialAccount.getAmountOfMoney());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspFinancialAccountRowMapper implements RowMapper<AdxDspFinancialAccount> {
        public AdxDspFinancialAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspFinancialAccount adxDspFinancialAccount = new AdxDspFinancialAccount();
            adxDspFinancialAccount.setId(rs.getLong("id"));
            adxDspFinancialAccount.setConsumeOrRecharge(rs.getDouble("consume_or_recharge"));
            adxDspFinancialAccount.setSerialNumber(rs.getString("serial_number"));
            adxDspFinancialAccount.setDateTime(rs.getTimestamp("date_time"));
            adxDspFinancialAccount.setState(rs.getInt("state"));
            adxDspFinancialAccount.setBalance(rs.getDouble("balance"));
            adxDspFinancialAccount.setUserId(rs.getLong("user_id"));
            adxDspFinancialAccount.setRemarks(rs.getString("remarks"));
            adxDspFinancialAccount.setUpdateTime(rs.getTimestamp("update_time"));
            adxDspFinancialAccount.setAmountOfMoney(rs.getDouble("amount_of_money"));
            return adxDspFinancialAccount;
        }
    }

}
