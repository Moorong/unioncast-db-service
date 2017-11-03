package com.unioncast.sspDictBuyTargetService;

import com.unioncast.BaseDbRestApiApplicationTest;
import com.unioncast.common.ssp.model.SspDictBuyTarget;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictBuyTargetDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Simple to Introduction
 *
 * @Description: [购买倾向测试服务类]
 * @Author: [dxy]
 * @CreateDate: [2017/3/4 12:18]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
public class sspDictBuyTargetTest extends BaseDbRestApiApplicationTest{

    @Autowired
    private SspDictBuyTargetDao targetDao;

    @Test
    public void getVal(){
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("D:\\购买倾向.txt"),
                    "gbk");
            BufferedReader br = new BufferedReader(isr);
            String r = br.readLine();
            List<SspDictBuyTarget> list = new ArrayList<>();
            HashMap<String, String> city = new HashMap<>();
            int code1 = 1000;
            String parentName = "";
            String name = "";
            while (r != null) {
                String str[] = r.split("/");
                int length = str.length;
                SspDictBuyTarget sspCityInfo = new SspDictBuyTarget();
                sspCityInfo.setName(str[length - 1]);
                if (length == 1) {
                    code1++;
//                    sspCityInfo.setCode(String.valueOf(code1));
                    city.put(str[length - 1], String.valueOf(code1) + "1001");
                } else {
                    parentName = "";
                    name = "";
                    for (int j = 0; j < length - 1; j++) {
                        parentName = parentName + str[j];
                    }
                    name = parentName + str[length - 1];
                    // 当前节点code
                    String currentCode = city.get(parentName);
//                    sspCityInfo.setCode(currentCode);

                    // 更新当前节点同一级下一节点code
                    System.out.println(r+"************************");
                    if("3C产品/移动存储/品牌/金士顿".equals(r)){
                        System.out.print("1111");
                    }
                    String lastStr = currentCode.substring(currentCode.length() - 4, currentCode.length());
                    String startStr = currentCode.substring(0, currentCode.length() - 4);
                    int codes = Integer.parseInt(lastStr);
                    codes = codes + 1;
                    String ss = startStr + String.valueOf(codes);
                    city.put(parentName, ss);
                    // 当前节点下一级code
                    city.put(name, currentCode + "1001");
                }
                list.add(sspCityInfo);
                r = br.readLine();
            }
            targetDao.batchAdd(list.toArray(new SspDictBuyTarget[list.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
