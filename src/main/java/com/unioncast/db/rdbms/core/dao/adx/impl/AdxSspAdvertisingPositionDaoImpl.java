package com.unioncast.db.rdbms.core.dao.adx.impl;

import com.unioncast.common.adx.model.AdxSspAdvertisingPosition;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspAdvertisingPositionDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("adxSspAdvertisingPositionDao")
public class AdxSspAdvertisingPositionDaoImpl extends AdxGeneralDao<AdxSspAdvertisingPosition, Long>
        implements AdxSspAdvertisingPositionDao {

    public static final String UPDATE_NOT_NULL_FIELD = SqlBuild
            .updateNotNullFieldSet(AdxSspAdvertisingPosition.TABLE_NAME);
    public static final String DELETE_BY_ID = SqlBuild.delete(AdxSspAdvertisingPosition.TABLE_NAME);
    public static final String FIND_ALL = SqlBuild.select(AdxSspAdvertisingPosition.TABLE_NAME,
            AdxSspAdvertisingPosition.PROPERTIES);
    public static final String BATCH_DELETE = SqlBuild.delete(AdxSspAdvertisingPosition.TABLE_NAME);
    public static final String BATCH_ADD = "insert into " + AdxSspAdvertisingPosition.TABLE_NAME + "(" + AdxSspAdvertisingPosition.PROPERTIES + ") values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String COUNT_ALL = SqlBuild.countAll(AdxSspAdvertisingPosition.TABLE_NAME);


    public Pagination<AdxSspAdvertisingPosition> page(PageCriteria pageCriteria) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringSearch = new StringBuilder();
        List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
        List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();


        stringBuilder.append(FIND_ALL);
        if (searchExpressionList != null && pageCriteria.getPredicate() != null) {
            String predicate = pageCriteria.getPredicate().getOperator();
            if (searchExpressionList.size() >= 1 && predicate != null) {
                stringSearch.append(" where ");
                for (int i = 0; i < searchExpressionList.size() - 1; i++) {
                    if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator())) {
                        stringSearch.append(searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator() + " '%" + searchExpressionList.get(i).getValue() + "%' " + predicate + " ");
                    } else {
                        stringSearch.append(searchExpressionList.get(i).getPropertyName() + " " + searchExpressionList.get(i).getOperation().getOperator() + " '" + searchExpressionList.get(i).getValue() + "' " + predicate + " ");
                    }
                }
                if ("like".equalsIgnoreCase(searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator())) {
                    stringSearch.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " " + searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator() + " '%" + searchExpressionList.get(searchExpressionList.size() - 1).getValue() + "%' ");

                } else {

                    stringSearch.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " " + searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator() + " '" + searchExpressionList.get(searchExpressionList.size() - 1).getValue() + "' ");
                }
            } else if (searchExpressionList.get(0) != null) {
                if ("like".equalsIgnoreCase(searchExpressionList.get(0).getOperation().getOperator())) {
                    stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " " + searchExpressionList.get(0).getOperation().getOperator() + " '%" + searchExpressionList.get(0).getValue() + "%' ");
                } else {
                    stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " " + searchExpressionList.get(0).getOperation().getOperator() + " '" + searchExpressionList.get(0).getValue() + "' ");
                }
            }
        }

        int totalCount = jdbcTemplate.queryForObject(COUNT_ALL + " " + stringSearch.toString(), int.class);
        Pagination<AdxSspAdvertisingPosition> pagination = new Pagination<AdxSspAdvertisingPosition>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
        Integer currentPageNo = pagination.getCurrentPageNo();
        Integer pageSize = pagination.getPageSize();
        stringBuilder.append(stringSearch);

        if (orderExpressionList != null) {
            stringBuilder.append(" ORDER BY ");
            for (int i = 0; i < orderExpressionList.size() - 1; i++) {
                stringBuilder.append(orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp() + ", ");
            }
            stringBuilder.append(orderExpressionList.get(orderExpressionList.size() - 1).getPropertyName() + " " + orderExpressionList.get(orderExpressionList.size() - 1).getOp());
        }

        if (currentPageNo != null && pageSize != null) {
            Integer start = (currentPageNo - 1) * pageSize;
            stringBuilder.append(" limit " + start + "," + pageSize);
        }

        List<AdxSspAdvertisingPosition> users = jdbcTemplate.query(stringBuilder.toString(), new AdxSspAdvertisingPositionRowMapper());
        pagination.setDataArray(users.toArray(new AdxSspAdvertisingPosition[]{}));

        return pagination;
    }




    /**
     * 查找
     */
    @Override
    public AdxSspAdvertisingPosition[] find(Long id) throws DaoException {
        if (id == null) {
            List<AdxSspAdvertisingPosition> list = jdbcTemplate.query(FIND_ALL, new AdxSspAdvertisingPositionRowMapper());
            return list.toArray(new AdxSspAdvertisingPosition[list.size()]);

        }
        AdxSspAdvertisingPosition adxSspAdvertisingPosition = jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new AdxSspAdvertisingPositionRowMapper(), id);
        AdxSspAdvertisingPosition[] adxSspAdvertisingPositions = new AdxSspAdvertisingPosition[]{adxSspAdvertisingPosition};
        return adxSspAdvertisingPositions;
    }


    @Override
    public Long[] batchAdd(AdxSspAdvertisingPosition[] entitys) throws DaoException {
        List<Long> list = null;
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
            for (AdxSspAdvertisingPosition adxSspAdvertisingPosition : entitys) {
                pstmt.setString(1, adxSspAdvertisingPosition.getAdName());
                pstmt.setLong(2, adxSspAdvertisingPosition.getApplicationIndustry());
                if (adxSspAdvertisingPosition.getAdType() != null)
                    pstmt.setInt(3, adxSspAdvertisingPosition.getAdType());
                else
                    pstmt.setNull(3, Types.INTEGER);
                pstmt.setInt(4, adxSspAdvertisingPosition.getAdPosition());
                if (adxSspAdvertisingPosition.getAuditStatus() != null)
                    pstmt.setInt(5, adxSspAdvertisingPosition.getAuditStatus());
                else
                    pstmt.setNull(5, Types.INTEGER);
                if (adxSspAdvertisingPosition.getAdForm() != null)
                    pstmt.setInt(6, adxSspAdvertisingPosition.getAdForm());
                else
                    pstmt.setNull(6, Types.INTEGER);
                if (adxSspAdvertisingPosition.getOwnedMedia() != null)
                    pstmt.setInt(7, adxSspAdvertisingPosition.getOwnedMedia());
                else
                    pstmt.setNull(7, Types.INTEGER);
                pstmt.setString(8, adxSspAdvertisingPosition.getDescription());
                pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                if (adxSspAdvertisingPosition.getLoadMode() != null)
                    pstmt.setInt(10, adxSspAdvertisingPosition.getLoadMode());
                else
                    pstmt.setNull(10, Types.INTEGER);
                if (adxSspAdvertisingPosition.getFloorPrice() != null)
                    pstmt.setInt(11, adxSspAdvertisingPosition.getFloorPrice());
                else
                    pstmt.setNull(11, Types.INTEGER);
                pstmt.setString(12, adxSspAdvertisingPosition.getRemarks());
                pstmt.setString(13, adxSspAdvertisingPosition.getAppOrWebId());
                pstmt.setString(14, adxSspAdvertisingPosition.getSlotId());
                if (adxSspAdvertisingPosition.getWidth() != null)
                    pstmt.setInt(15, adxSspAdvertisingPosition.getWidth());
                else
                    pstmt.setNull(15, Types.INTEGER);
                if (adxSspAdvertisingPosition.getHeight() != null)
                    pstmt.setInt(16, adxSspAdvertisingPosition.getHeight());
                else
                    pstmt.setNull(16, Types.INTEGER);
                pstmt.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            connection.commit();
            ResultSet rs = pstmt.getGeneratedKeys();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getLong(1));
            }
            connection.close();
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new Long[list.size()]);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        List<Object[]> batchArgs = new ArrayList<>();
        for (Long id : ids) {
            Object[] objects = new Object[]{id};
            batchArgs.add(objects);
        }
        int[] intArray = jdbcTemplate.batchUpdate(BATCH_DELETE, batchArgs);
        int j = 0;
        for (int i : intArray) {
            j += i;
        }
        return j;
    }


    @Override
    public AdxSspAdvertisingPosition findById(Long id) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(FIND_ALL);
        stringBuffer.append(" where id = ?");
        return (AdxSspAdvertisingPosition) jdbcTemplate.queryForObject(stringBuffer.toString(),
                new AdxSspAdvertisingPositionRowMapper(), id);
    }

    @Override
    public void updateNotNullField(AdxSspAdvertisingPosition entity) throws DaoException {
        StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
        List<Object> args = new ArrayList<>();
        entity.setUpdateTime(new Date());
        args.add(new Date());
        if (entity.getAdName() != null) {
            stringBuffer.append(",ad_name = ?");
            args.add(entity.getAdName());
        }
        if (entity.getApplicationIndustry() != null) {
            stringBuffer.append(",application_industry = ?");
            args.add(entity.getApplicationIndustry());
        }
        if (entity.getAdType() != null) {
            stringBuffer.append(",ad_type = ?");
            args.add(entity.getAdType());
        }
        if (entity.getAdPosition() != null) {
            stringBuffer.append(",ad_position = ?");
            args.add(entity.getAdPosition());
        }
        if (entity.getAuditStatus() != null) {
            stringBuffer.append(",audit_status = ?");
            args.add(entity.getAuditStatus());
        }
        if (entity.getAdForm() != null) {
            stringBuffer.append(",ad_form = ?");
            args.add(entity.getAdForm());
        }
        if (entity.getOwnedMedia() != null) {
            stringBuffer.append(",owned_media = ?");
            args.add(entity.getOwnedMedia());
        }
        if (entity.getDescription() != null) {
            stringBuffer.append(",description = ?");
            args.add(entity.getDescription());
        }
        if (entity.getCreateTime() != null) {
            stringBuffer.append(",create_time = ?");
            args.add(entity.getCreateTime());
        }
        if (entity.getLoadMode() != null) {
            stringBuffer.append(",load_mode = ?");
            args.add(entity.getLoadMode());
        }
        if (entity.getFloorPrice() != null) {
            stringBuffer.append(",floor_price = ?");
            args.add(entity.getFloorPrice());
        }
        if (entity.getRemarks() != null) {
            stringBuffer.append(",remarks = ?");
            args.add(entity.getRemarks());
        }
        if (entity.getAppOrWebId() != null) {
            stringBuffer.append(",app_or_web_id = ?");
            args.add(entity.getAppOrWebId());
        }
        if (entity.getSlotId() != null) {
            stringBuffer.append(",slot_id = ?");
            args.add(entity.getSlotId());
        }
        if (entity.getWidth() != null) {
            stringBuffer.append(",width = ?");
            args.add(entity.getWidth());
        }
        if (entity.getHeight() != null) {
            stringBuffer.append(",height = ?");
            args.add(entity.getHeight());
        }
        stringBuffer.append(" where id = ?");
        args.add(entity.getId());
        jdbcTemplate.update(stringBuffer.toString(), args.toArray());
    }

    @Override
    public Long save(AdxSspAdvertisingPosition entity) throws DaoException {
        return insertAndReturnId(entity);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private Long insertAndReturnId(AdxSspAdvertisingPosition adxSspAdvertisingPosition) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AdxSspAdvertisingPosition.TABLE_NAME);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("ad_name",
                adxSspAdvertisingPosition.getAdName() == null ? null : adxSspAdvertisingPosition.getAdName());
        args.put("application_industry", adxSspAdvertisingPosition.getApplicationIndustry() == null ? null
                : adxSspAdvertisingPosition.getApplicationIndustry());
        args.put("ad_type",
                adxSspAdvertisingPosition.getAdType() == null ? null : adxSspAdvertisingPosition.getAdType());
        args.put("ad_position",
                adxSspAdvertisingPosition.getAdPosition() == null ? null : adxSspAdvertisingPosition.getAdPosition());
        args.put("audit_status",
                adxSspAdvertisingPosition.getAuditStatus() == null ? null : adxSspAdvertisingPosition.getAuditStatus());
        args.put("ad_form",
                adxSspAdvertisingPosition.getAdForm() == null ? null : adxSspAdvertisingPosition.getAdForm());
        args.put("owned_media",
                adxSspAdvertisingPosition.getOwnedMedia() == null ? null : adxSspAdvertisingPosition.getOwnedMedia());
        args.put("description",
                adxSspAdvertisingPosition.getDescription() == null ? null : adxSspAdvertisingPosition.getDescription());
        args.put("create_time",
                adxSspAdvertisingPosition.getCreateTime() == null ? null : adxSspAdvertisingPosition.getCreateTime());
        args.put("load_mode",
                adxSspAdvertisingPosition.getLoadMode() == null ? null : adxSspAdvertisingPosition.getLoadMode());
        args.put("floor_price",
                adxSspAdvertisingPosition.getFloorPrice() == null ? null : adxSspAdvertisingPosition.getFloorPrice());
        args.put("remarks",
                adxSspAdvertisingPosition.getRemarks() == null ? null : adxSspAdvertisingPosition.getRemarks());
        args.put("app_or_web_id",
                adxSspAdvertisingPosition.getAppOrWebId() == null ? null : adxSspAdvertisingPosition.getAppOrWebId());
        args.put("slot_id",
                adxSspAdvertisingPosition.getSlotId() == null ? null : adxSspAdvertisingPosition.getSlotId());
        args.put("width",
                adxSspAdvertisingPosition.getWidth() == null ? null : adxSspAdvertisingPosition.getWidth());
        args.put("height",
                adxSspAdvertisingPosition.getHeight() == null ? null : adxSspAdvertisingPosition.getHeight());
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        return id;
    }

    private final class AdxSspAdvertisingPositionRowMapper implements RowMapper<AdxSspAdvertisingPosition> {
        public AdxSspAdvertisingPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdxSspAdvertisingPosition adxSspAdvertisingPosition = new AdxSspAdvertisingPosition();
            adxSspAdvertisingPosition.setId(rs.getLong("id"));
            adxSspAdvertisingPosition.setAdName(rs.getString("ad_name"));
            adxSspAdvertisingPosition.setApplicationIndustry(rs.getLong("application_industry"));
            adxSspAdvertisingPosition.setAdType(rs.getInt("ad_type"));
            adxSspAdvertisingPosition.setAdPosition(rs.getInt("ad_position"));
            adxSspAdvertisingPosition.setAuditStatus(rs.getInt("audit_status"));
            adxSspAdvertisingPosition.setAdForm(rs.getInt("ad_form"));
            adxSspAdvertisingPosition.setOwnedMedia(rs.getInt("owned_media"));
            adxSspAdvertisingPosition.setDescription(rs.getString("description"));
            adxSspAdvertisingPosition.setCreateTime(rs.getTimestamp("create_time"));
            adxSspAdvertisingPosition.setLoadMode(rs.getInt("load_mode"));
            adxSspAdvertisingPosition.setFloorPrice(rs.getInt("floor_price"));
            adxSspAdvertisingPosition.setRemarks(rs.getString("remarks"));
            adxSspAdvertisingPosition.setAppOrWebId(rs.getString("app_or_web_id"));
            adxSspAdvertisingPosition.setSlotId(rs.getString("slot_id"));
            adxSspAdvertisingPosition.setWidth(rs.getInt("width"));
            adxSspAdvertisingPosition.setHeight(rs.getInt("height"));
            adxSspAdvertisingPosition.setUpdateTime(rs.getTimestamp("update_time"));
            return adxSspAdvertisingPosition;
        }
    }

}
