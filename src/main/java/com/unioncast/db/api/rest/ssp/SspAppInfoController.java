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
import com.unioncast.common.ssp.model.SspAppInfo;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspAppInfoService;

import java.util.ArrayList;
import java.util.List;

@Api("ssp应用")
@RestController
@RequestMapping("/rest/ssp/appInfo")
public class SspAppInfoController extends GeneralController {

	private static final Logger LOG = LogManager.getLogger(SspAppInfoController.class);

	@Resource
	private SspAppInfoService sspAppInfoService;

	@Autowired
	private SspRedisMemory sspRedisMemory;

	@ApiOperation(value = "查询所有应用", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspAppInfo sspAppInfo)
			throws DaoException, IllegalAccessException {
		LOG.info("find sspAppInfo sspAppInfo:{}", sspAppInfo);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspAppInfoService.findT(sspAppInfo));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加应用", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspAppInfo", required = true, dataType = "SspAppInfo", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspAppInfo sspAppInfo)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		LOG.info("add sspAppInfo:{}", sspAppInfo);

		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		try {
			Long id = sspAppInfoService.save(sspAppInfo);
			restResponse.setResult(id);
			SspAppInfo[] sspAppInfos = new SspAppInfo[1];
			sspAppInfo.setId(id);
			sspAppInfos[0] = sspAppInfo;
			// 保存redis服务
			sspRedisMemory.batchAddSspAppInfo(sspAppInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新应用", httpMethod = "POST")
	@ApiImplicitParam(name = "sspAppInfo", required = true, dataType = "SspAppInfo", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody SspAppInfo sspAppInfo) throws Exception {
		LOG.info("update sspAppInfo:{}", sspAppInfo);
		sspAppInfoService.updateAndReturnNum(sspAppInfo);

		sspRedisMemory.batchDeleteBySspAppInfoIds(new Long[]{sspAppInfo.getId()});
		SspAppInfo[] sspAppInfos = sspAppInfoService.findT(sspAppInfo);
		sspRedisMemory.batchAddSspAppInfo(sspAppInfos);

	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public RestResponse page(@RequestBody PageCriteria pageCriteria) throws DaoException {
		LOG.info("page sspAppInfo pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		Pagination<SspAppInfo> pagination = sspAppInfoService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		restResponse.setResult(pagination);
		return restResponse;
	}

	/*
	 * @ApiOperation(value = "删除一个应用", httpMethod = "POST", response =
	 * RestResponse.class)
	 * 
	 * @ApiImplicitParam(name = "id", required = true, dataType = "long",
	 * paramType = "body")
	 * 
	 * @RequestMapping(value = "/delete", method = RequestMethod.POST) public
	 * RestResponse delete(@RequestBody Long id) throws DaoException {
	 * LOG.info("delete sspAppInfo id:{}", id); RestResponse restResponse = new
	 * RestResponse(); restResponse.setResult(sspAppInfoService.deleteById(id));
	 * restResponse.setStatus(RestResponse.OK); return restResponse; }
	 */
	@ApiOperation(value = "批量删除应用", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
		LOG.info("batchDelete sspAppInfo ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();
		int number = sspAppInfoService.batchDelete(ids);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		try {
			sspRedisMemory.batchDeleteBySspAppInfoIds(ids);
			List<SspAppInfo> list = new ArrayList<SspAppInfo>();
			for(Long id : ids) {
				SspAppInfo appInfo = sspAppInfoService.findById(id);
				if(appInfo != null) {
					list.add(appInfo);
				}
			}
			if(list != null && list.size() > 0) {
				sspRedisMemory.batchAddSspAppInfo(list.toArray(new SspAppInfo[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restResponse;
	}

}
