package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspMediaRegionDayReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("sspMediaRegionDayReportRepository")
public interface SspMediaRegionDayReportRepository
		extends JpaRepository<SspMediaRegionDayReport, Long>, JpaSpecificationExecutor<SspMediaRegionDayReport> {

}