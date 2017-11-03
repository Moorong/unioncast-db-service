package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.common.ssp.model.SspDictMobileBrandType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictMobileBrandTypeDao;
import com.unioncast.db.rdbms.core.dao.ssp.impl.SspCityInfoDaoImpl.SspCityInfoRowMapper;
import com.unioncast.db.rdbms.core.exception.DaoException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspDictMobileBrandTypeDao")
public  class SspDictMobileBrandTypeDaoImpl extends SspGeneralDao<SspDictMobileBrandType, Long> implements SspDictMobileBrandTypeDao {

    private static String FIND_ALL = SqlBuild.select(SspDictMobileBrandType.TABLE_NAME,
            SspDictMobileBrandType.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspDictMobileBrandType.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspDictMobileBrandType.TABLE_NAME);

    @Override
    public SspDictMobileBrandType[] find(Long id) throws DaoException {
        List<SspDictMobileBrandType> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspDictMobileBrandTypeDaoImpl.SspDictMobileBrandTypeRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictMobileBrandTypeDaoImpl.SspDictMobileBrandTypeRowMapper());
        }

        return list.toArray(new SspDictMobileBrandType[]{});
    }

    @Override
    public SspDictMobileBrandType[] findT(SspDictMobileBrandType s) throws DaoException, IllegalAccessException {
        List<SspDictMobileBrandType> list = new ArrayList<>();
        if (s != null) {
            SspDictMobileBrandType[] sspOrders = find(s, new SspDictMobileBrandTypeRowMapper(), SspDictMobileBrandType.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspDictMobileBrandTypeRowMapper());
        }
        return list.toArray(new SspDictMobileBrandType[]{});
    }

    @Override
    public Pagination<SspDictMobileBrandType> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspDictMobileBrandType.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspDictMobileBrandTypeDaoImpl.SspDictMobileBrandTypeRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspDictMobileBrandType.class, ids);
    }

    public final class SspDictMobileBrandTypeRowMapper implements RowMapper<SspDictMobileBrandType> {
        @Override
        public SspDictMobileBrandType mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new SspDictMobileBrandType(rs.getLong("id"), rs.getLong("code"), rs.getInt("level"),
                    rs.getString("name"));
        }
    }

	@Override
	public SspDictMobileBrandType[] batchFindbyCodes(String[] codes) {
		List<SspDictMobileBrandType> list = new ArrayList<SspDictMobileBrandType>();
		if (codes!=null && codes.length!=0) {
            for (int i = 0; i < codes.length; i++) {
                List<SspDictMobileBrandType> query = jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ", new SspDictMobileBrandTypeRowMapper());
                list.addAll(query);
            }
        }
		return list.toArray(new SspDictMobileBrandType[]{});
	}
}
