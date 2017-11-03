package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxDspUser;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspUserDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxDspUserDao")
public class AdxDspUserDaoImpl extends AdxGeneralDao<AdxDspUser, Long> implements AdxDspUserDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullFieldSet(AdxDspUser.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxDspUser.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxDspUser.TABLE_NAME, AdxDspUser.PROPERTIES);

    public static final String BATCH_DELETE = SqlBuild.delete(AdxDspUser.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxDspUser.TABLE_NAME + "(" + AdxDspUser.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    public static final String COUNT_ALL = SqlBuild.countAll(AdxDspUser.TABLE_NAME);



    public Pagination<AdxDspUser> page(PageCriteria pageCriteria) {
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
        Pagination<AdxDspUser> pagination = new Pagination<AdxDspUser>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<AdxDspUser> users = jdbcTemplate.query(stringBuilder.toString(), new AdxDspUserRowMapper());
        pagination.setDataArray(users.toArray(new AdxDspUser[]{}));

        return pagination;
    }


    /**
     * 查找
     */
    @Override
    public AdxDspUser[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxDspUser> list = jdbcTemplate.query(FIND_ALL, new AdxDspUserRowMapper());
            return list.toArray(new AdxDspUser[list.size()]);

        }
        AdxDspUser adxDspUser = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxDspUserRowMapper(), id);
        AdxDspUser[] adxDspUsers = new AdxDspUser[]{adxDspUser};
        return adxDspUsers;
    }


    @Override
    public Long[] batchAdd(AdxDspUser[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxDspUser adxDspUser : entitys) {
                pstmt.setString(1, adxDspUser.getCompanyaddress());
                pstmt.setString(2, adxDspUser.getCompanyname());
                pstmt.setString(3, adxDspUser.getCompanywebsite());
                pstmt.setString(4, adxDspUser.getContactperson());
                if (adxDspUser.getDeletestatus() != null)
                    pstmt.setInt(5, adxDspUser.getDeletestatus());
                else
                    pstmt.setNull(5, Types.INTEGER);
                pstmt.setString(6, adxDspUser.getFax());
                pstmt.setString(7, adxDspUser.getFixtelephone());
                if (adxDspUser.getLoginstatus() != null)
                    pstmt.setInt(8, adxDspUser.getLoginstatus());
                else
                    pstmt.setNull(8, Types.INTEGER);

                pstmt.setString(9, adxDspUser.getMail());
                pstmt.setString(10, adxDspUser.getMobilephone());
                pstmt.setString(11, adxDspUser.getPassword());
                pstmt.setString(12, adxDspUser.getQq());

                if (adxDspUser.getSignuptime() != null)
                    pstmt.setTimestamp(13, new Timestamp(adxDspUser.getSignuptime().getTime()));
                else
                    pstmt.setNull(13, Types.TIMESTAMP);

                if (adxDspUser.getUpdatetime() != null)
                    pstmt.setTimestamp(14, new Timestamp(adxDspUser.getUpdatetime().getTime()));
                else
                    pstmt.setNull(14, Types.TIMESTAMP);

                pstmt.setString(15, adxDspUser.getUsername());
                pstmt.setString(16, adxDspUser.getZipcode());

                if (adxDspUser.getIndustryId() != null)
                    pstmt.setLong(17, adxDspUser.getIndustryId());
                else
                    pstmt.setNull(17, Types.BIGINT);
                if (adxDspUser.getParentId() != null)
                    pstmt.setLong(18, adxDspUser.getParentId());
                else
                    pstmt.setNull(18, Types.BIGINT);
                if (adxDspUser.getBalance() != null)
                    pstmt.setDouble(19, adxDspUser.getBalance());
                else
                    pstmt.setNull(19, Types.DOUBLE);
                if (adxDspUser.getQualificationsId() != null)
                    pstmt.setLong(20, adxDspUser.getQualificationsId());
                else
                    pstmt.setNull(20, Types.BIGINT);
                pstmt.setString(21, adxDspUser.getRemarks());
                pstmt.setTimestamp(22, new Timestamp(System.currentTimeMillis()));
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
    public AdxDspUser findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxDspUser) jdbcTemplate.queryForObject(stringBuffer.toString(), new AdxDspUserRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxDspUser entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getCompanyaddress() != null) {
            stringBuffer.append(",companyaddress = ?");
            args.add(entity.getCompanyaddress());
        }
        if (entity.getCompanyname() != null) {
            stringBuffer.append(",companyname = ?");
            args.add(entity.getCompanyname());
        }
        if (entity.getCompanywebsite() != null) {
            stringBuffer.append(",companywebsite = ?");
            args.add(entity.getCompanywebsite());
        }
        if (entity.getContactperson() != null) {
            stringBuffer.append(",contactperson = ?");
            args.add(entity.getContactperson());
        }
        if (entity.getDeletestatus() != null) {
            stringBuffer.append(",deletestatus = ?");
            args.add(entity.getDeletestatus());
        }
        if (entity.getFax() != null) {
            stringBuffer.append(",fax = ?");
            args.add(entity.getFax());
        }
        if (entity.getFixtelephone() != null) {
            stringBuffer.append(",fixtelephone = ?");
            args.add(entity.getFixtelephone());
        }
        if (entity.getLoginstatus() != null) {
            stringBuffer.append(",loginstatus = ?");
            args.add(entity.getLoginstatus());
        }
        if (entity.getMail() != null) {
            stringBuffer.append(",mail = ?");
            args.add(entity.getMail());
        }
        if (entity.getMobilephone() != null) {
            stringBuffer.append(",mobilephone = ?");
            args.add(entity.getMobilephone());
        }
        if (entity.getPassword() != null) {
            stringBuffer.append(",password = ?");
            args.add(entity.getPassword());
        }
        if (entity.getQq() != null) {
            stringBuffer.append(",qq = ?");
            args.add(entity.getQq());
        }
        if (entity.getSignuptime() != null) {
            stringBuffer.append(",signuptime = ?");
            args.add(entity.getSignuptime());
        }
        if (entity.getUpdatetime() != null) {
            stringBuffer.append(",updatetime = ?");
            args.add(entity.getUpdatetime());
        }
        if (entity.getUsername() != null) {
            stringBuffer.append(",username = ?");
            args.add(entity.getUsername());
        }
        if (entity.getZipcode() != null) {
            stringBuffer.append(",zipcode = ?");
            args.add(entity.getZipcode());
        }
        if (entity.getIndustryId() != null) {
            stringBuffer.append(",industry_id = ?");
            args.add(entity.getIndustryId());
        }
        if (entity.getParentId() != null) {
            stringBuffer.append(",parent_id = ?");
            args.add(entity.getParentId());
        }
        if (entity.getBalance() != null) {
            stringBuffer.append(",balance = ?");
            args.add(entity.getBalance());
        }
        if (entity.getQualificationsId() != null) {
            stringBuffer.append(",qualifications_id = ?");
            args.add(entity.getQualificationsId());
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
    public Long save(AdxDspUser entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private Long insertAndReturnId(AdxDspUser adxDspUser) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AdxDspUser.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("companyaddress", adxDspUser.getCompanyaddress() == null ? null : adxDspUser.getCompanyaddress());
        args.put("companyname", adxDspUser.getCompanyname() == null ? null : adxDspUser.getCompanyname());
        args.put("companywebsite", adxDspUser.getCompanywebsite() == null ? null : adxDspUser.getCompanywebsite());
        args.put("contactperson", adxDspUser.getContactperson() == null ? null : adxDspUser.getContactperson());
        args.put("deletestatus", adxDspUser.getDeletestatus() == null ? null : adxDspUser.getDeletestatus());
        args.put("fax", adxDspUser.getFax() == null ? null : adxDspUser.getFax());
        args.put("fixtelephone", adxDspUser.getFixtelephone() == null ? null : adxDspUser.getFixtelephone());
        args.put("loginstatus", adxDspUser.getLoginstatus() == null ? null : adxDspUser.getLoginstatus());
        args.put("mail", adxDspUser.getMail() == null ? null : adxDspUser.getMail());
        args.put("mobilephone", adxDspUser.getMobilephone() == null ? null : adxDspUser.getMobilephone());
        args.put("password", adxDspUser.getPassword() == null ? null : adxDspUser.getPassword());
        args.put("qq", adxDspUser.getQq() == null ? null : adxDspUser.getQq());
        args.put("signuptime", adxDspUser.getSignuptime() == null ? null : adxDspUser.getSignuptime());
        args.put("updatetime", adxDspUser.getUpdatetime() == null ? null : adxDspUser.getUpdatetime());
        args.put("username", adxDspUser.getUsername() == null ? null : adxDspUser.getUsername());
        args.put("zipcode", adxDspUser.getZipcode() == null ? null : adxDspUser.getZipcode());
        args.put("industry_id", adxDspUser.getIndustryId() == null ? null : adxDspUser.getIndustryId());
        args.put("parent_id", adxDspUser.getParentId() == null ? null : adxDspUser.getParentId());
        args.put("balance", adxDspUser.getBalance() == null ? null : adxDspUser.getBalance());
        args.put("qualifications_id",
                adxDspUser.getQualificationsId() == null ? null : adxDspUser.getQualificationsId());
        args.put("remarks", adxDspUser.getRemarks() == null ? null : adxDspUser.getRemarks());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxDspUserRowMapper implements RowMapper<AdxDspUser> {
        public AdxDspUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxDspUser adxDspUser = new AdxDspUser();
            adxDspUser.setId(rs.getLong("id"));
            adxDspUser.setCompanyaddress(rs.getString("companyaddress"));
            adxDspUser.setCompanyname(rs.getString("companyname"));
            adxDspUser.setCompanywebsite(rs.getString("companywebsite"));
            adxDspUser.setContactperson(rs.getString("contactperson"));
            adxDspUser.setDeletestatus(rs.getInt("deletestatus"));
            adxDspUser.setFax(rs.getString("fax"));
            adxDspUser.setFixtelephone(rs.getString("fixtelephone"));
            adxDspUser.setLoginstatus(rs.getInt("loginstatus"));
            adxDspUser.setMail(rs.getString("mail"));
            adxDspUser.setMobilephone(rs.getString("mobilephone"));
            adxDspUser.setPassword(rs.getString("password"));
            adxDspUser.setQq(rs.getString("qq"));
            adxDspUser.setSignuptime(rs.getTimestamp("signuptime"));
            adxDspUser.setUpdatetime(rs.getTimestamp("updatetime"));
            adxDspUser.setUsername(rs.getString("username"));
            adxDspUser.setZipcode(rs.getString("zipcode"));
            adxDspUser.setIndustryId(rs.getLong("industry_id"));
            adxDspUser.setParentId(rs.getLong("parent_id"));
            adxDspUser.setBalance(rs.getDouble("balance"));
            adxDspUser.setQualificationsId(rs.getLong("qualifications_id"));
            adxDspUser.setRemarks(rs.getString("remarks"));
            adxDspUser.setUpdateTime(rs.getTimestamp("update_time"));
            return adxDspUser;
        }
    }

}
