package com.unioncast.db.rdbms.core.service.adx;

import com.unioncast.common.adx.model.AdxDspFlowAccessSetting;
import com.unioncast.common.adx.model.AdxDspPanter;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

import java.util.List;

public interface AdxDspFlowAccessSettingService extends GeneralService<AdxDspFlowAccessSetting,Long>{

    AdxDspFlowAccessSetting[] findByIds(Long[] ids) throws DaoException;

    AdxDspFlowAccessSetting[] findByDspIds(Long[] ids) throws DaoException;

    //   AdxDspPanter[] findAdxDspPanterByIdstype(Long flowType, Long[] ids) throws DaoException;
}
