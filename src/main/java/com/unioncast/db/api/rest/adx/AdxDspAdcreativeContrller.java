package com.unioncast.db.api.rest.adx;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.adx.model.AdxDspAdcreative;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAdcreativeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 刘蓉
 * @date 2016年10月26日 下午3:31:11
 *
 */
@Api("广告创意")
@RestController
@RequestMapping("/rest/adxDspAdcreative")
public class AdxDspAdcreativeContrller extends GeneralController {
	private static final Logger LOG = LogManager.getLogger(AdxDspAdcreativeContrller.class);
	
	
	
	@Resource
	private AdxDspAdcreativeService adxDspAdcreativeService;
	
	/**
	 * 查询所有广告创意
	 * @author 刘蓉
	 * @date 2016年10月27日 上午9:37:31
	 *
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有广告创意", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		LOG.info("find AdxDspAdcreative id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspAdcreativeService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 增加一个广告创意
	 * @author 刘蓉
	 * @date 2016年10月27日 上午9:40:35
	 *
	 * @param adxDspAdcreative
	 * @param uriComponentsBuilder
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@ApiOperation(value = "增加一个广告创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspAdcreative", required = true, dataType = "AdxDspAdcreative", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxDspAdcreative adxDspAdcreative) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxDspAdcreative:{}", adxDspAdcreative);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = adxDspAdcreativeService.save(adxDspAdcreative);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 更新广告创意
	 * @author 刘蓉
	 * @date 2016年10月27日 上午9:40:51
	 *
	 * @param adxDspAdcreative
	 * @throws Exception
	 */
	@ApiOperation(value = "更新广告创意", httpMethod = "POST")
	@ApiImplicitParam(name = "adxDspAdcreative", required = true, dataType = "AdxDspAdcreative", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxDspAdcreative adxDspAdcreative) throws Exception {
		LOG.info("update adxDspAdcreative:{}", adxDspAdcreative);
		adxDspAdcreativeService.updateNotNullField(adxDspAdcreative);
	}
	
	/**
	 * 删除一个广告创意
	 * @author 刘蓉
	 * @date 2016年10月27日 上午9:41:09
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "删除一个广告创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete AdxDspAdcreative id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(adxDspAdcreativeService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	
	/**
	 * 条件分页查询
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:33:35
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page AdxDspAdcreative pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<AdxDspAdcreative> pagination = adxDspAdcreativeService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	
	/**
	 * 批量添加广告创意
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:38:46
	 *
	 * @param adxDspAdcreativeList
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量添加广告创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "settingsList", required = true, dataType = "List<AdxDspAdcreative>", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	public RestResponse batchAdd(@RequestBody AdxDspAdcreative[] adxDspAdcreativeList) throws DaoException{
		LOG.info("batchAdd adxDspAdcreativeList:{}", (Object[]) adxDspAdcreativeList);
		RestResponse restResponse = new RestResponse();
		Long[] ids = adxDspAdcreativeService.batchAdd(adxDspAdcreativeList);
		restResponse.setResult(ids);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("batchAdd AdxDspAdcreative ids:{}", (Object[]) ids);
		return restResponse;
	}
	
	/**
	 * 批量删除广告创意
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:38:55
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除广告创意", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException{
		LOG.info("batchDelete AdxDspAdcreative ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = adxDspAdcreativeService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
