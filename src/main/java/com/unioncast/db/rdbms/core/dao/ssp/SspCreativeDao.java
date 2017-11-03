package com.unioncast.db.rdbms.core.dao.ssp;

import java.util.Map;

import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.PlanCreativeModel;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface SspCreativeDao extends GeneralDao<SspCreative , Long> {

	/**
	 * 将跟计划相关的创意删掉
	 * <p>
	 * </p>
	 * @author dmpchengyunyun
	 * @date 2017年1月13日 下午5:10:26
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	int deleteByPlanId(Long id) throws DaoException ;

	SspCreative[] findByAdId(Long advertiserId);

	Pagination<SspCreative> pageByPlanId(PlanCreativeModel planCreativeModel);
    int deleteById(Long id);

	Pagination<SspPlanCreativeRelation> searchPlanCreativeRelationPage(
			Map<String, String> params);
	SspCreative[] findCreativeByAdvertiser(Long advertiserId,Integer creativeType, String creativeLabel, String creativeName);
}
