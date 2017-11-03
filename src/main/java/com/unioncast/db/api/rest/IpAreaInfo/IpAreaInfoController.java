package com.unioncast.db.api.rest.IpAreaInfo;

import javax.annotation.Resource;

import com.unioncast.db.rdbms.core.service.common.IpAreaInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.ip.IpAreaInfo;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.UnioncastSystemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @auther wangyao
 * @date 2017-02-27 10:43
 */
@Api(value = "ip信息")
@RestController
@RequestMapping("/rest/ip")
public class IpAreaInfoController {
    private final static Logger LOG = LogManager.getLogger(IpAreaInfoController.class);
    @Resource
    private IpAreaInfoService ipAreaInfoService;

    @ApiOperation(value = "查询所有ip信息", httpMethod = "POST", response = RestResponse.class)
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public RestResponse find(@RequestBody(required = false)IpAreaInfo ipAreaInfo) throws DaoException, IllegalAccessException {
        LOG.info("find ipAreaInfo :{}", ipAreaInfo);
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        restResponse.setResult(ipAreaInfoService.findT(ipAreaInfo));
        LOG.info("restResponse:{}", restResponse);
        return restResponse;
    }
}
