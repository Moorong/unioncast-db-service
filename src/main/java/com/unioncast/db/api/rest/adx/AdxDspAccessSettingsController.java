		package com.unioncast.db.api.rest.adx;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.adx.model.AdxDspAccessSettings;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxDspAccessSettingsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 刘蓉
 * @date 2016年10月19日 上午10:26:55
 *
 */
@Api("dsp接入设置")
@RestController
@RequestMapping("/rest/adxDspAccessSettings")
public class AdxDspAccessSettingsController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxDspAccessSettingsController.class);

	@Resource
	private AdxDspAccessSettingsService adxDspAccessSettingsService;

	/**
	 * 查询所有dsp接入设置
	 * 
	 * @author 刘蓉
	 * @date 2016年10月19日 下午4:56:55
	 *
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有dsp接入设置", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		LOG.info("find id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(adxDspAccessSettingsService.find(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}
	
	/**
	 * 根据dspId查询
	 * @author 刘蓉
	 * @date 2016年11月8日 下午7:53:08
	 *
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据dspId查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "dspId", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/findByDspId", method = RequestMethod.POST)
	public RestResponse findByDspId(@RequestBody Long id) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("findByDspId dspId:{}", id);
		restResponse.setResult(adxDspAccessSettingsService.findByDspId(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	

	/**
	 * 新增一个AdxDspAccessSettings实体
	 * 
	 * @author 刘蓉
	 * @date 2016年10月19日 下午2:25:52
	 *
	 * @param adxDspAccessSettings
	 * @param uriComponentsBuilder
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@ApiOperation(value = "新增一个AdxDspAccessSettings实体", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxDspAccessSettings", required = true, dataType = "AdxDspAccessSettings", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxDspAccessSettings adxDspAccessSettings) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxDspAccessSettings:{}", adxDspAccessSettings);
		RestResponse restResponse = new RestResponse();
		Long id = adxDspAccessSettingsService.save(adxDspAccessSettings);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author 刘蓉
	 * @date 2016年10月19日 下午4:54:23
	 *
	 * @param adxDspAccessSettings
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST")
	@ApiImplicitParam(name = "adxDspAccessSettings", required = true, dataType = "AdxDspAccessSettings", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody AdxDspAccessSettings adxDspAccessSettings) throws Exception {
		LOG.info("update adxDspAccessSettings:{}", adxDspAccessSettings);
		adxDspAccessSettingsService.updateNotNullField(adxDspAccessSettings);
	}

	/**
	 * 根据id删除
	 * 
	 * @author 刘蓉
	 * @date 2016年10月20日 上午10:43:52
	 *
	 * @param id
	 * @return
	 * @throws DaoException 
	 */
	@ApiOperation(value = "根据id删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("deleteById id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(adxDspAccessSettingsService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 批量添加dsp接入设置
	 * @author 刘蓉
	 * @date 2016年10月27日 上午11:21:06
	 *
	 * @param settingsList
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量添加dsp接入设置", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "settingsList", required = true, dataType = "List<AdxDspAccessSettings>", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	public RestResponse batchAdd(@RequestBody AdxDspAccessSettings[] settingsList) throws DaoException{
		LOG.info("batchAdd adxDspAccessSettingsList:{}", (Object[]) settingsList);
		RestResponse restResponse = new RestResponse();
		Long[] ids = adxDspAccessSettingsService.batchAdd(settingsList);
		restResponse.setResult(ids);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("batchAdd AdxDspAccessSettings ids:{}", (Object[]) ids);
		return restResponse;
	}
	
	/**
	 * 批量删除dsp接入设置
	 * @author 刘蓉
	 * @date 2016年10月27日 上午11:21:32
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除dsp接入设置", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException{
		LOG.info("batchDelete adxDspAccessSettings ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = adxDspAccessSettingsService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
	
	/**
	 * 条件分页查询
	 * @author 刘蓉
	 * @date 2016年11月2日 上午10:51:13
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page AdxDspAccessSettings pageCriteria:{}", pageCriteria);
		Pagination<AdxDspAccessSettings> pagination = adxDspAccessSettingsService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}
	
	/**
	 * 通过流量类型查询
	 * @author 刘蓉
	 * @date 2016年11月10日 下午3:17:51
	 *
	 * @param flowType
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "通过流量类型查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "flowType", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/findByFlowType", method = RequestMethod.POST)
	public RestResponse findByFlowType(@RequestBody Integer flowType) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("findByFlowType AdxDspAccessSettings flowType:{}", flowType);
		restResponse.setStatus(RestResponse.OK);
		AdxDspAccessSettings[] adxDspAccessSettingsArray = adxDspAccessSettingsService.findByFlowType(flowType);
		restResponse.setResult(adxDspAccessSettingsArray);
		LOG.info("findByFlowType adxDspAccessSettingsArray:{}", (Object[])adxDspAccessSettingsArray);
		return restResponse;
	}
	
	
	/**
	 * 存储过程示例接口
	 * @author 刘蓉
	 * @date 2016年11月7日 下午2:31:45
	 *
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	@ApiOperation(value = "存储过程示例接口", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", value = "json格式的id值", required = true, dataType = "Map<String, Long>", paramType = "body")
	@RequestMapping(value = "/procedure", method = RequestMethod.POST)
	public RestResponse procedure(@RequestBody(required = true) Long id) throws SQLException{
		LOG.info("procedure AdxDspAccessSettings id:{}", id);
		RestResponse restResponse = new RestResponse();
		AdxDspAccessSettings adxDspAccessSettings = adxDspAccessSettingsService.procedure(id);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxDspAccessSettings);
		return restResponse;
	}
}
