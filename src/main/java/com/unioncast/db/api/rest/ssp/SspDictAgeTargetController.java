package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspCityInfo;
import com.unioncast.common.ssp.model.SspDictAgeTarget;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAgeTargetService;

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

@Api("ssp目标代理")
@RestController
@RequestMapping("/rest/ssp/dictAgeTarget")
public class SspDictAgeTargetController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictAgeTargetController.class);

	@Resource
	private SspDictAgeTargetService sspDictAgeTargetService;

	@ApiOperation(value = "查询所有目标代理", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictAgeTarget sspDictAgeTarget) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictAgeTarget sspDictAgeTarget:{}", sspDictAgeTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictAgeTargetService.findT(sspDictAgeTarget));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加目标代理", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictAgeTarget", required = true, dataType = "SspDictAgeTarget", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictAgeTarget sspDictAgeTarget) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictAgeTarget:{}", sspDictAgeTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictAgeTargetService.save(sspDictAgeTarget);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新目标代理", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictAgeTarget", required = true, dataType = "SspDictAgeTarget", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictAgeTarget sspDictAgeTarget) throws Exception {
		LOG.info("update sspDictAgeTarget:{}", sspDictAgeTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictAgeTargetService.updateAndReturnNum(sspDictAgeTarget);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictAgeTarget pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictAgeTarget> pagination = sspDictAgeTargetService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
/*
	@ApiOperation(value = "删除一个目标代理", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspDictAgeTarget id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspDictAgeTargetService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
*/
	@ApiOperation(value = "批量删除目标代理", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictAgeTarget ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictAgeTargetService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
	
	@ApiOperation(value = "根据code数组批量查询年龄对象", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchFindbyCodes", method = RequestMethod.POST)
	public RestResponse batchFindbyCodes(@RequestBody String[] codes) throws DaoException {
		LOG.info("batchFindbyCodes  codes:{}", (Object[]) codes);
		RestResponse restResponse = new RestResponse();
		SspDictAgeTarget[] number = sspDictAgeTargetService.batchFindbyCodes(codes);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
