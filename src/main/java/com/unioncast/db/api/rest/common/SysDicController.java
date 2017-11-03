package com.unioncast.db.api.rest.common;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.page.DeleteParam;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.user.model.SysDic;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.SysDicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 系统字典
 * 
 * @author juchaochao
 * @date 2016年11月9日 上午11:59:17
 *
 */
@Api(value = "系统字典")
@RestController
@RequestMapping("/rest/sysDic")
public class SysDicController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SysDicController.class);

	@Autowired
	private SysDicService sysDicService;

	/**
	 * 未分页条件查找
	 * 
	 * @author juchaochao
	 * @date 2016年11月9日 下午1:20:51
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@ApiOperation(value = "未分页条件查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageCriteria", required = true, dataType = "PageCriteria<SysDic>", paramType = "body") })
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody PageCriteria pageCriteria)
			throws DaoException, InstantiationException, IllegalAccessException {
		LOG.info("pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sysDicService.find(pageCriteria));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 分页条件查找
	 * 
	 * @author juchaochao
	 * @date 2016年11月9日 下午4:58:30
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "分页条件查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageCriteria", required = true, dataType = "PageCriteria<SysDic>", paramType = "body") })
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria)
			throws DaoException, InstantiationException, IllegalAccessException {
		LOG.info("pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sysDicService.generalPage(pageCriteria));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 增加(适用于单个和批量)
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:23:40
	 *
	 * @param qualification
	 * @return
	 * @throws SQLException
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@ApiOperation(value = "增加(适用于单个和批量)", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysDics", required = true, dataType = "SysDic[]", paramType = "body") })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse add(@RequestBody SysDic[] sysDics)
			throws SQLException, DaoException, IllegalArgumentException, IllegalAccessException {
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sysDicService.add(sysDics));
		return restResponse;
	}

	/**
	 * 删除（适用于单个和批量）note:父字典删除后，子字典也会删除，请慎重操作！
	 * 
	 * @author juchaochao
	 * @param <T>
	 * @date 2016年11月10日 下午1:56:23
	 *
	 * @param ids
	 * @return 删除了多少条
	 * @throws DaoException
	 */
	@ApiOperation(value = "删除（适用于单个和批量）", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body") })
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody DeleteParam deleteParam) throws DaoException {
		RestResponse restResponse = new RestResponse();
		int deleteCount = sysDicService.delete(deleteParam.getEntityClass(), deleteParam.getIds());
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(deleteCount);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 修改（适用于单个和批量）
	 * 
	 * @author juchaochao
	 * @date 2016年11月11日 下午2:52:00
	 *
	 * @param sysDics
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "修改（适用于单个和批量）", httpMethod = "POST")
	@ApiImplicitParam(name = "sysDics", required = true, dataType = "SysDic[]", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SysDic[] sysDics) throws Exception {
		RestResponse restResponse = new RestResponse();
		LOG.info("sysDics:{}", (Object) sysDics);
		int count = sysDicService.updateNotNullField(sysDics);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(count);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

}
