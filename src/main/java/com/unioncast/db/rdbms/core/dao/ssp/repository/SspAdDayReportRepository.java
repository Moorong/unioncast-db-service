package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspAdDayReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @auther wangyao
 * @date 2017-05-12 16:41
 */

@Repository("sspAdDayReportRepository")
public interface SspAdDayReportRepository
		extends JpaRepository<SspAdDayReport, Long>, JpaSpecificationExecutor<SspAdDayReport> {


}
