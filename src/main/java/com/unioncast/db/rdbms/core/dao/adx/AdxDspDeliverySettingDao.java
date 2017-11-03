package com.unioncast.db.rdbms.core.dao.adx;

import com.unioncast.common.adx.model.AdxDspDeliverySetting;
import com.unioncast.db.rdbms.common.dao.GeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

import java.util.List;

public interface AdxDspDeliverySettingDao extends GeneralDao<AdxDspDeliverySetting, Long>{

    AdxDspDeliverySetting[] findByAdxOrSspId(Long adxOrSspId) throws DaoException;

}
