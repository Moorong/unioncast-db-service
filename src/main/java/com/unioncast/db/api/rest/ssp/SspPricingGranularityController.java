package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspPricingGranularity;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspPricingGranularityService;
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
 * @date 2017-04-24 19:48
 */
@Api("ssp定价粒度")
@RestController
@RequestMapping("/rest/ssp/pricingGranularity")
public class SspPricingGranularityController extends GeneralController {

    private static final Logger LOG = LogManager.getLogger(SspPricingGranularityController.class);

    @Resource
    private SspPricingGranularityService sspPricingGranularityService;

    @ApiOperation(value = "增加定价粒度", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "sspPricingGranularity", required = true, dataType = "sspPricingGranularity", paramType = "body")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody SspPricingGranularity sspPricingGranularity) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("add sspPricingGranularity:{}", sspPricingGranularity);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = sspPricingGranularityService.save(sspPricingGranularity);
        restResponse.setResult(id);
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }

}
