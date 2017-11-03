package com.unioncast.db.rdbms.core.service.adx;

import com.unioncast.common.adx.model.AdxDspDeliverySetting;
import com.unioncast.db.rdbms.common.service.GeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;

import java.util.List;

public interface AdxDspDeliverySettingService extends GeneralService<AdxDspDeliverySetting,Long> {


    AdxDspDeliverySetting[] findByAdxOrSspId(Long adxOrSspId) throws DaoException;
}
