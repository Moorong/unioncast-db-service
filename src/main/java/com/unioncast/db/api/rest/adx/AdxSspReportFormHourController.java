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

import com.unioncast.common.adx.model.AdxSspReportFormHour;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxSspReportFormHourService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * ADX-SSP 报表按小时统计
 * 
 * @author juchaochao
 * @date 2016年11月2日 上午10:07:50
 *
 */
@Api(value = "ADX-SSP 报表按小时统计")
@RestController
@RequestMapping("/rest/adxSspReportFormHour")
public class AdxSspReportFormHourController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxSspReportFormHourController.class);

	@Autowired
	private AdxSspReportFormHourService adxSspReportFormHourService;

	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:12:31
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
		restResponse.setResult(adxSspReportFormHourService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:12:39
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page AdxSspReportFormHour pageCriteria:{}", pageCriteria);
		Pagination<AdxSspReportFormHour> pagination = adxSspReportFormHourService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:12:49
	 * @param adxSspReportFormHour
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspReportFormHour", required = true, dataType = "AdxSspReportFormHour", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxSspReportFormHour adxSspReportFormHour)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxSspReportFormHour:{}", adxSspReportFormHour);
		RestResponse restResponse = new RestResponse();
		Long id = adxSspReportFormHourService.save(adxSspReportFormHour);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:13:00
	 * @param adxSspReportFormHours
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspReportFormHours", required = true, dataType = "AdxSspReportFormHour[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody AdxSspReportFormHour[] adxSspReportFormHours) throws DaoException {
		LOG.info("batchAdd adxSspReportFormHours:{}", adxSspReportFormHours.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspReportFormHourService.batchAdd(adxSspReportFormHours));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:13:11
	 * @param adxSspReportFormHour
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST")
	@ApiImplicitParam(name = "adxSspReportFormHour", required = true, dataType = "AdxSspReportFormHour", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxSspReportFormHour adxSspReportFormHour) throws Exception {
		LOG.info("update adxSspReportFormHour:{}", adxSspReportFormHour);
		adxSspReportFormHourService.updateNotNullField(adxSspReportFormHour);
	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:13:23
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
		restResponse.setResult(adxSspReportFormHourService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:13:34
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete adxSspReportFormHour ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspReportFormHourService.batchDelete(ids));
		return restResponse;
	}

}
