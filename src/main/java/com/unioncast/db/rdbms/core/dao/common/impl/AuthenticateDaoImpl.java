package com.unioncast.db.rdbms.core.dao.common.impl;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.AuthenticationApiInfo;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.core.dao.common.AuthenticateDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Repository("authenticateDao")
public class AuthenticateDaoImpl extends CommonGeneralDao<Authentication, Long> implements AuthenticateDao {

    public static final String UPDATE_NOT_NULL_FIELD = "update common_authentication set update_time = ?";
    private static final String COUNT_ALL = "select count(*) from common_authentication";
    private static final String INSERT_FOR_AUTHENCATION = "INSERT INTO common_authentication(`system_id`,`name`,`token`,`create_time`,`update_time`,`expire`,`remark`) VALUES(?,?,?,?,?,?,?)";
    private static final String DELETE_FOR_AUTH = "DELETE FROM `unioncast_common_manager`.`common_authentication`";
    private static final String AUTHENTICATION_APIINFO = "DELETE FROM `unioncast_common_manager`.`common_authentication_apiinfo`";
    private static final String INSERT_FOR_AUTHENTICATIONAPIINFO = "INSERT INTO common_authentication_apiinfo(authen_apiInfo_id,authentication_id,apiInfo_id,sort,remark,create_time,update_time) VALUES(?,?,?,?,?,?,?)";
    private static String AUTHENTICATION_APIINFO_LEFT = "SELECT * FROM `common_apiInfo` r LEFT  JOIN `common_authentication_apiinfo` u ON r.`id`=u.`apiInfo_id` WHERE u.`authentication_id`= ";
    private static String QUERY_FOR_OBJECT = "select id,system_id,name,token,create_time,expire,remark,update_time from common_authentication";
    private static String QUERY_FOR_APIINFO = "SELECT * FROM `common_apiInfo` a LEFT JOIN `common_authentication_apiinfo` b ON a.id=b.apiInfo_id WHERE b.`authentication_id`= ?";

    Pagination<Authentication> pagination;


    public Pagination<Authentication> page(PageCriteria pageCriteria) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringSearch = new StringBuilder();
        List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();

        stringBuilder.append(QUERY_FOR_OBJECT);
        if (searchExpressionList != null && pageCriteria.getPredicate() != null) {
            String predicate = pageCriteria.getPredicate().getOperator();
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
        Pagination<Authentication> pagination = new Pagination<Authentication>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

        List<Authentication> users = jdbcTemplate.query(stringBuilder.toString(), new AuthenticateRowMapper());
        pagination.setDataArray(users.toArray(new Authentication[]{}));

        return pagination;
    }


    /**
     * 查找
     */
    @Override
    public Authentication[] find(Long id) throws DaoException {
        if (id == null) {
            List<Authentication> list = jdbcTemplate.query(QUERY_FOR_OBJECT, new AuthenticateRowMapper());
            return list.toArray(new Authentication[list.size()]);

        }
        Authentication authentication = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where id = ?", new AuthenticateRowMapper(), id);
        Authentication[] authentications = new Authentication[]{authentication};
        return authentications;
    }

    public ApiInfo[] findRoleIdByUserId(Long id) {

        List<ApiInfo> list = jdbcTemplate.query(AUTHENTICATION_APIINFO_LEFT + id, new ApiInfoRowMapper());

        return list.toArray(new ApiInfo[list.size()]);
    }

