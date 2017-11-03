package com.unioncast.db.rdbms.common.dao;

import com.unioncast.common.adx.model.AdxDspDeliverySetting;
import com.unioncast.common.adx.model.AdxDspFlowAccessSetting;
import com.unioncast.common.adx.model.AdxDspPanter;
import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.*;
import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.AuthenticationApiInfo;
import com.unioncast.common.user.model.User;
import com.unioncast.common.user.model.UserRole;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.mapper.MyRowMapper;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SspGeneralDao<T extends Serializable, ID extends Serializable> extends AbstractGeneralDao<T, ID>
        implements GeneralDao<T, ID> {

    @Autowired
    @Qualifier("sspJdbcTemplate")
    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public T findByAppOrWebId(Long id) {
        return null;
    }

    public List<T> findByUserId(Long id) {
        return null;
    }

    public T[] findBySystemId(Long id) throws DaoException {
        return null;
    }

    public T findByString(String str) throws DaoException {
        return null;
    }

    public T findBySysId(Long id) {
        return null;
    }

    @Override
    public int updateAndReturnNum(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                            sb.append("set " + myColumn.value() + " = ?");
                        else
                            sb.append("," + myColumn.value() + " = ?");
                        args.add(field.get(entity));
                    }
                }
            }
        }
        sb.append(" where " + strId + " = ?");
        args.add(objId);
        return jdbcTemplate.update(sb.toString(), args.toArray());
    }

    @Override
    public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                            sb.append("set " + myColumn.value() + " = ?");
                        else
                            sb.append("," + myColumn.value() + " = ?");
                        args.add(field.get(entity));
                    }
                }
            }
        }
        sb.append(" where " + strId + " = ?");
        args.add(objId);
        jdbcTemplate.update(sb.toString(), args.toArray());
    }

    @Override
    public ID save(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
        return insertAndReturnId(entity);
    }

    @SuppressWarnings("unchecked")
    private ID insertAndReturnId(T entity) throws IllegalArgumentException, IllegalAccessException {
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
                        args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                    }
                }
            }
        }
        return (ID) jdbcInsert.executeAndReturnKey(args);
    }

    public int updateNotNullFieldAndReturn(User user) throws DaoException {
        return 0;
    }

    public User findByUsername(String username) throws DaoException {
        return null;
    }

    public User findByEmail(String email) throws DaoException {
        return null;
    }

    public int deleteUserRoleByUserId(Long id) throws DaoException {
        return 0;
    }

    public int batchAddOneUesrIdAndManyRoleId(Long uesrId, List<Long> roleIds) throws DaoException {
        return 0;
    }

    public int batchAddManyUesrIdAndOneRoleId(Long roleId, List<Long> uesrIds) throws DaoException {
        return 0;
    }

    public int deleteUserRoleByRoleId(Long id) throws DaoException {
        return 0;
    }

    public int batchDeleteUserRoleByRoleId(List<Long> ids) throws DaoException {
        return 0;
    }

    public List<Long> batchAddUserRole(List<UserRole> userRoles) throws DaoException {
        return null;
    }

    public int updateNotNullFieldAndReturn(Authentication authentications) throws DaoException {
        return 0;
    }

    public int deleteAuthApiInfoByAuthId(Long id) throws DaoException {
        return 0;
    }

    public int deleteAuthApiInfoByApiInfoId(Long id) throws DaoException {
        return 0;
    }

    public int batchDeleteAuthApiInfoByAuthId(List<Long> ids) throws DaoException {
        return 0;
    }

    public List<Long> batchAddAuthenticationApiInfo(List<AuthenticationApiInfo> authenticationApiInfos)
            throws DaoException {
        return null;
    }

    public AdxDspDeliverySetting[] findByAdxOrSspId(Long adxOrSspId) throws DaoException {
        return null;
    }

    public AdxDspPanter[] findAdxDspPanterByIdstype(Long flowType, Long[] ids) throws DaoException {
        return null;
    }

    ;

    public Long[] batchAdd(T[] entitys) throws DaoException {
        // if(ArrayUtils.isNotEmpty(entitys)){
        //
        // Class<?> clazzT = entitys[0].getClass();
        // MyTable myTable = clazzT.getAnnotation(MyTable.class);
        //
        // String BATCH_ADD = SqlBuild.batchAdd(myTable.value(),AdxDspAccessSettings.PROPERTIES);
        //
        // List<Long> list = null;
        // try {
        // Connection connection = jdbcTemplate.getDataSource().getConnection();
        // connection.setAutoCommit(false);
        //
        //
        // PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
        // for (T entity : entitys) {
        //
        // Class<?> clazz = entity.getClass();
        //
        //
        //
        // if (qualification.getUser() != null)
        // pstmt.setLong(1, qualification.getUser().getId());
        // else
        // pstmt.setNull(1, Types.NULL);
        // pstmt.setString(2, qualification.getName());
        // if (qualification.getFiletype() != null)
        // pstmt.setInt(3, qualification.getFiletype());
        // else
        // pstmt.setNull(3, Types.INTEGER);
        // pstmt.setString(4, qualification.getFilevalidatecode());
        // pstmt.setString(5, qualification.getUrl());
        // pstmt.setString(6, qualification.getScope());
        // if (qualification.getStarttime() != null)
        // pstmt.setTimestamp(7, new Timestamp(qualification.getStarttime().getTime()));
        // else
        // pstmt.setNull(7, Types.TIMESTAMP);
        // if (qualification.getEndtime() != null)
        // pstmt.setTimestamp(8, new Timestamp(qualification.getEndtime().getTime()));
        // else
        // pstmt.setNull(8, Types.TIMESTAMP);
        // pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
        // pstmt.addBatch();
        // }
        // pstmt.executeBatch();
        // connection.commit();
        // ResultSet rs = pstmt.getGeneratedKeys();
        // list = new ArrayList<>();
        // while (rs.next()) {
        // list.add(rs.getLong(1));
        // }
        // connection.close();
        // pstmt.close();
        // rs.close();
        // } catch (SQLException e) {
        // e.printStackTrace();
        // }
        // return list;
        //
        //
        // }
        return null;
    }

    public int batchDelete(Long[] ids) throws DaoException {
        return 0;
    }

    public AdxDspFlowAccessSetting[] findByIds(Long[] ids) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> page(PageCriteria pageCriteria) throws DaoException {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pagination<T> page(PageCriteria pageCriteria, String findAllSql, String countAllSql, RowMapper<T> rowMapper) throws DaoException {
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        StringBuilder pageSql = new StringBuilder(findAllSql + " where 1=1");
        StringBuilder countAll = new StringBuilder(countAllSql + " where 1=1");
        if (searchExpressionList != null && searchExpressionList.size() != 0) {
            for (int i = 0; i < searchExpressionList.size(); i++) {
                String value = (String) searchExpressionList.get(i).getValue();
                /*if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator()))
                    value = "'%" + value + "%'";*/
                //TODO  上面注释代码，比如传时间查询，没有拼接引号
                if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
                    value = " '%" + value + "%' ";
                } else {
                    value = " '" + value + "' ";
                }
                String criteriaSql = " " + pageCriteria.getPredicate() + " "
                        + searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator()
                        + " " + value;
                pageSql.append(criteriaSql);
                countAll.append(criteriaSql);
            }
        }
        List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
        if (orderExpressionList != null && orderExpressionList.size() != 0) {
            pageSql.append(" order by");
            for (int i = 0; i < orderExpressionList.size(); i++) {
                if (i > 0) pageSql.append(",");
                pageSql.append(
                        " " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
            }
        }
        pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
                + pageCriteria.getPageSize());
        int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);

        List<T> list = jdbcTemplate.query(pageSql.toString(), rowMapper);

        return new Pagination<T>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), (T[]) list.toArray((T[]) Array.newInstance(pageCriteria.getEntityClass(), list.size())));
    }

    /**
     * @param userFieldName 所要传入的用户id表字段名称
     * @param userId        用户id值
     */
    @SuppressWarnings("unchecked")
    @Override
    public Pagination<T> page(PageCriteria pageCriteria, String findAllSql, String countAllSql, RowMapper<T> rowMapper, String userFieldName, Long userId) throws DaoException {
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        StringBuilder pageSql = new StringBuilder(findAllSql + " where 1=1" + " and " + userFieldName + "=" + userId);
        StringBuilder countAll = new StringBuilder(countAllSql + " where 1=1" + " and " + userFieldName + "=" + userId);
        if (searchExpressionList != null && searchExpressionList.size() != 0) {
            for (int i = 0; i < searchExpressionList.size(); i++) {
                String value = (String) searchExpressionList.get(i).getValue();
                if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
                    value = " '%" + value + "%' ";
                } else {
                    value = " '" + value + "' ";
                }
                String criteriaSql = " " + pageCriteria.getPredicate() + " "
                        + searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator()
                        + " " + value;
                pageSql.append(criteriaSql);
                countAll.append(criteriaSql);
            }
        }
        List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
        if (orderExpressionList != null && orderExpressionList.size() != 0) {
            pageSql.append(" order by");
            for (int i = 0; i < orderExpressionList.size(); i++) {
                if (i > 0) pageSql.append(",");
                pageSql.append(
                        " " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
            }
        }
        pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
                + pageCriteria.getPageSize());
        int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);

        List<T> list = jdbcTemplate.query(pageSql.toString(), rowMapper);

        return new Pagination<T>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), (T[]) list.toArray((T[]) Array.newInstance(pageCriteria.getEntityClass(), list.size())));
    }

    public T[] find(ID id) throws DaoException {
        return null;
    }

    public T[] findT(T id) throws DaoException, IllegalAccessException {
        return null;
    }

    public T[] find(T entity, RowMapper<T> rowMapper, Class claz) throws DaoException, IllegalAccessException {
        List<Object> args = new ArrayList<>();
        Class<?> clazz = entity.getClass();
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        StringBuilder sb = new StringBuilder(SqlBuild.select(myTable.value()));
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
                        sb.append("and " + myColumn.value() + " = ? ");
                        try {
                            Object obj = field.get(entity);
                            Class objClass = obj.getClass();
                            if(!CommonUtil.isJavaClass(objClass)){
                                Field idF = objClass.getDeclaredField("id");
                                idF.setAccessible(true);
                                Object idObj = idF.get(obj);
                                args.add(idObj);
                            }else{
                                args.add(obj);
                            }
                        } catch (Exception e) {
                            args.add(field.get(entity));
                        }
                    }
                }
            }
        }
        List<T> query = jdbcTemplate.query(sb.toString(), rowMapper, args.toArray());
        return (T[]) query.toArray((T[]) Array.newInstance(claz, query.size()));

    }

    public AdxDspFlowAccessSetting[] findByDspIds(Long[] ids) {
        return null;
    }

    @Override
    public Long[] add(T[] entitys) throws SQLException, DaoException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = entitys[0].getClass();
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        StringBuilder sqlSb = new StringBuilder("insert into " + myTable.value());
        StringBuilder key = new StringBuilder("(");
        StringBuilder placeholder = new StringBuilder(" values(");
        MyId myId = null;
        MyColumn myColumn = null;
        Field[] classFields = clazz.getDeclaredFields();
        List<Field> needFields = new ArrayList<>();
        for (Field field : classFields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                myId = field.getAnnotation(MyId.class);
                if (myId == null) {
                    needFields.add(field);
                    myColumn = field.getDeclaredAnnotation(MyColumn.class);
                    if (myColumn != null) {
                        key.append(myColumn.value() + ",");
                        placeholder.append("?,");
                    }
                }
            }
        }
        String keyStr = key.substring(0, key.length() - 1);
        keyStr += ") ";
        String placeholderStr = placeholder.substring(0, placeholder.length() - 1);
        placeholderStr += ") ";
        sqlSb.append(keyStr).append(placeholderStr).toString();
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstmt = connection.prepareStatement(sqlSb.toString(),
                PreparedStatement.RETURN_GENERATED_KEYS);
        for (T entity : entitys) {
            int parameterIndex = 1;
            for (Field field : needFields) {
                pstmt.setObject(parameterIndex++, field.get(entity));
            }
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        connection.commit();
        ResultSet rs = pstmt.getGeneratedKeys();
        List<Long> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getLong(1));
        }
        connection.close();
        pstmt.close();
        rs.close();
        return list.toArray(new Long[]{});
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException {
        ArrayList<Object> arrayList = getFindSqlAndList(pageCriteria);
        StringBuilder sql = (StringBuilder) arrayList.get(0);
        List<Object> list = (List<Object>) arrayList.get(1);
        List<T> results = jdbcTemplate.query(sql.toString(), new MyRowMapper<T>(pageCriteria.getEntityClass()),
                list.toArray());
        return (T[]) results.toArray((T[]) Array.newInstance(pageCriteria.getEntityClass(), results.size()));
    }

    protected ArrayList<Object> getFindSqlAndList(PageCriteria pageCriteria) {
        Class<?> clazz = pageCriteria.getEntityClass();
        MyTable myTable = clazz.getDeclaredAnnotation(MyTable.class);
        List<SearchExpression> searchExpression = pageCriteria.getSearchExpressionList();
        StringBuilder sql = new StringBuilder("select * from " + myTable.value() + " where 1=1");
        List<Object> list = new ArrayList<>();
        for (SearchExpression searchExpression1 : searchExpression) {
            String propertyName = searchExpression1.getPropertyName();
            Object value = searchExpression1.getValue();
            Operation operation = searchExpression1.getOperation();
            Field field = null;
            try {
                field = clazz.getDeclaredField(propertyName);
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            MyColumn myColumn = field.getDeclaredAnnotation(MyColumn.class);
            sql.append(myColumn.value() + " " + operation.getOperator() + " ?");
            switch (operation) {
                case LIKE:
                    list.add("%" + value + "%");
                    break;
                default:
                    list.add(value);
                    break;
            }
        }
        ArrayList<Object> result = new ArrayList<>();
        result.add(sql);
        result.add(list);
        return result;
    }

    /**
     * 基础分页方法
     * juchaochao
     */
    @SuppressWarnings("unchecked")
    @Override
    public Pagination<T> generalPage(PageCriteria pageCriteria)
            throws DaoException, InstantiationException, IllegalAccessException {
        ArrayList<Object> arrayList = getFindSqlAndList(pageCriteria);
        StringBuilder sql = (StringBuilder) arrayList.get(0);
        List<Object> list = (List<Object>) arrayList.get(1);
        int start = 0;
        start = (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize();
        sql.append(" limit " + start + "," + pageCriteria.getPageSize());
        List<T> results = jdbcTemplate.query(sql.toString(), new MyRowMapper<T>(pageCriteria.getEntityClass()),
                list.toArray());
        return new Pagination<T>(1, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
                results.toArray((T[]) Array.newInstance(pageCriteria.getEntityClass(), results.size())));
    }

    @Override
    public int delete(Class<?> clazz, ID[] ids) throws DaoException {
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        StringBuilder sqlSb = new StringBuilder("delete from " + myTable.value() + " where id = ?");
        List<Object[]> batchArgs = new ArrayList<>();
        for (ID id : ids) {
            Object[] objects = new Object[]{id};
            batchArgs.add(objects);
        }
        int[] intArray = jdbcTemplate.batchUpdate(sqlSb.toString(), batchArgs);
        int deleteCount = 0;
        for (int i : intArray) {
            deleteCount += i;
        }
        return deleteCount;
    }

    @Override
    public int updateNotNullField(T[] entities)
            throws DaoException, IllegalArgumentException, IllegalAccessException, SQLException {
        List<Object> args = new ArrayList<>();
        Class<?> clazz = entities[0].getClass();
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        StringBuilder builder = new StringBuilder("update " + myTable.value());
        MyColumn myColumn = null;
        MyId myId = null;
        String idColumunName = null;
        Object idColumnValue = null;
        Field[] fields = clazz.getDeclaredFields();
        List<Field> needFields = new ArrayList<>();
        Field idField = null;
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                if (field.get(entities[0]) != null) {
                    myId = field.getDeclaredAnnotation(MyId.class);
                    if (myId == null) {
                        needFields.add(field);
                        myColumn = field.getDeclaredAnnotation(MyColumn.class);
                        if ("?".equals(builder.subSequence(builder.length() - 1, builder.length())))
                            builder.append("," + myColumn.value() + " = ?");
                        else
                            builder.append(" set " + myColumn.value() + " = ?");
                        args.add(field.get(entities[0]));
                    } else {
                        idField = field;
                        idColumunName = myId.value();
                        idColumnValue = field.get(entities[0]);
                    }
                }
            }
        }
        builder.append(" where " + idColumunName + " = ?");
        args.add(idColumnValue);
        final Field finalIdFiel = idField;
        int[] arrayInt = jdbcTemplate.batchUpdate(builder.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                T entity = entities[i];
                try {
                    int parameterIndex = 1;
                    for (Field field : needFields) {
                        ps.setObject(parameterIndex++, field.get(entity));
                    }
                    ps.setObject(parameterIndex, finalIdFiel.get(entity));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public int getBatchSize() {
                return 0;
            }
        });
        int count = 0;
        for (int i : arrayInt) {
            count += i;
        }
        return count;
    }

}
