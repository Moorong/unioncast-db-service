package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictCreativeSize;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictCreativeSizeDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictCreativeSizeDao")
public class SspDictCreativeSizeDaoImpl extends SspGeneralDao<SspDictCreativeSize, Long> implements SspDictCreativeSizeDao {


    private static String FIND_ALL = SqlBuild.select(SspDictCreativeSize.TABLE_NAME,
            SspDictCreativeSize.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictCreativeSize.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictCreativeSize.TABLE_NAME);

    @Override
    public SspDictCreativeSize[] find(Long id) throws DaoException {
        List<SspDictCreativeSize> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictCreativeSizeDaoImpl.SspDictCreativeSizeRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictCreativeSizeDaoImpl.SspDictCreativeSizeRowMapper());
        }

        return list.toArray(new SspDictCreativeSize[]{});
    }

    @Override
    public SspDictCreativeSize[] findT(SspDictCreativeSize s) throws DaoException, IllegalAccessException {
        List<SspDictCreativeSize> list = new ArrayList<>();
        SspDictCreativeSize creativeSize = new SspDictCreativeSize();
        Boolean flag = creativeSize.equals(s);
        if (s != null) {
            SspDictCreativeSize[] sspOrders = find(s, new SspDictCreativeSizeRowMapper(), SspDictCreativeSize.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictCreativeSizeRowMapper());
        }
        return list.toArray(new SspDictCreativeSize[]{});
    }


    @Override
    public Pagination<SspDictCreativeSize> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictCreativeSize.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictCreativeSizeDaoImpl.SspDictCreativeSizeRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictCreativeSize.class, ids);
    }

    public static final class SspDictCreativeSizeRowMapper implements RowMapper<SspDictCreativeSize> {
        @Override
        public SspDictCreativeSize mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictCreativeSize(rs.getLong("id"), rs.getInt("type"),
                    rs.getInt("width"), rs.getInt("height"));
        }
    }

}
