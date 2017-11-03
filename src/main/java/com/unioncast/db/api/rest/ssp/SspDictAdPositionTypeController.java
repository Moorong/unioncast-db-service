package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictAdPositionType;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAdPositionTypeService;

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

@Api("ssp广告位类型")
@RestController
@RequestMapping("/rest/ssp/dictAdPositionType")
public class SspDictAdPositionTypeController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictAdPositionTypeController.class);

	@Resource
	private SspDictAdPositionTypeService sspDictAdPositionTypeService;

	@ApiOperation(value = "查询所有广告位类型", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictAdPositionType sspDictAdPositionType) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictAdPositionType sspDictAdPositionType:{}", sspDictAdPositionType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictAdPositionTypeService.findT(sspDictAdPositionType));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加广告位类型", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictAdPositionType", required = true, dataType = "SspDictAdPositionType", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictAdPositionType sspDictAdPositionType) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictAdPositionType:{}", sspDictAdPositionType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictAdPositionTypeService.save(sspDictAdPositionType);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新广告位类型", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictAdPositionType", required = true, dataType = "SspDictAdPositionType", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictAdPositionType sspDictAdPositionType) throws Exception {
		LOG.info("update sspDictAdPositionType:{}", sspDictAdPositionType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictAdPositionTypeService.updateAndReturnNum(sspDictAdPositionType);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictAdPositionType pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictAdPositionType> pagination = sspDictAdPositionTypeService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
/*
	@ApiOperation(value = "删除一个广告位类型", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspDictAdPositionType id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspDictAdPositionTypeService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
*/
	@ApiOperation(value = "批量删除广告位类型", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictAdPositionType ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictAdPositionTypeService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
