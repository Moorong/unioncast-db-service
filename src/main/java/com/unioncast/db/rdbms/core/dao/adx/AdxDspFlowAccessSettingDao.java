package com.unioncast.db.rdbms.core.dao.adx;

import com.unioncast.common.adx.model.AdxDspFlowAccessSetting;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

public interface AdxDspFlowAccessSettingDao extends GeneralDao<AdxDspFlowAccessSetting, Long> {
    AdxDspFlowAccessSetting[] findByIds(Long[] ids) throws DaoException;
    AdxDspFlowAccessSetting[] findByDspIds(Long[] ids) throws DaoException;
    //AdxDspPanter[] findAdxDspPanterByIdstype(Long flowType, Long[] ids) throws DaoException;

}
