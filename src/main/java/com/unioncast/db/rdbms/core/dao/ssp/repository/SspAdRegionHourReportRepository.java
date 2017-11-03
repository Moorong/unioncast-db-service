package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspAdRegionHourReport;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("sspAdRegionHourReportRepository")
public interface SspAdRegionHourReportRepository
		extends CrudRepository<SspAdRegionHourReport, Long>, JpaSpecificationExecutor<SspAdRegionHourReport> {

}