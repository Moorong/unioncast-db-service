package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspAdHourReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @auther wangyao
 * @date 2017-05-15 18:56
 */
@Repository("sspAdHourReportRepository")
public interface SspAdHourReportRepository
		extends JpaRepository<SspAdHourReport, Long>, JpaSpecificationExecutor<SspAdHourReport> {
}
