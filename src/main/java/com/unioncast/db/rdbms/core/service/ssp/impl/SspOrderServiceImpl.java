package com.unioncast.db.rdbms.core.service.ssp.impl;

import com.unioncast.common.page.AdvertiserOrderModel;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspOrder;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.service.SspGeneralService;
import com.unioncast.db.rdbms.core.dao.ssp.SspOrderDao;
import com.unioncast.db.rdbms.core.service.ssp.SspOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

@Service("sspOrderService")
@Transactional
public class SspOrderServiceImpl extends SspGeneralService<SspOrder, Long> implements SspOrderService {

    @Resource
    private SspOrderDao sspOrderDao;

    @Autowired
    @Qualifier("sspOrderDao")
    @Override
    public void setGeneralDao(SspGeneralDao<SspOrder, Long> generalDao) {
        this.generalDao = generalDao;
    }


	@Override
	public Integer countOrders(PageCriteria pageCriteria) {
		return sspOrderDao.countOrders(pageCriteria);
	}

    @Override
    public List<SspOrder> findOrderAll() {
        return sspOrderDao.findOrderAll();
    }


	@Override
	public Pagination<SspOrder> AdStatePage( AdvertiserOrderModel params) {
		return sspOrderDao.AdStatePage(params );
	}


	@Override
	public SspOrder[] findByAdvertiserId(Long advertiserId) {
		return sspOrderDao.findByAdId(advertiserId);
	}
}
