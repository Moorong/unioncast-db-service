package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictPlatform;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictPlatformDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictPlatformDao")
public class SspDictPlatformDaoImpl extends SspGeneralDao<SspDictPlatform, Long> implements SspDictPlatformDao {

    private static String FIND_ALL = SqlBuild.select(SspDictPlatform.TABLE_NAME,
            SspDictPlatform.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictPlatform.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictPlatform.TABLE_NAME);

    @Override
    public SspDictPlatform[] find(Long id) throws DaoException {
        List<SspDictPlatform> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictPlatformDaoImpl.SspDictPlatformRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictPlatformDaoImpl.SspDictPlatformRowMapper());
        }

        return list.toArray(new SspDictPlatform[]{});
    }

    @Override
    public SspDictPlatform[] findT(SspDictPlatform s) throws DaoException, IllegalAccessException {
        List<SspDictPlatform> list = new ArrayList<>();
        if (s != null) {
            SspDictPlatform[] sspOrders = find(s, new SspDictPlatformRowMapper(), SspDictPlatform.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictPlatformRowMapper());
        }
        return list.toArray(new SspDictPlatform[]{});
    }


    @Override
    public Pagination<SspDictPlatform> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictPlatform.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictPlatformDaoImpl.SspDictPlatformRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictPlatform.class, ids);
    }

    public static final class SspDictPlatformRowMapper implements RowMapper<SspDictPlatform> {
        @Override
        public SspDictPlatform mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictPlatform(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"),
                    rs.getString("name"),null);
        }
    }
}
