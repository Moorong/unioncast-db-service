package com.unioncast.db.rdbms.core.service.common;

import com.unioncast.common.user.model.Qualification;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface QualificationService extends GeneralService<Qualification, Long> {

	public Qualification[] findByUserId(Long id) throws DaoException;

}
