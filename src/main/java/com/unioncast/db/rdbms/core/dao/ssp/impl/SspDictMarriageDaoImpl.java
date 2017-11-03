package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictMarriageDao;
import com.unioncast.db.rdbms.core.dao.ssp.impl.SspDictBuyTargetDaoImpl.SspDictBuyTargetRowMapper;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("sspDictMarriageDao")
public class SspDictMarriageDaoImpl extends SspGeneralDao<SspDictMarriageTarget, Long> implements SspDictMarriageDao {

	public final class SspDictMarriageRowMapper implements RowMapper<SspDictMarriageTarget> {

		@Override
		public SspDictMarriageTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SspDictMarriageTarget(rs.getLong("id"), rs.getLong("code"), rs.getString("name"),
					rs.getLong("level"));
		}

	}

	private static String FIND_ALL = SqlBuild
			.select(SspDictMarriageTarget.TABLE_NAME, SspDictMarriageTarget.PROPERTIES);
	private static String COUNT_ALL = SqlBuild.countAll(SspDictMarriageTarget.TABLE_NAME);

	public SspDictMarriageTarget[] findT(SspDictMarriageTarget s) throws DaoException, IllegalAccessException {
		List<SspDictMarriageTarget> list = new ArrayList<>();
		if (s != null) {
			SspDictMarriageTarget[] sspDictMarriageTargets = find(s, new SspDictMarriageRowMapper(),
					SspDictMarriageTarget.class);
			return sspDictMarriageTargets;
		} else {
			list = jdbcTemplate.query(FIND_ALL, new SspDictMarriageRowMapper());
		}
		return list.toArray(new SspDictMarriageTarget[] {});
	}

	@Override
	public SspDictMarriageTarget[] batchFindbyCodes(String[] codes) {
		List<SspDictMarriageTarget> list = new ArrayList<SspDictMarriageTarget>();

		if (codes != null && codes.length != 0) {
			for (int i = 0; i < codes.length; i++) {
				List<SspDictMarriageTarget> quer = jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ",
						new SspDictMarriageRowMapper());
				list.addAll(quer);
			}
		}
		return list.toArray(new SspDictMarriageTarget[] {});
	}
}
