package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.nosql.redis.SspRedisMemory;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspAdvertiserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api("ssp广告主")
@RestController
@RequestMapping("/rest/ssp/advertiser")
public class SspAdvertiserController extends GeneralController {
	
private static final Logger LOG = LogManager.getLogger(SspAdvertiserController.class);
	
	@Resource
	private SspAdvertiserService sspAdvertiserService;

	@Autowired
	private SspRedisMemory sspRedisMemory;
	
	@ApiOperation(value = "查询所有广告主", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspAdvertiser sspAdvertiser) throws DaoException, IllegalAccessException {
		LOG.info("find sspAdvertiser sspAdvertiser:{}", sspAdvertiser);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspAdvertiserService.findT(sspAdvertiser));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "增加一个广告主", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspAdvertiser", required = true, dataType = "SspAdvertiser", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspAdvertiser sspAdvertiser) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspAdvertiser:{}", sspAdvertiser);
		RestResponse restResponse = new RestResponse();
		try {
			restResponse.setStatus(RestResponse.OK);
			Long id = sspAdvertiserService.save(sspAdvertiser);
			restResponse.setResult(id);
			SspAdvertiser[] sspAdvertisers = new SspAdvertiser[1];
			sspAdvertiser.setId(id);
			sspAdvertisers[0] = sspAdvertiser;
			//保存redis服务
			sspRedisMemory.batchAddSspAdvertiser(sspAdvertisers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "更新广告计划", httpMethod = "POST")
	@ApiImplicitParam(name = "sspAdvertiser", required = true, dataType = "SspAdvertiser", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspAdvertiser sspAdvertiser) throws Exception {
		LOG.info("update sspAdvertiser:{}", sspAdvertiser);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int num = sspAdvertiserService.updateAndReturnNum(sspAdvertiser);
		restResponse.setResult(num);
		
		sspRedisMemory.batchDeleteBySspAdvertiserIds(new Long[]{sspAdvertiser.getId()});
		SspAdvertiser newAdvertiser = sspAdvertiserService.findById(sspAdvertiser.getId());
        sspRedisMemory.batchAddSspAdvertiser(new SspAdvertiser[]{newAdvertiser});
		
		return restResponse;
	}
	
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspAdvertiser pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspAdvertiser> pagination = sspAdvertiserService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	/*
	@ApiOperation(value = "删除一个广告主", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspAdvertiser id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspAdvertiserService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	*/
	@ApiOperation(value = "批量删除广告主", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws Exception{
		LOG.info("batchDelete sspAdvertiser ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspAdvertiserService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		
		sspRedisMemory.batchDeleteBySspAdvertiserIds(ids);
		List<SspAdvertiser> list = new ArrayList<SspAdvertiser>();
		for(Long id : ids) {
			SspAdvertiser newAdvertiser = sspAdvertiserService.findById(id);
			if(newAdvertiser != null) {
				list.add(newAdvertiser);
			}
		}
		if(list != null && list.size() > 0) {
			sspRedisMemory.batchAddSspAdvertiser(list.toArray(new SspAdvertiser[0]));
		}
		
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}


	@ApiOperation(value = "查询当前用户关联的未删除状态广告主", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findByUserId", method = RequestMethod.POST)
	public RestResponse findByUserId(@RequestBody(required = false) SspAdvertiser sspAdvertiser) throws DaoException, IllegalAccessException {
		LOG.info("find sspAdvertiser sspAdvertiser:{}", sspAdvertiser);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspAdvertiserService.findByUserId(sspAdvertiser));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

}
