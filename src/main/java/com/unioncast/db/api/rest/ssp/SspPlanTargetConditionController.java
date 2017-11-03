package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspPlanTargetCondition;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanTargetConditionService;

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

@Api("ssp广告定向条件")
@RestController
@RequestMapping("/rest/ssp/planTargetCondition")
public class SspPlanTargetConditionController extends GeneralController {

    private static final Logger LOG = LogManager.getLogger(SspPlanTargetConditionController.class);

    @Resource(name = "sspPlanTargetConditionService")
    private SspPlanTargetConditionService sspPlanTargetConditionService;

    @ApiOperation(value = "查询所有广告定向条件", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false) SspPlanTargetCondition sspPlanTargetCondition) throws DaoException, IllegalAccessException {
        LOG.info("find sspPlanTargetCondition :{}", sspPlanTargetCondition);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(sspPlanTargetConditionService.findT(sspPlanTargetCondition));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "增加广告定向条件", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "sspPlanTargetCondition", required = true, dataType = "SspPlanTargetCondition", paramType = "body")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody SspPlanTargetCondition sspPlanTargetCondition) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("add sspPlanTargetCondition:{}", sspPlanTargetCondition);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = sspPlanTargetConditionService.save(sspPlanTargetCondition);
        restResponse.setResult(id);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "更新广告定向条件", httpMethod = "POST")
    @ApiImplicitParam(name = "sspPlanTargetCondition", required = true, dataType = "SspPlanTargetCondition", paramType = "body")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody SspPlanTargetCondition sspPlanTargetCondition) throws Exception {
        LOG.info("update sspPlanTargetCondition:{}", sspPlanTargetCondition);
        
        int i = sspPlanTargetConditionService.updateAndReturnNum(sspPlanTargetCondition);
        
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(i);
        
        LOG.info("restResponse:{}", restResponse);
        
        return restResponse;
    }

    @ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
        LOG.info("page sspPlanTargetCondition pageCriteria:{}", pageCriteria);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Pagination<SspPlanTargetCondition> pagination = sspPlanTargetConditionService.page(pageCriteria);
        LOG.info("pagination:{}", pagination);
        restResponse.setResult(pagination);
        return restResponse;
    }

//	@ApiOperation(value = "删除一个广告定向条件", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspPlanTargetCondition id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspPlanTargetConditionService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

    @ApiOperation(value = "批量删除广告定向条件", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
        LOG.info("batchDelete sspPlanTargetCondition ids:{}", (Object[]) ids);
        RestResponse restResponse = new RestResponse();
        int number = sspPlanTargetConditionService.batchDelete(ids);
        restResponse.setResult(number);
        restResponse.setStatus(RestResponse.OK);
        LOG.info("the number of delete:{}", number);
        return restResponse;
    }

}
