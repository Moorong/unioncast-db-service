package com.unioncast.db.api.rest.ssp.report;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.restClient.RestResponseFactory;
import com.unioncast.common.ssp.model.SspAppInfo;
import com.unioncast.common.ssp.model.report.SspAdDayReport;
import com.unioncast.common.ssp.model.report.SspAdHourReport;
import com.unioncast.db.api.rest.ssp.SspAdPositionReportController;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspAdDayReportService;
import com.unioncast.db.rdbms.core.service.ssp.report.SspAdHourReportService;
import com.unioncast.db.rdbms.core.service.ssp.report.SspMediaDayReportService;
import com.unioncast.db.rdbms.core.service.ssp.report.SspPlatformDayReportService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @auther wangyao
 * @date 2017-05-15 9:49
 */

@RestController
@RequestMapping("/report")
public class SspReportController {

	private static final Logger LOG = LogManager.getLogger(SspAdPositionReportController.class);

	@Resource
	private SspAdDayReportService sspAdDayReportService;
	@Resource
	private SspAdHourReportService sspAdHourReportService;
	@Resource
	private SspMediaDayReportService sspMediaDayReportService;
	@Resource
	private SspPlatformDayReportService sspPlatformDayReportService;

	@ApiOperation(value = "广告报表条件分页查询（按天）", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
	@RequestMapping(value = "/adDayReportPage", method = RequestMethod.POST)
	public RestResponse adDayReport(@RequestBody PageCriteria pageCriteria) throws DaoException {
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponse.OK);
		try {
			Pagination<SspAdDayReport> page = sspAdDayReportService.page(pageCriteria);
			restResponse.setResult(page);
		} catch (Exception e) {
			LOG.error("广告报表分页出错: " + e.getMessage(), e);
		}
		return restResponse;
	}

    @ApiOperation(value = "广告报表条件分页查询（按小时）", httpMethod = "POST", response = RestResponse.class)
    @ApiImplicitParam(name = "pageCriteria", value = "分页查询条件", required = true, dataType = "PageCriteria", paramType = "body")
    @RequestMapping(value = "/adHourReportPage", method = RequestMethod.POST)
    public RestResponse adHourReport(@RequestBody PageCriteria pageCriteria) throws DaoException {
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(RestResponse.OK);
        try {
            Pagination<SspAdHourReport> page = sspAdHourReportService.page(pageCriteria);
            restResponse.setResult(page);
        } catch (Exception e) {
            LOG.error("广告报表分页出错: " + e.getMessage(), e);
        }
        return restResponse;
    }

}
