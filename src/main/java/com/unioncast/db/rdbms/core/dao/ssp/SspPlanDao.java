package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspOperLog;
import com.unioncast.common.ssp.model.SspPlan;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface SspPlanDao extends GeneralDao<SspPlan , Long> {

	SspOperLog[] findLogsByPlanId(Long id) throws DaoException ;

	SspCreative[] findCreativesByPlanId(Long id) throws DaoException ;

	Pagination<SspPlan> page(PageCriteria pageCriteria, Long userId) throws DaoException ;

	SspPlan[] find(Long id, Long userId);
	SspPlan findById (Long id) throws DaoException ;

	void updateState(Long id, int state);
	
	int deleteByOrderId(Long id) throws DaoException ;
	
	int deleteByParentId(Long id) throws DaoException ;

	SspPlan[] findByIsPlanGroup(Integer isPlanGroup);

    SspPlan[] findByOrderId(Long id);

	Map<Long, Integer> findChildPlans(List<Long> ids);
//查询所有状态为开启的未删除的计划
	List<SspPlan> findStateAndDelete();

	/**
	 * @Author dxy
	 * @Date 2017/2/13 9:33
	 * @Description (查询每个订单下可执行计划，和所有计划总数)
	 *  @param orderId
	 * @throws
	 */
	Map<String,Object> findPlanCountByOrderId(Long orderId);

	List<SspPlanCreativeRelation> findAllPlanCreatives();

	/**
	 * 校验策略名称
	 * @Author changguobin@unioncast.cn
	 * @param name
	 * @return
	 */
	boolean validateSspPlanName(String name,Long advertiserId);
}
