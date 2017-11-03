package com.unioncast.db.api.rest.redis;

import com.unioncast.common.adx.model.AdxDspAdcreative;
import com.unioncast.common.adx.model.AdxDspAdvertisers;
import com.unioncast.common.adx.model.AdxSspAdvertisingPosition;
import com.unioncast.common.adx.model.AdxSspMedia;
import com.unioncast.common.restClient.RestError;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.nosql.redis.RedisMemory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * RedisApi
 *
 * @author zhangzhe
 * @date 2016年10月25日 下午7:07:03
 */

@Api(value = "RedisApi")
@RestController
@RequestMapping("/rest/redis")
public class RedisController {
    private static final Logger LOG = LogManager.getLogger(RedisController.class);

    @Autowired
    private RedisMemory redisMemory;


    /**
     * 向redis存入广告主List<AdxDspAdvertisers>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入广告主List<AdxDspAdvertisers>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddAdxDspAdvertisersList", method = RequestMethod.POST)
    public <T> RestResponse batchAddAdxDspAdvertisersList(@RequestBody List<AdxDspAdvertisers> list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchAddAdxDspAdvertisersList(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取广告主AdxDspAdvertisers对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午5:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取广告主AdxDspAdvertisers对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllByAdxDspAdvertisersIds", method = RequestMethod.POST)
    public RestResponse findAllByAdxDspAdvertisersIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            List<AdxDspAdvertisers> adxDspAdvertisers = redisMemory.findAllByAdxDspAdvertisersIds(ids);
            restResponse.setResult(adxDspAdvertisers);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除广告主AdxDspAdvertisers对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午7:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除广告主AdxDspAdvertisers对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteByAdxDspAdvertisersIds", method = RequestMethod.POST)
    public RestResponse batchDeleteByAdxDspAdvertisersIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchDeleteByAdxDspAdvertisersIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 向redis存入广告创意List<AdxDspAdcreative>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入广告创意List<AdxDspAdcreative>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddAdxDspAdcreativeList", method = RequestMethod.POST)
    public <T> RestResponse batchAddAdxDspAdcreativeList(@RequestBody List<AdxDspAdcreative> list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchAddAdxDspAdcreativeList(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取广告创意AdxDspAdcreative对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午5:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取广告创意AdxDspAdcreative对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllByAdxDspAdcreativeIds", method = RequestMethod.POST)
    public RestResponse findAllByAdxDspAdcreativeIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            List<AdxDspAdcreative> adxDspAdvertisers = redisMemory.findAllByAdxDspAdcreativeIds(ids);
            restResponse.setResult(adxDspAdvertisers);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除广告创意AdxDspAdcreative对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午7:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除广告创意AdxDspAdcreative对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteByAdxDspAdcreativeIds", method = RequestMethod.POST)
    public RestResponse batchDeleteByAdxDspAdcreativeIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchDeleteByAdxDspAdcreativeIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 向redis存入媒体List<AdxSspMedia>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入媒体List<AdxSspMedia>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddAdxSspMediaList", method = RequestMethod.POST)
    public <T> RestResponse batchAddAdxSspMediaList(@RequestBody List<AdxSspMedia> list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchAddAdxSspMediaList(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取媒体AdxSspMedia对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午5:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取媒体AdxSspMedia对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllByAdxSspMediaIds", method = RequestMethod.POST)
    public RestResponse findAllByAdxSspMediaIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            List<AdxSspMedia> adxDspAdvertisers = redisMemory.findAllByAdxSspMediaIds(ids);
            restResponse.setResult(adxDspAdvertisers);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除媒体AdxSspMedia对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午7:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除媒体AdxSspMedia对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteByAdxSspMediaIds", method = RequestMethod.POST)
    public RestResponse batchDeleteByAdxSspMediaIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchDeleteByAdxSspMediaIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 向redis存入广告位置List<AdxSspAdvertisingPosition>
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午4:08:01
     */
    @ApiOperation(value = "向redis存入广告位置List<AdxSspAdvertisingPosition>", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddAdxSspAdvertisingPositionList", method = RequestMethod.POST)
    public <T> RestResponse batchAddAdxSspAdvertisingPositionList(@RequestBody List<AdxSspAdvertisingPosition> list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchAddAdxSspAdvertisingPositionList(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量向redis获取广告位置AdxSspAdvertisingPosition对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午5:08:01
     */
    @ApiOperation(value = "根据id批量向redis获取广告位置AdxSspAdvertisingPosition对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findAllByAdxSspAdvertisingPositionIds", method = RequestMethod.POST)
    public RestResponse findAllByAdxSspAdvertisingPositionIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            List<AdxSspAdvertisingPosition> adxDspAdvertisers = redisMemory.findAllByAdxSspAdvertisingPositionIds(ids);
            restResponse.setResult(adxDspAdvertisers);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 根据id批量从redis删除广告位置AdxSspAdvertisingPosition对象
     *
     * @param ids
     * @return
     * @author zhangzhe
     * @date 2016年11月1日 下午7:08:01
     */

    @ApiOperation(value = "根据id批量从redis删除广告位置AdxSspAdvertisingPosition对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDeleteByAdxSspAdvertisingPositionIds", method = RequestMethod.POST)
    public RestResponse batchDeleteByAdxSspAdvertisingPositionIds(@RequestBody List<Long> ids) {
        LOG.info("ids:{}", ids);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchDeleteByAdxSspAdvertisingPositionIds(ids);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }


    /**
     * 向redis存入对象,若是实体类那么key值可为[类名全称小写+"_"+id]方便获取,其他类对象等可以自行定义key
     *
     * @param key
     * @param object
     * @return
     * @author zhangzhe
     * @date 2016年10月18日 下午3:08:36
     */
    @ApiOperation(value = "向redis存入对象,若是实体类那么key值可为[类名全称小写+\"_\"+id]方便获取,其他类对象等可以自行定义key", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/addByKey/{key}", method = RequestMethod.POST)
    public <T> RestResponse addByKey(@PathVariable String key, @RequestBody T object) {
        LOG.info("key:{}", key);
        RestResponse restResponse = new RestResponse();
        try {
            // String name = object.getClass().getName();
            restResponse.setStatus(RestResponse.OK);
            String ok = redisMemory.addByKey(key, object);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 向redis存入对象,随机key,返回key 可以是任意对象,包括list,map等
     *
     * @param object
     * @return
     * @author zhangzhe
     * @date 2016年10月18日 下午3:08:25
     */
    @ApiOperation(value = "向redis存入对象,随机key,返回key 可以是任意对象,包括list,map等", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public <T> RestResponse add(@RequestBody T object) {

        LOG.info("object:{}", object);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            String uuid = redisMemory.add(object);
            restResponse.setResult(uuid);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 根据自行设置维护的key向redis获取对象
     *
     * @param key
     * @return
     * @author zhangzhe
     * @date 2016年10月18日 下午3:08:15
     */
    @ApiOperation(value = "根据自行设置维护的key向redis获取对象", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/findByKey", method = RequestMethod.POST)
    public RestResponse getObject(@RequestBody String key) {
        LOG.info("key:{}", key);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            Object ok = redisMemory.getObject(key);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 向redis存入List<T>,随机key,返回key
     *
     * @param list
     * @return
     * @author zhangzhe
     * @date 2016年10月18日 下午3:08:01
     */
    @ApiOperation(value = "向redis存入List<T>,随机key,返回key", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public <T> RestResponse addList(@RequestBody List<T> list) {
        LOG.info("list:{}", list);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            List<String> ok = redisMemory.addList(list);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * redis存入map,返回成功值
     *
     * @param map
     * @return
     * @author zhangzhe
     * @date 2016年10月18日 下午3:07:47
     */
    @ApiOperation(value = "redis存入map,返回成功值", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAddKey", method = RequestMethod.POST)
    public <T> RestResponse addListKey(@RequestBody Map<String, T> map) {

        LOG.info("map:{}", map);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.addListKey(map);
            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 根据key删除value
     *
     * @param <T>
     * @return
     * @author zhangzhe
     * @date 2016年10月18日 上午10:47:03
     */
    @ApiOperation(value = "根据key删除value", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public <T> RestResponse deleteByKey(@RequestBody String key) {

        LOG.info("key:{}", key);

        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);

            Long ok = redisMemory.deleteByKey(key);

            restResponse.setResult(ok);
            LOG.info("restResponse:{}", restResponse);
            return restResponse;
        } catch (Exception e) {
            return throwException(restResponse, e);
        }

    }

    /**
     * 根据keyList批量删除value
     *
     * @param <T>
     * @return
     * @author zhangzhe
     * @date 2016年10月18日 下午15:47:03
     */

    @ApiOperation(value = "根据keyList批量删除value", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public <T> RestResponse batchDelete(@RequestBody List<String> strList) {
        LOG.info("strList:{}", strList);
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setStatus(RestResponse.OK);
            Long ok = redisMemory.batchDelete(strList);
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

}
