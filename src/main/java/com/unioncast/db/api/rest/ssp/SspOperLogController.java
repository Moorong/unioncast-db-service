package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspOperLog;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspOperLogService;
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

@Api("ssp操作日志")
@RestController
@RequestMapping("/rest/ssp/operLog")
public class SspOperLogController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspOperLogController.class);

	@Resource
	private SspOperLogService sspOperLogService;

	@ApiOperation(value = "查询所有操作日志", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspOperLog sspOperLog) throws DaoException, IllegalAccessException {
		LOG.info("find sspOperLog:{}", sspOperLog);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspOperLogService.findT(sspOperLog));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加操作日志", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspOperLog", required = true, dataType = "SspOperLog", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspOperLog sspOperLog) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspOperLog:{}", sspOperLog);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspOperLogService.save(sspOperLog);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新操作日志", httpMethod = "POST")
	@ApiImplicitParam(name = "sspOperLog", required = true, dataType = "SspOperLog", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspOperLog sspOperLog) throws Exception {
		LOG.info("update sspOperLog:{}", sspOperLog);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspOperLogService.updateAndReturnNum(sspOperLog);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

/*	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspOperLog pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspOperLog> pagination = sspOperLogService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}*/


	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public Pagination<SspOperLog> page(@RequestBody PageCriteria pageCriteria)
			throws DaoException {
		LOG.info("page sspOperLog pageCriteria:{}", pageCriteria);
		Pagination<SspOperLog> pagination = sspOperLogService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		return pagination;
	}

//	@ApiOperation(value = "删除一个操作日志", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspOperLog id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspOperLogService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

	@ApiOperation(value = "批量删除操作日志", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspOperLog ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspOperLogService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
