package com.unioncast.db.rdbms.common.service;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.ssp.model.report.SspAdDayReport;
import com.unioncast.db.rdbms.core.exception.DaoException;

import javax.jdo.annotations.Serialized;
import java.io.Serializable;

/**
 * @auther wangyao
 * @date 2017-05-18 10:18
 */
public interface SspReportService<T extends Serializable,TD extends Serializable> {

    /**
     * 报表分页
     * @param entity
     * @return
     * @throws DaoException
     */
    Pagination<T> page(TD entity) throws DaoException;

}
