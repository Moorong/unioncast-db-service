package com.unioncast.db.api.rest.ssp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspDictCrowdSexType;
import com.unioncast.common.ssp.model.SspDictMobileBrandType;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictCrowdSexService;

@Api("ssp人群性别")
@RestController
@RequestMapping("/rest/ssp/dictCrowdSex")
public class SspDictCrowdSexController extends GeneralController{
	private static final Logger LOG = LogManager.getLogger(SspDictCrowdSexController.class);
    
	@Resource
	private SspDictCrowdSexService sspDictCrowdSexService;
	@ApiOperation(value = "查询所有人群性别", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictCrowdSexType sspDictCrowdSexType) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictCrowdSexType sspDictCrowdSexType:{}", sspDictCrowdSexType);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictCrowdSexService.findT(sspDictCrowdSexType));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	@ApiOperation(value = "根据code数组批量查询性别定向对象", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchFindbyCodes", method = RequestMethod.POST)
	public RestResponse batchFindbyCodes(@RequestBody String[] codes) throws DaoException {
		LOG.info("batchFindbyCodes  codes:{}", (Object[]) codes);
		RestResponse restResponse = new RestResponse();
		SspDictCrowdSexType[] number = sspDictCrowdSexService.batchFindbyCodes(codes);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
