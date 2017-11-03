package com.unioncast.db.api.rest.common;

import com.unioncast.db.nosql.redis.SspRedisMemory;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.user.model.Role;
import com.unioncast.common.user.model.User;
import com.unioncast.common.user.model.UserRole;
import com.unioncast.common.user.model.UserSystem;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 *
 * @author zhangzhe
 * @date 2016年10月25日 下午7:07:03
 */
@Api(value = "用户信息")
@RestController
@RequestMapping("/rest/user")
public class UserController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SspRedisMemory sspRedisMemory;

	/**
	 * 分页条件查找
	 *
	 * @param pageCriteria
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日下午3:14:54
	 */
	@ApiOperation(value = "分页条件查找", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("PageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.page(pageCriteria));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 分页角色系统id查找
	 *
	 * @param pageCriteria
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日下午3:14:54
	 */
	@ApiOperation(value = "分页角色系统id查找", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/pageByRoleSysId", method = RequestMethod.POST)
	public RestResponse pageByRoleSysId(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("PageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.pageByRoleSysIds(pageCriteria));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据条件分页查找（ssp）
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 * @author wangyao
	 * @date 2017/1/13 10:17
	 */
	@ApiOperation(value = "根据条件分页查找(ssp)", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/sspUserPage", method = RequestMethod.POST)
	public RestResponse sspUserPage(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("PageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.sspUserPage(pageCriteria));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 角色系统id查找
	 *
	 * @param role
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日下午3:14:54
	 */
	@ApiOperation(value = "角色系统id查找", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findByRoleSysIds", method = RequestMethod.POST)
	public RestResponse findByRoleSysIds(@RequestBody Role role) throws DaoException {
		LOG.info("role:{}", role);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findByRole(role));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据角色ID获取当前角色的所有用户信息
	 *
	 * @param pageCriteria
	 * @return
	 * @author zhangzhe
	 * @date 2016年11月11日下午3:14:54
	 */
	@ApiOperation(value = "根据角色ID获取当前角色的所有用户信息", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/pageByRoleIds", method = RequestMethod.POST)
	public RestResponse pageByRoleIds(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("PageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.pageByRoleIds(pageCriteria));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 返回UserCount
	 *
	 * @return
	 * @author zhangzhe
	 * @date 2016年11月11日下午3:14:54
	 */
	@ApiOperation(value = "返回UserCount", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findUserCount", method = RequestMethod.POST)
	public RestResponse findUserCount() throws DaoException {
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findUserCount());
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * user对象查询
	 *
	 * @param user
	 * @return User
	 * @author zhangzhe
	 * @date 2016年12月8日上午9:52:27
	 */
	@ApiOperation(value = "对象查询", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findUserByUser", method = RequestMethod.POST)
	public RestResponse findUserByUser(@RequestBody User user) throws DaoException {
		LOG.info("user:{}", user);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findUserByUser(user));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 查找所有or根据id查询
	 *
	 * @param id
	 * @return User
	 * @author zhangzhe
	 * @date 2016年10月8日上午9:52:27
	 */
	@ApiOperation(value = "查找所有or根据id查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path") })
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.find(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据登陆账号查找
	 *
	 * @param loginName
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日上午10:01:00
	 */
	@ApiOperation(value = "根据登陆账号查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", required = true, dataType = "String", paramType = "path") })
	@RequestMapping(value = "/findByLoginName", method = RequestMethod.POST)
	public RestResponse findByLoginName(@RequestBody String loginName) throws DaoException {
		LOG.info("loginName:{}", loginName);

		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findByString(loginName));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据username查找
	 *
	 * @param username
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日上午10:01:00
	 */
	@ApiOperation(value = "根据username查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", required = true, dataType = "String", paramType = "path") })
	@RequestMapping(value = "/findByUsername", method = RequestMethod.POST)
	public RestResponse findByUsername(@RequestBody String username) throws DaoException {
		LOG.info("username:{}", username);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findByUsername(username));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据邮箱查找
	 *
	 * @param email
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日上午10:01:00
	 */
	@ApiOperation(value = "根据邮箱查找", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "email", required = true, dataType = "String", paramType = "path") })
	@RequestMapping(value = "/findByEmail", method = RequestMethod.POST)
	public RestResponse findByEmail(@RequestBody String email) throws DaoException {
		LOG.info("email:{}", email);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findByEmail(email));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 添加
	 *
	 * @param user
	 * @return Long
	 * @author zhangzhe
	 * @date 2016年9月30日下午3:39:08
	 */

	@ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "user", required = true, dataType = "User", paramType = "body") })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody User user)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("user:{}", user);

		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		Long id = userService.save(user);
		restResponse.setResult(id);
		User[] users = new User[1];
		user.setId(id);
		users[0] = user;
		// 保存redis服务
		try {
			sspRedisMemory.batchAddUserInfo(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 批量添加
	 *
	 * @param users
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月10日 上午9:51:41
	 */
	@ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "users", required = true, dataType = "User[]", paramType = "body") })
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RestResponse saveAll(@RequestBody User[] users) throws DaoException {

		LOG.info("users:{}", users);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.batchAdd(users));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 修改
	 *
	 * @param user
	 * @author zhangzhe
	 * @date 2016年10月8日上午10:29:33
	 */
	@ApiOperation(value = "修改", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "user", required = true, dataType = "User", paramType = "body") })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody User user)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("user:{}", user);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.updateAndReturnNum(user));

		try {
			sspRedisMemory.batchDeleteByUserIds(new Long[]{user.getId()});
			User[] users = userService.findUserByUser(user);
			sspRedisMemory.batchAddUserInfo(users);
		} catch (Exception e) {
			e.printStackTrace();
		}


		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 根据邮箱修改密码
	 *
	 * @param user
	 * @return
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @author wangyao
	 * @date 2017/2/10 13:01
	 */
	@ApiOperation(value = "根据邮箱修改密码", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "user", required = true, dataType = "User", paramType = "body") })
	@RequestMapping(value = "/updateByEmail", method = RequestMethod.POST)
	public RestResponse updateByEmail(@RequestBody User user)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("user:{}", user);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.updateByEmail(user));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return Long
	 * @author zhangzhe
	 * @date 2016年9月30日下午3:39:20
	 */
	@ApiOperation(value = "删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path") })
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse deleteById(@RequestBody Long id) throws DaoException {
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.deleteById(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日上午10:25:47
	 */
	@ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "path") })
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("ids:{}", ids);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.deleteById(ids));
		try {
			sspRedisMemory.batchDeleteByUserIds(ids);
			List<User> list = new ArrayList<User>();
			for(Long id : ids) {
				User[] user = userService.find(id);
				if(ArrayUtils.isNotEmpty(user)) {
					list.add(user[0]);
				}
			}
			if(list != null && list.size() > 0) {
				sspRedisMemory.batchAddUserInfo(list.toArray(new User[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	// ==================和user_role相关=======================================================

	/**
	 * 根据用户id 删除 user role
	 *
	 * @param id
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月10日 下午4:19:10
	 */
	@ApiOperation(value = "根据用户id 删除 user role", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/deleteUserRoleByUserId", method = RequestMethod.POST)
	public RestResponse deleteUserRoleByUserId(@RequestBody Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.deleteUserRoleByUserId(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据roleId 删除 userrole
	 *
	 * @param id
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月10日 下午4:19:10
	 */
	@ApiOperation(value = "根据roleId 删除 userrole", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/deleteUserRoleByRoleId", method = RequestMethod.POST)
	public RestResponse deleteUserRoleByRoleId(@RequestBody Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.deleteUserRoleByRoleId(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据roleId 批量删除 userrole
	 *
	 * @param ids
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月10日 下午4:19:10
	 */
	@ApiOperation(value = "根据roleId 批量删除 userrole", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchDeleteUserRoleByRoleId", method = RequestMethod.POST)
	public RestResponse batchDeleteUserRoleByRoleId(@RequestBody Long[] ids) throws DaoException {
		LOG.info("ids:{}", ids);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.batchDeleteUserRoleByRoleId(ids));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 批量添加userRole
	 *
	 * @param userRoles
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月10日 上午9:51:41
	 */
	@ApiOperation(value = "批量添加userRole", httpMethod = "POST", response = RestResponse.class)
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/batchAddUserRole", method = RequestMethod.POST)
	public RestResponse batchAddUserRole(@RequestBody UserRole[] userRoles) throws DaoException {
		LOG.info("userRoles:{}", userRoles);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.batchAddUserRole(userRoles));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据用户id查询用户角色list
	 *
	 * @param id
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月20日 上午10:49:55
	 */
	@ApiOperation(value = "根据用户id查询用户角色list", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findRoleByUserId", method = RequestMethod.POST)
	public RestResponse findRoleByUserId(@RequestBody Long id) throws DaoException {
		LOG.info("id:{}", id);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findRoleByUserId(id));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/*************************** UserSystem部分方法 **********************************/

	/**
	 * 批量添加userSystem
	 *
	 * @param userSystems
	 * @return
	 * @author wangyao
	 * @date 2017/2/15 16:03
	 */
	@ApiOperation(value = "批量添加userSystem", httpMethod = "POST", response = RestResponse.class)
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/batchAddUserSystem", method = RequestMethod.POST)
	public RestResponse batchAddUserSystem(@RequestBody UserSystem[] userSystems) throws DaoException {
		LOG.info("userSystems:{}", userSystems);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.batchAddUserSystem(userSystems));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	/**
	 * 根据userId 批量删除 userSystem
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 * @author wangyao
	 * @date 2017/2/15 16:23
	 */
	@ApiOperation(value = "根据userId 批量删除 userSystem", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchDeleteUserSystemByUserId", method = RequestMethod.POST)
	public RestResponse batchDeleteUserSystemByUserId(@RequestBody Long[] ids) throws DaoException {
		LOG.info("ids:{}", ids);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.batchDeleteUserSystemByUserId(ids));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

	@ApiOperation(value = "根据用户信息及系统id查询用户", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findUserByUserSystem", method = RequestMethod.POST)
	public RestResponse findUserByUserSystem(@RequestBody User user) throws DaoException {
		LOG.info("user:{}", user);
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findUserByUserSystem(user));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}




	// /**
	// * 用户登陆验证,包括角色验证
	// *
	// * @param loginname
	// * @param password
	// * @param roleid
	// * @return
	// * @author zhangzhe
	// * @date 2016年10月20日 上午11:00:11
	// */
	// @ApiOperation(value = "用户登陆验证,包括角色验证", httpMethod = "POST", response =
	// RestResponse.class)
	// @RequestMapping(value = "/checkbyLoginPWR", method = RequestMethod.POST)
	// public RestResponse checkbyLoginPWR(@RequestBody String loginname,
	// @RequestBody String password,
	// @RequestBody Long roleid) throws DaoException {
	// LOG.info("loginname:{}", loginname);
	// LOG.info("password:{}", password);
	// LOG.info("roleid:{}", roleid);
	// RestResponse restResponse = new RestResponse();
	//
	// restResponse.setStatus(RestResponse.OK);
	// restResponse.setResult(userService.checkbyLoginPWR(loginname, password,
	// roleid));
	// LOG.info("restResponse:{}", restResponse);
	// return restResponse;
	//
	// }

	//
	//
	// /**
	// * 分页查找
	// *
	// * @param currentPageNo
	// * @param pageSize
	// * @return
	// * @author zhangzhe
	// * @date 2016年10月8日上午10:13:34
	// */
	// @ApiOperation(value = "分页查找", httpMethod = "POST", response =
	// RestResponse.class)
	// @ApiImplicitParams({
	// @ApiImplicitParam(name = "currentPageNo", required = true, dataType =
	// "Integer", paramType = "path"),
	// @ApiImplicitParam(name = "pageSize", required = true, dataType =
	// "Integer", paramType = "path")})
	// @RequestMapping(value = "/paginationAll", method = RequestMethod.POST)
	// public RestResponse paginationAll(@RequestBody Integer currentPageNo,
	// @RequestBody Integer pageSize)
	// throws DaoException {
	// LOG.info("currentPageNo:{}", currentPageNo);
	// LOG.info("pageSize:{}", pageSize);
	// RestResponse restResponse = new RestResponse();
	//
	// restResponse.setStatus(RestResponse.OK);
	// restResponse.setResult(userService.paginationAll(currentPageNo,
	// pageSize));
	// LOG.info("restResponse:{}", restResponse);
	// return restResponse;
	//
	// }
	//
	// /**
	// * 带条件查询的分页
	// *
	// * @param user
	// * @param currentPage
	// * @param pageSize
	// * @return
	// * @author zhangzhe
	// * @date 2016年10月8日下午3:14:54
	// */
	// @ApiOperation(value = "分页条件查找", httpMethod = "POST", response =
	// RestResponse.class)
	// @ApiImplicitParams({
	// @ApiImplicitParam(name = "currentPage", required = true, dataType =
	// "Integer", paramType = "path"),
	// @ApiImplicitParam(name = "pageSize", required = true, dataType =
	// "Integer", paramType = "path"),
	// @ApiImplicitParam(name = "user", required = true, dataType = "User",
	// paramType = "body")})
	// @RequestMapping(value = "/page", method = RequestMethod.POST)
	// public RestResponse page(@RequestBody User user, @RequestBody Integer
	// currentPage, @RequestBody Integer pageSize)
	// throws DaoException {
	// LOG.info("user:{}", user);
	// LOG.info("currentPage:{}", currentPage);
	// LOG.info("pageSize:{}", pageSize);
	// RestResponse restResponse = new RestResponse();
	//
	// restResponse.setStatus(RestResponse.OK);
	// restResponse.setResult(userService.page(user, currentPage, pageSize));
	// LOG.info("restResponse:{}", restResponse);
	// return restResponse;
	//
	// }

	/**
	 * 查询总记录数
	 *
	 * @return
	 * @author zhangzhe
	 * @date 2016年10月8日上午9:58:05
	 */
	@ApiOperation(value = "查询总记录数", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	public RestResponse count() throws DaoException {
		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.countAll());
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

//	@ApiOperation(value = "查找所有用户", httpMethod = "POST", response = RestResponse.class)
//	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
//	public RestResponse findAll() throws DaoException {
//		RestResponse restResponse = new RestResponse();
//		restResponse.setStatus(RestResponse.OK);
//		restResponse.setResult(userService.findAll());
//		LOG.info("restResponse:{}", restResponse);
//		return restResponse;
//
//	}

	// /**
	// * 添加一个uesrId和多个roleId
	// * @author zhangzhe
	// * @date 2016年10月10日 下午4:40:14
	// *
	// * @param uesrId
	// * @param roleId
	// * @return
	// *
	// *
	// */
	// @RequestMapping(value = "/batchAddOneUesrIdAndManyRoleId/{uesrId}",
	// method = RequestMethod.POST)
	// @ResponseStatus(HttpStatus.CREATED)
	// public int batchAddOneUesrIdAndManyRoleId(@PathVariable Long
	// uesrId,@RequestBody Long[] roleIds) {
	// LOG.info("uesrId:{}", uesrId);
	// LOG.info("roleIds:{}", roleIds);
	// int num = userService.batchAddOneUesrIdAndManyRoleId(uesrId,roleIds);
	// LOG.info("num:{}", num);
	// return num;
	// }
	//
	//
	// /**
	// * 添加多个uesrId和1个roleId
	// * @author zhangzhe
	// * @date 2016年10月10日 下午4:40:14
	// *
	// * @param uesrId
	// * @param roleId
	// * @return
	// *
	// *
	// */
	// @RequestMapping(value = "/batchAddManyUesrIdAndOneRoleId/{roleId}",
	// method = RequestMethod.POST)
	// @ResponseStatus(HttpStatus.CREATED)
	// public int batchAddManyUesrIdAndOneRoleId(@PathVariable Long
	// roleId,@RequestBody Long[] uesrIds) {
	// LOG.info("roleId:{}", roleId);
	// LOG.info("uesrIds:{}", uesrIds);
	// int num = userService.batchAddManyUesrIdAndOneRoleId(roleId,uesrIds);
	// LOG.info("num:{}", num);
	// return num;
	// }
	/**
	 * 查询所有
	 *
	 * @return User[]
	 * @author zhangzhe
	 * @date 2016年9月30日下午3:38:57
	 */
	@ApiOperation(value = "查找所有", httpMethod = "POST", response =
			RestResponse.class)
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	public RestResponse findAll() throws DaoException {

		RestResponse restResponse = new RestResponse();

		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(userService.findAll());
		LOG.info("restResponse:{}", restResponse);
		return restResponse;

	}

}
