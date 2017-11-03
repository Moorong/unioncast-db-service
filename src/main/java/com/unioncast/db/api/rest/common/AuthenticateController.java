package com.unioncast.db.api.rest.common;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.AuthenticationApiInfo;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.AuthenticateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * 鉴权信息
 *
 * @author zhangzhe
 * @date 2016年10月25日 下午7:07:03
 */
@Api(value = "鉴权信息")
@RestController
@RequestMapping("/rest/authenticate")
public class AuthenticateController extends GeneralController {

    private static final Logger LOG = LogManager.getLogger(AuthenticateController.class);

    @Autowired
    private AuthenticateService authenticateService;


    /**
     * 分页条件查找
     *
     * @param pageCriteria
     * @return
     * @author zhangzhe
     * @date 2016年11月8日下午3:14:54
     */
    @ApiOperation(value = "分页条件查找", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse page(@RequestBody PageCriteria pageCriteria)
            throws DaoException {
        LOG.info("PageCriteria:{}", pageCriteria);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.page(pageCriteria));
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
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path")})
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
        LOG.info("id:{}", id);
        RestResponse restResponse = new RestResponse();

        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.find(id));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }


    /**
     * authentication对象查询
     *
     * @param authentication
     * @return Authentication[]
     * @author zhangzhe
     * @date 2016年12月8日上午9:52:27
     */
    @ApiOperation(value = "authentication对象查询", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAuthByAuth", method = RequestMethod.POST)
    public RestResponse findAuthByAuth(@RequestBody Authentication authentication) throws DaoException {
        LOG.info("authentication:{}", authentication);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.findAuthByAuth(authentication));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }


    /**
     * 根据SystemId查询
     *
     * @param systemId
     * @return
     * @author zhangzhe
     * @date 2016年10月9日 上午10:59:27
     */
    @ApiOperation(value = "根据SystemId查询", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "systemId", required = true, dataType = "Long", paramType = "path")})
    @RequestMapping(value = "/findBySystemId", method = RequestMethod.POST)
    public RestResponse findBySystemId(@RequestBody Long systemId) throws DaoException {
        LOG.info("systemId:{}", systemId);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.findBySysId(systemId));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

