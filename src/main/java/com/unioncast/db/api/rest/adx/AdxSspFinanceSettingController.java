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

import com.unioncast.common.adx.model.AdxSspFinanceSetting;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxSspFinanceSettingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 财务设置
 * 
 * @author juchaochao
 * @date 2016年11月2日 上午10:05:38
 *
 */
@Api(value = "财务设置")
@RestController
@RequestMapping("/rest/adxSspFinanceSetting")
public class AdxSspFinanceSettingController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxSspFinanceSettingController.class);

	@Autowired
	private AdxSspFinanceSettingService adxSspFinanceSettingService;

	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:37:31
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
		restResponse.setResult(adxSspFinanceSettingService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:40:45
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page AdxSspFinanceSetting pageCriteria:{}", pageCriteria);
		Pagination<AdxSspFinanceSetting> pagination = adxSspFinanceSettingService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:49:47
	 * @param adxSspFinanceSetting
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspFinanceSetting", required = true, dataType = "AdxSspFinanceSetting", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxSspFinanceSetting adxSspFinanceSetting)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxSspFinanceSetting:{}", adxSspFinanceSetting);
		RestResponse restResponse = new RestResponse();
		Long id = adxSspFinanceSettingService.save(adxSspFinanceSetting);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:52:44
	 * @param adxSspFinanceSettings
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspFinanceSettings", required = true, dataType = "AdxSspFinanceSetting[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody AdxSspFinanceSetting[] adxSspFinanceSettings) throws DaoException {
		LOG.info("batchAdd adxSspFinanceSettings:{}", adxSspFinanceSettings.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspFinanceSettingService.batchAdd(adxSspFinanceSettings));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:54:01
	 * @param adxSspFinanceSetting
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST")
	@ApiImplicitParam(name = "adxSspFinanceSetting", required = true, dataType = "AdxSspFinanceSetting", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxSspFinanceSetting adxSspFinanceSetting) throws Exception {
		LOG.info("update adxSspFinanceSetting:{}", adxSspFinanceSetting);
		adxSspFinanceSettingService.updateNotNullField(adxSspFinanceSetting);
	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:57:21
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
		restResponse.setResult(adxSspFinanceSettingService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 上午10:57:32
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete adxSspFinanceSetting ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspFinanceSettingService.batchDelete(ids));
		return restResponse;
	}
}
