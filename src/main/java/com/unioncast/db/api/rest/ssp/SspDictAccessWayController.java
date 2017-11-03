package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictAccessWay;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAccessWayService;

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

@Api("ssp接入方式")
@RestController
@RequestMapping("/rest/ssp/dictAccessWay")
public class SspDictAccessWayController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictAccessWayController.class);

	@Resource
	private SspDictAccessWayService sspDictAccessWayService;

	@ApiOperation(value = "查询所有接入方式", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictAccessWay sspDictAccessWay) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictAccessWay sspDictAccessWay:{}", sspDictAccessWay);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictAccessWayService.findT(sspDictAccessWay));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加接入方式", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictAccessWay", required = true, dataType = "SspDictAccessWay", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictAccessWay sspDictAccessWay) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictAccessWay:{}", sspDictAccessWay);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictAccessWayService.save(sspDictAccessWay);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新接入方式", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictAccessWay", required = true, dataType = "SspDictAccessWay", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictAccessWay sspDictAccessWay) throws Exception {
		LOG.info("update sspDictAccessWay:{}", sspDictAccessWay);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictAccessWayService.updateAndReturnNum(sspDictAccessWay);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictAccessWay pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictAccessWay> pagination = sspDictAccessWayService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
/*
	@ApiOperation(value = "删除一个接入方式", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspDictAccessWay id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspDictAccessWayService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
*/
	@ApiOperation(value = "批量删除接入方式", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictAccessWay ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictAccessWayService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
