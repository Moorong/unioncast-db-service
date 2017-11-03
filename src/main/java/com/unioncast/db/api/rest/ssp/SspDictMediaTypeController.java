package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictMediaType;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictMediaTypeService;

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

@Api("ssp媒体类型定向")
@RestController
@RequestMapping("/rest/ssp/dictMediaType")
public class SspDictMediaTypeController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictMediaTypeController.class);

	@Resource
	private SspDictMediaTypeService sspDictMediaTypeService;

	@ApiOperation(value = "查询所有媒体类型定向", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictMediaType sspDictMediaType) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictMediaType :{}", sspDictMediaType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictMediaTypeService.findT(sspDictMediaType));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加媒体类型定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictMediaType", required = true, dataType = "SspDictMediaType", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictMediaType sspDictMediaType) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictMediaType:{}", sspDictMediaType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictMediaTypeService.save(sspDictMediaType);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新媒体类型定向", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictMediaType", required = true, dataType = "SspDictMediaType", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictMediaType sspDictMediaType) throws Exception {
		LOG.info("update sspDictMediaType:{}", sspDictMediaType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictMediaTypeService.updateAndReturnNum(sspDictMediaType);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictMediaType pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictMediaType> pagination = sspDictMediaTypeService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

//	@ApiOperation(value = "删除一个媒体类型定向", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspDictMediaType id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspDictMediaTypeService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

	@ApiOperation(value = "批量删除媒体类型定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictMediaType ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictMediaTypeService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