//


    /**
     * 添加
     *
     * @param authentication
     * @return
     * @author zhangzhe
     * @date 2016年10月10日 下午2:19:20
     */
    @ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authentication", required = true, dataType = "Authentication", paramType = "body")})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody Authentication authentication) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("authentication:{}", authentication);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = authenticateService.save(authentication);
        restResponse.setResult(id);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    /**
     * 批量添加
     *
     * @param authentications
     * @return
     * @author zhangzhe
     * @date 2016年10月10日 下午2:19:48
     */
    @ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authentications", required = true, dataType = "Authentication[]", paramType = "body")})
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse batchAdd(@RequestBody Authentication[] authentications) throws DaoException {
        LOG.info("authentications:{}", authentications);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.batchAdd(authentications));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 修改
     *
     * @param authentications
     * @author zhangzhe
     * @date 2016年10月10日 下午2:20:04
     */
    @ApiOperation(value = "修改", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authentications", required = true, dataType = "Authentication", paramType = "body")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody Authentication authentications) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("authentications:{}", authentications);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.updateAndReturnNum(authentications));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 删除
     *
     * @param id
     * @return
     * @author zhangzhe
     * @date 2016年10月10日 下午2:20:14
     */
    @ApiOperation(value = "删除", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path")})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RestResponse deleteById(@RequestBody Long id) throws DaoException {
        LOG.info("id:{}", id);
        RestResponse restResponse = new RestResponse();

        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.deleteById(id));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年10月10日 下午2:20:23
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, dataType = "Long[]", paramType = "path")})
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.deleteById(ids));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    // ==================和AuthenticationApiInfo相关=======================================================

    /**
     * 根据authenticationId 删除AuthenticationApiInfo
     *
     * @param id
     * @return
     * @author zhangzhe
     * @date 2016年10月13日 下午4:19:10
     */
    @ApiOperation(value = "根据authenticationId 删除AuthenticationApiInfo", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/deleteAuthApiInfoByAuthId", method = RequestMethod.POST)
    public RestResponse deleteAuthApiInfoByAuthId(@RequestBody Long id) throws DaoException {
        LOG.info("id:{}", id);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.deleteAuthApiInfoByAuthId(id));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 根据apiInfoId 删除 AuthenticationApiInfo
     *
     * @param id
     * @return
     * @author zhangzhe
     * @date 2016年10月13日 下午4:19:10
     */
    @ApiOperation(value = "根据apiInfoId 删除 AuthenticationApiInfo", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/deleteAuthApiInfoByApiInfoId", method = RequestMethod.POST)
    public RestResponse deleteAuthApiInfoByApiInfoId(@RequestBody Long id) throws DaoException {
        LOG.info("id:{}", id);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.deleteAuthApiInfoByApiInfoId(id));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 根据authenticationId 批量删除AuthenticationApiInfo
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年10月13日 下午4:19:10
     */
    @ApiOperation(value = "根据authenticationId 批量删除AuthenticationApiInfo", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteAuthApiInfoByAuthId", method = RequestMethod.POST)
    public RestResponse batchDeleteAuthApiInfoByAuthId(@RequestBody Long[] ids) throws DaoException {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.batchDeleteAuthApiInfoByAuthId(ids));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 批量添加AuthenticationApiInfo
     *
     * @param authenticationApiInfos
     * @return
     * @author zhangzhe
     * @date 2016年10月13日 上午9:51:41
     */
    @ApiOperation(value = "批量添加AuthenticationApiInfo", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddAuthenticationApiInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse batchAddAuthenticationApiInfo(@RequestBody AuthenticationApiInfo[] authenticationApiInfos)
            throws DaoException {
        LOG.info("authenticationApiInfos:{}", authenticationApiInfos);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.batchAddAuthenticationApiInfo(authenticationApiInfos));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }


    /**
     * 根据apiInfoId 批量删除AuthenticationApiInfo
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年10月13日 下午4:19:10
     */
    @ApiOperation(value = "根据apiInfoId 批量删除AuthenticationApiInfo", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteAuthApiInfoByApiInfoId", method = RequestMethod.POST)
    public RestResponse batchDeleteAuthApiInfoByApiInfoId(@RequestBody Long[] ids) throws DaoException {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.batchDeleteAuthApiInfoByApiInfoId(ids));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 根据authenticationId获取apiInfo集合
     *
     * @param id
     * @return
     * @author zhangzhe
     * @date 2016年10月13日 下午4:19:10
     */
    @ApiOperation(value = "根据authenticationId获取apiInfo集合", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findApiInfoByAuthId", method = RequestMethod.POST)
    public RestResponse findApiInfoByAuthId(@RequestBody Long id) throws DaoException {
        LOG.info("id:{}", id);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(authenticateService.findApiInfoByAuthId(id));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }


    //    /**
//     * 根据id查询
//     *
//     * @param id
//     * @return
//     * @author zhangzhe
//     * @date 2016年10月9日 上午10:58:53
//     */
//    @ApiOperation(value = "根据id查找", httpMethod = "POST", response = RestResponse.class)
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path")})
//    @RequestMapping(value = "/findById", method = RequestMethod.POST)
//    public RestResponse findById(@RequestBody Long id) throws DaoException {
//        LOG.info("id:{}", id);
//        RestResponse restResponse = new RestResponse();
//        restResponse.setStatus(RestResponse.OK);
//        restResponse.setResult(authenticateService.findById(id));
//        LOG.info("restResponse:{}", restResponse);
//        return restResponse;
//
//    }

    /**
     //     * 查询所有
     //     *
     //     * @return
     //     * @author zhangzhe
     //     * @date 2016年10月10日 下午2:11:28
     //     */
//    @ApiOperation(value = "查找所有", httpMethod = "POST", response = RestResponse.class)
//    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
//    public RestResponse findAll() throws DaoException {
//        RestResponse restResponse = new RestResponse();
//        restResponse.setStatus(RestResponse.OK);
//        restResponse.setResult(authenticateService.findAll());
//        LOG.info("restResponse:{}", restResponse);
//        return restResponse;
//
//    }

//    /**
//     * 带条件查询的分页
//     *
//     * @param authentication
//     * @param currentPage
//     * @param pageSize
//     * @return
//     * @author zhangzhe
//     * @date 2016年10月10日 下午2:18:44
//     */
//    @ApiOperation(value = "分页条件查找", httpMethod = "POST", response = RestResponse.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "currentPage", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "pageSize", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "authentication", required = true, dataType = "Authentication", paramType = "body")})
//    @RequestMapping(value = "/page", method = RequestMethod.POST)
//    public RestResponse page(@RequestBody Authentication authentication, @RequestBody Integer currentPage,
//                             @RequestBody Integer pageSize) throws DaoException {
//        LOG.info("authentication:{}", authentication);
//        LOG.info("currentPage:{}", currentPage);
//        LOG.info("pageSize:{}", pageSize);
//
//        RestResponse restResponse = new RestResponse();
//        restResponse.setStatus(RestResponse.OK);
//        restResponse.setResult(authenticateService.page(authentication, currentPage, pageSize));
//        LOG.info("restResponse:{}", restResponse);
//        return restResponse;
//    }


//
//    /**
//     * 分页查找
//     *
//     * @param currentPageNo
//     * @param pageSize
//     * @return
//     * @author zhangzhe
//     * @date 2016年10月10日 下午2:13:06
//     */
//    @ApiOperation(value = "分页查找", httpMethod = "POST", response = RestResponse.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "currentPageNo", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "pageSize", required = true, dataType = "Integer", paramType = "path")})
//    @RequestMapping(value = "/paginationAll", method = RequestMethod.POST)
//    public RestResponse paginationAll(@RequestBody Integer currentPageNo, @RequestBody Integer pageSize)
//            throws DaoException {
//        LOG.info("currentPageNo:{}", currentPageNo);
//        LOG.info("pageSize:{}", pageSize);
//        RestResponse restResponse = new RestResponse();
//        restResponse.setStatus(RestResponse.OK);
//        restResponse.setResult(authenticateService.paginationAll(currentPageNo, pageSize));
//        LOG.info("restResponse:{}", restResponse);
//        return restResponse;
//
//    }


    /**
     //     * 带条件查询的分页
     //     *
     //     * @param authentication
     //     * @param currentPage
     //     * @param pageSize
     //     * @return
     //     * @author zhangzhe
     //     * @date 2016年10月10日 下午2:18:44
     //     */
//    @ApiOperation(value = "分页条件查找", httpMethod = "POST", response = RestResponse.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "currentPage", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "pageSize", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "authentication", required = true, dataType = "Authentication", paramType = "body")})
//    @RequestMapping(value = "/page", method = RequestMethod.POST)
//    public RestResponse page(@RequestBody Authentication authentication, @RequestBody Integer currentPage,
//                             @RequestBody Integer pageSize) throws DaoException {
//        LOG.info("authentication:{}", authentication);
//        LOG.info("currentPage:{}", currentPage);
//        LOG.info("pageSize:{}", pageSize);
//
//        RestResponse restResponse = new RestResponse();
//        restResponse.setStatus(RestResponse.OK);
//        restResponse.setResult(authenticateService.page(authentication, currentPage, pageSize));
//        LOG.info("restResponse:{}", restResponse);
//        return restResponse;
//    }

}
