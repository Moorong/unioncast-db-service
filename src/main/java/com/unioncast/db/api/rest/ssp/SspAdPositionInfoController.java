package com.unioncast.db.api.rest.ssp;

import javax.annotation.Resource;

import com.unioncast.db.nosql.redis.SspRedisMemory;
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

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspAdPositionInfo;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspAdPositionInfoService;

import java.util.ArrayList;
import java.util.List;

@Api("ssp广告位")
@RestController
@RequestMapping("/rest/ssp/adPositionInfo")
public class SspAdPositionInfoController extends GeneralController {
	
private static final Logger LOG = LogManager.getLogger(SspAdPositionInfoController.class);
	
	@Resource
	private SspAdPositionInfoService sspAdPositionInfoService;

	@Autowired
	private SspRedisMemory sspRedisMemory;
	
	@ApiOperation(value = "查询所有广告位", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspAdPositionInfo sspAdPositionInfo) throws DaoException, IllegalAccessException {
		LOG.info("find sspAdPositionInfo sspAdPositionInfo:{}", sspAdPositionInfo);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspAdPositionInfoService.findT(sspAdPositionInfo));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "增加广告位", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspAdPositionInfo", required = true, dataType = "SspAdPositionInfo", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspAdPositionInfo sspAdPositionInfo) throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspAdPositionInfo:{}", sspAdPositionInfo);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Long id = sspAdPositionInfoService.save(sspAdPositionInfo);
		restResponse.setResult(id);
		SspAdPositionInfo[] sspAdPositionInfos = new SspAdPositionInfo[1];
		sspAdPositionInfo.setId(id);
		sspAdPositionInfos[0] = sspAdPositionInfo;
		//保存redis服务
		try {
			sspRedisMemory.batchAddSspAdPositionInfo(sspAdPositionInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}
	
	@ApiOperation(value = "更新广告位", httpMethod = "POST")
	@ApiImplicitParam(name = "sspAdPositionInfo", required = true, dataType = "SspAdPositionInfo", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody SspAdPositionInfo sspAdPositionInfo) throws Exception {
		LOG.info("update sspAdPositionInfo:{}", sspAdPositionInfo);
		sspAdPositionInfoService.updateAndReturnNum(sspAdPositionInfo);

		sspRedisMemory.batchDeleteBySspAdPositionInfoIds(new Long[]{sspAdPositionInfo.getId()});
		SspAdPositionInfo[] newAdPositionInfo = sspAdPositionInfoService.findT(sspAdPositionInfo);
		sspRedisMemory.batchAddSspAdPositionInfo(newAdPositionInfo);
	}
	
	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspAdPositionInfo pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspAdPositionInfo> pagination = sspAdPositionInfoService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}
	/*
	@ApiOperation(value = "删除一个广告位", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "long", paramType = "body")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody Long id) throws DaoException {
		LOG.info("delete sspAdPositionInfo id:{}", id);
		RestResponse restResponse = new RestResponse();
		restResponse.setResult(sspAdPositionInfoService.deleteById(id));
		restResponse.setStatus(RestResponse.OK);
		return restResponse;
	}
	*/
	@ApiOperation(value = "批量删除广告位", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException{
		LOG.info("batchDelete sspAdPositionInfo ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspAdPositionInfoService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);

		try {
			sspRedisMemory.batchDeleteBySspAdPositionInfoIds(ids);
			List<SspAdPositionInfo> list = new ArrayList<SspAdPositionInfo>();
			for(Long id : ids) {
				SspAdPositionInfo newAdPositionInfo = sspAdPositionInfoService.findById(id);
				if(newAdPositionInfo != null) {
					list.add(newAdPositionInfo);
				}
			}
			if(list != null && list.size() > 0) {
				sspRedisMemory.batchAddSspAdPositionInfo(list.toArray(new SspAdPositionInfo[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

}
