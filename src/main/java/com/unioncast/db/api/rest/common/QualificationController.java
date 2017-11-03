package com.unioncast.db.api.rest.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.user.model.Qualification;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.QualificationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "资质信息", "QualificationController" })
@RestController
@RequestMapping("/rest/qualification")
public class QualificationController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(QualificationController.class);

	@Autowired
	private QualificationService qualificationService;

	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:53:36
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(qualificationService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:53:40
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page Qualification pageCriteria:{}", pageCriteria);
		Pagination<Qualification> pagination = qualificationService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 根据userId查询
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:53:44
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据userId查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", required = true, dataType = "Long", paramType = "body") })
	@RequestMapping(value = "/findByUserId", method = RequestMethod.POST)
	public RestResponse findByUserId(@RequestBody Long userId) throws DaoException {
		LOG.info("userId:{}", userId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(qualificationService.findByUserId(userId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:53:48
	 * @param qualification
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "qualification", required = true, dataType = "Qualification", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody Qualification qualification)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add qualification:{}", qualification);
		RestResponse restResponse = new RestResponse();
		Long id = qualificationService.save(qualification);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:53:53
	 * @param qualifications
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "qualifications", required = true, dataType = "Qualification[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody Qualification[] qualifications) throws DaoException {
		LOG.info("batchAdd qualifications:{}", qualifications.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(qualificationService.batchAdd(qualifications));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:53:57
	 * @param qualification
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "更新", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "qualification", required = true, dataType = "Qualification", paramType = "body") })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody Qualification qualification)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("qualification:{}", qualification);
		qualificationService.updateNotNullField(qualification);

	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:54:02
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据id删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("deleteById id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(qualificationService.deleteById(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:54:06
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete qualification ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		int i = qualificationService.batchDelete(ids);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(i);
		LOG.info("the number of delete:{}", i);
		return restResponse;
	}

}
