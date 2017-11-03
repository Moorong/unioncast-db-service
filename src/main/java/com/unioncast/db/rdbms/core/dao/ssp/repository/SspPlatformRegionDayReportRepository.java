package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspPlatformRegionDayReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("sspPlatformRegionDayReportRepository")
public interface SspPlatformRegionDayReportRepository
		extends JpaRepository<SspPlatformRegionDayReport, Long>, JpaSpecificationExecutor<SspPlatformRegionDayReport> {

}