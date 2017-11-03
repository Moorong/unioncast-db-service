package com.unioncast.db.rdbms.core.dao.adx;

import com.unioncast.common.adx.model.AdxSspMedia;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface AdxSspMediaDao extends GeneralDao<AdxSspMedia, Long> {

	Pagination<AdxSspMedia> page(PageCriteria pageCriteria);
	
	public AdxSspMedia findByAppOrWebId(Long id) throws DaoException;

}
