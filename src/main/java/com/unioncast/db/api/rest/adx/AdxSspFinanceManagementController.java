package com.unioncast.db.api.rest.adx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.adx.model.AdxSspFinanceManagement;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxSspFinanceManagementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 财务管理
 * 
 * @author juchaochao
 * @date 2016年11月2日 上午9:56:04
 *
 */
@Api(value = "财务管理")
@RestController
@RequestMapping("/rest/adxSspFinanceManagement")
public class AdxSspFinanceManagementController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxSspFinanceManagementController.class);

	@Autowired
	private AdxSspFinanceManagementService adxSspFinanceManagementService;

	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月7日 下午4:02:19
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
		restResponse.setResult(adxSspFinanceManagementService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月7日 下午4:50:02
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page AdxSspFinanceManagement pageCriteria:{}", pageCriteria);
		Pagination<AdxSspFinanceManagement> pagination = adxSspFinanceManagementService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月7日 下午5:12:57
	 * @param adxSspFinanceManagement
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspFinanceManagement", required = true, dataType = "AdxSspFinanceManagement", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxSspFinanceManagement adxSspFinanceManagement)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxSspFinanceManagement:{}", adxSspFinanceManagement);
		RestResponse restResponse = new RestResponse();
		Long id = adxSspFinanceManagementService.save(adxSspFinanceManagement);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月7日 下午5:31:51
	 * @param adxSspFinanceManagements
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspFinanceManagements", required = true, dataType = "AdxSspFinanceManagement[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody AdxSspFinanceManagement[] adxSspFinanceManagements) throws DaoException {
		LOG.info("batchAdd adxSspFinanceManagements:{}", adxSspFinanceManagements.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspFinanceManagementService.batchAdd(adxSspFinanceManagements));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:52:15
	 * @param adxSspFinanceManagement
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST")
	@ApiImplicitParam(name = "adxSspFinanceManagement", required = true, dataType = "AdxSspFinanceManagement", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxSspFinanceManagement adxSspFinanceManagement) throws Exception {
		LOG.info("update adxSspFinanceManagement:{}", adxSspFinanceManagement);
		adxSspFinanceManagementService.updateNotNullField(adxSspFinanceManagement);
	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月7日 下午7:12:05
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
		restResponse.setResult(adxSspFinanceManagementService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月7日 下午7:19:14
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete adxSspFinanceManagement ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspFinanceManagementService.batchDelete(ids));
		return restResponse;
	}

}
