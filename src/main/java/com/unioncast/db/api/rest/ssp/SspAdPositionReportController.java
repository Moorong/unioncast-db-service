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
import com.unioncast.common.ssp.model.SspAdPositionReport;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspAdPositionReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api("ssp媒体端报表")
@RestController
@RequestMapping("/rest/ssp/adPositionReport")
public class SspAdPositionReportController extends GeneralController {
	
private static final Logger LOG = LogManager.getLogger(SspAdPositionReportController.class);
	
	@Resource
	private SspAdPositionReportService sspAdPositionReportService;
	

	@ApiOperation(value = "查询所有媒体端报表", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspAdPositionReport sspAdPositionReport) throws DaoException, IllegalAccessException {
		LOG.info("find sspAdPositionReport sspAdPositionReport:{}", sspAdPositionReport);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspAdPositionReportService.findT(sspAdPositionReport));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "增加媒体端报表", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspAdPositionReport", required = true, dataType = "SspAdPositionReport", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspAdPositionReport sspAdPositionReport) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspAdPositionReport:{}", sspAdPositionReport);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspAdPositionReportService.save(sspAdPositionReport);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "更新媒体端报表", httpMethod = "POST")
	@ApiImplicitParam(name = "sspAdPositionReport", required = true, dataType = "SspAdPositionReport", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody SspAdPositionReport sspAdPositionReport) throws Exception {
		LOG.info("update sspAdPositionReport:{}", sspAdPositionReport);
		sspAdPositionReportService.updateNotNullField(sspAdPositionReport);
	}
	
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspAdPositionReport pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspAdPositionReport> pagination = sspAdPositionReportService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	/*
	@ApiOperation(value = "删除一个广告位", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspAdPositionInfo id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspAdPositionInfoService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	*/
	@ApiOperation(value = "批量删除媒体端报表", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException{
		LOG.info("batchDelete sspAdPositionReport ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspAdPositionReportService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
