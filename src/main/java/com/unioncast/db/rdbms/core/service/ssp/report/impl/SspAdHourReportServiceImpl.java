
package com.unioncast.db.rdbms.core.service.ssp.report.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.report.SspAdDayReport;
import com.unioncast.common.ssp.model.report.SspAdHourReport;
import com.unioncast.common.util.DateUtil;
import com.unioncast.db.rdbms.core.dao.ssp.repository.SspAdDayReportRepository;
import com.unioncast.db.rdbms.core.dao.ssp.repository.SspAdHourReportRepository;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.ssp.report.SspAdHourReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class SspAdHourReportServiceImpl implements SspAdHourReportService {

    @Resource
    private SspAdHourReportRepository sspAdHourReportRepository;


    @Override
    public Pagination<SspAdHourReport> page(PageCriteria pageCriteria) throws DaoException {
        Sort sort = new Sort(Sort.Direction.DESC, "lastModifyTime");
        Specification<SspAdHourReport> specification = getWhereClause(pageCriteria);
        Page<SspAdHourReport> all = sspAdHourReportRepository.findAll(specification,
                new PageRequest(pageCriteria.getCurrentPageNo()-1, pageCriteria.getPageSize(),sort));
        return new Pagination<SspAdHourReport>(Integer.valueOf(String.valueOf(all.getTotalElements())), pageCriteria.getPageSize(),
                pageCriteria.getCurrentPageNo(), all.getContent().toArray(new SspAdHourReport[] {}));
    }



    /**
     * 动态生成where语句
     *
     * @param pageCriteria
     * @return
     */
    private Specification<SspAdHourReport> getWhereClause(final PageCriteria pageCriteria) {
        return new Specification<SspAdHourReport>() {
            @Override
            public Predicate toPredicate(Root<SspAdHourReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
                                String[] times = value.split("/");
                                if (times.length == 2) {
                                    Date beginTime = DateUtil.parseWithYYYYMMDDHHMMSS(times[0]);
                                    String beginTimeStr = DateUtil.formatWithYYYYMMDD(beginTime);
                                    Date endTime = DateUtil.parseWithYYYYMMDDHHMMSS(times[1]);
                                    String endTimeStr = DateUtil.formatWithYYYYMMDD(endTime);
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
