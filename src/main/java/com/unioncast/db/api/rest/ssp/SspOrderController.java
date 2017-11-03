package com.unioncast.db.api.rest.ssp;

import com.unioncast.common.page.AdvertiserOrderModel;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspOrder;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.nosql.redis.SspRedisMemory;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@Api("ssp订单")
@RestController
@RequestMapping("/rest/ssp/order")
public class SspOrderController extends GeneralController {

	private static final Logger LOG = LogManager
			.getLogger(SspOrderController.class);

	@Resource
	private SspOrderService sspOrderService;

	@Resource
	private SspRedisMemory sspRedisMemory;

	@ApiOperation(value = "查询所有订单", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public RestResponse find(@RequestBody(required = false) SspOrder sspOrder)
			throws DaoException, IllegalAccessException {
		LOG.info("find sspOrder :{}", sspOrder);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		restResponse.setResult(sspOrderService.findT(sspOrder));
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "增加订单", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspOrder", required = true, dataType = "SspOrder", paramType = "body")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse add(@RequestBody SspOrder sspOrder)
			throws DaoException, IllegalArgumentException,
			IllegalAccessException {
		LOG.info("add sspOrder:{}", sspOrder);
		RestResponse restResponse = new RestResponse();
		try {
			restResponse.setStatus(RestResponse.OK);
			Long id = sspOrderService.save(sspOrder);
			restResponse.setResult(id);
			SspOrder[] orders = new SspOrder[1];
			sspOrder.setId(id);
			orders[0] = sspOrder;
			// 保存redis服务
			sspRedisMemory.batchAddSspOrder(orders);
			//查找当前广告主下面的所有订单id
			List<Long> orderIds = sspRedisMemory.findByAdvIdOrders(sspOrder.getSspAdvertiser().getId());
			if(orderIds == null) {
				orderIds = new ArrayList<Long>();
			}
			orderIds.add(sspOrder.getId());
			//更新当前广告主的数据
			sspRedisMemory.addByAdvIdOrders(sspOrder.getSspAdvertiser().getId() , orderIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "更新订单", httpMethod = "POST")
	@ApiImplicitParam(name = "sspOrder", required = true, dataType = "SspOrder", paramType = "body")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody SspOrder sspOrder) throws Exception {
		LOG.info("update sspOrder:{}", sspOrder);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		int i = sspOrderService.updateAndReturnNum(sspOrder);
		restResponse.setResult(i);

		sspRedisMemory
				.batchDeleteBySspOrderIds(new Long[] { sspOrder.getId() });
		SspOrder newOrder = sspOrderService.findById(sspOrder.getId());
		sspRedisMemory.batchAddSspOrder(new SspOrder[] { newOrder });

		if (CommonUtil.isNotNull(newOrder)&&CommonUtil.isNotNull(newOrder.getDeleteState()) && 2 == newOrder.getDeleteState()){
			//维护广告主和订单关系缓存
			Long oId = newOrder.getId();
			Long advId = newOrder.getSspAdvertiser().getId();
			List<Long> orderIds = sspRedisMemory.findByAdvIdOrders(advId);
			orderIds.remove(oId);
			//更新当前广告主的数据
			sspRedisMemory.addByAdvIdOrders(advId,orderIds);
		}

		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "条件分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public Pagination<SspOrder> page(@RequestBody PageCriteria pageCriteria)
			throws DaoException {
		LOG.info("page sspOrder pageCriteria:{}", pageCriteria);
		Pagination<SspOrder> pagination = sspOrderService.page(pageCriteria);
		LOG.info("pagination:{}", pagination);
		return pagination;
	}

	// 含广告主状态的分页查询
	@ApiOperation(value = "含广告主状态的分页查询", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/AdStatePage", method = RequestMethod.POST)
	public Pagination<SspOrder> AdStatePage(
			@RequestBody AdvertiserOrderModel params) throws DaoException {
		LOG.info("AdStatePage  AdvertiserOrderModel params :{}", params);
		Pagination<SspOrder> pagination = sspOrderService.AdStatePage(params);
		LOG.info("pagination:{}", pagination);
		return pagination;
	}

	// @ApiOperation(value = "删除一个订单", httpMethod = "POST", response =
	// RestResponse.class)
	// @ApiImplicitParam(name = "id", required = true, dataType = "long",
	// paramType = "body")
	// @RequestMapping(value = "/delete", method = RequestMethod.POST)
	// public RestResponse delete(@RequestBody Long id) throws DaoException {
	// LOG.info("delete sspOrder id:{}", id);
	// RestResponse restResponse = new RestResponse();
	// restResponse.setResult(sspOrderService.deleteById(id));
	// restResponse.setStatus(RestResponse.OK);
	// return restResponse;
	// }

	@ApiOperation(value = "批量删除订单", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "ids", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public RestResponse batchDelete(@RequestBody Long[] ids) throws Exception {
		LOG.info("batchDelete sspOrder ids:{}", (Object[]) ids);
		RestResponse restResponse = new RestResponse();

		try {
			if(!ArrayUtils.isEmpty(ids)){
				List<SspOrder> willDellOrderList = new ArrayList<SspOrder>();
				for (Long id:ids){
					SspOrder newOrder = sspOrderService.findById(id);
					if (CommonUtil.isNotNull(newOrder)) {
						willDellOrderList.add(newOrder);
					}
				}
				int number = sspOrderService.batchDelete(ids);
				restResponse.setResult(number);
				restResponse.setStatus(RestResponse.OK);

				LOG.info("the number of delete:{}", number);

				sspRedisMemory.batchDeleteBySspOrderIds(ids);

				//维护广告主和订单关系缓存
				//查找当前广告主下面的所有订单id
				for(SspOrder so:willDellOrderList){
					Long oId = so.getId();
					Long advId = so.getSspAdvertiser().getId();
					List<Long> orderIds = sspRedisMemory.findByAdvIdOrders(advId);
					orderIds.remove(oId);
					//更新当前广告主的数据
					sspRedisMemory.addByAdvIdOrders(advId,orderIds);
				}

            }
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("批量删除订单异常:{}",e);
			restResponse.setResult(e);
			restResponse.setStatus(RestResponse.FAIL);
		}
		return restResponse;
	}

	@ApiOperation(value = "批量删除订单", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "id", required = true, dataType = "List<Long>", paramType = "body")
	@RequestMapping(value = "/findByAdvertiser", method = RequestMethod.POST)
	public RestResponse findByAdvertiser(@RequestBody PageCriteria pageCriteria)
			throws DaoException {
		LOG.info("count sspOrder pageCriteria:{}", pageCriteria);
		RestResponse restResponse = new RestResponse();
		Integer number = sspOrderService.countOrders(pageCriteria);
		restResponse.setResult(number);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the number of delete:{}", number);
		return restResponse;
	}

	@ApiOperation(value = "订单未删除的状态开启的所有订单", httpMethod = "POST", response = RestResponse.class)
	@RequestMapping(value = "/findAllOrder", method = RequestMethod.POST)
	public RestResponse findAllOrder() throws DaoException {
		RestResponse restResponse = new RestResponse();
		List<SspOrder> results = sspOrderService.findOrderAll();
		restResponse.setResult(results);
		restResponse.setStatus(RestResponse.OK);
		LOG.info("the result num:{}", results.size());
		return restResponse;
	}

	@ApiOperation(value = "通过广告主id查询订单", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "sspCreative", required = true, dataType = "SspCreative", paramType = "body")
	@RequestMapping(value = "/findByAdvertiserId", method = RequestMethod.POST)
	public RestResponse findByAdvertiserId(@RequestBody Long advertiserId)
			throws DaoException {
		LOG.info("advertiserId:{}", advertiserId);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		SspOrder[] sspOrders = sspOrderService.findByAdvertiserId(advertiserId);
		restResponse.setResult(sspOrders);
		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

	@ApiOperation(value = "批量更新订单", httpMethod = "POST")
	@ApiImplicitParam(name = "sspOrder", required = true, dataType = "SspOrder", paramType = "body")
	@RequestMapping(value = "/updates", method = RequestMethod.POST)
	public RestResponse updates(@RequestBody SspOrder[] sspOrders)
			throws Exception {
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		List<Long> ids = new ArrayList<Long>();
		for (SspOrder sspOrder : sspOrders) {
			int i = sspOrderService.updateAndReturnNum(sspOrder);
			restResponse.setResult(i);
			ids.add(sspOrder.getId());
		}

		sspRedisMemory.batchDeleteBySspOrderIds(ids.toArray(new Long[0]));
		List<SspOrder> orderList = new ArrayList<SspOrder>();
		for (Long id : ids) {
			SspOrder newOrder = sspOrderService.findById(id);
			orderList.add(newOrder);
		}
		sspRedisMemory.batchAddSspOrder(orderList.toArray(new SspOrder[0]));

		LOG.info("restResponse:{}", restResponse);
		return restResponse;
	}

}
