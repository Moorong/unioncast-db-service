package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspAdRegionDayReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("sspAdRegionDayReportRepository")
public interface SspAdRegionDayReportRepository
		extends JpaRepository<SspAdRegionDayReport, Long>, JpaSpecificationExecutor<SspAdRegionDayReport> {

}