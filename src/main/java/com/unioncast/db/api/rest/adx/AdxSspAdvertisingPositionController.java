package com.unioncast.db.api.rest.adx;

import com.unioncast.common.adx.model.AdxSspAdvertisingPosition;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxSspAdvertisingPositionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * ADX_SSP_广告位
 *
 * @author zhangzhe
 * @date 2016年10月25日 下午7:07:03
 */
@Api(value = "ADX_SSP_广告位")
@RestController
@RequestMapping("/rest/adxSspAdvertisingPosition")
public class AdxSspAdvertisingPositionController extends GeneralController {

    private static final Logger LOG = LogManager.getLogger(AdxSspAdvertisingPositionController.class);

    @Autowired
    private AdxSspAdvertisingPositionService adxSspAdvertisingPositionService;


    /**
     * 分页条件查找
     *
     * @param pageCriteria
     * @return
     * @author zhangzhe
     * @date 2016年11月8日下午3:14:54
     */
    @ApiOperation(value = "分页条件查找", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse page(@RequestBody PageCriteria pageCriteria)
            throws DaoException {
        LOG.info("PageCriteria:{}", pageCriteria);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(adxSspAdvertisingPositionService.page(pageCriteria));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }


    /**
     * 查找所有or根据id查询
     *
     * @param id
     * @return User
     * @author zhangzhe
     * @date 2016年11月8日上午9:52:27
     */
    @ApiOperation(value = "查找所有or根据id查询", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path")})
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false) Long id) throws DaoException {
        LOG.info("id:{}", id);
        RestResponse restResponse = new RestResponse();

        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(adxSspAdvertisingPositionService.find(id));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }


    /**
     * 批量增加
     *
     * @param adxSspAdvertisingPosition
     * @return
     * @author zhangzhe
     * @date 2016年11月3日 下午5:02:47
     */
    @ApiOperation(value = "批量增加", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse batchAdd(@RequestBody AdxSspAdvertisingPosition[] adxSspAdvertisingPosition) throws DaoException {
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long[] ids = adxSspAdvertisingPositionService.batchAdd(adxSspAdvertisingPosition);
        LOG.info("ids:{}", ids);
        restResponse.setResult(ids);
        return restResponse;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return 删除了多少条
     * @author zhangzhe
     * @date 2016年11月3日 下午7:29:22
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", required = true, dataType = "Long", paramType = "body")})
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public RestResponse batchDelete(@RequestBody Long[] ids) throws DaoException {
        RestResponse restResponse = new RestResponse();
        int i = adxSspAdvertisingPositionService.batchDelete(ids);
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(i);
        LOG.info("the number of delete:{}", i);
        return restResponse;
    }


//    /**
//     * 根据id查找
//     *
//     * @param id
//     * @return
//     * @author zhangzhe
//     * @date 2016年10月25日 下午7:07:03
//     */
//    @ApiOperation(value = "根据id查找", httpMethod = "POST", response = RestResponse.class)
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path")})
//    @RequestMapping(value = "/findById", method = RequestMethod.POST)
//    public RestResponse findById(@RequestBody Long id) throws DaoException {
//        LOG.info("id:{}", id);
//        RestResponse restResponse = new RestResponse();
//        restResponse.setStatus(RestResponse.OK);
//        AdxSspAdvertisingPosition adxSspAdvertisingPosition = adxSspAdvertisingPositionService.findById(id);
//        LOG.info("adxSspReportFormHour:{}", adxSspAdvertisingPosition);
//        restResponse.setResult(adxSspAdvertisingPositionService.findById(id));
//        return restResponse;
//    }

    /**
     * 增加
     *
     * @param adxSspAdvertisingPosition
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @author zhangzhe
     * @date 2016年10月24日 下午7:07:03
     */
    @ApiOperation(value = "增加", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adxSspAdvertisingPosition", required = true, dataType = "AdxSspAdvertisingPosition", paramType = "body")})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse add(@RequestBody AdxSspAdvertisingPosition adxSspAdvertisingPosition) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("adxSspAdvertisingPosition:{}", adxSspAdvertisingPosition);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        Long id = adxSspAdvertisingPositionService.save(adxSspAdvertisingPosition);
        LOG.info("id:{}", id);
        restResponse.setResult(id);
        return restResponse;
    }

    /**
     * 修改
     *
     * @param adxSspAdvertisingPosition
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @author zhangzhe
     * @date 2016年10月24日 下午7:07:03
     */
    @ApiOperation(value = "修改", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adxSspAdvertisingPosition", required = true, dataType = "AdxSspAdvertisingPosition", paramType = "body")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody AdxSspAdvertisingPosition adxSspAdvertisingPosition) throws DaoException, IllegalArgumentException, IllegalAccessException {
        LOG.info("adxSspAdvertisingPosition:{}", adxSspAdvertisingPosition);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(adxSspAdvertisingPositionService.updateAndReturnNum(adxSspAdvertisingPosition));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;

    }

    /**
     * 删除
     *
     * @param id
     * @return 删除了多少条
     * @author zhangzhe
     * @date 2016年10月24日 下午7:07:03
     */
    @ApiOperation(value = "删除", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, dataType = "Long", paramType = "path")})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RestResponse delete(@RequestBody Long id) throws DaoException {
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        int i = adxSspAdvertisingPositionService.deleteById(id);
        LOG.info("the number of delete:{}", i);
        restResponse.setResult(i);
        return restResponse;
    }

}