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
import com.unioncast.common.user.model.Module;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.ModuleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 模块控制器
 * 
 * @author zylei
 * @data 2016年10月8日 下午1:36:04
 */
@RestController
@RequestMapping("/rest/module")
public class ModuleController {

	private static final Logger LOG = LogManager.getLogger(ModuleController.class);

	@Resource
	private ModuleService moduleService;

//	@RequestMapping(value = "/test", method = RequestMethod.POST)
//	public void test() {
//		HttpHeaders requestHeaders = new HttpHeaders();
//		HttpEntity<Long> httpEntity = new HttpEntity<Long>(1L, requestHeaders);
//		RestTemplate restTemplate = restTemplateBuilder.build();
//		ModuleArrayResponse result = restTemplate.postForObject("http://127.0.0.1:9330/v1/rest/module/find", httpEntity,
//				ModuleArrayResponse.class);
//		System.out.println(result);
//	}
	
	/**
	 * 查询所有
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午7:58:41
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body") })
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(moduleService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 条件分页查询
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午7:58:50
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("page Module pageCriteria:{}", pageCriteria);
		Pagination<Module> pagination = moduleService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 根据roleId查询
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午7:59:03
	 * @param roleId
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据roleId查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", required = true, dataType = "Long", paramType = "body") })
	@RequestMapping(value = "/findByRoleId", method = RequestMethod.POST)
	public RestResponse findByRoleId(@RequestBody Long roleId) throws DaoException {
		LOG.info("roleId:{}", roleId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(moduleService.findByRoleId(roleId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 根据(systemId)系统id查询模块
	 * @author zylei
	 * @data   2016年12月21日 上午11:46:51
	 * @param systemId
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据(systemId)系统id查询模块", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "systemId", required = true, dataType = "Long", paramType = "body") })
	@RequestMapping(value = "/findModuleBySystemId", method = RequestMethod.POST)
	public RestResponse findModuleBySystemId(@RequestBody Long systemId) throws DaoException {
		LOG.info("systemId:{}", systemId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(moduleService.findModuleBySystemId(systemId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 根据模块名称和系统id查找模块
	 * @author zylei
	 * @data   2016年12月21日 下午12:05:18
	 * @param param
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据模块名称和系统id查找模块", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "param", required = true, dataType = "Map<String, String>", paramType = "body")})
	@RequestMapping(value = "/findByNameAndSystem", method = RequestMethod.POST)
	public RestResponse findByNameAndSystem(@RequestBody Map<String, String> param) throws DaoException {
		LOG.info("param: {}", param);
		String moduleName = param.get("moduleName");
		Long systemId = Long.parseLong(param.get("systemId"));
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(moduleService.findByNameAndSystem(moduleName, systemId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 增加
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午7:59:17
	 * @param module
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "module", required = true, dataType = "Module", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody Module module)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add module:{}", module);
		RestResponse restResponse = new RestResponse();
		Long id = moduleService.save(module);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午7:59:24
	 * @param module
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "module", required = true, dataType = "Module[]", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse batchAdd(@RequestBody Module[] module) throws DaoException {
		LOG.info("batchAdd module:{}", module.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(moduleService.batchAdd(module));
		return restResponse;
	}

	/**
	 * 更新
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午7:59:33
	 * @param module
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "更新", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "module", required = true, dataType = "Module", paramType = "body") })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody Module module)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("module:{}", module);
		moduleService.updateNotNullField(module);

	}

	/**
	 * 根据id删除
	 * 
	 * @author zylei
	 * @data 2016年11月10日 下午7:59:42
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
		restResponse.setResult(moduleService.deleteById(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author zylei
	 * @data 2016年11月11日 下午1:54:19
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete module ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		int i = moduleService.batchDelete(ids);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(i);
		LOG.info("the number of delete:{}", i);
		return restResponse;
	}

	/**
	 * 根据id删除RoleModule
	 * 
	 * @author zylei
	 * @data 2016年11月11日 下午2:05:10
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据id删除RoleModule", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "body")
	@RequestMapping(value = "/delRoleModById", method = RequestMethod.POST)
	public RestResponse delRoleModById(@RequestBody Long[] ids) throws DaoException {
		LOG.info("ids:{}", ids.toString());
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(moduleService.delRoleModById(ids));
		return restResponse;
	}

}
