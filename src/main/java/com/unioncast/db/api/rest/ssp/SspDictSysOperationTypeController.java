package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictSysOperationType;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictSysOperationTypeService;

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

@Api("ssp操作系统定向")
@RestController
@RequestMapping("/rest/ssp/dictSysOperationType")
public class SspDictSysOperationTypeController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictSysOperationTypeController.class);

	@Resource
	private SspDictSysOperationTypeService sspDictSysOperationTypeService;

	@ApiOperation(value = "查询所有操作系统定向", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictSysOperationType sspDictSysOperationType) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictSysOperationType:{}", sspDictSysOperationType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictSysOperationTypeService.findT(sspDictSysOperationType));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加操作系统定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictSysOperationType", required = true, dataType = "SspDictSysOperationType", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictSysOperationType sspDictSysOperationType) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictSysOperationType:{}", sspDictSysOperationType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictSysOperationTypeService.save(sspDictSysOperationType);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新操作系统定向", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictSysOperationType", required = true, dataType = "SspDictSysOperationType", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictSysOperationType sspDictSysOperationType) throws Exception {
		LOG.info("update sspDictSysOperationType:{}", sspDictSysOperationType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictSysOperationTypeService.updateAndReturnNum(sspDictSysOperationType);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictSysOperationType pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictSysOperationType> pagination = sspDictSysOperationTypeService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

//	@ApiOperation(value = "删除一个操作系统定向", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspDictSysOperationType id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspDictSysOperationTypeService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

	@ApiOperation(value = "批量删除操作系统定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictSysOperationType ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictSysOperationTypeService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
