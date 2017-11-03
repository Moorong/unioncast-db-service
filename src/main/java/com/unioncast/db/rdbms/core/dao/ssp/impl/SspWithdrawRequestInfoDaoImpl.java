package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspFinanceInfo;
import com.unioncast.common.ssp.model.SspWithdrawRequestInfo;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspWithdrawRequestInfoDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.UserService;
import com.unioncast.db.rdbms.core.service.ssp.SspFinanceInfoService;
import com.unioncast.db.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sspWithdrawRequestInfoDao")
public class SspWithdrawRequestInfoDaoImpl extends SspGeneralDao<SspWithdrawRequestInfo, Long> implements SspWithdrawRequestInfoDao {
    private static String FIND_ALL = SqlBuild.select(SspWithdrawRequestInfo.TABLE_NAME,
            SspWithdrawRequestInfo.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspWithdrawRequestInfo.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspWithdrawRequestInfo.TABLE_NAME);

    @Override
    public SspWithdrawRequestInfo[] find(Long id) throws DaoException {
        List<SspWithdrawRequestInfo> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspWithdrawRequestInfoDaoImpl.SspWithdrawRequestInfoRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspWithdrawRequestInfoDaoImpl.SspWithdrawRequestInfoRowMapper());
        }

        return list.toArray(new SspWithdrawRequestInfo[]{});
    }

    @Override
    public SspWithdrawRequestInfo[] findT(SspWithdrawRequestInfo s) throws DaoException, IllegalAccessException {
        List<SspWithdrawRequestInfo> list = new ArrayList<>();
        if (s != null) {
            SspWithdrawRequestInfo[] sspOrders = find(s, new SspWithdrawRequestInfoRowMapper(), SspWithdrawRequestInfo.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspWithdrawRequestInfoRowMapper());
        }
        return list.toArray(new SspWithdrawRequestInfo[]{});
    }

    @Override
    public int updateAndReturnNum(SspWithdrawRequestInfo entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        if (myColumn.value().equals("account_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getUser().getId());

                        } else if (myColumn.value().equals("finance_info_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspFinanceInfo().getId());
                        } else {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(field.get(entity));
                        }
                    }
                }
            }
        }
        sb.append(" where " + strId + " = ?");
        args.add(objId);
        return jdbcTemplate.update(sb.toString(), args.toArray());
    }

    @Override
    public Pagination<SspWithdrawRequestInfo> page(PageCriteria pageCriteria)
            throws DaoException {
        if(null==pageCriteria)
            return null;
        if(null==pageCriteria.getCurrUserId())
            return null;
        pageCriteria.setEntityClass(SspWithdrawRequestInfo.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspWithdrawRequestInfoDaoImpl.SspWithdrawRequestInfoRowMapper(), "account_id", pageCriteria.getCurrUserId());

    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspWithdrawRequestInfo.class, ids);
    }


    @Override
    public Long save(SspWithdrawRequestInfo entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

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
                        if (myColumn.value().equals("account_id")) {
                            args.put("account_id", entity.getUser() == null ? null : entity.getUser().getId());
                        } else if (myColumn.value().equals("finance_info_id")) {
                            args.put("finance_info_id", entity.getSspFinanceInfo() == null ? null : entity.getSspFinanceInfo().getId());
                        } else {
                            args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                        }
                    }
                }
            }
        }
        return jdbcInsert.executeAndReturnKey(args).longValue();
    }


    public static final class SspWithdrawRequestInfoRowMapper implements RowMapper<SspWithdrawRequestInfo> {
        @Override
        public SspWithdrawRequestInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long accountId = rs.getLong("account_id");
            User user = null;
            if (accountId != null) {
                try {
                    UserService userService = SpringUtils.getBean(UserService.class);
                    user = userService.findById(accountId);
                } catch (Exception e) {
                    user = new User();
                    user.setId(accountId);
                }
                if(CommonUtil.isNull(user)){
                    user = new User();
                    user.setId(accountId);
                }
            }

            Long inanceInfo = rs.getLong("finance_info_id");
            SspFinanceInfo spFinanceInfo = null;
            if (inanceInfo != null) {
                try {
                    SspFinanceInfoService sspFinanceInfoService = SpringUtils.getBean(SspFinanceInfoService.class);
                    spFinanceInfo = sspFinanceInfoService.findById(inanceInfo);
                } catch (Exception e) {
                    spFinanceInfo = new SspFinanceInfo();
                    spFinanceInfo.setId(inanceInfo);
                }
                if(CommonUtil.isNull(user)){
                    spFinanceInfo = new SspFinanceInfo();
                    spFinanceInfo.setId(inanceInfo);
                }
            }


            return new SspWithdrawRequestInfo(rs.getLong("id"), user, rs.getDouble("preTax"), rs.getDouble("tax"), rs.getDouble("afterTax"), rs.getLong("state"), rs.getTimestamp("create_time"), rs.getTimestamp("update_time"), spFinanceInfo, rs.getLong("delete_state"));
        }
    }


	@Override
	public SspWithdrawRequestInfo findByDeveloperId(Long developerId) {
		String sql = "SELECT * FROM ssp_withdraw_request_info WHERE finance_info_id in (SELECT id FROM ssp_finance_info WHERE account_id = ?)";
		
		return null;
	}
}
