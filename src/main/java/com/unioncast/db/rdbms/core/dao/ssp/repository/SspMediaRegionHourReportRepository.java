
package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspMediaRegionHourReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("sspMediaRegionHourReportRepository")
public interface SspMediaRegionHourReportRepository
		extends JpaRepository<SspMediaRegionHourReport, Long>, JpaSpecificationExecutor<SspMediaRegionHourReport> {

}