package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspPlatformRegionHourReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("sspPlatformRegionHourReportRepository")
public interface SspPlatformRegionHourReportRepository extends JpaRepository<SspPlatformRegionHourReport, Long>,
		JpaSpecificationExecutor<SspPlatformRegionHourReport> {

}