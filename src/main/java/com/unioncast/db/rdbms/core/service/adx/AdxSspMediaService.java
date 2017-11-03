package com.unioncast.db.rdbms.core.service.adx;

import com.unioncast.common.adx.model.AdxSspMedia;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface AdxSspMediaService extends GeneralService<AdxSspMedia, Long> {

	Pagination<AdxSspMedia> page(PageCriteria pageCriteria);

	public AdxSspMedia findByAppOrWebId(Long id) throws DaoException;

}
