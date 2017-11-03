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

import com.unioncast.common.adx.model.AdxSspMedia;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxSspMediaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ADX-SSP_媒体表
 * 
 * @author juchaochao
 * @date 2016年11月2日 上午10:05:51
 *
 */
@Api(value = "ADX-SSP_媒体表")
@RestController
@RequestMapping("/rest/adxSspMedia")
public class AdxSspMediaController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(AdxSspMediaController.class);

	@Autowired
	private AdxSspMediaService adxSspMediaService;

	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:01:03
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
		restResponse.setResult(adxSspMediaService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:01:11
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page AdxSspMedia pageCriteria:{}", pageCriteria);
		Pagination<AdxSspMedia> pagination = adxSspMediaService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:01:24
	 * @param adxSspMedia
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspMedia", required = true, dataType = "AdxSspMedia", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody AdxSspMedia adxSspMedia)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add adxSspMedia:{}", adxSspMedia);
		RestResponse restResponse = new RestResponse();
		Long id = adxSspMediaService.save(adxSspMedia);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:01:34
	 * @param adxSspMedias
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "adxSspMedias", required = true, dataType = "AdxSspMedia[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody AdxSspMedia[] adxSspMedias) throws DaoException {
		LOG.info("batchAdd adxSspMedias:{}", adxSspMedias.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspMediaService.batchAdd(adxSspMedias));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:01:43
	 * @param adxSspMedia
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST")
	@ApiImplicitParam(name = "adxSspMedia", required = true, dataType = "AdxSspMedia", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody AdxSspMedia adxSspMedia) throws Exception {
		LOG.info("update adxSspMedia:{}", adxSspMedia);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspMediaService.updateAndReturnNum(adxSspMedia));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:01:50
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
		restResponse.setResult(adxSspMediaService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月8日 下午3:01:58
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete adxSspMedia ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		int i = adxSspMediaService.batchDelete(ids);
		restResponse.setResult(i);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", i);
		return restResponse;
	}

	/**
	 * 根据appOrWebId查询
	 * 
	 * @author zylei
	 * @data 2016年11月9日 下午4:31:43
	 * @param appOrWebId
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据appOrWebId查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "appOrWebId", required = true, dataType = "Long", paramType = "body") })
	@RequestMapping(value = "/findByAppOrWebId", method = RequestMethod.POST)
	public RestResponse findByAppOrWebId(@RequestBody Long appOrWebId) throws DaoException {
		LOG.info("appOrWebId:{}", appOrWebId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(adxSspMediaService.findByAppOrWebId(appOrWebId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

}
