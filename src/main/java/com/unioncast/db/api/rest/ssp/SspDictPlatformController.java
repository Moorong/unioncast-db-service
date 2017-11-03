package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictPlatform;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictPlatformService;

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

@Api("ssp所属平台")
@RestController
@RequestMapping("/rest/ssp/dictPlatform")
public class SspDictPlatformController extends GeneralController {

    private static final Logger LOG = LogManager.getLogger(SspDictPlatformController.class);

    @Resource
    private SspDictPlatformService sspDictPlatformService;

    @ApiOperation(value = "查询所有所属平台", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false) SspDictPlatform sspDictPlatform) throws DaoException, IllegalAccessException {
        LOG.info("find sspDictPlatform :{}", sspDictPlatform);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(sspDictPlatformService.findT(sspDictPlatform));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "增加所属平台", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "sspDictPlatform", required = true, dataType = "SspDictPlatform", paramType = "body")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody SspDictPlatform sspDictPlatform) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("add sspDictPlatform:{}", sspDictPlatform);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = sspDictPlatformService.save(sspDictPlatform);
        restResponse.setResult(id);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "更新所属平台", httpMethod = "POST")
    @ApiImplicitParam(name = "sspDictPlatform", required = true, dataType = "SspDictPlatform", paramType = "body")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody SspDictPlatform sspDictPlatform) throws Exception {
        LOG.info("update sspDictPlatform:{}", sspDictPlatform);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        int i = sspDictPlatformService.updateAndReturnNum(sspDictPlatform);
        restResponse.setResult(i);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
        LOG.info("page sspDictPlatform pageCriteria:{}", pageCriteria);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Pagination<SspDictPlatform> pagination = sspDictPlatformService.page(pageCriteria);
        LOG.info("pagination:{}", pagination);
        restResponse.setResult(pagination);
        return restResponse;
    }

//    @ApiOperation(value = "删除一个所属平台", httpMethod = "POST", response = RestResponse.class)
//    @ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//    @RequestMapping(value = "/delete", method = RequestMethod.POST)
//    public RestResponse delete(@RequestBody Long id) throws DaoException {
//        LOG.info("delete sspDictPlatform id:{}", id);
//        RestResponse restResponse = new RestResponse();
//        restResponse.setResult(sspDictPlatformService.deleteById(id));
//        restResponse.setStatus(RestResponse.OK);
//        return restResponse;
//    }

    @ApiOperation(value = "批量删除所属平台", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
        LOG.info("batchDelete sspDictPlatform ids:{}", (Object[]) ids);
        RestResponse restResponse = new RestResponse();
        int number = sspDictPlatformService.batchDelete(ids);
        restResponse.setResult(number);
        restResponse.setStatus(RestResponse.OK);
        LOG.info("the number of delete:{}", number);
        return restResponse;
    }

}
