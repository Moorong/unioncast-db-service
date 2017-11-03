package com.unioncast.db.rdbms.core.service.adx.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxSspMedia;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspMediaDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxSspMediaService;

@Service("adxSspMediaService")
@Transactional
public class AdxSspMediaServiceImpl extends AdxDBGeneralService<AdxSspMedia, Long> implements AdxSspMediaService {

	@Autowired
	@Qualifier("adxSspMediaDao")
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxSspMedia, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Resource
	private AdxSspMediaDao adxSspMediaDao;

	@Override
	public Pagination<AdxSspMedia> page(PageCriteria pageCriteria) {
		return adxSspMediaDao.page(pageCriteria);
	}

	@Override
	public AdxSspMedia findByAppOrWebId(Long id) throws DaoException {
		return generalDao.findByAppOrWebId(id);
	}

}
