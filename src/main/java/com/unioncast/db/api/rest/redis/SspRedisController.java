package com.unioncast.db.api.rest.redis;

import java.util.List;
import java.util.Map;

import com.unioncast.common.restClient.RestError;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.*;
import com.unioncast.db.nosql.redis.SspRedisMemory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SspRedisApi
 *
 * @author zhangzhe
 * @date 2017年2月13日 下午7:07:03
 */

@Api(value = "SspRedisApi")
@RestController
@RequestMapping("/rest/sspRedis")
public class SspRedisController {
    private static final Logger LOG = LogManager.getLogger(SspRedisController.class);

    @Autowired
    private SspRedisMemory sspRedisMemory;

    /**
     * 向redis存入广告主List<SspAdvertiser>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入广告主List<SspAdvertiser>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddSspAdvertiser", method = RequestMethod.POST)
    public RestResponse batchAddSspAdvertiser(@RequestBody SspAdvertiser[] list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchAddSspAdvertiser(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取广告主SspAdvertiser对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取广告主SspAdvertiser对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllBySspAdvertiserIds", method = RequestMethod.POST)
    public RestResponse findAllBySspAdvertiserIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            SspAdvertiser[] list = sspRedisMemory.findAllBySspAdvertiserIds(ids);
            restResponse.setResult(list);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除广告主SspAdvertiser对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除广告主SspAdvertiser对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteBySspAdvertiserIds", method = RequestMethod.POST)
    public RestResponse batchDeleteBySspAdvertiserIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchDeleteBySspAdvertiserIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 向redis存入订单List<SspOrder>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入订单List<SspOrder>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddSspOrder", method = RequestMethod.POST)
    public RestResponse batchAddSspOrder(@RequestBody SspOrder[] list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchAddSspOrder(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取订单SspOrder对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取订单SspOrder对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllBySspOrderIds", method = RequestMethod.POST)
    public RestResponse findAllBySspOrderIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            SspOrder[] list = sspRedisMemory.findAllBySspOrderIds(ids);
            restResponse.setResult(list);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除订单SspOrder对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除订单SspOrder对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteBySspOrderIds", method = RequestMethod.POST)
    public RestResponse batchDeleteBySspOrderIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchDeleteBySspOrderIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 向redis存入计划List<SspPlan>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入计划List<SspPlan>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddSspPlan", method = RequestMethod.POST)
    public RestResponse batchAddSspPlan(@RequestBody SspPlan[] list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchAddSspPlan(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取计划SspPlan对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取计划SspPlan对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllBySspPlanIds", method = RequestMethod.POST)
    public RestResponse findAllBySspPlanIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            SspPlan[] list = sspRedisMemory.findAllBySspPlanIds(ids);
            restResponse.setResult(list);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除计划SspPlan对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除计划SspPlan对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteBySspPlanIds", method = RequestMethod.POST)
    public RestResponse batchDeleteBySspPlanIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchDeleteBySspPlanIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 向redis存入创意List<SspCreative>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入创意List<SspCreative>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddSspCreative", method = RequestMethod.POST)
    public RestResponse batchAddSspCreative(@RequestBody SspCreative[] list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchAddSspCreative(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取创意SspCreative对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取创意SspCreative对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllBySspCreativeIds", method = RequestMethod.POST)
    public RestResponse findAllBySspCreativeIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            SspCreative[] list = sspRedisMemory.findAllBySspCreativeIds(ids);
            restResponse.setResult(list);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除创意SspCreative对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除创意SspCreative对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteBySspCreativeIds", method = RequestMethod.POST)
    public RestResponse batchDeleteBySspCreativeIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchDeleteBySspCreativeIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有广告主
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有广告主", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAdvertiser", method = RequestMethod.POST)
    public RestResponse findAdvertiser() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            SspAdvertiser[] ok = sspRedisMemory.findSspAdvertiser();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有订单
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有订单", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findSspOrder", method = RequestMethod.POST)
    public RestResponse findSspOrder() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            SspOrder[] ok = sspRedisMemory.findSspOrder();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有计划
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有计划", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findPlan", method = RequestMethod.POST)
    public RestResponse findPlan() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            SspPlan[] ok = sspRedisMemory.findSspPlan();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有创意
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有创意", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findCreative", method = RequestMethod.POST)
    public RestResponse findCreative() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            SspCreative[] ok = sspRedisMemory.findSspCreative();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }
    
    /**
     * 查询所有广告主
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有广告主", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAdvertiserMap", method = RequestMethod.POST)
    public RestResponse findAdvertiserMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , SspAdvertiser> ok = sspRedisMemory.findAllAdvertiser();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有订单
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有订单", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findSspOrderMap", method = RequestMethod.POST)
    public RestResponse findSspOrderMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , SspOrder> ok = sspRedisMemory.findAllOrder();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有计划
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有计划", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findPlanMap", method = RequestMethod.POST)
    public RestResponse findPlanMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , SspPlan> ok = sspRedisMemory.findAllPlan();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
        	e.printStackTrace();
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有创意
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有创意", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findCreativeMap", method = RequestMethod.POST)
    public RestResponse findCreativeMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , SspCreative> ok = sspRedisMemory.findAllCreative();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }
    
    /**
     * 查询所有创意
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有策略-创意关系表", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findPlanCreativeMap", method = RequestMethod.POST)
    public RestResponse findPlanCreativeMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , List<Long>> ok = sspRedisMemory.findAllPlanCreativeIds();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }










    /**
     * 向redis存入计划创意中间表List<SspPlanCreativeRelation>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入计划创意中间表List<SspPlanCreativeRelation>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddSspPlanCreativeRelation", method = RequestMethod.POST)
    public RestResponse batchAddSspPlanCreativeRelation(@RequestBody SspPlanCreativeRelation[] list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchAddSspPlanCreativeRelation(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取计划创意中间表SspPlanCreativeRelation对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取计划创意中间表SspPlanCreativeRelation对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllBySspPlanCreativeRelationIds", method = RequestMethod.POST)
    public RestResponse findAllBySspPlanCreativeRelationIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            SspPlanCreativeRelation[] list = sspRedisMemory.findAllBySspPlanCreativeRelationIds(ids);
            restResponse.setResult(list);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除计划创意中间表SspPlanCreativeRelation对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除计划创意中间表SspPlanCreativeRelation对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteBySspPlanCreativeRelationIds", method = RequestMethod.POST)
    public RestResponse batchDeleteBySspPlanCreativeRelationIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchDeleteBySspPlanCreativeRelationIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 查询所有计划创意中间表
     *
     * @return
     * @author zhangzhe
     * @date 2017年2月13日 下午4:08:01
     */
    @ApiOperation(value = "查询所有计划创意中间表", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findSspPlanCreativeRelation", method = RequestMethod.POST)
    public RestResponse findSspPlanCreativeRelation() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            SspPlanCreativeRelation[] ok = sspRedisMemory.findSspPlanCreativeRelation();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }




