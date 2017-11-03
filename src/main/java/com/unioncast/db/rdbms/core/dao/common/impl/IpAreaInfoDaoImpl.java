package com.unioncast.db.rdbms.core.dao.common.impl;

import com.unioncast.common.ip.IpAreaInfo;
import com.unioncast.common.user.model.UnioncastSystem;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.IpAreaInfoDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther wangyao
 * @date 2017-02-27 11:20
 */
@Repository("ipAreaInfoDao")
public class IpAreaInfoDaoImpl extends CommonGeneralDao<IpAreaInfo, Long> implements IpAreaInfoDao {
	private static String FIND_ALL = SqlBuild.select(IpAreaInfo.TABLE_NAME, IpAreaInfo.PROPERTIES);

	@Override
	public IpAreaInfo[] find(Long id) throws DaoException {
		List<IpAreaInfo> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new IpAreaInfoRowMapper(), id));
		} else {
			list = jdbcTemplate.query(FIND_ALL, new IpAreaInfoRowMapper());
		}

		return list.toArray(new IpAreaInfo[] {});
	}

	@Override
	public IpAreaInfo[] findT(IpAreaInfo ipAreaInfo) throws DaoException, IllegalAccessException {
		List<IpAreaInfo> list = new ArrayList<>();
		if (ipAreaInfo != null) {
			IpAreaInfo[] ipAreaInfos = find(ipAreaInfo, new IpAreaInfoRowMapper(), IpAreaInfo.class);
			return ipAreaInfos;
		} else {
			list = jdbcTemplate.query(FIND_ALL, new IpAreaInfoRowMapper());
		}
		return list.toArray(new IpAreaInfo[] {});
	}

	public static final class IpAreaInfoRowMapper implements RowMapper<IpAreaInfo> {

		@Override
		public IpAreaInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new IpAreaInfo(rs.getLong("id"), rs.getString("ip_start"), rs.getString("ip_end"),
					rs.getLong("ip_start_num"), rs.getLong("ip_end_num"), rs.getString("continent"),
					rs.getString("country"), rs.getString("province"), rs.getString("city"), rs.getString("district"),
					rs.getString("isp"), rs.getString("area_code"), rs.getString("country_english"),
					rs.getString("country_code"), rs.getString("longitude"), rs.getString("latitude"),
					rs.getLong("province_id"), rs.getLong("city_id"), rs.getTimestamp("update_time"));
		}
	}
}
