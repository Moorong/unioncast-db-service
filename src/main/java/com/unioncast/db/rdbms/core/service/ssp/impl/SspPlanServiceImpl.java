package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspOperLog;
import com.unioncast.common.ssp.model.SspPlan;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspCreativeDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlanDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

@Service("sspPlanService")
@Transactional
public class SspPlanServiceImpl extends SspGeneralService<SspPlan, Long> implements SspPlanService {
	
	@Autowired
	@Qualifier("sspPlanDao")
	@Override
	public void setGeneralDao(SspGeneralDao<SspPlan, Long> generalDao) {
		this.generalDao = generalDao;
	}

	@Resource
	private SspPlanDao sspPlanDao;
	
	@Resource
	private SspCreativeDao sspCreativeDao;

	/**
	 * 校验策略名称
	 * @Author changguobin@unioncast.cn
	 * @param name
	 * @return
	 */
	@Override
	public boolean validateSspPlanName(String name,Long advertiserId){
		return sspPlanDao.validateSspPlanName(name,advertiserId);
	}

	@Override
	public SspOperLog[] findLogsByPlanId(Long id) throws DaoException {
		return sspPlanDao.findLogsByPlanId(id);
	}

	@Override
	public SspCreative[] findCreativesByPlanId(Long id) throws DaoException {
		return sspPlanDao.findCreativesByPlanId(id);
	}

	/*@Override
	public Pagination<SspPlan> page(PageCriteria pageCriteria, Long userId) throws DaoException {
		return sspPlanDao.page(pageCriteria , userId);
	}*/

	@Override
	public SspPlan[] find(Long id, Long userId) throws DaoException {
		return sspPlanDao.find(id , userId);
	}
	
	//删除计划的时候需要将计划相关的创意也删掉
	@Override
	public int deleteById(Long id) throws DaoException {
		sspPlanDao.find(id);
		sspCreativeDao.deleteByPlanId(id);
		return 1;
	}

	@Override
	public int updateState(Long id, int state) throws DaoException {
		sspPlanDao.updateState(id , state);
//		sspCreativeDao.updateState(id , state);
		return 0;
	}

	@Override
	public int deleteByParentId(Long parentId) throws DaoException {
		return sspPlanDao.deleteByParentId(parentId);
	}

	@Override
	public SspPlan[] findByIsPlanGroup(Integer isPlanGroup) {
		return sspPlanDao.findByIsPlanGroup(isPlanGroup);
	}

    @Override
    public SspPlan[] findByOrderId(Long id) {
        return sspPlanDao.findByOrderId(id);
    }

	@Override
	public Map<Long, Integer> findChildPlans(List<Long> ids) {
		
		return sspPlanDao.findChildPlans(ids);
	}

//查询所有未删除的状态为开启的所有计划
	public List<SspPlan> findStateAndDelete() {
		
		return sspPlanDao.findStateAndDelete();
	}
	/*public Pagination<SspPlan> page(PageCriteria pageCriteria) throws DaoException{
		return sspPlanDao.page(pageCriteria);
		
	};*/

	@Override
	public Map<String, Object> findPlanCountByOrderId(Long orderId) {
		return sspPlanDao.findPlanCountByOrderId(orderId);
	}

	@Override
	public List<SspPlanCreativeRelation> findAllPlanCreatives() {
		return sspPlanDao.findAllPlanCreatives();
	}
}
