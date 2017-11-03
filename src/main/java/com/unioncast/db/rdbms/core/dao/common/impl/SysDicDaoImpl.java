package com.unioncast.db.rdbms.core.dao.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.user.model.SysDic;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.dao.mapper.MyRowMapper;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.SysDicDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

/**
 * 
 * @author juchaochao
 * @date 2016年11月8日 下午3:25:02
 *
 */
@Repository("sysDicDao")
public class SysDicDaoImpl extends CommonGeneralDao<SysDic, Long>implements SysDicDao {

	@SuppressWarnings("unchecked")
	@Override
	public SysDic[] find(PageCriteria pageCriteria)
			throws DaoException, InstantiationException, IllegalAccessException {
		ArrayList<Object> arrayList = getFindSqlAndList(pageCriteria);
		StringBuilder sql = (StringBuilder) arrayList.get(0);
		List<Object> list = (List<Object>) arrayList.get(1);
		List<SysDic> results = jdbcTemplate.query(sql.toString(),
				new MyRowMapper<SysDic>(pageCriteria.getEntityClass()), list.toArray());
		for (SysDic sysDic : results) {
			SysDic parentSysDic = null;

			if (sysDic.getParentSysDic() != null && sysDic.getParentSysDic().getDicId() != null) {
				String findById = SqlBuild.findByID(sysDic.getParentSysDic().getClass());
				parentSysDic = jdbcTemplate.queryForObject(findById,
						new MyRowMapper<SysDic>(pageCriteria.getEntityClass()), sysDic.getParentSysDic().getDicId());
				sysDic.setParentSysDic(parentSysDic);
			}
		}
		return results.toArray(new SysDic[] {});
	}
}
