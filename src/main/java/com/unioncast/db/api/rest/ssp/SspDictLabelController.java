package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictLabel;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictLabelService;

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

/**
 * 
 * @author juchaochao
 * @2017年1月19日@上午11:12:29
 */
@Api("ssp创意标签")
@RestController
@RequestMapping("/rest/ssp/dictLabel")
public class SspDictLabelController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspDictLabelController.class);

	@Resource
	private SspDictLabelService sspDictLabelService;
	
	/**
	 * 
	 * @author juchaochao
	 * @2017年1月19日上午11:14:34
	 * @param sspDictLabel
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "查询所有创意标签", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictLabel sspDictLabel) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictLabel :{}", sspDictLabel);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictLabelService.findT(sspDictLabel));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加创意标签", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspDictLabel", required = true, dataType = "SspDictLabel", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspDictLabel sspDictLabel) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspDictLabel:{}", sspDictLabel);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspDictLabelService.save(sspDictLabel);
		sspDictLabel.setId(id);
		sspDictLabel.setCode(Long.parseLong((id + "000001")));
		sspDictLabelService.updateNotNullField(sspDictLabel);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新创意标签", httpMethod = "POST")
	@ApiImplicitParam(name = "sspDictLabel", required = true, dataType = "SspDictLabel", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspDictLabel sspDictLabel) throws Exception {
		LOG.info("update sspDictLabel:{}", sspDictLabel);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspDictLabelService.updateAndReturnNum(sspDictLabel);
		restResponse.setResult(i);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspDictLabel pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspDictLabel> pagination = sspDictLabelService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	
	@ApiOperation(value = "分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "clickCount", value = "分页查询条件", required = true, dataType = "Integer", paramType = "body")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	public RestResponse findPage(@RequestBody Integer clickCount) throws DaoException {
		LOG.info("page sspDictLabel clickCount:{}", clickCount);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		SspDictLabel[] sspDictLabels = sspDictLabelService.findPage(clickCount);
		LOG.info("sspDictLabels:{}", sspDictLabels);
		restResponse.setResult(sspDictLabels);
		return restResponse;
	}

	@ApiOperation(value = "查询标签总个数", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	public RestResponse count() throws DaoException {
		LOG.info("count sspDictLabel");
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspDictLabelService.count());
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}

	@ApiOperation(value = "批量删除创意标签", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspDictLabel ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspDictLabelService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
