package com.unioncast.db.api.rest.common;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.RoleModule;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author： 刘蓉
 * @date： 2016年10月9日 上午9:16:44
 * 角色
 */
@Api("角色")
@RestController
@RequestMapping("/rest/role")
public class RoleController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	/**
	 * 查询所有
	 * 
	 * @author： 刘蓉
	 * @date： 2016年10月9日 上午9:23:05
	 * 
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "查询所有", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	
	@ApiOperation(value = "根据名称查找", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findByName", method = RequestMethod.POST)
	public RestResponse findByName(@RequestBody(required = false) String name) throws DaoException {
		RestResponse restResponse = new RestResponse();
		LOG.info("findByName Role name:{}", name);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.findByName(name));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	/**
	 * 分页查询
	 * 
	 * @author： 刘蓉
	 * @date： 2016年10月9日 上午9:24:39
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
		LOG.info("page Role pageCriteria:{}", pageCriteria);
		Pagination<Role> pagination = roleService.page(pageCriteria);
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/**
	 * 根据systemId进行查找
	 * 
	 * @author： 刘蓉
	 * @date： 2016年10月9日 上午9:31:24
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "根据systemId进行查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "systemId", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/findBySystemId", method = RequestMethod.POST)
	public RestResponse findBySystemId(@RequestBody Long systemId) throws DaoException {
		LOG.info("systemId:{}", systemId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.findBySystemId(systemId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 单个增加
	 * 
	 * @author： 刘蓉
	 * @date： 2016年10月9日 上午9:33:01
	 * 
	 * @param role
	 * @return 返回新增的roleId，在HttpHeader的url属性中包含了新增记录的访问路径
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException 
	 */
	@ApiOperation(value = "单个增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "role", required = true, dataType = "Role", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody Role role)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("role:{}", role);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = roleService.save(role);
		restResponse.setResult(id);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量增加
	 * 
	 * @author： 刘蓉
	 * @date： 2016年10月9日 上午9:33:41
	 * 
	 * @param roles
	 * @return 新增角色id的集合
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "roles", required = true, dataType = "List<Role>", paramType = "body")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	public RestResponse batchAdd(@RequestBody Role[] roles) throws DaoException {
		LOG.info("roles:{}", (Object[]) roles);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.batchAdd(roles));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 更新
	 * @author 刘蓉
	 * 
	 * @date 2016年10月10日 下午5:16:44
	 * @param role
	 * @return 影响的行数(1行)
	 * @throws Exception
	 */
	@ApiOperation(value = "更新", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "role", required = true, dataType = "Role", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody Role role) throws Exception {
		LOG.info("role:{}", role);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.updateAndReturnNum(role));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 单个删除
	 * 
	 * @author： 刘蓉
	 * @date： 2016年10月9日 上午9:35:00
	 * @param id
	 * @return 影响的条数(1条)
	 * @throws DaoException
	 */
	@ApiOperation(value = "单个删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.deleteById(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量删除
	 * 
	 * @author： 刘蓉
	 * @date： 2016年10月9日 上午9:35:30
	 * @param ids
	 * @return 影响的条数
	 * @throws DaoException
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.batchDelete(ids));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 根据角色id删除角色模块表数据
	 * 
	 * @author 刘蓉
	 * @date 2016年10月10日 下午6:47:08
	 * @param roleId
	 * @return 影响的行数
	 */
	@ApiOperation(value = "根据角色id删除角色模块表数据", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "roleId", required = true, dataType = "Long", paramType = "body")
	@RequestMapping(value = "/deleteRoleModule", method = RequestMethod.POST)
	public RestResponse deleteRoleModule(@RequestBody Long roleId) {
		LOG.info("roleId:{}", roleId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.deleteRoleModule(roleId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 根据角色id的集合批量删除角色模块表数据
	 * 
	 * @author 刘蓉
	 * @date 2016年10月10日 下午6:55:14
	 * @param roleIds
	 * @return
	 */
	@ApiOperation(value = "根据角色id的集合批量删除角色模块表数据", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "roleIds", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDeleteRoleModule", method = RequestMethod.POST)
	public RestResponse batchDeleteRoleModule(@RequestBody Long[] roleIds) {

		LOG.info("roleIds:{}", (Object[]) roleIds);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.batchDeleteRoleModule(roleIds));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 批量插入角色模块数据
	 * 
	 * @author 刘蓉
	 * @date 2016年10月10日 下午7:00:36
	 * @param roleModules
	 * @return
	 */
	@ApiOperation(value = "批量插入角色模块数据", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "roleModules", required = true, dataType = "List<RoleModule>", paramType = "body")
	@RequestMapping(value = "/batchAddRoleModule", method = RequestMethod.POST)
	public RestResponse batchAddRoleModule(@RequestBody RoleModule[] roleModules) {
		LOG.info("roleModules:{}", (Object[]) roleModules);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.batchAddRoleModule(roleModules));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	
	/**
	 * 根据角色名name及systemId查找
	 * @author 刘蓉
	 * @date 2016年12月9日 上午11:55:25
	 *
	 * @return
	 */
	@ApiOperation(value = "根据角色名name及systemId查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "param", value = "name和systemId的键值对", required = true, dataType = "Map<String, String>", paramType = "body")
	@RequestMapping(value = "/findByNameAndSystemId", method = RequestMethod.POST)
	public RestResponse findByNameAndSystemId(@RequestBody Map<String, String> param){
		LOG.info("find by name and systemId, param: {}", param);
		String name = param.get("roleName");
		Integer systemId = Integer.parseInt(param.get("systemId"));
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(roleService.findByNameAndSystemId(name, systemId));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
}
