package com.unioncast.db.rdbms.core.service.ssp;

import com.unioncast.common.ssp.model.SspDictLabel;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface SspDictLabelService extends GeneralService<SspDictLabel, Long> {

	int count() throws DaoException;

	SspDictLabel[] findPage(Integer clickCount);

}
