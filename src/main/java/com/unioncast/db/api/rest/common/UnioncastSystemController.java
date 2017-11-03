package com.unioncast.db.api.rest.common;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.user.model.UnioncastSystem;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.UnioncastSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther wangyao
 * @date 2017-02-15 16:20
 */
@Api(value = "系统信息")
@RestController
@RequestMapping("/rest/system")
public class UnioncastSystemController {

    private static final Logger LOG = LogManager.getLogger(UnioncastSystemController.class);

    @Resource
    private UnioncastSystemService unioncastSystemService;

    @ApiOperation(value = "查询所有系统", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false) UnioncastSystem unioncastSystem) throws DaoException, IllegalAccessException {
        LOG.info("find unioncastSystem :{}", unioncastSystem);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(unioncastSystemService.findT(unioncastSystem));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "增加系统", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "unioncastSystem", required = true, dataType = "UnioncastSystem", paramType = "body")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody UnioncastSystem unioncastSystem) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("add unioncastSystem:{}", unioncastSystem);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = unioncastSystemService.save(unioncastSystem);
        restResponse.setResult(id);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "更新系统", httpMethod = "POST")
    @ApiImplicitParam(name = "unioncastSystem", required = true, dataType = "UnioncastSystem", paramType = "body")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody UnioncastSystem unioncastSystem) throws Exception {
        LOG.info("update unioncastSystem:{}", unioncastSystem);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        int i = unioncastSystemService.updateAndReturnNum(unioncastSystem);
        restResponse.setResult(i);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
        LOG.info("page unioncastSystem pageCriteria:{}", pageCriteria);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Pagination<UnioncastSystem> pagination = unioncastSystemService.page(pageCriteria);
        LOG.info("pagination:{}", pagination);
        restResponse.setResult(pagination);
        return restResponse;
    }

//	@ApiOperation(value = "删除一个系统", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete unioncastSystem id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(unioncastSystemService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

    @ApiOperation(value = "批量删除系统", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
        LOG.info("batchDelete unioncastSystem ids:{}", (Object[]) ids);
        RestResponse restResponse = new RestResponse();
        int number = unioncastSystemService.batchDelete(ids);
        restResponse.setResult(number);
        restResponse.setStatus(RestResponse.OK);
        LOG.info("the number of delete:{}", number);
        return restResponse;
    }


}
