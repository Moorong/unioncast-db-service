
package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspPlatformHourReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("sspPlatformHourReportRepository")
public interface SspPlatformHourReportRepository
		extends JpaRepository<SspPlatformHourReport, Long>, JpaSpecificationExecutor<SspPlatformHourReport> {

}