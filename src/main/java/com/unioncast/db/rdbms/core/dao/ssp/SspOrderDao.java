package com.unioncast.db.rdbms.core.dao.ssp;

import com.unioncast.common.page.AdvertiserOrderModel;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.SspOrder;
import com.unioncast.db.rdbms.common.dao.GeneralDao;

import java.util.List;

public interface SspOrderDao extends GeneralDao<SspOrder , Long> {
	public Integer countOrders(PageCriteria pageCriteria);

	/**
	* @Author dxy
	* @Date 2017/2/9 16:24
	* @Description (订单未删除的,状态开启的所有订单)
	 *  @param
	* @throws
	*/
	public List<SspOrder> findOrderAll();
	//含有广告主的状态的查询列表
	public Pagination<SspOrder> AdStatePage( AdvertiserOrderModel params );

	public SspOrder[] findByAdId(Long advertiserId);
}
