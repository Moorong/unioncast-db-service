package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspOperLog;
import com.unioncast.common.ssp.model.SspPlan;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface SspPlanService extends GeneralService<SspPlan, Long> {
	/**
	 * 校验策略名称
	 * @Author changguobin@unioncast.cn
	 * @param name
	 * @return
	 */
	boolean validateSspPlanName(String name,Long advertiserId);

	/**
	 * 根据计划id查询相关操作日志
	 * <p>
	 * </p>
	 * @author dmpchengyunyun
	 * @date 2017年1月12日 下午3:38:15
	 * @param id
	 * @return
	 */
	SspOperLog[] findLogsByPlanId(Long id) throws DaoException;

	/**
	 * 根据计划id查询所有相关创意
	 * <p>
	 * </p>
	 * @author dmpchengyunyun
	 * @date 2017年1月12日 下午3:38:27
	 * @param id
	 * @return
	 */
	SspCreative[] findCreativesByPlanId(Long id) throws DaoException;

	/*Pagination<SspPlan> page(PageCriteria pageCriteria, Long userId) throws DaoException;*/

	SspPlan[] find(Long id, Long userId) throws DaoException;
	
	int updateState(Long id , int state) throws DaoException;
	
	int deleteByParentId(Long parentId) throws DaoException ;

	SspPlan[] findByIsPlanGroup(Integer isPlanGroup);
    SspPlan findById (Long id) throws DaoException ;
    SspPlan[] findByOrderId(Long id);

	Map<Long, Integer> findChildPlans(List<Long> ids);

	List<SspPlan> findStateAndDelete();
//	Pagination<SspPlan> page(PageCriteria pageCriteria) throws DaoException;

	/**
	* @Author dxy
	* @Date 2017/2/13 9:33
	* @Description (查询每个订单下可执行计划，和所有计划总数)
	 *  @param orderId
	* @throws
	*/
	Map<String,Object> findPlanCountByOrderId(Long orderId);

	List<SspPlanCreativeRelation> findAllPlanCreatives();
}
