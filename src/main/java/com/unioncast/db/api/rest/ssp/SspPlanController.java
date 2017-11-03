package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspOperLog;
import com.unioncast.common.ssp.model.SspPlan;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.nosql.redis.SspRedisMemory;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 广告计划
 * <p>
 * </p>
 * @author dmpchengyunyun
 * @date 2017年1月10日下午12:00:45
 */
@Api("ssp广告计划")
@RestController
@RequestMapping("/rest/ssp/plan")
public class SspPlanController extends GeneralController {
	
	private static final Logger LOG = LogManager.getLogger(SspPlanController.class);
	
	@Resource
	private SspPlanService sspPlanService;

	@Autowired
	private SspRedisMemory sspRedisMemory;
	
	@ApiOperation(value = "查询所有广告计划", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	//public RestResponse find(@RequestBody(required = false) Long id , HttpServletRequest request) throws DaoException {
		
	public RestResponse find(@RequestBody(required = false) Long id ) throws DaoException {LOG.info("find SspPlan id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		//User user = (User) request.getSession().getAttribute("user");
		//restResponse.setResult(sspPlanService.find(id , user.getId()));
		SspPlan result = sspPlanService.findById(id);
		if(result!=null){
			restResponse.setResult(result);
			return restResponse;
		}
		return null;
	}
	
	@ApiOperation(value = "增加一个广告计划", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspPlan", required = true, dataType = "SspPlan", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspPlan sspPlan) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspPlan:{}", sspPlan);
		RestResponse restResponse = new RestResponse();
		try {
			restResponse.setStatus(RestResponse.OK);
			Long id = sspPlanService.save(sspPlan);
			restResponse.setResult(id);
			SspPlan[] plans = new SspPlan[1];
			sspPlan.setId(id);
			plans[0] = sspPlan;
			//保存redis服务
			sspRedisMemory.batchAddSspPlan(plans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	@ApiOperation(value = "检验策略名称", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "name", required = true, dataType = "String", paramType = "body")
	@RequestMapping(value = "/validataSspPlanName", method = RequestMethod.POST)
	public RestResponse validatePlanName(String name,Long advertiserId) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("validatePlanName name:{} advertiserId:{}", name,advertiserId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		boolean flag = sspPlanService.validateSspPlanName(name,advertiserId);
		restResponse.setResult(flag);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "更新广告计划", httpMethod = "POST")
	@ApiImplicitParam(name = "sspPlan", required = true, dataType = "SspPlan", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspPlan sspPlan) throws Exception {
		LOG.info("update sspPlan:{}", sspPlan);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspPlanService.updateAndReturnNum(sspPlan);
		restResponse.setResult(i);
	    LOG.info("restResponse:{}", restResponse);
	    
	    sspRedisMemory.batchDeleteBySspPlanIds(new Long[]{sspPlan.getId()});
	    SspPlan newPlan = sspPlanService.findById(sspPlan.getId());
        sspRedisMemory.batchAddSspPlan(new SspPlan[]{newPlan});
	   
		return restResponse;
	}
	
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	//public RestResponse page(@RequestBody PageCriteria pageCriteria ) throws DaoException {
	public RestResponse page(@RequestBody PageCriteria pageCriteria ) throws DaoException {
		LOG.info("page sspPlan pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);

		Pagination<SspPlan> pagination = sspPlanService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	
	@ApiOperation(value = "删除一个广告计划", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws Exception {
		LOG.info("delete sspPlan id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspPlanService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		sspRedisMemory.batchDeleteBySspPlanIds(new Long[]{id});
		SspPlan newPlan = sspPlanService.findById(id);
		if(newPlan != null) {
			sspRedisMemory.batchAddSspPlan(new SspPlan[]{newPlan});
		}
		return restResponse;
	}
	
	@ApiOperation(value = "批量删除广告计划", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws Exception{
		LOG.info("batchDelete sspPlan ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspPlanService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		sspRedisMemory.batchDeleteBySspPlanIds(ids);
		List<SspPlan> list = new ArrayList<SspPlan>();
		for(Long id : ids) {
			SspPlan newAdvertiser = sspPlanService.findById(id);
			if(newAdvertiser != null) {
				list.add(newAdvertiser);
			}
		}
		if(list != null && list.size() > 0) {
			sspRedisMemory.batchAddSspPlan(list.toArray(new SspPlan[0]));
		}
		return restResponse;
	}
	
	@ApiOperation(value = "根据计划id获取操作日志", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/findLogsByPlanId", method = RequestMethod.POST)
	public RestResponse findLogsByPlanId(@RequestBody Long id) throws DaoException{
		LOG.info("findLogByPlanId id:{}", id);
		RestResponse restResponse = new RestResponse();
		SspOperLog[] sspOperLogs = sspPlanService.findLogsByPlanId(id);
		restResponse.setResult(sspOperLogs);
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	
	@ApiOperation(value = "根据计划id获取创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/findCreativesByPlanId", method = RequestMethod.POST)
	public RestResponse findCreativesByPlanId(@RequestBody Long id) throws DaoException{
		LOG.info("findCreativesByPlanId id:{}", id);
		RestResponse restResponse = new RestResponse();
		SspCreative[] creatives = sspPlanService.findCreativesByPlanId(id);
		restResponse.setResult(creatives);
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	@ApiOperation(value = "根据是否为计划组查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "isPlanGroup", required = true, dataType = "Integer", paramType = "body")
	@RequestMapping(value = "/findByIsPlanGroup", method = RequestMethod.POST)
	public RestResponse findByIsPlanGroup(@RequestBody Integer isPlanGroup) throws DaoException{
		LOG.info("findByIsPlanGroup isPlanGroup:{}", isPlanGroup);
		RestResponse restResponse = new RestResponse();
		SspPlan[] plans = sspPlanService.findByIsPlanGroup(isPlanGroup);
		restResponse.setResult(plans);
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}


	@ApiOperation(value = "根据订单id查询相关计划", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findByOrderId", method = RequestMethod.POST)
	public RestResponse findByOrderId(@RequestBody Long id) throws DaoException{
		LOG.info("findByOrderId id:{}", id);
		RestResponse restResponse = new RestResponse();
		SspPlan[] plans = sspPlanService.findByOrderId(id);
		restResponse.setResult(plans);
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	@ApiOperation(value = "根据计划id查询子计划", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "根据计划id查询子计划分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/searchPlanChildren", method = RequestMethod.POST)
	public RestResponse searchPlanChildren(@RequestBody PageCriteria pageCriteria ) throws DaoException {
		LOG.info("searchPlanChildren sspPlan pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspPlan> pagination = sspPlanService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	@ApiOperation(value = "根据计划id查询个数", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", value = "根据计划id查询子计划分页查询条件", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/findChildPlans", method = RequestMethod.POST)
	public RestResponse findChildPlans(@RequestBody List<Long> ids ) throws DaoException {
		LOG.info("findChildPlans sspPlan ids:{}", ids);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Map<Long,Integer> childcount = sspPlanService.findChildPlans(ids);
		LOG.info("childcount:{}", childcount);
		restResponse.setResult(childcount);
		return restResponse;
	}
	
	@ApiOperation(value = "查询所有状态开启的未删除的策略", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findStateAndDelete", method = RequestMethod.POST)
	public RestResponse findStateAndDelete() throws DaoException {
		LOG.info("findStateAndDelete 查询所有未删除的状态为开启的计划");
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		List<SspPlan> result = sspPlanService.findStateAndDelete();
		restResponse.setResult(result);
		LOG.info("result:{}", result);
		return restResponse;
	}
	
	@ApiOperation(value = "获取所有的计划-创意列表", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findAllPlanCreatives", method = RequestMethod.POST)
	public RestResponse findAllPlanCreatives() throws DaoException{
		LOG.info("findAllPlanCreatives...");
		RestResponse restResponse = new RestResponse();
		List<SspPlanCreativeRelation> creatives = sspPlanService.findAllPlanCreatives();
		restResponse.setResult(creatives);
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	
	
	@ApiOperation(value = "更新广告计划", httpMethod = "POST")
	@ApiImplicitParam(name = "sspPlan", required = true, dataType = "SspPlan", paramType = "body")
	@RequestMapping(value = "/updates", method = RequestMethod.POST)
	public RestResponse updates(@RequestBody SspPlan[] sspPlans) throws Exception {
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		 List<Long> ids = new ArrayList<Long>();
		for(SspPlan sspPlan : sspPlans) {
			int i = sspPlanService.updateAndReturnNum(sspPlan);
			restResponse.setResult(i);
			ids.add(sspPlan.getId());
		}
		 LOG.info("restResponse:{}", restResponse);
		 
	    sspRedisMemory.batchDeleteBySspPlanIds(ids.toArray(new Long[0]));
	    
	    List<SspPlan> list = new ArrayList<SspPlan>();
	    for(Long id : ids) {
			SspPlan newPlan = sspPlanService.findById(id);
			if(newPlan != null) {
				list.add(newPlan);
			}
		}
		if(list != null && list.size() > 0) {
			sspRedisMemory.batchAddSspPlan(list.toArray(new SspPlan[0]));
		}
		return restResponse;
	}
}
