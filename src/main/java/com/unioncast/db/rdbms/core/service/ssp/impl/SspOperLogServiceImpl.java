package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspOperLog;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspOperLogDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspOperLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspOperLogService")
@Transactional
public class SspOperLogServiceImpl extends SspGeneralService<SspOperLog, Long> implements SspOperLogService {
    @Resource
    private SspOperLogDao sspOperLogDao;

    @Autowired
    @Qualifier("sspOperLogDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspOperLog, Long> generalDao) {
        this.generalDao = generalDao;
    }


    @Override
    public Pagination<SspOperLog> page(PageCriteria pageCriteria) throws DaoException {
        return sspOperLogDao.page(pageCriteria,1L);
    }

}