    /**
     * 根据id查询
     */
    @Override
    public Authentication findById(Long id) throws DaoException {
        Authentication authentication = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where id = " + id,
            new AuthenticateRowMapper());
        return authentication;
    }

    /**
     * 根据SystemId查询
     */
    @Override
    public Authentication findBySysId(Long id) {
        Authentication authentication = jdbcTemplate.queryForObject(QUERY_FOR_OBJECT + " where system_id = " + id,
            new AuthenticateRowMapper());
        return authentication;
    }

    /**
     * 查询所有
     */
    public Authentication[] findAll() throws DaoException {
        List<Authentication> list = jdbcTemplate.query(QUERY_FOR_OBJECT, new AuthenticateRowMapper());
        Authentication[] authentications = new Authentication[list.size()];
        for (int i = 0; i < list.size(); i++) {
            authentications[i] = list.get(i);
        }
        return authentications;
    }

    /**
     * 批量添加
     */
    @Override
    public Long[] batchAdd(Authentication[] entitys) {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(INSERT_FOR_AUTHENCATION,
                PreparedStatement.RETURN_GENERATED_KEYS);
            for (Authentication authentication : entitys) {
                if (authentication.getSystemId() != null)
                    pstmt.setLong(1, authentication.getSystemId());
                else
                    pstmt.setNull(1, Types.BIGINT);
                pstmt.setString(2, authentication.getName());
                pstmt.setString(3, authentication.getToken());
                pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
                pstmt.setTimestamp(5, new Timestamp(new Date().getTime()));
                if (authentication.getExpire() != null)
                    pstmt.setTimestamp(6, new Timestamp(authentication.getExpire().getTime()));
                else
                    pstmt.setNull(6, Types.DATE);
                pstmt.setString(7, authentication.getRemark());
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

    /**
     * 删除
     */
    @Override
    public int deleteById(Long id) throws DaoException {
        int num = jdbcTemplate.update(DELETE_FOR_AUTH + " WHERE id = " + id);

        return num;

    }

    /**
     * 批量删除
     */
    @Override
    public int deleteById(Long[] ids) throws DaoException {

        List<Object[]> batchArgs = new ArrayList<>();
        for (Long id : ids) {
            Object[] objects = new Object[]{id};
            batchArgs.add(objects);
        }
        int[] intArray = jdbcTemplate.batchUpdate(DELETE_FOR_AUTH + " where id = ?", batchArgs);
        int j = 0;
        for (int i : intArray) {
            j += i;
        }
        return j;

    }

    // ==========================================================================

    public int deleteAuthApiInfoByAuthId(Long id) throws DaoException {
        int num = jdbcTemplate.update(AUTHENTICATION_APIINFO + " WHERE authentication_id = " + id);

        return num;
    }

    public int deleteAuthApiInfoByApiInfoId(Long id) throws DaoException {
        int num = jdbcTemplate.update(AUTHENTICATION_APIINFO + " WHERE apiInfo_id = " + id);

        return num;
    }

    public int batchDeleteAuthApiInfoByAuthId(Long[] ids) throws DaoException {
        List<Object[]> batchArgs = new ArrayList<>();
        for (Long id : ids) {
            Object[] objects = new Object[]{id};
            batchArgs.add(objects);
        }
        int[] intArray = jdbcTemplate.batchUpdate(AUTHENTICATION_APIINFO + " where authentication_id = ?", batchArgs);
        int j = 0;
        for (int i : intArray) {
            j += i;
        }
        return j;
    }

    @Override
    public int batchDeleteAuthApiInfoByApiInfoId(Long[] ids) throws DaoException {
        List<Object[]> batchArgs = new ArrayList<>();
        for (Long id : ids) {
            Object[] objects = new Object[]{id};
            batchArgs.add(objects);
        }
        int[] intArray = jdbcTemplate.batchUpdate(AUTHENTICATION_APIINFO + " where apiInfo_id = ?", batchArgs);
        int j = 0;
        for (int i : intArray) {
            j += i;
        }
        return j;
    }

    @Override
    public ApiInfo[] findApiInfoByAuthId(Long id) throws DaoException {
        List<ApiInfo> list = jdbcTemplate.query(QUERY_FOR_APIINFO, new ApiInfoRowMapper(), id);

        ApiInfo[] apiInfos = list.toArray(new ApiInfo[]{});
        return apiInfos;

    }

    @Override
    public Authentication[] findAuthByAuth(Authentication authentication) {
        StringBuilder sb = new StringBuilder();
        sb.append(QUERY_FOR_OBJECT + " where 1=1 ");
        if (authentication != null) {
            if (authentication.getSystemId() != null) {
                sb.append(" and system_id = '" + authentication.getSystemId() + "' ");
            }
            if (authentication.getName() != null) {
                sb.append(" and name = '" + authentication.getName() + "' ");
            }
        }
        List<Authentication> query = jdbcTemplate.query(sb.toString(), new AuthenticateRowMapper());

        return query.toArray(new Authentication[]{});

    }

    public Long[] batchAddAuthenticationApiInfo(AuthenticationApiInfo[] authenticationApiInfos)
        throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(INSERT_FOR_AUTHENTICATIONAPIINFO,
                PreparedStatement.RETURN_GENERATED_KEYS);
            for (AuthenticationApiInfo authenticationApiInfo : authenticationApiInfos) {
                pstmt.setString(1, authenticationApiInfo.getAuthenApiInfoId());
                if (authenticationApiInfo.getAuthenId() != null)
                    pstmt.setLong(2, authenticationApiInfo.getAuthenId());
                else
                    pstmt.setNull(2, Types.BIGINT);
                if (authenticationApiInfo.getApiInfoId() != null)
                    pstmt.setLong(3, authenticationApiInfo.getApiInfoId());
                else
                    pstmt.setNull(3, Types.BIGINT);
                if (authenticationApiInfo.getSort() != null)
                    pstmt.setLong(4, authenticationApiInfo.getSort());
                else
                    pstmt.setNull(4, Types.BIGINT);
                pstmt.setString(5, authenticationApiInfo.getRemark());
                pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
                pstmt.setTimestamp(7, new Timestamp(new Date().getTime()));
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

    /**
     * 封装结果集
     */

    public final class AuthenticateRowMapper implements RowMapper<Authentication> {
        @Override
        public Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Authentication(rs.getLong("id"), rs.getInt("system_id"), rs.getString("name"), rs.getString("token"),
                rs.getTimestamp("create_time"), rs.getTimestamp("expire"), Arrays.asList(findRoleIdByUserId(rs.getLong("id"))),
                rs.getString("remark"), rs.getTimestamp("update_time"));
        }

    }

    public final class ApiInfoRowMapper implements RowMapper<ApiInfo> {

        @Override
        public ApiInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ApiInfo(rs.getLong("id"), rs.getInt("system_id"), rs.getString("system_name"),
                rs.getString("prefix"), rs.getString("remark"), rs.getTimestamp("create_time"),
                rs.getTimestamp("update_time"), rs.getString("ip_white_list"));
        }

    }

//
//    /**
//     * 分页查找
//     */
//
//    public Pagination<Authentication> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException {
//        int totalCount = countAll();
//        int maxPage = PaginationUtil.calTotalPage(totalCount, pageSize);
//        Authentication[] dataArray = null;
//        pagination = new Pagination<>(totalCount, pageSize, maxPage, currentPageNo, dataArray);
//        int currentPageNo2 = pagination.getCurrentPageNo();
//        int start = (currentPageNo2 - 1) * pageSize;
//        List<Authentication> list = jdbcTemplate.query(QUERY_FOR_OBJECT + " limit " + start + "," + pageSize,
//                new AuthenticateRowMapper());
//        Authentication[] s = new Authentication[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            s[i] = list.get(i);
//        }
//        pagination.setDataArray(s);
//        return pagination;
//    }
//
//    /**
//     * 带条件查询的分页
//     */
//    @Override
//    public Pagination<Authentication> page(Authentication authentications, Integer currentPageNo, Integer pageSize) {
//        StringBuilder stringBuilder = new StringBuilder(QUERY_FOR_OBJECT);
//        List<Object> list = new ArrayList<>();
//
//        if (authentications.getId() != null) {
//            stringBuilder.append(" where id = ?");
//            list.add(authentications.getId());
//        }
//
//        if (authentications.getSystemId() != null) {
//            String beginningIndex = QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
//            if ("?".equals(beginningIndex))
//                stringBuilder.append(" and system_id = ?");
//            else
//                stringBuilder.append(" where system_id = ?");
//            list.add(authentications.getSystemId());
//        }
//        if (authentications.getToken() != null) {
//            String beginningIndex = QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
//            if ("?".equals(beginningIndex))
//                stringBuilder.append(" and token = ?");
//            else
//                stringBuilder.append(" where token = ?");
//            list.add(authentications.getToken());
//        }
//        if (authentications.getCreateTime() != null) {
//            String beginningIndex = QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
//            if ("?".equals(beginningIndex))
//                stringBuilder.append(" and create_time = ?");
//            else
//                stringBuilder.append(" where create_time = ?");
//            list.add(authentications.getCreateTime());
//        }
//        if (authentications.getExpire() != null) {
//            String beginningIndex = QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
//            if ("?".equals(beginningIndex))
//                stringBuilder.append(" and expire = ?");
//            else
//                stringBuilder.append(" where expire = ?");
//            list.add(authentications.getExpire());
//        }
//        // if (authentications.getApiInfoList() != null) {
//        // String beginningIndex =
//        // QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
//        // if ("?".equals(beginningIndex))
//        // stringBuilder.append(" and apiInfo_id = ?");
//        // else
//        // stringBuilder.append(" where apiInfo_id = ?");
//        // list.add(authentications.getApiInfoList());
//        // }
//        if (authentications.getRemark() != null) {
//            String beginningIndex = QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
//            if ("?".equals(beginningIndex))
//                stringBuilder.append(" and remark like ?");
//            else
//                stringBuilder.append(" where remark like ?");
//            list.add(" %" + authentications.getRemark() + "% ");
//        }
//        if (authentications.getUpdateTime() != null) {
//            String beginningIndex = QUERY_FOR_OBJECT.substring(QUERY_FOR_OBJECT.length() - 1);
//            if ("?".equals(beginningIndex))
//                stringBuilder.append(" and update_time = ?");
//            else
//                stringBuilder.append(" where update_time = ?");
//            list.add(authentications.getUpdateTime());
//        }
//
//        if (currentPageNo != null && pageSize != null) {
//            Integer start = (currentPageNo - 1) * pageSize;
//            stringBuilder.append(" limit ?,?");
//            list.add(start);
//            list.add(pageSize);
//        }
//        List<Authentication> users = jdbcTemplate.query(stringBuilder.toString(), new AuthenticateRowMapper(),
//                list.toArray());
//        int totalCount = jdbcTemplate.queryForObject(COUNT_ALL, int.class);
//        return new Pagination<Authentication>(totalCount, pageSize, currentPageNo,
//                users.toArray(new Authentication[]{}));
//
//    }

//	@Override
//	public int updateNotNullFieldAndReturn(Authentication authentication) throws DaoException {
//
//		StringBuilder stringBuilder = new StringBuilder(UPDATE_NOT_NULL_FIELD);
//		List<Object> args = new ArrayList<>();
//		authentication.setUpdateTime(new Date());
//		args.add(new Date());
//
//		if (authentication.getSystemId() != null) {
//			stringBuilder.append(",system_id = ?");
//			args.add(authentication.getSystemId());
//		}
//		if (authentication.getApiInfoList() != null) {
//			stringBuilder.append(",apiInfo_id = ?");
//			args.add(authentication.getApiInfoList().get(0).getId());
//		}
//		if (authentication.getToken() != null) {
//			stringBuilder.append(",token = ?");
//			args.add(authentication.getToken());
//		}
//		if (authentication.getExpire() != null) {
//			stringBuilder.append(",expire = ?");
//			args.add(authentication.getExpire());
//		}
//		if (authentication.getRemark() != null) {
//			stringBuilder.append(",remark = ?");
//			args.add(authentication.getRemark());
//		}
//
//		stringBuilder.append(" where id = ?");
//		args.add(authentication.getId());
//		int num = jdbcTemplate.update(stringBuilder.toString(), args.toArray());
//		return num;
//
//	}


//	@Override
//	public Long save(Authentication authentication) throws DaoException {
//
//		return insertAndReturnId(authentication);
//
//	}
//
//	private long insertAndReturnId(Authentication authentication) {
//		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("common_authentication");
//		jdbcInsert.setGeneratedKeyName("id");
//		Map<String, Object> args = new HashMap<String, Object>();
//		args.put("system_id", authentication.getSystemId());
//		if (authentication.getApiInfoList() != null) {
//			args.put("apiInfo_id", authentication.getApiInfoList().get(0).getId());
//		}
//		args.put("token", authentication.getToken());
//		args.put("create_time", new Date());
//		args.put("update_time", new Date());
//		args.put("expire", authentication.getExpire());
//		args.put("remark", authentication.getRemark());
//		long id = jdbcInsert.executeAndReturnKey(args).longValue();
//		return id;
//	}

}
