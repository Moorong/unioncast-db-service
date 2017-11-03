package com.unioncast.sspOrderService;

import com.unioncast.BaseDbRestApiApplicationTest;
import com.unioncast.common.ssp.model.SspOrder;
import com.unioncast.db.rdbms.core.service.ssp.SspOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * @Description: [Order订单测试类]
 * @Author: [dxy]
 * @CreateDate: [2017/2/9 16:53]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
public class sspOrderTest extends BaseDbRestApiApplicationTest{

    @Autowired
    private SspOrderService service;

    @Test
    public void TestFindAllOrder(){
        List<SspOrder> results = service.findOrderAll();
        Assert.assertNotNull(results);
    }
}
