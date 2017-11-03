package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictIncomeTarget;
import com.unioncast.common.ssp.model.SspDictInterestsTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictInterestsTargetDao;
import com.unioncast.db.rdbms.core.dao.ssp.impl.SspDictIncomeDaoImpl.SspDictIncomeRowMapper;
import com.unioncast.db.rdbms.core.exception.DaoException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictInterestsTargetDao")
public class SspDictInterestsTargetDaoImpl extends SspGeneralDao<SspDictInterestsTarget, Long> implements SspDictInterestsTargetDao {

    private static String FIND_ALL = SqlBuild.select(SspDictInterestsTarget.TABLE_NAME,
            SspDictInterestsTarget.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictInterestsTarget.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictInterestsTarget.TABLE_NAME);

    @Override
    public SspDictInterestsTarget[] find(Long id) throws DaoException {
        List<SspDictInterestsTarget> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictInterestsTargetDaoImpl.SspDictInterestsTargetRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictInterestsTargetDaoImpl.SspDictInterestsTargetRowMapper());
        }

        return list.toArray(new SspDictInterestsTarget[]{});
    }

    @Override
    public SspDictInterestsTarget[] findT(SspDictInterestsTarget s) throws DaoException, IllegalAccessException {
        List<SspDictInterestsTarget> list = new ArrayList<>();
        if (s != null) {
            SspDictInterestsTarget[] sspOrders = find(s, new SspDictInterestsTargetRowMapper(), SspDictInterestsTarget.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictInterestsTargetRowMapper());
        }
        return list.toArray(new SspDictInterestsTarget[]{});
    }

    @Override
    public Pagination<SspDictInterestsTarget> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictInterestsTarget.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictInterestsTargetDaoImpl.SspDictInterestsTargetRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictInterestsTarget.class, ids);
    }

    public final class SspDictInterestsTargetRowMapper implements RowMapper<SspDictInterestsTarget> {
        @Override
        public SspDictInterestsTarget mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictInterestsTarget(rs.getLong("id"), rs.getLong("code"),
                    rs.getString("name"),rs.getLong("level"));
        }
    }

	@Override
	public SspDictInterestsTarget[] batchFindbyCodes(String[] codes) {
		List<SspDictInterestsTarget> list = new ArrayList<SspDictInterestsTarget>();

		if (codes != null && codes.length != 0) {
			for (int i = 0; i < codes.length; i++) {
				List<SspDictInterestsTarget> quer = jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ",
						new SspDictInterestsTargetRowMapper());
				list.addAll(quer);
			}
		}
		return list.toArray(new SspDictInterestsTarget[]{});
	}
}
