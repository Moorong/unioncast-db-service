package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictSysOperationType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictSysOperationTypeDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictSysOperationTypeDao")
public class SspDictSysOperationTypeDaoImpl extends SspGeneralDao<SspDictSysOperationType, Long> implements SspDictSysOperationTypeDao {

    private static String FIND_ALL = SqlBuild.select(SspDictSysOperationType.TABLE_NAME,
            SspDictSysOperationType.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictSysOperationType.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictSysOperationType.TABLE_NAME);

    @Override
    public SspDictSysOperationType[] find(Long id) throws DaoException {
        List<SspDictSysOperationType> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictSysOperationTypeDaoImpl.SspDictSysOperationTypeRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictSysOperationTypeDaoImpl.SspDictSysOperationTypeRowMapper());
        }

        return list.toArray(new SspDictSysOperationType[]{});
    }

    @Override
    public SspDictSysOperationType[] findT(SspDictSysOperationType s) throws DaoException, IllegalAccessException {
        List<SspDictSysOperationType> list = new ArrayList<>();
        if (s != null) {
            SspDictSysOperationType[] sspOrders = find(s, new SspDictSysOperationTypeRowMapper(), SspDictSysOperationType.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictSysOperationTypeRowMapper());
        }
        return list.toArray(new SspDictSysOperationType[]{});
    }

    @Override
    public Pagination<SspDictSysOperationType> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictSysOperationType.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictSysOperationTypeDaoImpl.SspDictSysOperationTypeRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictSysOperationType.class, ids);
    }

    public final class SspDictSysOperationTypeRowMapper implements RowMapper<SspDictSysOperationType> {
        @Override
        public SspDictSysOperationType mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictSysOperationType(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"),
                    rs.getString("name"));
        }
    }
}