    public RestResponse throwException(RestResponse restResponse, Exception e) {
        restResponse.setStatus(RestResponse.FAIL);
        RestError restErrors = new RestError();
        restErrors.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        restErrors.setMessage(e.getMessage());
        restResponse.setRestErrors(new RestError[]{restErrors});
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }
    
    @ApiOperation(value = "向redis存入广告主List<SspAdvertiser>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddCreativeDayConsume", method = RequestMethod.POST)
    public RestResponse batchAddCreativeDayConsume(@RequestBody SspCreativeReport[] list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchAddCreativeDayConsume(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }
    }
    
    @ApiOperation(value = "根据id批量从redis删除广告主SspAdvertiser对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteCreativeDayConsumeByCreativeIds", method = RequestMethod.POST)
    public RestResponse batchDeleteCreativeDayConsumeByCreativeIds(@RequestBody Long[] ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.batchDeleteCreativeDayConsumeByCreativeIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }
    }
    
    @ApiOperation(value = "查询所有策略-创意最近几天ecpm", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findPlanCreativeDayConsumeMap", method = RequestMethod.POST)
    public RestResponse findCreativeDayConsumeMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , SspCreativeReport> ok = sspRedisMemory.findCreativeDayConsumeMap();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }
    
    @ApiOperation(value = "更新所有策略-创意最近几天ecpm", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/updateCreativeDayConsume", method = RequestMethod.POST)
    public RestResponse updateCreativeDayConsume(SspCreativeReport[] dayConsumeLogs) {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            sspRedisMemory.deleteAllCreativeDayConsume();
            Long ok = sspRedisMemory.batchAddCreativeDayConsume(dayConsumeLogs);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }
    
    @ApiOperation(value = "查询广告主-订单表", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAdvertiserOrderMap", method = RequestMethod.POST)
    public RestResponse findAdvertiserOrderMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , List<Long>> ok = sspRedisMemory.findAllAdvertiserOrderIds();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

   /**
    * 
    *<p>method:  向redis存入创意的ecpm</p>
    * @param map
    * @return
    *author:dsp2liufengjiao
    *date: 2017年2月23日 下午2:56:52
    */
    @ApiOperation(value = "向redis存入创意的ecpm", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/updateEcpmRedis", method = RequestMethod.POST)
    public RestResponse updateEcpmRedis(@RequestBody Map<Long, Double> map) {
        LOG.info("updateEcpmRedis map:{}", map);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            String ok = sspRedisMemory.updateEcpmRedis(map);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }
    
    @ApiOperation(value = "查询所有创意ecpm", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findCreativeEcpmMap", method = RequestMethod.POST)
    public RestResponse findCreativeEcpmMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long,Double> result = sspRedisMemory.findCreativeEcpmMap();
            restResponse.setResult(result);
            LOG.info("findAdvertiserOrderMap result:{}", result);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }
    }
    
    @ApiOperation(value = "更新订单，计划每天的消费情况", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/updateRedisConsume", method = RequestMethod.POST)
    public RestResponse updateRedisConsume(@RequestBody RedisConsume redisConsume) {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = sspRedisMemory.updateRedisConsume(redisConsume);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }
    }
    
    @ApiOperation(value = "查询订单每天预算数据", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findOrderConsumeMap", method = RequestMethod.POST)
    public RestResponse findOrderConsumeMap() {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , SspDayOrHourReport> ok = sspRedisMemory.findOrderConsumeMap();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
        	e.printStackTrace();
            return throwException(restResponse, e);
        }

    }
    
    @ApiOperation(value = "查询计划每天预算数据", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findPlanConsumeMap", method = RequestMethod.POST)
    public RestResponse findPlanConsumeMap() {

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Map<Long , SspDayOrHourReport> ok = sspRedisMemory.findPlanConsumeMap();
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
        	e.printStackTrace();
            return throwException(restResponse, e);
        }

    }
}
