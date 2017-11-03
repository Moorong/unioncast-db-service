package com.unioncast.db.rdbms.core.service.common.impl;

import com.unioncast.common.user.model.UnioncastSystem;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.service.CommonDBGeneralService;
import com.unioncast.db.rdbms.core.service.common.UnioncastSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangzhe
 * @date 2017/2/15 16:44
 */
@Service("unioncastSystemService")
@Transactional
public class UnioncastSystemServiceImpl extends CommonDBGeneralService<UnioncastSystem, Long> implements UnioncastSystemService {


    @Autowired
    @Qualifier("unioncastSystemDao")
    @Override
    public void setGeneralDao(CommonGeneralDao<UnioncastSystem, Long> generalDao) {
        super.setGeneralDao(generalDao);
    }

}
