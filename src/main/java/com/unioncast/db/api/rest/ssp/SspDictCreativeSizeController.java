package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictCreativeSize;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictCreativeSizeService;

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

@Api("ssp创意尺寸")
@RestController
@RequestMapping("/rest/ssp/dictCreativeSize")
public class SspDictCreativeSizeController extends GeneralController {

    private static final Logger LOG = LogManager.getLogger(SspDictCreativeSizeController.class);

    @Resource
    private SspDictCreativeSizeService sspDictCreativeSizeService;
    
    /**
     * 
     * @author juchaochao
     * @2017年1月19日下午4:03:12
     * @param sspDictCreativeSize
     * @return
     * @throws DaoException
     * @throws IllegalAccessException
     */
    @ApiOperation(value = "查询所有创意尺寸", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false) SspDictCreativeSize sspDictCreativeSize) throws DaoException, IllegalAccessException {
        LOG.info("find sspDictCreativeSize:{}", sspDictCreativeSize);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(sspDictCreativeSizeService.findT(sspDictCreativeSize));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "增加创意尺寸", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "sspDictCreativeSize", required = true, dataType = "SspDictCreativeSize", paramType = "body")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody SspDictCreativeSize sspDictCreativeSize) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("add sspDictCreativeSize:{}", sspDictCreativeSize);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = sspDictCreativeSizeService.save(sspDictCreativeSize);
        restResponse.setResult(id);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "更新创意尺寸", httpMethod = "POST")
    @ApiImplicitParam(name = "sspDictCreativeSize", required = true, dataType = "SspDictCreativeSize", paramType = "body")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody SspDictCreativeSize sspDictCreativeSize) throws Exception {
        LOG.info("update sspDictCreativeSize:{}", sspDictCreativeSize);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        int i = sspDictCreativeSizeService.updateAndReturnNum(sspDictCreativeSize);
        restResponse.setResult(i);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

    @ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
        LOG.info("page sspDictCreativeSize pageCriteria:{}", pageCriteria);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Pagination<SspDictCreativeSize> pagination = sspDictCreativeSizeService.page(pageCriteria);
        LOG.info("pagination:{}", pagination);
        restResponse.setResult(pagination);
        return restResponse;
    }

//	@ApiOperation(value = "删除一个创意尺寸", httpMethod = "POST", response = RestResponse.class)
//	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody Long id) throws DaoException {
//		LOG.info("delete sspDictCreativeSize id:{}", id);
//		RestResponse restResponse = new RestResponse();
//		restResponse.setResult(sspDictCreativeSizeService.deleteById(id));
//		restResponse.setStatus(RestResponse.OK);
//		return restResponse;
//	}

    @ApiOperation(value = "批量删除创意尺寸", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
        LOG.info("batchDelete sspDictCreativeSize ids:{}", (Object[]) ids);
        RestResponse restResponse = new RestResponse();
        int number = sspDictCreativeSizeService.batchDelete(ids);
        restResponse.setResult(number);
        restResponse.setStatus(RestResponse.OK);
        LOG.info("the number of delete:{}", number);
        return restResponse;
    }

}
