package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictIndustry;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictIndustryDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictIndustryDao")
public class SspDictIndustryDaoImpl extends SspGeneralDao<SspDictIndustry, Long> implements SspDictIndustryDao {

    private static String FIND_ALL = SqlBuild.select(SspDictIndustry.TABLE_NAME,
            SspDictIndustry.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictIndustry.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictIndustry.TABLE_NAME);

    @Override
    public SspDictIndustry[] find(Long id) throws DaoException {
        List<SspDictIndustry> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictIndustryDaoImpl.SspDictIndustryRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictIndustryDaoImpl.SspDictIndustryRowMapper());
        }

        return list.toArray(new SspDictIndustry[]{});
    }

    @Override
    public SspDictIndustry[] findT(SspDictIndustry s) throws DaoException, IllegalAccessException {
        List<SspDictIndustry> list = new ArrayList<>();
        if (s != null) {
            SspDictIndustry[] sspOrders = find(s, new SspDictIndustryRowMapper(), SspDictIndustry.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictIndustryRowMapper());
        }
        return list.toArray(new SspDictIndustry[]{});
    }

    @Override
    public Pagination<SspDictIndustry> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictIndustry.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictIndustryDaoImpl.SspDictIndustryRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictIndustry.class, ids);
    }

    public static final class SspDictIndustryRowMapper implements RowMapper<SspDictIndustry> {
        @Override
        public SspDictIndustry mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictIndustry(rs.getLong("id"), rs.getLong("code"), rs.getLong("level")
                    , rs.getString("name"), null);
        }
    }
}
