package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.ssp.model.*;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.*;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("sspCreativeReportDao")
public class SspCreativeReportDaoImpl extends SspGeneralDao<SspCreativeReport, Long> implements SspCreativeReportDao {


    private static String FIND_ALL = SqlBuild.select(SspCreativeReport.TABLE_NAME, SspCreativeReport.PROPERTIES);
    private static String COUNT_ALL = SqlBuild.countAll(SspCreativeReport.TABLE_NAME);


    @Autowired
    SspAdvertiserDao sspAdvertiserDao;

    @Autowired
    SspOrderDao sspOrderDao;

    @Autowired
    SspPlanDao sspPlanDao;

    @Autowired
    SspCreativeDao sspCreativeDao;


    public SspAdvertiser findAdvertiserById(Long id) {
        SspAdvertiser sspAdvertiser = new SspAdvertiser();
        sspAdvertiser.setId(id);
        try {
            SspAdvertiser[] t = sspAdvertiserDao.findT(sspAdvertiser);
            if (t != null && t.length != 0) {
                return t[0];
            }
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SspOrder findOrderById(Long id) {
        SspOrder sspOrder = new SspOrder();
        sspOrder.setId(id);
        try {
            SspOrder[] t = sspOrderDao.findT(sspOrder);
            if (t != null && t.length != 0) {
                return t[0];
            }
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SspPlan findPlanById(Long id) {
        SspPlan[] sspPlans = null;
        try {
            sspPlans = sspPlanDao.find(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (sspPlans != null && sspPlans.length != 0) {
            return sspPlans[0];
        }
        return null;
    }

    public SspCreative findCreativeById(Long id) {
        SspCreative sspCreative = new SspCreative();
        sspCreative.setId(id);
        try {
            SspCreative[] t = sspCreativeDao.findT(sspCreative);
            if (t != null && t.length != 0) {
                return t[0];
            }
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SspCreativeReport[] findT(SspCreativeReport s) throws DaoException, IllegalAccessException {
        List<SspCreativeReport> list = new ArrayList<>();
        if (s != null) {
            SspCreativeReport[] sspCreativeReports = find(s, new SspCreativeReportRowMapper(), SspCreativeReport.class);
            return sspCreativeReports;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspCreativeReportRowMapper());
        }
        return list.toArray(new SspCreativeReport[]{});
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspCreativeReport.class, ids);
    }

    /*@Override
    public Pagination<SspCreativeReport> page(PageCriteria pageCriteria) throws DaoException {
        pageCriteria.setEntityClass(SspCreativeReport.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspCreativeReportRowMapper());

    }*/

    @Override

    public Pagination<SspCreativeReport> page(PageCriteria pageCriteria) throws DaoException {
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
        StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1 ");
        StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 ");
        if (searchExpressionList != null && searchExpressionList.size() != 0) {
            for (int i = 0; i < searchExpressionList.size(); i++) {
                String value = searchExpressionList.get(i).getValue().toString();
//				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) value = "'%" + value + "%'";
                if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
                    value = " '%" + value + "%' ";
                } else {
                    value = " '" + value + "' ";
                }
                String criteriaSql = " " + pageCriteria.getPredicate() + " "
                        + searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator()
                        + " " + value;
                pageSql.append(criteriaSql);
                countAll.append(criteriaSql);
            }
        }
        List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
        if (orderExpressionList != null && orderExpressionList.size() != 0) {
            pageSql.append(" order by");
            for (int i = 0; i < orderExpressionList.size(); i++) {
                if (i > 0) pageSql.append(",");
                pageSql.append(
                        " " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
            }
        }


        pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
                + pageCriteria.getPageSize());
        List<SspCreativeReport> list = jdbcTemplate.query(pageSql.toString(), new SspCreativeReportRowMapper());
        int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
        return new Pagination<SspCreativeReport>(totalCount, pageCriteria.getPageSize(),
                pageCriteria.getCurrentPageNo(), list.toArray(new SspCreativeReport[]{}));
    }

    public final class SspCreativeReportRowMapper implements RowMapper<SspCreativeReport> {

        @Override
        public SspCreativeReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SspCreativeReport(rs.getLong("id"), rs.getDate("start_time"), rs.getDate("end_time"),
                    findAdvertiserById(rs.getLong("advertiser_id")), findOrderById(rs.getLong("order_id")),
                    findPlanById(rs.getLong("plan_id")), findCreativeById(rs.getLong("creative_id")), rs.getInt("show_times"),
                    rs.getDouble("consumption"), rs.getDouble("cpm"), rs.getInt("click_times"), rs.getDouble("click_ratio"),
                    rs.getDouble("cpc"), rs.getLong("arrival"), rs.getDouble("transform"), rs.getLong("cpa"), rs.getLong("second_jump"),
                    rs.getDouble("second_jump_ratio"), rs.getDouble("arrival_ratio"), rs.getLong("click_transform"),
                    rs.getDouble("click_transform_ratio"), rs.getDate("update_time"));
        }

    }

}
