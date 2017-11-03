package com.unioncast.sspPlanService;

import com.unioncast.BaseDbRestApiApplicationTest;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Simple to Introduction
 *
 * @Description: [策略服务测试类]
 * @Author: [dxy]
 * @CreateDate: [2017/2/10 15:44]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
public class sspPlanTest extends BaseDbRestApiApplicationTest {

    @Autowired
    private SspPlanService service;

    @Test
    public void TestfindPlanCountByOrderId(){
        Map<String,Object> result = service.findPlanCountByOrderId(15L);
        Assert.assertNotNull(result);
    }

}
