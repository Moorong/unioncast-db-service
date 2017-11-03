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
import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictMarriageServise;

@Api("ssp婚姻定向")
@RestController
@RequestMapping("/rest/ssp/dictMarriage")
public class SspDictMarriageController  extends GeneralController{
	private static final Logger LOG = LogManager.getLogger(SspDictMarriageController.class);
	
	@Resource
	private SspDictMarriageServise sspDictMarriageServise;
	
	
	@ApiOperation(value = "查询所有婚姻定向内容", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspDictMarriageTarget sspDictMarriage) throws DaoException, IllegalAccessException {
		LOG.info("find sspDictMarriage :{}", sspDictMarriage);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspDictMarriageServise.findT(sspDictMarriage));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	@ApiOperation(value = "根据code数组批量查询婚姻定向对象", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/batchFindbyCodes", method = RequestMethod.POST)
	public RestResponse batchFindbyCodes(@RequestBody String[] codes) throws DaoException {
		LOG.info("batchFindbyCodes  codes:{}", (Object[]) codes);
		RestResponse restResponse = new RestResponse();
		SspDictMarriageTarget[] number = sspDictMarriageServise.batchFindbyCodes(codes);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}
}
