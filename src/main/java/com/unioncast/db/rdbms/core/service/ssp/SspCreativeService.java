package com.unioncast.db.rdbms.core.service.ssp;

import java.util.Map;

import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.PlanCreativeModel;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface SspCreativeService extends GeneralService<SspCreative, Long> {

	SspCreative[] findByAdvertiserId(Long advertiserId);
	SspCreative[] findCreativeByAdvertiser(Long advertiserId,Integer creativeType,String creativeLabel,String creativeName);

	Pagination<SspCreative> pageByPlanId(PlanCreativeModel planCreativeModel);
	int deleteById(Long id)throws DaoException;
	
	Pagination<SspPlanCreativeRelation> searchPlanCreativeRelationPage(
			Map<String, String> params);

}
