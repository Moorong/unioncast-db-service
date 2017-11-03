package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.ssp.model.SspDictIncomeTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictIncomeDao;
import com.unioncast.db.rdbms.core.dao.ssp.impl.SspDictMarriageDaoImpl.SspDictMarriageRowMapper;
import com.unioncast.db.rdbms.core.exception.DaoException;
@Repository("sspDictIncomeDao")
public class SspDictIncomeDaoImpl extends SspGeneralDao<SspDictIncomeTarget,Long> implements SspDictIncomeDao{

	
	public final class SspDictIncomeRowMapper implements RowMapper<SspDictIncomeTarget> {

		@Override
		public SspDictIncomeTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictIncomeTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),rs.getLong("level"));
		}
		
	}
	
	private static String FIND_ALL = SqlBuild.select(SspDictIncomeTarget.TABLE_NAME, SspDictIncomeTarget.PROPERTIES);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictIncomeTarget.TABLE_NAME);
	  public SspDictIncomeTarget[] findT(SspDictIncomeTarget s) throws DaoException, IllegalAccessException {
	        List<SspDictIncomeTarget> list = new ArrayList<>();
	        if (s != null) {
	        	SspDictIncomeTarget[] sspDictIncomeTargets = find(s, new SspDictIncomeRowMapper(), SspDictIncomeTarget.class);
	            return sspDictIncomeTargets;
	        } else {
	            list = jdbcTemplate.query(FIND_ALL, new SspDictIncomeRowMapper());
	        }
	        return list.toArray(new SspDictIncomeTarget[]{});
	    }
	@Override
	public SspDictIncomeTarget[] batchFindbyCodes(String[] codes) {
		List<SspDictIncomeTarget> list = new ArrayList<SspDictIncomeTarget>();

		if (codes != null && codes.length != 0) {
			for (int i = 0; i < codes.length; i++) {
				List<SspDictIncomeTarget> quer = jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ",
						new SspDictIncomeRowMapper());
				list.addAll(quer);
			}
		}
		return list.toArray(new SspDictIncomeTarget[]{});
	}
	
}
