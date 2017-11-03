package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictIncomeTarget;
import com.unioncast.common.ssp.model.SspDictInterestsTarget;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictInterestsTargetService;

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

@Api("ssp兴趣定向")
@RestController
@RequestMapping("/rest/ssp/dictInterestsTarget")
public class SspDictInterestsTargetController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictInterestsTargetController.class);

	@Resource
	private SspDictInterestsTargetService sspDictInterestsTargetService;

	@ApiOperation(value = "查询所有兴趣定向", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictInterestsTarget sspDictInterestsTarget) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictInterestsTarget :{}", sspDictInterestsTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictInterestsTargetService.findT(sspDictInterestsTarget));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加兴趣定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictInterestsTarget", required = true, dataType = "SspDictInterestsTarget", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictInterestsTarget sspDictInterestsTarget) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictInterestsTarget:{}", sspDictInterestsTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictInterestsTargetService.save(sspDictInterestsTarget);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新兴趣定向", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictInterestsTarget", required = true, dataType = "SspDictInterestsTarget", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictInterestsTarget sspDictInterestsTarget) throws Exception {
		LOG.info("update sspDictInterestsTarget:{}", sspDictInterestsTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictInterestsTargetService.updateAndReturnNum(sspDictInterestsTarget);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictInterestsTarget pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictInterestsTarget> pagination = sspDictInterestsTargetService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

//	@ApiOperation(value = "删除一个兴趣定向", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspDictInterestsTarget id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspDictInterestsTargetService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

	@ApiOperation(value = "批量删除兴趣定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictInterestsTarget ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictInterestsTargetService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
	@ApiOperation(value = "根据code数组批量查询婚姻定向对象", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchFindbyCodes", method = RequestMethod.POST)
	public RestResponse batchFindbyCodes(@RequestBody String[] codes) throws DaoException {
		LOG.info("batchFindbyCodes  codes:{}", (Object[]) codes);
		RestResponse restResponse = new RestResponse();
		SspDictInterestsTarget[] number = sspDictInterestsTargetService.batchFindbyCodes(codes);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
