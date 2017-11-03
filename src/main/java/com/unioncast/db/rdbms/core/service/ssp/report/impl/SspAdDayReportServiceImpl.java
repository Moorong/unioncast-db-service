package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspAdPositionInfo;
import com.unioncast.common.ssp.model.report.SspAdDayReport;
import com.unioncast.common.util.DateUtil;
import com.unioncast.db.rdbms.core.dao.ssp.repository.SspAdDayReportRepository;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspAdDayReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SspAdDayReportServiceImpl implements SspAdDayReportService {

	@Resource
	private SspAdDayReportRepository sspAdDayReportRepository;

	@Override
	public Pagination<SspAdDayReport> page(PageCriteria pageCriteria) throws DaoException {
		Sort sort = new Sort(Sort.Direction.DESC, "lastModifyTime");
		Specification<SspAdDayReport> specification = getWhereClause(pageCriteria);
		Page<SspAdDayReport> all = sspAdDayReportRepository.findAll(specification,
				new PageRequest(pageCriteria.getCurrentPageNo()-1, pageCriteria.getPageSize(), sort));
		return new Pagination<SspAdDayReport>(all.getSize(), pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), all.getContent().toArray(new SspAdDayReport[] {}));
	}

	/**
	 * 动态生成where语句
	 * 
	 * @param pageCriteria
	 * @return
	 */
	private Specification<SspAdDayReport> getWhereClause(final PageCriteria pageCriteria) {
		return new Specification<SspAdDayReport>() {
			@Override
			public Predicate toPredicate(Root<SspAdDayReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
				List<Predicate> predicate = new ArrayList<>();
				if (searchExpressionList != null && searchExpressionList.size() != 0) {
					for (int i = 0; i < searchExpressionList.size(); i++) {
						String value = String.valueOf(searchExpressionList.get(i).getValue());
						String propertyName = searchExpressionList.get(i).getPropertyName();
						if (StringUtils.isNotBlank(value)) {
							if ("day".equals(propertyName)) {
								// hour 参数为时间范围 beginTime/endTime
								// 需解析为俩个yyyy-mm-dd类型
								// TODO 解析时间段
								String[] times = value.split("/");
								if (times.length == 2) {
									Date beginTime = DateUtil.parseWithYYYYMMDD(times[0]);
									Date endTime = DateUtil.parseWithYYYYMMDD(times[1]);
									String beginTimeStr = String.valueOf(beginTime);
									String endTimeStr = String.valueOf(endTime);
									predicate.add(cb.between(root.get(propertyName),beginTimeStr,endTimeStr));
								}
							} else {
								predicate.add(cb.equal(root.get(propertyName), value));
							}
						}
					}
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}


}
