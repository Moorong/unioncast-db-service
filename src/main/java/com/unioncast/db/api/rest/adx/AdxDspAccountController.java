package com.unioncast.db.api.rest.adx;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.adx.model.AdxDspAccount;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 刘蓉
 * @date 2016年10月19日 上午11:11:55
 *
 */
@Api("adx-dsp-账户")
@RestController
@RequestMapping("/rest/adxDspAccount")
public class AdxDspAccountController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxDspAccountController.class);
	@Resource
	private AdxDspAccountService adxDspAccountService;

	/**
	 * 查询所有adx-dsp-账户
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午10:47:56
	 *
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有adx-dsp-账户", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		LOG.info("find AdxDspAccount id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspAccountService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 增加一个adx-dsp-账户
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午10:51:35
	 *
	 * @param adxDspAccount
	 * @param uriComponentsBuilder
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@ApiOperation(value = "增加一个adx-dsp-账户", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspAccount", required = true, dataType = "AdxDspAccount", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxDspAccount adxDspAccount) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("adxDspAccount:{}", adxDspAccount);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = adxDspAccountService.save(adxDspAccount);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 修改账户
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午10:53:51
	 *
	 * @param adxDspAccount
	 * @throws Exception
	 */
	@ApiOperation(value = "修改账户", httpMethod = "POST")
	@ApiImplicitParam(name = "adxDspAccount", required = true, dataType = "AdxDspAccount", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxDspAccount adxDspAccount) throws Exception {
		LOG.info("adxDspAccount:{}", adxDspAccount);
		adxDspAccountService.updateNotNullField(adxDspAccount);
	}

	/**
	 * 根据id删除
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午10:55:19
	 *
	 * @param id
	 * @return
	 * @throws DaoException 
	 */
	@ApiOperation(value = "根据id删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspAccountService.deleteById(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 条件分页查询
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:30:59
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page AdxDspAccount pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<AdxDspAccount> pagination = adxDspAccountService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	
	/**
	 * 批量添加adx-dsp-账户
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:31:27
	 *
	 * @param adxDspAccountList
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量添加adx-dsp-账户", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspAccountList", required = true, dataType = "List<AdxDspAccount>", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	public RestResponse batchAdd(@RequestBody AdxDspAccount[] adxDspAccountList) throws DaoException{
		LOG.info("batchAdd adxDspAccessSettingsList:{}", (Object[]) adxDspAccountList);
		RestResponse restResponse = new RestResponse();
		Long[] ids = adxDspAccountService.batchAdd(adxDspAccountList);
		restResponse.setResult(ids);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("batchAdd AdxDspAccessSettings ids:{}", (Object[]) ids);
		return restResponse;
	}
	
	/**
	 * 批量删除adx-dsp-账户
	 * @author 刘蓉
	 * @date 2016年11月8日 下午5:32:31
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除adx-dsp-账户", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException{
		LOG.info("batchDelete adxDspAccessSettings ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = adxDspAccountService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
	
}
