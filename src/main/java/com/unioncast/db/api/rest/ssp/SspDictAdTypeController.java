package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictAdType;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAdTypeService;

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

@Api("ssp广告类型")
@RestController
@RequestMapping("/rest/ssp/dictAdType")
public class SspDictAdTypeController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictAdTypeController.class);

	@Resource
	private SspDictAdTypeService sspDictAdTypeService;

	@ApiOperation(value = "查询所有广告类型", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictAdType sspDictAdType) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictAdType sspDictAdType:{}", sspDictAdType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictAdTypeService.findT(sspDictAdType));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加广告类型", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictAdType", required = true, dataType = "SspDictAdType", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictAdType sspDictAdType) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictAdType:{}", sspDictAdType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictAdTypeService.save(sspDictAdType);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新广告类型", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictAdType", required = true, dataType = "SspDictAdType", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictAdType sspDictAdType) throws Exception {
		LOG.info("update sspDictAdType:{}", sspDictAdType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictAdTypeService.updateAndReturnNum(sspDictAdType);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictAdType pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictAdType> pagination = sspDictAdTypeService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
/*
	@ApiOperation(value = "删除一个广告类型", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspDictAdType id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspDictAdTypeService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
*/
	@ApiOperation(value = "批量删除广告类型", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictAdType ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictAdTypeService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
