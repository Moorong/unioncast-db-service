package com.unioncast.db.rdbms.core.service.ssp.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.PlanCreativeModel;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspCreativeDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspCreativeService;

@Service("sspCreativeService")
@Transactional
public class SspCreativeServiceImpl extends SspGeneralService<SspCreative, Long> implements SspCreativeService {

    @Autowired
    @Qualifier("sspCreativeDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspCreative, Long> generalDao) {
        this.generalDao = generalDao;
    }

    @Resource
	private SspCreativeDao sspCreativeDao;
    
	@Override
	public SspCreative[] findByAdvertiserId(Long advertiserId) {
		return sspCreativeDao.findByAdId(advertiserId);
	}
	@Override
	public SspCreative[] findCreativeByAdvertiser(Long advertiserId,Integer creativeType,String creativeLabel,String creativeName) {
		return sspCreativeDao.findCreativeByAdvertiser(advertiserId,creativeType,creativeLabel,creativeName);
	}

	@Override
	public Pagination<SspCreative> pageByPlanId(
			PlanCreativeModel planCreativeModel) {
		return sspCreativeDao.pageByPlanId(planCreativeModel);
	}
	
	public int deleteById(Long id) throws DaoException{
		
		return sspCreativeDao.deleteById(id);
	};
	@Override
	public Pagination<SspPlanCreativeRelation> searchPlanCreativeRelationPage(
			Map<String, String> params) {
		return sspCreativeDao.searchPlanCreativeRelationPage(params);
	}
}
