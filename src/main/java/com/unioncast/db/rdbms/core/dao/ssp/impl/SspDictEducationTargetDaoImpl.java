package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictEducationTargetDao;
import com.unioncast.db.rdbms.core.dao.ssp.impl.SspDictCrowdSexDaoImpl.SspDictCrowdSexTypeRowMapper;
import com.unioncast.db.rdbms.core.exception.DaoException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictEducationTargetDao")
public class SspDictEducationTargetDaoImpl extends SspGeneralDao<SspDictEducationTarget, Long> implements SspDictEducationTargetDao {


    private static String FIND_ALL = SqlBuild.select(SspDictEducationTarget.TABLE_NAME,
            SspDictEducationTarget.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictEducationTarget.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictEducationTarget.TABLE_NAME);

    @Override
    public SspDictEducationTarget[] find(Long id) throws DaoException {
        List<SspDictEducationTarget> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictEducationTargetDaoImpl.SspDictEducationTargetRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictEducationTargetDaoImpl.SspDictEducationTargetRowMapper());
        }

        return list.toArray(new SspDictEducationTarget[]{});
    }

    @Override
    public SspDictEducationTarget[] findT(SspDictEducationTarget s) throws DaoException, IllegalAccessException {
        List<SspDictEducationTarget> list = new ArrayList<>();
        if (s != null) {
            SspDictEducationTarget[] sspOrders = find(s, new SspDictEducationTargetRowMapper(), SspDictEducationTarget.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictEducationTargetRowMapper());
        }
        return list.toArray(new SspDictEducationTarget[]{});
    }

    @Override
    public Pagination<SspDictEducationTarget> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictEducationTarget.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictEducationTargetDaoImpl.SspDictEducationTargetRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictEducationTarget.class, ids);
    }

    public final class SspDictEducationTargetRowMapper implements RowMapper<SspDictEducationTarget> {
        @Override
        public SspDictEducationTarget mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictEducationTarget(rs.getLong("id"), rs.getLong("code"),
                    rs.getString("name"),rs.getLong("level"));
        }
    }

	@Override
	public SspDictEducationTarget[] batchFindbyCodes(String[] codes) {
		List<SspDictEducationTarget> list = new ArrayList<SspDictEducationTarget>();
		if(codes!=null && codes.length!=0){
			for(int i=0;i<codes.length;i++){
			List<SspDictEducationTarget> quer =jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ",new SspDictEducationTargetRowMapper());
			list.addAll(quer);
			}
		}
		return list.toArray(new SspDictEducationTarget[]{});
	}
}
