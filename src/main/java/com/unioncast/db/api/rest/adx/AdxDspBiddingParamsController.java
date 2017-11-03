package com.unioncast.db.api.rest.adx;

import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.adx.model.AdxDspBiddingParamCriteria;
import com.unioncast.common.adx.model.AdxDspBiddingParams;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxDspBiddingParamsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 刘蓉
 * @date 2016年10月19日 上午11:14:03
 *
 */
@Api("adx-dsp竞价参数")
@RestController
@RequestMapping("/rest/adxDspBiddingParam")
public class AdxDspBiddingParamsController {

	private static final Logger LOG = LogManager.getLogger(AdxDspBiddingParamsController.class);
	@Resource
	private AdxDspBiddingParamsService adxDspBiddingParamsService;

	/**
	 * 查询所有ADX-DSP竞价参数
	 * 
	 * @author 刘蓉
	 * @date 2016年11月8日 下午7:56:39
	 *
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有ADX-DSP竞价参数", httpMethod = "POST")
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		LOG.info("find AdxDspBiddingParams id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspBiddingParamsService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 增加一个ADX-DSP竞价参数
	 * 
	 * @author 刘蓉
	 * @date 2016年11月8日 下午7:58:43
	 *
	 * @param adxDspBiddingParams
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST")
	@ApiImplicitParam(name = "adxDspBidding", required = true, dataType = "AdxDspBidding", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxDspBiddingParams adxDspBiddingParams)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxDspBidding:{}", adxDspBiddingParams);
		RestResponse restResponse = new RestResponse();
		Long id = adxDspBiddingParamsService.save(adxDspBiddingParams);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("add adxDspBidding id:{}", id);
		return restResponse;
	}

	/**
	 * 更新ADX-DSP竞价参数
	 * 
	 * @author 刘蓉
	 * @date 2016年11月8日 下午7:59:09
	 *
	 * @param adxDspBiddingParams
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST")
	@ApiImplicitParam(name = "adxDspBidding", required = true, dataType = "AdxDspBidding", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxDspBiddingParams adxDspBiddingParams) throws Exception {
		LOG.info("update adxDspBidding:{}", adxDspBiddingParams);
		adxDspBiddingParamsService.updateNotNullField(adxDspBiddingParams);
	}

	/**
	 * 根据id删除一个ADX-DSP竞价参数
	 * 
	 * @author 刘蓉
	 * @date 2016年11月8日 下午7:59:25
	 *
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据id删除", httpMethod = "POST")
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete adxDspBidding id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(adxDspBiddingParamsService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("delete adxDspBidding restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 获取竞价消费各参数
	 * 
	 * @author 刘蓉
	 * @date 2016年11月1日 下午4:17:12
	 *
	 * @param flowType
	 * @param dspId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@ApiOperation(value = "获取竞价消费各参数", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "biddingParamCriteria", required = true, dataType = "AdxDspBiddingParamCriteria", paramType = "body")
	@RequestMapping("/getSumDataByBE")
	public @ResponseBody RestResponse getSumDataByBE(@RequestBody AdxDspBiddingParamCriteria biddingParamCriteria) {
		LOG.info("getSumDataByBE AdxDspBiddingParamCriteria:{}", biddingParamCriteria);
		RestResponse restResponse = new RestResponse();
		AdxDspBiddingParams adxDspBiddingParams = adxDspBiddingParamsService.getSumDataByBE(biddingParamCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspBiddingParams);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 获取每日竞价消费明细
	 * 
	 * @author 刘蓉
	 * @date 2016年11月2日 上午9:55:54
	 *
	 * @param dspId
	 * @param flowType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	@ApiOperation(value = "获取每日竞价消费明细", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "biddingParamCriteria", required = true, dataType = "AdxDspBiddingParamCriteria", paramType = "body")
	@RequestMapping("/getDataByBE")
	public @ResponseBody RestResponse getDataByBE(@RequestBody AdxDspBiddingParamCriteria biddingParamCriteria)
			throws ParseException {
		LOG.info("getSumDataByBE AdxDspBiddingParamCriteria:{}", biddingParamCriteria);
		RestResponse restResponse = new RestResponse();
		Map<String, AdxDspBiddingParams> adxDspBiddingParamsMap = adxDspBiddingParamsService.getDataByBE(biddingParamCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspBiddingParamsMap);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:46:36
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page AdxDspBiddingParams pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<AdxDspBiddingParams> pagination = adxDspBiddingParamsService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 批量添加adx-dsp竞价参数
	 * 
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:46:48
	 *
	 * @param settingsList
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量添加adx-dsp竞价参数", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspBiddingParamsList", required = true, dataType = "List<AdxDspBiddingParams>", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	public RestResponse batchAdd(@RequestBody AdxDspBiddingParams[] adxDspBiddingParamsList) throws DaoException {
		LOG.info("batchAdd adxDspAccessSettingsList:{}", (Object[]) adxDspBiddingParamsList);
		RestResponse restResponse = new RestResponse();
		Long[] ids = adxDspBiddingParamsService.batchAdd(adxDspBiddingParamsList);
		restResponse.setResult(ids);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("batchAdd AdxDspBiddingParams ids:{}", (Object[]) ids);
		return restResponse;
	}

	/**
	 * 批量删除adx-dsp竞价参数
	 * 
	 * @author 刘蓉
	 * @date 2016年11月8日 下午8:00:25
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除adx-dsp竞价参数", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete adxDspBiddingParams ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = adxDspBiddingParamsService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
