package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictCrowdSexDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("sspDictCrowdSexDao")
public class SspDictCrowdSexDaoImpl extends SspGeneralDao<SspDictCrowdSexType, Long>implements SspDictCrowdSexDao{
	
	
	
	public final class SspDictCrowdSexTypeRowMapper implements RowMapper<SspDictCrowdSexType> {
	@Override
	public SspDictCrowdSexType mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new SspDictCrowdSexType(rs.getLong("id"), rs.getLong("code"), rs.getLong("level"),rs.getString("name"));
	}
	
}



private static String FIND_ALL = SqlBuild.select(SspDictCrowdSexType.TABLE_NAME, SspDictCrowdSexType.PROPERTIES);
//private static String ADD = "insert into " + SspDictBuyTarget.TABLE_NAME + "("
//		+ SspDictBuyTarget.PROPERTIES + ") values (null,?,?)";
//private static String DELETE_BY_ID = SqlBuild.delete(SspDictBuyTarget.TABLE_NAME);
private static String COUNT_ALL = SqlBuild.countAll(SspDictCrowdSexType.TABLE_NAME);
	
	  @Override
	    public SspDictCrowdSexType[] findT(SspDictCrowdSexType s) throws DaoException, IllegalAccessException {
	        List<SspDictCrowdSexType> list = new ArrayList<>();
	        if (s != null) {
	        	SspDictCrowdSexType[] sspDictCrowdSexTypes = find(s, new SspDictCrowdSexTypeRowMapper(), SspDictCrowdSexType.class);
	            return sspDictCrowdSexTypes;
	        } else {
	            list = jdbcTemplate.query(FIND_ALL, new SspDictCrowdSexTypeRowMapper());
	        }
	        return list.toArray(new SspDictCrowdSexType[]{});
	    }

	@Override
	public SspDictCrowdSexType[] batchFindbyCodes(String[] codes) {
		List<SspDictCrowdSexType> list = new ArrayList<SspDictCrowdSexType>();
		if(codes!=null&&codes.length!=0){
			 for (int i = 0; i < codes.length; i++) {
				 String quer = FIND_ALL + " where code = '" + codes[i] + "' ";
				 System.out.println("查询语句是--"+quer);
				 List<SspDictCrowdSexType> query= jdbcTemplate.query(FIND_ALL + " where code = '" + codes[i] + "' ",new SspDictCrowdSexTypeRowMapper());
				 list.addAll(query);
			 }
		}
		return list.toArray(new SspDictCrowdSexType[]{});
	}
}

