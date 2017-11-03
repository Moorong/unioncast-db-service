package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspWithdrawRequestInfo;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspWithdrawRequestInfoDao;
import com.unioncast.db.rdbms.core.service.ssp.SspWithdrawRequestInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspWithdrawRequestInfoService")
@Transactional
public class SspWithdrawRequestInfoServiceImpl extends SspGeneralService<SspWithdrawRequestInfo, Long> implements SspWithdrawRequestInfoService {

    @Resource
    private SspWithdrawRequestInfoDao sspWithdrawRequestInfoDao;

    @Autowired
    @Qualifier("sspWithdrawRequestInfoDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspWithdrawRequestInfo, Long> generalDao) {
        this.generalDao = generalDao;
    }

	@Override
	public SspWithdrawRequestInfo findByDeveloperId(Long developerId) {
		SspWithdrawRequestInfo withdrawRequestInfo = sspWithdrawRequestInfoDao.findByDeveloperId(developerId);
		return withdrawRequestInfo;
	}
}
