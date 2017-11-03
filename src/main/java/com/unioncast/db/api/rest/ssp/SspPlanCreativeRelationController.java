package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspPlan;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.api.rest.redis.RedisController;
import com.unioncast.db.nosql.redis.SspRedisMemory;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanCreativeRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@Api("ssp计划与创意关联中间表")
@RestController
@RequestMapping("/rest/ssp/planCreativeRelation")
public class SspPlanCreativeRelationController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspPlanCreativeRelationController.class);

	@Resource
	private SspPlanCreativeRelationService sspPlanCreativeRelationService;
	@Resource
	private SspRedisMemory sspRedisMemory;

	@Resource
	private RedisController redisController;

	@ApiOperation(value = "查询所有计划与创意关联中间表", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspPlanCreativeRelation sspPlanCreativeRelation)
			throws DaoException, IllegalAccessException {
		LOG.info("find sspPlanCreativeRelation:{}", sspPlanCreativeRelation);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspPlanCreativeRelationService.findT(sspPlanCreativeRelation));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "查询所有计划与创意关联中间表", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findByPlanId", method = RequestMethod.POST)
	public RestResponse findByPlanId(@RequestBody(required = false) Long planId) throws DaoException,
			IllegalAccessException {
		LOG.info("find findByPlanId:{}", planId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspPlanCreativeRelationService.findByPlanId(planId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "根据广告主查询所有计划与创意关联中间表", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findByAdvertiserId", method = RequestMethod.POST)
	public RestResponse findByAdvertiserIdId(@RequestBody(required = false) Long advertiserId) throws DaoException,
			IllegalAccessException {
		LOG.info("find findByAdvertiserId:{}", advertiserId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspPlanCreativeRelationService.findByAdvertiserId(advertiserId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加计划与创意关联中间表", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspPlanCreativeRelation", required = true, dataType = "SspPlanCreativeRelation", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspPlanCreativeRelation sspPlanCreativeRelation) throws DaoException,
			IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspPlanCreativeRelation:{}", sspPlanCreativeRelation);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspPlanCreativeRelationService.save(sspPlanCreativeRelation);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新计划与创意关联中间表", httpMethod = "POST")
	@ApiImplicitParam(name = "sspPlanCreativeRelation", required = true, dataType = "SspPlanCreativeRelation", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspPlanCreativeRelation sspPlanCreativeRelation) throws Exception {
		LOG.info("update sspPlanCreativeRelation:{}", sspPlanCreativeRelation);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspPlanCreativeRelationService.updateAndReturnNum(sspPlanCreativeRelation);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspPlanCreativeRelation pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspPlanCreativeRelation> pagination = sspPlanCreativeRelationService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

	// @ApiOperation(value = "删除一个计划与创意关联中间表", httpMethod = "POST", response =
	// RestResponse.class)
	// @ApiImplicitParam(name = "id", required = true, dataType = "long",
	// paramType = "body")
	// @RequestMapping(value = "/delete", method = RequestMethod.POST)
	// public RestResponse delete(@RequestBody Long id) throws DaoException {
	// LOG.info("delete sspPlanCreativeRelation id:{}", id);
	// RestResponse restResponse = new RestResponse();
	// restResponse.setResult(sspPlanCreativeRelationService.deleteById(id));
	// restResponse.setStatus(RestResponse.OK);
	// return restResponse;
	// }

	@ApiOperation(value = "批量删除计划与创意关联中间表", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspPlanCreativeRelation ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspPlanCreativeRelationService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

	@ApiOperation(value = "将计划-创意关联数据添加进去出错", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "addPlanCreativeRelations", required = true, dataType = "SspPlanCreativeRelation[]", paramType = "body")
	@RequestMapping(value = "/addPlanCreativeRelations", method = RequestMethod.POST)
	public RestResponse addPlanCreativeRelations(@RequestBody SspPlanCreativeRelation[] relations) throws DaoException {
		LOG.info("addPlanCreativeRelations:{}", (Object[]) relations);
		RestResponse restResponse = new RestResponse();
		int number = sspPlanCreativeRelationService.addPlanCreativeRelations(relations);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

	@ApiOperation(value = "将计划-创意关联数据添加进去出错", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "addPlanCreativeRelationsMap", required = true, dataType = "SspPlanCreativeRelation[]", paramType = "body")
	@RequestMapping(value = "/addPlanCreativeRelationsMap", method = RequestMethod.POST)
	public RestResponse addPlanCreativeRelationsMap(@RequestBody Map<String, String> relations) throws Exception {
		if (relations == null || relations.size() == 0) {
			return null;
		}
		LOG.info("addPlanCreativeRelations:{}", relations);
		RestResponse restResponse = new RestResponse();

		String creativeIds = relations.get("creativeIds");
		String planId = relations.get("planId");
		String groupName = relations.get("groupName");

		List<SspPlanCreativeRelation> relationList = new ArrayList<SspPlanCreativeRelation>();
		String[] creativeStrArr = creativeIds.split(",");
		for (String creativeId : creativeStrArr) {
			SspPlanCreativeRelation relation = new SspPlanCreativeRelation();
			SspCreative sspCreative = new SspCreative();
			sspCreative.setId(Long.parseLong(creativeId));
			relation.setSspCreative(sspCreative);
			relation.setDeleteState(1);
			SspPlan sspPlan = new SspPlan();
			sspPlan.setId(Long.parseLong(planId));
			relation.setSspPlan(sspPlan);
			relation.setState(1);
			relation.setCreateTime(new Date());
			relation.setUpdateTime(new Date());
			relation.setCreativeGroup(groupName);
			relationList.add(relation);
		}
		int number = sspPlanCreativeRelationService.addPlanCreativeRelations(relationList
				.toArray(new SspPlanCreativeRelation[0]));
		System.out.println("number--" + number);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);

		if (number == 1) {
			// 存放到数据库中成功 再存放到redis缓存中去 存放的应该是创意id 而不是计划id
			// 先将redis缓存中原来的数据删除
			Long pId = Long.parseLong(planId);
			Long[] pIds = new Long[1];
			pIds[0] = pId;
			Long result = sspRedisMemory.batchDeleteBySspPlanCreativeRelationIds(pIds);
			System.out.println("删除的结果是--" + result);

			// 存到缓存中去

			// 先查询所有的创意是不是状态开的 只存状态开的创意
			Map<Long, String> map = new HashMap<Long, String>();
			map.put(pId, creativeIds);
			sspRedisMemory.batchAddSspPlanCreativeRelationByPlanId(map);

			//
		}

		return restResponse;
	}

	@ApiOperation(value = "修改创意状态", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "params", required = true, dataType = "SspCreative", paramType = "body")
	@RequestMapping(value = "/changePlanCreativeRelationState", method = RequestMethod.POST)
	public RestResponse changePlanCreativeRelationState(@RequestBody SspPlanCreativeRelation params)
			throws DaoException {
		LOG.info("changePlanCreativeRelationState:{}", params);
		RestResponse restResponse = new RestResponse();
		int number = sspPlanCreativeRelationService.changePlanCreativeRelationState(params);
		System.out.println("状态修改后返回的结果是---" + number);

		Long planId = null;// 页面传递的参数之计划id
		Long creativeId = null;// 页面传递的参数之计划id
		Integer state = null;// 页面传递的参数之状态
		String value = null;// 根据计划id查询出的字符串结果
		if (number > 0) {
			// 成功修改状态
			if (params != null) {
				if (null != params.getSspCreative() && null != params.getSspCreative().getId()) {
					creativeId = params.getSspCreative().getId();

				}
				if (null != params.getSspPlan() && null != params.getSspPlan().getId()) {
					planId = params.getSspPlan().getId();
					// 从缓存中查找所有的
					value = sspRedisMemory.findPlanCreativeRelationByPlanId(planId);
				}
				if (params.getState() != null) {
					state = params.getState();

					if (null != creativeId) {

						if (state == 1) {

							if (!value.contains(creativeId.toString())) {
                                StringBuffer sbf = new StringBuffer(value);
                                if (sbf.length() > 0)
                                    sbf.append(",");
                                sbf.append(creativeId);
                                value = sbf.toString();
							}

						} else {
							// 移除
							if (value.contains(creativeId.toString())) {
								String reg = String.format("((,%s)|(%s,)|(%s))", creativeId, creativeId, creativeId);
								value = value.replaceAll(reg, "");
							}
						}
					}

				}

				// 将操作完成的数据放回redis中
				Map<Long, String> map = new HashMap<Long, String>();
				map.put(planId, value);
				try {
					sspRedisMemory.batchAddSspPlanCreativeRelationByPlanId(map);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

	@ApiOperation(value = "删除计划创意关系表", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/deletePlanCreativeRelationById", method = RequestMethod.POST)
	public RestResponse deletePlanCreativeRelationById(@RequestBody SspPlanCreativeRelation pcr) throws DaoException {
		LOG.info("deletePlanCreativeRelationById:{}", pcr);
		RestResponse restResponse = new RestResponse();
		int number = sspPlanCreativeRelationService.deletePlanCreativeRelationById(pcr);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
