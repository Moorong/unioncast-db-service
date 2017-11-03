package com.unioncast.db.rdbms.core.service.ssp.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.ssp.model.SspDictEducationTarget;
import com.unioncast.common.ssp.model.SspDictIncomeTarget;
import com.unioncast.common.ssp.model.SspDictMarriageTarget;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictIncomeDao;
import com.unioncast.db.rdbms.core.service.ssp.SspDictIncomeService;

@Service("sspDictIncomeSevice")
@Transactional
public class SspDictIncomeServiceImpl  extends SspGeneralService<SspDictIncomeTarget,Long> implements SspDictIncomeService{
  @Resource
  private SspDictIncomeDao sspDictIncomeDao;
  
  @Autowired
  @Qualifier("sspDictIncomeDao")
  @Override
  public void setGeneralDao(SspGeneralDao<SspDictIncomeTarget, Long> generalDao) {
      this.generalDao = generalDao;
  }

@Override
public SspDictIncomeTarget[] batchFindbyCodes(String[] codes) {
	return sspDictIncomeDao.batchFindbyCodes(codes);
}
}
