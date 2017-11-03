package com.unioncast.db.rdbms.core.service.adx.impl;

import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unioncast.common.adx.model.AdxDspBiddingParamCriteria;
import com.unioncast.common.adx.model.AdxDspBiddingParams;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.service.AdxDBGeneralService;
import com.unioncast.db.rdbms.core.dao.adx.AdxDspBiddingParamsDao;
import com.unioncast.db.rdbms.core.service.adx.AdxDspBiddingParamsService;

@Service("adxDspBiddingParamsService")
@Transactional
public class AdxDspBiddingParamsServiceImpl extends AdxDBGeneralService<AdxDspBiddingParams, Long> implements AdxDspBiddingParamsService {

	@Resource
	private AdxDspBiddingParamsDao adxDspBiddingParamsDao;
	
	@Resource
	@Override
	public void setGeneralDao(AdxGeneralDao<AdxDspBiddingParams, Long> generalDao) {
		super.setGeneralDao(generalDao);
	}

	@Override
	public AdxDspBiddingParams getSumDataByBE(AdxDspBiddingParamCriteria biddingParamCriteria) {
		return adxDspBiddingParamsDao.getSumDataByBE(biddingParamCriteria);
	}

	@Override
	public Map<String, AdxDspBiddingParams> getDataByBE(AdxDspBiddingParamCriteria biddingParamCriteria) throws ParseException {
		return adxDspBiddingParamsDao.getDataByBE(biddingParamCriteria);
	}
}
