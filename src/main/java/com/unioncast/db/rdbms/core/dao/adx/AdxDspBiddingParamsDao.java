package com.unioncast.db.rdbms.core.dao.adx;

import java.text.ParseException;
import java.util.Map;

import com.unioncast.common.adx.model.AdxDspBiddingParamCriteria;
import com.unioncast.common.adx.model.AdxDspBiddingParams;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

public interface AdxDspBiddingParamsDao extends GeneralDao<AdxDspBiddingParams, Long> {

	AdxDspBiddingParams getSumDataByBE(AdxDspBiddingParamCriteria biddingParamCriteria);

	Map<String, AdxDspBiddingParams> getDataByBE(AdxDspBiddingParamCriteria biddingParamCriteria) throws ParseException;

}
