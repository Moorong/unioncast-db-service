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

import com.unioncast.common.adx.model.AdxSspReportFormDay;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxSspReportFormDayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * ADX-SSP 报表按天统计
 * 
 * @author juchaochao
 * @date 2016年11月2日 上午10:06:49
 *
 */
@Api(value = " ADX-SSP 报表按天统计")
@RestController
@RequestMapping("/rest/adxSspReportFormDay")
public class AdxSspReportFormDayController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxSspReportFormDayController.class);

	@Autowired
	private AdxSspReportFormDayService adxSspReportFormDayService;

	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:03:32
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
		restResponse.setResult(adxSspReportFormDayService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:04:20
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page AdxSspReportFormDay pageCriteria:{}", pageCriteria);
		Pagination<AdxSspReportFormDay> pagination = adxSspReportFormDayService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:05:06
	 * @param adxSspReportFormDay
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspReportFormDay", required = true, dataType = "AdxSspReportFormDay", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxSspReportFormDay adxSspReportFormDay)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxSspReportFormDay:{}", adxSspReportFormDay);
		RestResponse restResponse = new RestResponse();
		Long id = adxSspReportFormDayService.save(adxSspReportFormDay);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:05:59
	 * @param adxSspReportFormDays
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspReportFormDays", required = true, dataType = "AdxSspReportFormDay[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody AdxSspReportFormDay[] adxSspReportFormDays) throws DaoException {
		LOG.info("batchAdd adxSspReportFormDays:{}", adxSspReportFormDays.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspReportFormDayService.batchAdd(adxSspReportFormDays));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:06:52
	 * @param adxSspReportFormDay
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST")
	@ApiImplicitParam(name = "adxSspReportFormDay", required = true, dataType = "AdxSspReportFormDay", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxSspReportFormDay adxSspReportFormDay) throws Exception {
		LOG.info("update adxSspReportFormDay:{}", adxSspReportFormDay);
		adxSspReportFormDayService.updateNotNullField(adxSspReportFormDay);
	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:07:55
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
		restResponse.setResult(adxSspReportFormDayService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:08:27
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete adxSspReportFormDay ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspReportFormDayService.batchDelete(ids));
		return restResponse;
	}

}
