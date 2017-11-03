package com.unioncast.db.api.rest.common;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.user.model.ApiInfo;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.ApiInfoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 系统信息控制器
 * 
 * @author zylei
 * @data 2016年10月11日 下午7:15:35
 */
@RestController
@RequestMapping("/rest/apiInfo")
public class ApiInfoController {

	private static final Logger LOG = LogManager.getLogger(ApiInfoController.class);

	@Resource
	private ApiInfoService apiInfoService;

	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午8:03:48
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
		restResponse.setResult(apiInfoService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 根据(systemId)系统id查询
	 * @author zylei
	 * @data   2016年12月26日 上午11:55:21
	 * @param systemId
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据(systemId)系统id查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "systemId", required = true, dataType = "Long", paramType = "body") })
	@RequestMapping(value = "/findBySystemId", method = RequestMethod.POST)
	public RestResponse findBySystemId(@RequestBody Long systemId) throws DaoException {
		LOG.info("systemId:{}", systemId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(apiInfoService.findBySystemId(systemId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 根据系统id和系统name查找模块
	 * @author zylei
	 * @data   2016年12月26日 下午2:05:57
	 * @param param
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据系统id和系统name查找模块", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "param", required = true, dataType = "Map<String, String>", paramType = "body")})
	@RequestMapping(value = "/findByIdAndName", method = RequestMethod.POST)
	public RestResponse findByIdAndName(@RequestBody Map<String, String> param) throws DaoException {
		LOG.info("param: {}", param);
		Long systemId = Long.parseLong(param.get("systemId"));
		String systemName = param.get("systemName");
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(apiInfoService.findByIdAndName(systemId, systemName));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午8:03:52
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page ApiInfo pageCriteria:{}", pageCriteria);
		Pagination<ApiInfo> pagination = apiInfoService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午8:03:58
	 * @param apiInfo
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "apiInfo", required = true, dataType = "ApiInfo", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody ApiInfo apiInfo)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add apiInfo:{}", apiInfo);
		RestResponse restResponse = new RestResponse();
		Long id = apiInfoService.save(apiInfo);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午8:04:03
	 * @param apiInfo
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "apiInfo", required = true, dataType = "ApiInfo[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody ApiInfo[] apiInfo) throws DaoException {
		LOG.info("batchAdd apiInfo:{}", apiInfo.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(apiInfoService.batchAdd(apiInfo));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午8:04:09
	 * @param apiInfo
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "更新", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "apiInfo", required = true, dataType = "ApiInfo", paramType = "body") })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody ApiInfo apiInfo)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("apiInfo:{}", apiInfo);
		apiInfoService.updateNotNullField(apiInfo);

	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午8:04:14
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
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(apiInfoService.deleteById(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午8:04:19
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete apiInfo ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		int i = apiInfoService.batchDelete(ids);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(i);
		LOG.info("the number of delete:{}", i);
		return restResponse;
	}

}
