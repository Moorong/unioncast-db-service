package com.unioncast.db.api.rest.ssp;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspCreativeReport;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspCreativeReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api("ssp广告报表")
@RestController
@RequestMapping("/rest/ssp/creativeReport")
public class SspCreativeReportController extends GeneralController {
	
private static final Logger LOG = LogManager.getLogger(SspCreativeReportController.class);
	
	@Resource
	private SspCreativeReportService sspCreativeReportService;
	

	@ApiOperation(value = "查询所有创意组", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspCreativeReport sspCreativeReport) throws DaoException, IllegalAccessException {
		LOG.info("find sspCreativeReport SspCreativeReport:{}", sspCreativeReport);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspCreativeReportService.findT(sspCreativeReport));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "增加创意组", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspCreativeGroup", required = true, dataType = "sspCreativeReport", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspCreativeReport sspCreativeReport) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspCreativeReport:{}", sspCreativeReport);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspCreativeReportService.save(sspCreativeReport);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "更新创意组", httpMethod = "POST")
	@ApiImplicitParam(name = "sspCreativeGroup", required = true, dataType = "sspCreativeReport", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody SspCreativeReport sspCreativeReport) throws Exception {
		LOG.info("update sspCreativeReport:{}", sspCreativeReport);
		sspCreativeReportService.updateNotNullField(sspCreativeReport);
	}
	
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspCreativeReport pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspCreativeReport> pagination = sspCreativeReportService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

	@ApiOperation(value = "批量删除创意组", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException{
		LOG.info("batchDelete sspCreativeReport ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspCreativeReportService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
