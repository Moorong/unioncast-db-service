package com.unioncast.db.api.rest.adx;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.adx.model.AdxDspAdvertisers;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAdvertisersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 刘蓉
 * @date 2016年10月19日 上午11:13:31
 *
 */
@Api("广告主")
@RestController
@RequestMapping("/rest/adxDspAdvertisers")
public class AdxDspAdvertisersContrller extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxDspAdvertisersContrller.class);
	@Resource
	private AdxDspAdvertisersService adxDspAdvertisersService;

	/**
	 * 查询所有广告主
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午11:00:10
	 *
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有广告主", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		LOG.info("find AdxDspAdvertisers id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspAdvertisersService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}


	/**
	 * 增加一个广告主
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午11:00:36
	 *
	 * @param adxDspAdvertisers
	 * @param uriComponentsBuilder
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException 
	 */
	@ApiOperation(value = "增加一个广告主", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspAdvertisers", required = true, dataType = "AdxDspAdvertisers", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxDspAdvertisers adxDspAdvertisers) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("adxDspAccount:{}", adxDspAdvertisers);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = adxDspAdvertisersService.save(adxDspAdvertisers);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 更新广告主信息
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午11:01:14
	 *
	 * @param adxDspAdvertisers
	 * @throws Exception
	 */
	@ApiOperation(value = "更新广告主信息", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspAdvertisers", required = true, dataType = "AdxDspAdvertisers", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxDspAdvertisers adxDspAdvertisers) throws Exception {
		LOG.info("adxDspAccount:{}", adxDspAdvertisers);
		adxDspAdvertisersService.updateNotNullField(adxDspAdvertisers);
	}

	/**
	 * 根据id删除
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午11:01:45
	 *
	 * @param id
	 * @return
	 * @throws DaoException 
	 */
	@ApiOperation(value = "根据id删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspAdvertisersService.deleteById(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	
	/**
	 * 条件分页查询
	 * @author 刘蓉
	 * @date 2016年11月8日 下午2:48:24
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page AdxDspAdvertisers pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<AdxDspAdvertisers> pagination = adxDspAdvertisersService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	
	/**
	 * 批量添加广告主
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:40:07
	 *
	 * @param adxDspAdvertisersList
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量添加广告主", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspAdvertisersList", required = true, dataType = "List<AdxDspAdvertisers>", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	public RestResponse batchAdd(@RequestBody AdxDspAdvertisers[] adxDspAdvertisersList) throws DaoException{
		LOG.info("batchAdd adxDspAdvertisersList:{}", (Object[]) adxDspAdvertisersList);
		RestResponse restResponse = new RestResponse();
		Long[] ids = adxDspAdvertisersService.batchAdd(adxDspAdvertisersList);
		restResponse.setResult(ids);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("batchAdd AdxDspAdvertisersList ids:{}", (Object[]) ids);
		return restResponse;
	}
	
	/**
	 * 批量删除广告主
	 * @author 刘蓉
	 * @date 2016年11月8日 下午7:56:21
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除广告主", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException{
		LOG.info("batchDelete adxDspAdvertisers ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = adxDspAdvertisersService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
