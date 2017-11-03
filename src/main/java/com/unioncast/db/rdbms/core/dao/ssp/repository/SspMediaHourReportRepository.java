package com.unioncast.db.rdbms.core.dao.ssp.repository;

import com.unioncast.common.ssp.model.report.SspMediaHourReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("sspMediaHourReportRepository")
public interface SspMediaHourReportRepository
		extends JpaRepository<SspMediaHourReport, Long>, JpaSpecificationExecutor<SspMediaHourReport> {

}