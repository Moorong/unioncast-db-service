package com.unioncast.db.rdbms.core.service.adx.impl;

import com.unioncast.common.adx.model.AdxDspFlowAccessSetting;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.adx.AdxDspFlowAccessSettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adxDspFlowAccessSettingService")
@Transactional
public class AdxDspFlowAccessSettingServiceImpl extends AdxDBGeneralService<AdxDspFlowAccessSetting, Long>
        implements AdxDspFlowAccessSettingService {

    @Autowired
    @Qualifier("adxDspFlowAccessSettingDao")
    @Override
    public void setGeneralDao(AdxGeneralDao<AdxDspFlowAccessSetting, Long> generalDao) {
        super.setGeneralDao(generalDao);
    }

    @Override
    public AdxDspFlowAccessSetting[] findByIds(Long[] ids) throws DaoException {
        return generalDao.findByIds(ids);
    }

    @Override
    public AdxDspFlowAccessSetting[] findByDspIds(Long[] ids) throws DaoException {
        return generalDao.findByDspIds(ids);
    }

//	@Override
//	public AdxDspPanter[] findAdxDspPanterByIdstype(Long flowType, Long[] ids) throws DaoException {
//		return generalDao.findAdxDspPanterByIdstype(flowType,ids);
//	}
}
