package com.unioncast.db.rdbms.core.service.common.impl;

import com.unioncast.common.ip.IpAreaInfo;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.service.common.IpAreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther wangyao
 * @date 2017-02-27 10:51
 */
@Service("ipAreaInfoService")
@Transactional
public class IpAreaInfoServiceImpl extends CommonDBGeneralService<IpAreaInfo,Long> implements IpAreaInfoService {
    @Autowired
    @Qualifier("ipAreaInfoDao")
    @Override
    public void setGeneralDao(CommonGeneralDao<IpAreaInfo, Long> generalDao) {
        super.setGeneralDao(generalDao);
    }
}
