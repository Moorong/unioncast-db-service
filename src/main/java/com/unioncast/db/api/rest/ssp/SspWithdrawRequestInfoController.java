package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestError;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspWithdrawRequestInfo;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspWithdrawRequestInfoService;

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

@Api("ssp提现申请")
@RestController
@RequestMapping("/rest/ssp/withdrawRequestInfo")
public class SspWithdrawRequestInfoController extends GeneralController {

    private static final Logger LOG = LogManager.getLogger(SspWithdrawRequestInfoController.class);

    @Resource
    private SspWithdrawRequestInfoService sspWithdrawRequestInfoService;

    @ApiOperation(value = "查询所有提现申请", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false) SspWithdrawRequestInfo sspWithdrawRequestInfo) throws DaoException, IllegalAccessException {
        LOG.info("find sspWithdrawRequestInfo:{}", sspWithdrawRequestInfo);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(sspWithdrawRequestInfoService.findT(sspWithdrawRequestInfo));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }
    
    @ApiOperation(value = "查询所有提现申请", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findByDeveloperId", method = RequestMethod.POST)
    public RestResponse findByDeveloperId(Long id) throws DaoException, IllegalAccessException {
        LOG.info("find sspWithdrawRequestInfo by developer id:{}", id);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        SspWithdrawRequestInfo withdrawRequestInfo = sspWithdrawRequestInfoService.findByDeveloperId(id);
        restResponse.setResult(withdrawRequestInfo);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }
    

    @ApiOperation(value = "增加提现申请", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "sspWithdrawRequestInfo", required = true, dataType = "SspWithdrawRequestInfo", paramType = "body")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody SspWithdrawRequestInfo sspWithdrawRequestInfo) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("add sspWithdrawRequestInfo:{}", sspWithdrawRequestInfo);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = sspWithdrawRequestInfoService.save(sspWithdrawRequestInfo);
        restResponse.setResult(id);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "更新提现申请", httpMethod = "POST")
    @ApiImplicitParam(name = "sspWithdrawRequestInfo", required = true, dataType = "SspWithdrawRequestInfo", paramType = "body")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody SspWithdrawRequestInfo sspWithdrawRequestInfo) throws Exception {
        LOG.info("update sspWithdrawRequestInfo:{}", sspWithdrawRequestInfo);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        int i = sspWithdrawRequestInfoService.updateAndReturnNum(sspWithdrawRequestInfo);
        restResponse.setResult(i);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
        LOG.info("page sspWithdrawRequestInfo pageCriteria:{}", pageCriteria);
        RestResponse restResponse = new RestResponse();
        if(null==pageCriteria) {
            restResponse.setStatus(RestResponse.FAIL);
            return restResponse;
        }else {
            if(null==pageCriteria.getCurrUserId()){
                restResponse.setStatus(RestResponse.FAIL);
                RestError restError = new RestError();
                restError.setMessage("当前用户为空!");
                restResponse.setRestErrors(new RestError[]{restError});
                return restResponse;
            }
            restResponse.setStatus(RestResponse.OK);
        }
        Pagination<SspWithdrawRequestInfo> pagination = sspWithdrawRequestInfoService.page(pageCriteria);
        LOG.info("pagination:{}", pagination);
        restResponse.setResult(pagination);
        return restResponse;
    }

//	@ApiOperation(value = "删除一个提现申请", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspWithdrawRequestInfo id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspWithdrawRequestInfoService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

    @ApiOperation(value = "批量删除提现申请", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
        LOG.info("batchDelete sspWithdrawRequestInfo ids:{}", (Object[]) ids);
        RestResponse restResponse = new RestResponse();
        int number = sspWithdrawRequestInfoService.batchDelete(ids);
        restResponse.setResult(number);
        restResponse.setStatus(RestResponse.OK);
        LOG.info("the number of delete:{}", number);
        return restResponse;
    }

}
