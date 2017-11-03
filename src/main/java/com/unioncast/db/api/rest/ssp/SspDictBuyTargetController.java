package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictBuyTargetService;

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

@Api("ssp购买目标")
@RestController
@RequestMapping("/rest/ssp/dictBuyTarget")
public class SspDictBuyTargetController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictBuyTargetController.class);

	@Resource
	private SspDictBuyTargetService sspDictBuyTargetService;

	@ApiOperation(value = "查询所有购买目标", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictBuyTarget sspDictBuyTarget) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictBuyTarget sspDictBuyTarget:{}", sspDictBuyTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictBuyTargetService.findT(sspDictBuyTarget));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加购买目标", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictBuyTarget", required = true, dataType = "SspDictBuyTarget", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictBuyTarget sspDictBuyTarget) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictBuyTarget:{}", sspDictBuyTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictBuyTargetService.save(sspDictBuyTarget);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新购买目标", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictBuyTarget", required = true, dataType = "SspDictBuyTarget", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictBuyTarget sspDictBuyTarget) throws Exception {
		LOG.info("update sspDictBuyTarget:{}", sspDictBuyTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictBuyTargetService.updateAndReturnNum(sspDictBuyTarget);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictBuyTarget pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictBuyTarget> pagination = sspDictBuyTargetService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
/*
	@ApiOperation(value = "删除一个购买目标", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspDictBuyTarget id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspDictBuyTargetService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
*/
	@ApiOperation(value = "批量删除购买目标", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictBuyTarget ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictBuyTargetService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
	@ApiOperation(value = "根据code数组批量查询购买定向对象", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchFindbyCodes", method = RequestMethod.POST)
	public RestResponse batchFindbyCodes(@RequestBody String[] codes) throws DaoException {
		LOG.info("batchFindbyCodes  codes:{}", (Object[]) codes);
		RestResponse restResponse = new RestResponse();
		SspDictBuyTarget[] number = sspDictBuyTargetService.batchFindbyCodes(codes);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
