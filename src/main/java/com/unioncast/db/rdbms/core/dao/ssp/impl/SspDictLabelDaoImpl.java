package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictLabel;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictLabelDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictLabelDao")
public class SspDictLabelDaoImpl extends SspGeneralDao<SspDictLabel, Long> implements SspDictLabelDao {

    private static String FIND_ALL = SqlBuild.select(SspDictLabel.TABLE_NAME,
            SspDictLabel.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictLabel.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictLabel.TABLE_NAME);

    @Override
    public SspDictLabel[] find(Long id) throws DaoException {
        List<SspDictLabel> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictLabelDaoImpl.SspDictLabelRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictLabelDaoImpl.SspDictLabelRowMapper());
        }

        return list.toArray(new SspDictLabel[]{});
    }

    @Override
    public SspDictLabel[] findT(SspDictLabel s) throws DaoException, IllegalAccessException {
        List<SspDictLabel> list = new ArrayList<>();
        if (s != null) {
            SspDictLabel[] sspDictLabels = find(s, new SspDictLabelRowMapper(), SspDictLabel.class);
            return sspDictLabels;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictLabelRowMapper());
        }
        return list.toArray(new SspDictLabel[]{});
    }

    @Override
    public Pagination<SspDictLabel> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictLabel.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictLabelDaoImpl.SspDictLabelRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictLabel.class, ids);
    }
    
    @Override
	public SspDictLabel[] findPage(Integer clickCount) {
		List<SspDictLabel> dictLabels = jdbcTemplate.query("SELECT * FROM ssp_dict_label LIMIT ?,5", new SspDictLabelRowMapper(), clickCount*5);
		return dictLabels.toArray(new SspDictLabel[]{});
	}
    
    @Override
    public int countAll() throws DaoException {
    	return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM ssp_dict_label", Integer.class);
    }

    public static final class SspDictLabelRowMapper implements RowMapper<SspDictLabel> {
        @Override
        public SspDictLabel mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictLabel(rs.getLong("id"), rs.getLong("code"),
                    rs.getString("name"));
        }
    }
}
