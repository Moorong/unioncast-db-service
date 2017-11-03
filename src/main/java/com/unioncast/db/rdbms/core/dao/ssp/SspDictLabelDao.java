package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.ssp.model.SspDictLabel;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface SspDictLabelDao extends GeneralDao<SspDictLabel , Long> {

	int countAll() throws DaoException;

	SspDictLabel[] findPage(Integer clickCount);
}
