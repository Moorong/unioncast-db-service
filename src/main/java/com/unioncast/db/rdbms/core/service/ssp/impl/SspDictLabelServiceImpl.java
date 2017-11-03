package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.ssp.model.SspDictLabel;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictLabelDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.SspDictLabelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sspDictLabelService")
@Transactional
public class SspDictLabelServiceImpl extends SspGeneralService<SspDictLabel, Long> implements SspDictLabelService {
    @Resource
    private SspDictLabelDao sspDictLabelDao;

    @Autowired
    @Qualifier("sspDictLabelDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspDictLabel, Long> generalDao) {
        this.generalDao = generalDao;
    }

	@Override
	public int count() throws DaoException {
		return sspDictLabelDao.countAll();
	}

	@Override
	public SspDictLabel[] findPage(Integer clickCount) {
		return sspDictLabelDao.findPage(clickCount);
	}
}
