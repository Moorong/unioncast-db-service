package com.unioncast.db.rdbms.core.dao.common.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.user.model.Qualification;
import com.unioncast.common.user.model.User;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.core.dao.common.QualificationDao;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("qualificationDao")
public class QualificationDaoImpl extends CommonGeneralDao<Qualification, Long> implements QualificationDao {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	// 关于查询
	private static String FIND_ALL = "select id,user_id,name,fileType,fileValidateCode,url,scope,startTime,endTime,update_time from common_qualification";
	private static final String COUNT_ALL = "select count(*) from common_qualification";

	@Override
	public Qualification[] find(Long id) throws DaoException {
		List<Qualification> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new QualificationRowMapper(), id));
			return list.toArray(new Qualification[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new QualificationRowMapper());
		return list.toArray(new Qualification[] {});
	}

	@Override
	public Pagination<Qualification> page(PageCriteria pageCriteria) {
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
						stringSearch.append(searchExpressionList.get(i).getPropertyName() + " "
								+ searchExpressionList.get(i).getOperation().getOperator() + " '%"
								+ searchExpressionList.get(i).getValue() + "%' " + predicate + " ");
					} else {
						stringSearch.append(searchExpressionList.get(i).getPropertyName() + " "
								+ searchExpressionList.get(i).getOperation().getOperator() + " '"
								+ searchExpressionList.get(i).getValue() + "' " + predicate + " ");
					}
				}
				if ("like".equalsIgnoreCase(
						searchExpressionList.get(searchExpressionList.size() - 1).getOperation().getOperator())) {
					stringSearch
							.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " "
									+ searchExpressionList.get(searchExpressionList.size() - 1).getOperation()
											.getOperator()
									+ " '%" + searchExpressionList.get(searchExpressionList.size() - 1).getValue()
									+ "%' ");

				} else {
					stringSearch
							.append(searchExpressionList.get(searchExpressionList.size() - 1).getPropertyName() + " "
									+ searchExpressionList.get(searchExpressionList.size() - 1).getOperation()
											.getOperator()
									+ " '" + searchExpressionList.get(searchExpressionList.size() - 1).getValue()
									+ "' ");
				}
			} else if (searchExpressionList.get(0) != null) {
				if ("like".equalsIgnoreCase(searchExpressionList.get(0).getOperation().getOperator())) {
					stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " "
							+ searchExpressionList.get(0).getOperation().getOperator() + " '%"
							+ searchExpressionList.get(0).getValue() + "%' ");
				} else {
					stringSearch.append(" where " + searchExpressionList.get(0).getPropertyName() + " "
							+ searchExpressionList.get(0).getOperation().getOperator() + " '"
							+ searchExpressionList.get(0).getValue() + "' ");
				}
			}
		}
		int totalCount = jdbcTemplate.queryForObject(COUNT_ALL + " " + stringSearch.toString(), int.class);
		Pagination<Qualification> pagination = new Pagination<Qualification>(totalCount, pageCriteria.getPageSize(),
				pageCriteria.getCurrentPageNo(), null);
		Integer currentPageNo = pagination.getCurrentPageNo();
		Integer pageSize = pagination.getPageSize();
		stringBuilder.append(stringSearch);
		if (orderExpressionList != null) {
			stringBuilder.append(" ORDER BY ");
			for (int i = 0; i < orderExpressionList.size() - 1; i++) {
				stringBuilder.append(
						orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp() + ", ");
			}
			stringBuilder.append(orderExpressionList.get(orderExpressionList.size() - 1).getPropertyName() + " "
					+ orderExpressionList.get(orderExpressionList.size() - 1).getOp());
		}
		if (currentPageNo != null && pageSize != null) {
			Integer start = (currentPageNo - 1) * pageSize;
			stringBuilder.append(" limit " + start + "," + pageSize);
		}
		List<Qualification> qualifications = jdbcTemplate.query(stringBuilder.toString(), new QualificationRowMapper());
		pagination.setDataArray(qualifications.toArray(new Qualification[] {}));

		return pagination;
	}

	@Override
	public Qualification[] findByUserId(Long id) {
		List<Qualification> list = new ArrayList<>();
		list.add(jdbcTemplate.queryForObject(FIND_ALL + " where user_id = ?", new QualificationRowMapper(), id));
		return list.toArray(new Qualification[] {});
	}

	// 关于增加
	public static String BATCH_ADD = "insert into common_qualification(user_id,name,fileType,fileValidateCode,url,scope,startTime,endTime,update_time) values(?,?,?,?,?,?,?,?,?)";

	@Override
	public Long[] batchAdd(Qualification[] entitys) {
		List<Long> list = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (Qualification qualification : entitys) {
				if (qualification.getUser() != null) {
					pstmt.setLong(1, qualification.getUser().getId());
				} else {
					pstmt.setNull(1, Types.NULL);
				}
				pstmt.setString(2, qualification.getName());
				if (qualification.getFiletype() != null) {
					pstmt.setInt(3, qualification.getFiletype());
				} else {
					pstmt.setNull(3, Types.INTEGER);
				}
				pstmt.setString(4, qualification.getFilevalidatecode());
				pstmt.setString(5, qualification.getUrl());
				pstmt.setString(6, qualification.getScope());
				if (qualification.getStarttime() != null) {
					pstmt.setTimestamp(7, new Timestamp(qualification.getStarttime().getTime()));
				} else {
					pstmt.setNull(7, Types.TIMESTAMP);
				}
				if (qualification.getEndtime() != null) {
					pstmt.setTimestamp(8, new Timestamp(qualification.getEndtime().getTime()));
				} else {
					pstmt.setNull(8, Types.TIMESTAMP);
				}
				if (qualification.getUpdatetime() != null) {
					pstmt.setTimestamp(9, new Timestamp(qualification.getUpdatetime().getTime()));
				} else {
					pstmt.setNull(9, Types.TIMESTAMP);
				}
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

	// 关于更新
	public static String UPDATE_NOT_NULL_FIELD = "update common_qualification set update_time = ?";

	@Override
	public void updateNotNullField(Qualification entity) throws DaoException {
		StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
		List<Object> args = new ArrayList<>();
		entity.setUpdatetime(new Date());
		args.add(new Date());
		if (entity.getUser() != null && entity.getUser().getId() != null) {
			stringBuffer.append(",user_id = ?");
			args.add(entity.getUser().getId());
		}
		if (entity.getName() != null) {
			stringBuffer.append(",name = ?");
			args.add(entity.getName());
		}
		if (entity.getFiletype() != null) {
			stringBuffer.append(",fileType = ?");
			args.add(entity.getFiletype());
		}
		if (entity.getFilevalidatecode() != null) {
			stringBuffer.append(",fileValidateCode = ?");
			args.add(entity.getFilevalidatecode());
		}
		if (entity.getUrl() != null) {
			stringBuffer.append(",url = ?");
			args.add(entity.getUrl());
		}
		if (entity.getScope() != null) {
			stringBuffer.append(",scope = ?");
			args.add(entity.getScope());
		}
		if (entity.getStarttime() != null) {
			stringBuffer.append(",startTime = ?");
			args.add(entity.getStarttime());
		}
		if (entity.getEndtime() != null) {
			stringBuffer.append(",endTime = ?");
			args.add(entity.getEndtime());
		}
		stringBuffer.append(" where id = ?");
		args.add(entity.getId());
		jdbcTemplate.update(stringBuffer.toString(), args.toArray());
	}

	// 关于删除
	public static String DELETE_BY_ID = "delete from common_qualification where id = ?";
	public static String BATCH_DELETE = "delete from common_qualification where id = ?";

	@Override
	public int deleteById(Long id) throws DaoException {
		return jdbcTemplate.update(DELETE_BY_ID, id);
	}

	@Override
	public int batchDelete(Long[] ids) {
		List<Object[]> batchArgs = new ArrayList<>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}
		int[] intArray = jdbcTemplate.batchUpdate(BATCH_DELETE, batchArgs);
		int j = 0;
		for (int i : intArray) {
			j += i;
		}
		return j;
	}

	private final class QualificationRowMapper implements RowMapper<Qualification> {
		public Qualification mapRow(ResultSet rs, int rowNum) throws SQLException {
			Qualification qualification = new Qualification();
			qualification.setId(rs.getLong("id"));
			User user = null;
			try {
				user = userDao.findById(rs.getLong("user_id"));
			} catch (DaoException e) {
				e.printStackTrace();
			}
			qualification.setUser(user);
			qualification.setName(rs.getString("name"));
			qualification.setFiletype(rs.getInt("fileType"));
			qualification.setFilevalidatecode(rs.getString("fileValidateCode"));
			qualification.setUrl(rs.getString("url"));
			qualification.setScope(rs.getString("scope"));
			qualification.setStarttime(rs.getTimestamp("startTime"));
			qualification.setEndtime(rs.getTimestamp("endTime"));
			qualification.setUpdatetime(rs.getTimestamp("update_time"));
			return qualification;
		}
	}

}
