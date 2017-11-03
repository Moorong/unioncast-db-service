package com.unioncast.db.api.rest.ssp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.page.Operation;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.PlanCreativeModel;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.restClient.RestResponseFactory;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.nosql.redis.SspRedisMemory;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspCreativeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api("ssp创意")
@RestController
@RequestMapping("/rest/ssp/creative")
public class SspCreativeController extends GeneralController {

	private static final Logger LOG = LogManager
			.getLogger(SspCreativeController.class);

	@Autowired
	private SspCreativeService sspCreativeService;

	@Autowired
	private SspRedisMemory sspRedisMemory;

	@ApiOperation(value = "查询所有创意", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id)
			throws DaoException, InstantiationException, IllegalAccessException {
		LOG.info("find sspCreative id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		// restResponse.setResult(sspCreativeService.find(id));
		// 构建查询条件
		PageCriteria pageCriteria = new PageCriteria();
		pageCriteria.setEntityClass(SspCreative.class);
		List<SearchExpression> searchExpressionList = new ArrayList<>();
		if (id != null) {
			SearchExpression searchExpression = new SearchExpression();
			searchExpression.setPropertyName("id");
			searchExpression.setValue(id);
			searchExpression.setValueType("Long");
			searchExpression.setOperation(Operation.EQ);
			searchExpressionList.add(searchExpression);
		}
		pageCriteria.setSearchExpressionList(searchExpressionList);
		SspCreative[] result = sspCreativeService.find(pageCriteria);
		restResponse.setResult(result);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "查询所有创意", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findT", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspCreative creative) throws DaoException, IllegalAccessException {
		LOG.info("find sspCreative creative:{}", creative);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspCreativeService.findT(creative));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	

	@ApiOperation(value = "增加创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspCreative", required = true, dataType = "SspCreative", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspCreative sspCreative)
			throws DaoException, IllegalArgumentException,
			IllegalAccessException {
		LOG.info("add sspCreative:{}", sspCreative);
		RestResponse restResponse = new RestResponse();
		try {
			restResponse.setStatus(RestResponse.OK);
			Long id = sspCreativeService.save(sspCreative);
			restResponse.setResult(id);
			SspCreative[] creatives = new SspCreative[1];
			sspCreative.setId(id);
			creatives[0] = sspCreative;
			// 保存redis服务
			sspRedisMemory.batchAddSspCreative(creatives);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新创意", httpMethod = "POST")
	@ApiImplicitParam(name = "sspCreative", required = true, dataType = "SspCreative", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspCreative sspCreative) throws Exception {
		LOG.info("update sspCreative:{}", sspCreative);
		RestResponse response = new RestResponse();
		response.setResult(sspCreativeService.updateAndReturnNum(sspCreative));
		response.setStatus(RestResponse.OK);
		
		sspRedisMemory.batchDeleteBySspCreativeIds(new Long[] { sspCreative.getId() });
		SspCreative newCreative = sspCreativeService.findById(sspCreative.getId());
		if (newCreative.getDeleteState() != 2) {
			sspRedisMemory.batchAddSspCreative(new SspCreative[] { newCreative });
		}
		return response;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria)
			throws DaoException {
		LOG.info("page sspCreative pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspCreative> pagination = sspCreativeService
				.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

	@ApiOperation(value = "删除一个创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws Exception {
		LOG.info("delete sspCreative id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspCreativeService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);

		sspRedisMemory.batchDeleteBySspCreativeIds(new Long[] { id });
		return restResponse;
	}

	@ApiOperation(value = "批量删除创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws Exception {
		LOG.info("batchDelete sspCreative ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspCreativeService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		sspRedisMemory.batchDeleteBySspCreativeIds(ids);
		return restResponse;
	}

	@ApiOperation(value = "通过广告主id查询创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspCreative", required = true, dataType = "SspCreative", paramType = "body")
	@RequestMapping(value = "/findByAdvertiserId", method = RequestMethod.POST)
	public RestResponse findByAdvertiserId(@RequestBody Long advertiserId)
			throws DaoException {
		LOG.info("advertiserId:{}", advertiserId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		SspCreative[] creatives = sspCreativeService
				.findByAdvertiserId(advertiserId);
		restResponse.setResult(creatives);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	@ApiOperation(value = "通过广告主查询创意", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findCreativeByAdvertiser", method = RequestMethod.POST)
	public RestResponse findCreativeByAdvertiser(Long advertiserId,Integer creativeType,String creativeLabel,String creativeName)
			throws DaoException {
		LOG.info("advertiserId:{},creativeType:{},creativeLabel:{},creativeName:{}", advertiserId,creativeType,creativeLabel,creativeName);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		SspCreative[] creatives = sspCreativeService.findCreativeByAdvertiser(advertiserId,creativeType,creativeLabel,creativeName);
		restResponse.setResult(creatives);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "根据计划来条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageByPlanId", value = "分页查询条件", required = true, dataType = "PlanCreativeModel", paramType = "body")
	@RequestMapping(value = "/pageByPlanId", method = RequestMethod.POST)
	public RestResponse pageByPlanId(
			@RequestBody PlanCreativeModel planCreativeModel)
			throws DaoException {
		LOG.info("page sspCreative planCreativeModel:{}", planCreativeModel);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspCreative> pagination = sspCreativeService
				.pageByPlanId(planCreativeModel);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "searchPlanCreativeRelationPage", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/searchPlanCreativeRelationPage", method = RequestMethod.POST)
	public RestResponse searchPlanCreativeRelationPage(@RequestBody Map<String,String> params) throws DaoException {
		LOG.info("page sspPlanCreativeRelation searchPlanCreativeRelationPage:{}", params);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspPlanCreativeRelation> pagination = sspCreativeService.searchPlanCreativeRelationPage(params);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
}
