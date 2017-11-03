package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictAudit;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictAuditService;

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

@Api("ssp审计")
@RestController
@RequestMapping("/rest/ssp/dictAudit")
public class SspDictAuditController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictAuditController.class);

	@Resource
	private SspDictAuditService sspDictAuditService;

	@ApiOperation(value = "查询所有审计", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictAudit sspDictAudit) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictAudit id:{}", sspDictAudit);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictAuditService.findT(sspDictAudit));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加审计", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictAudit", required = true, dataType = "SspDictAudit", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictAudit sspDictAudit) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictAudit:{}", sspDictAudit);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictAuditService.save(sspDictAudit);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新审计", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictAudit", required = true, dataType = "SspDictAudit", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictAudit sspDictAudit) throws Exception {
		LOG.info("update sspDictAudit:{}", sspDictAudit);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictAuditService.updateAndReturnNum(sspDictAudit);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictAudit pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictAudit> pagination = sspDictAuditService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
/*
	@ApiOperation(value = "删除一个审计", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspDictAudit id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspDictAuditService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
*/
	@ApiOperation(value = "批量删除审计", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictAudit ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictAuditService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
