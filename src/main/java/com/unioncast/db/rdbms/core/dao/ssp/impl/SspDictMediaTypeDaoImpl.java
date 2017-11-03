package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictMediaType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictMediaTypeDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictMediaTypeDao")
public class SspDictMediaTypeDaoImpl extends SspGeneralDao<SspDictMediaType, Long> implements SspDictMediaTypeDao {

    private static String FIND_ALL = SqlBuild.select(SspDictMediaType.TABLE_NAME,
            SspDictMediaType.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictMediaType.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictMediaType.TABLE_NAME);

    @Override
    public SspDictMediaType[] find(Long id) throws DaoException {
        List<SspDictMediaType> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictMediaTypeDaoImpl.SspDictMediaTypeRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictMediaTypeDaoImpl.SspDictMediaTypeRowMapper());
        }

        return list.toArray(new SspDictMediaType[]{});
    }

    @Override
    public SspDictMediaType[] findT(SspDictMediaType s) throws DaoException, IllegalAccessException {
        List<SspDictMediaType> list = new ArrayList<>();
        if (s != null) {
            SspDictMediaType[] sspOrders = find(s, new SspDictMediaTypeRowMapper(), SspDictMediaType.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictMediaTypeRowMapper());
        }
        return list.toArray(new SspDictMediaType[]{});
    }

    @Override
    public Pagination<SspDictMediaType> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictMediaType.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictMediaTypeDaoImpl.SspDictMediaTypeRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictMediaType.class, ids);
    }

    public final class SspDictMediaTypeRowMapper implements RowMapper<SspDictMediaType> {
        @Override
        public SspDictMediaType mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictMediaType(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"),
                    rs.getString("name"));
        }
    }
}
