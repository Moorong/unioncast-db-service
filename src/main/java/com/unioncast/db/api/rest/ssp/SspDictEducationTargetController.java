package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictEducationTargetService;

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

@Api("ssp学历定向")
@RestController
@RequestMapping("/rest/ssp/dictEducationTarget")
public class SspDictEducationTargetController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictEducationTargetController.class);

	@Resource
	private SspDictEducationTargetService sspDictEducationTargetService;

	@ApiOperation(value = "查询所有学历定向", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictEducationTarget sspDictEducationTarget) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictEducationTarget :{}", sspDictEducationTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictEducationTargetService.findT(sspDictEducationTarget));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加学历定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictEducationTarget", required = true, dataType = "SspDictEducationTarget", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictEducationTarget sspDictEducationTarget) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictEducationTarget:{}", sspDictEducationTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictEducationTargetService.save(sspDictEducationTarget);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新学历定向", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictEducationTarget", required = true, dataType = "SspDictEducationTarget", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictEducationTarget sspDictEducationTarget) throws Exception {
		LOG.info("update sspDictEducationTarget:{}", sspDictEducationTarget);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictEducationTargetService.updateAndReturnNum(sspDictEducationTarget);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictEducationTarget pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictEducationTarget> pagination = sspDictEducationTargetService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

//	@ApiOperation(value = "删除一个学历定向", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspDictEducationTarget id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspDictEducationTargetService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

	@ApiOperation(value = "批量删除学历定向", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictEducationTarget ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictEducationTargetService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
	@ApiOperation(value = "根据code数组批量查询学历定向对象", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchFindbyCodes", method = RequestMethod.POST)
	public RestResponse batchFindbyCodes(@RequestBody String[] codes) throws DaoException {
		LOG.info("batchFindbyCodes  codes:{}", (Object[]) codes);
		RestResponse restResponse = new RestResponse();
		SspDictEducationTarget[] number = sspDictEducationTargetService.batchFindbyCodes(codes);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
