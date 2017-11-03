package com.unioncast.db.rdbms.core.dao.adx.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.unioncast.common.adx.model.AdxSspFinanceSetting;
import com.unioncast.common.page.OrderExpression;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.page.SearchExpression;
import com.unioncast.common.user.model.User;
import com.unioncast.db.rdbms.common.dao.AdxGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.adx.AdxSspFinanceSettingDao;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

@Repository("adxSspFinanceSettingDao")
public class AdxSspFinanceSettingDaoImpl extends AdxGeneralDao<AdxSspFinanceSetting, Long>
		implements AdxSspFinanceSettingDao {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	// 关于查询
	public static String FIND_ALL = SqlBuild.select(AdxSspFinanceSetting.TABLE_NAME, AdxSspFinanceSetting.PROPERTIES);
	public static final String COUNT_ALL = SqlBuild.countAll(AdxSspFinanceSetting.TABLE_NAME);

	@Override
	public AdxSspFinanceSetting[] find(Long id) throws DaoException {
		List<AdxSspFinanceSetting> list = new ArrayList<>();
		if (id != null) {
			list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new EntityRowMapper(), id));
			return list.toArray(new AdxSspFinanceSetting[] {});
		}
		list = jdbcTemplate.query(FIND_ALL, new EntityRowMapper());
		return list.toArray(new AdxSspFinanceSetting[] {});
	}

	@Override
	public Pagination<AdxSspFinanceSetting> page(PageCriteria pageCriteria) {
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
		Pagination<AdxSspFinanceSetting> pagination = new Pagination<AdxSspFinanceSetting>(totalCount,
				pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(), null);
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

		List<AdxSspFinanceSetting> settings = jdbcTemplate.query(stringBuilder.toString(), new EntityRowMapper());
		pagination.setDataArray(settings.toArray(new AdxSspFinanceSetting[] {}));

		return pagination;
	}

	// 关于增加
	private static String BATCH_ADD = "insert into adx_ssp_finance_setting(account_type,certification_status,name,bank_name,bank_account,document_type,identification_number,certificates_file,business_license,tax_reg_certificate,org_ins_certificates,user_id,remarks,update_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public Long save(AdxSspFinanceSetting entity) throws DaoException {
		return insertAndReturnId(entity);
	}

	@Override
	public Long[] batchAdd(AdxSspFinanceSetting[] entitys) {
		List<Long> ids = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(BATCH_ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			for (AdxSspFinanceSetting adxSspFinanceSetting : entitys) {
				if (adxSspFinanceSetting.getAccountType() != null) {
					pstmt.setInt(1, adxSspFinanceSetting.getAccountType());
				} else {
					pstmt.setNull(1, Types.INTEGER);
				}
				if (adxSspFinanceSetting.getCertificationStatus() != null) {
					pstmt.setInt(2, adxSspFinanceSetting.getCertificationStatus());
				} else {
					pstmt.setNull(2, Types.INTEGER);
				}
				pstmt.setString(3, adxSspFinanceSetting.getName());
				pstmt.setString(4, adxSspFinanceSetting.getBankName());
				pstmt.setString(5, adxSspFinanceSetting.getBankAccount());
				if (adxSspFinanceSetting.getDocumentType() != null) {
					pstmt.setInt(6, adxSspFinanceSetting.getDocumentType());
				} else {
					pstmt.setNull(6, Types.INTEGER);
				}
				pstmt.setString(7, adxSspFinanceSetting.getIdentificationNumber());
				pstmt.setString(8, adxSspFinanceSetting.getCertificatesFile());
				pstmt.setString(9, adxSspFinanceSetting.getBusinessLicense());
				pstmt.setString(10, adxSspFinanceSetting.getTaxRegCertificate());
				pstmt.setString(11, adxSspFinanceSetting.getOrgInsCertificates());
				if (adxSspFinanceSetting.getUser() != null) {
					pstmt.setLong(12, adxSspFinanceSetting.getUser().getId());
				} else {
					pstmt.setNull(12, Types.NULL);
				}
				pstmt.setString(13, adxSspFinanceSetting.getRemarks());
				if (adxSspFinanceSetting.getUpdateTime() != null) {
					pstmt.setTimestamp(14, new Timestamp(adxSspFinanceSetting.getUpdateTime().getTime()));
				} else {
					pstmt.setNull(14, Types.TIMESTAMP);
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			ResultSet rs = pstmt.getGeneratedKeys();
			ids = new ArrayList<>();
			while (rs.next()) {
				ids.add(rs.getLong(1));
			}
			connection.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids.toArray(new Long[ids.size()]);
	}

	// 关于更新
	public static String UPDATE_NOT_NULL_FIELD = SqlBuild.updateNotNullField(AdxSspFinanceSetting.TABLE_NAME);

	@Override
	public void updateNotNullField(AdxSspFinanceSetting entity) throws DaoException {
		StringBuffer stringBuffer = new StringBuffer(UPDATE_NOT_NULL_FIELD);
		List<Object> args = new ArrayList<>();
		entity.setUpdateTime(new Date());
		args.add(new Date());
		if (entity.getAccountType() != null) {
			stringBuffer.append(",account_type = ?");
			args.add(entity.getAccountType());
		}
		if (entity.getCertificationStatus() != null) {
			stringBuffer.append(",certification_status = ?");
			args.add(entity.getCertificationStatus());
		}
		if (entity.getName() != null) {
			stringBuffer.append(",name = ?");
			args.add(entity.getName());
		}
		if (entity.getBankName() != null) {
			stringBuffer.append(",bank_name = ?");
			args.add(entity.getName());
		}
		if (entity.getBankAccount() != null) {
			stringBuffer.append(",bank_account = ?");
			args.add(entity.getName());
		}
		if (entity.getDocumentType() != null) {
			stringBuffer.append(",document_type = ?");
			args.add(entity.getDocumentType());
		}
		if (entity.getIdentificationNumber() != null) {
			stringBuffer.append(",identification_number = ?");
			args.add(entity.getIdentificationNumber());
		}
		if (entity.getCertificatesFile() != null) {
			stringBuffer.append(",certificates_file = ?");
			args.add(entity.getCertificatesFile());
		}
		if (entity.getBusinessLicense() != null) {
			stringBuffer.append(",business_license = ?");
			args.add(entity.getBusinessLicense());
		}
		if (entity.getTaxRegCertificate() != null) {
			stringBuffer.append(",tax_reg_certificate = ?");
			args.add(entity.getTaxRegCertificate());
		}
		if (entity.getOrgInsCertificates() != null) {
			stringBuffer.append(",org_ins_certificates = ?");
			args.add(entity.getOrgInsCertificates());
		}
		if (entity.getUser() != null) {
			stringBuffer.append(",user_id = ?");
			args.add(entity.getUser().getId());
		}
		if (entity.getRemarks() != null) {
			stringBuffer.append(",remarks = ?");
			args.add(entity.getRemarks());
		}
		stringBuffer.append(" where id = ?");
		args.add(entity.getId());
		jdbcTemplate.update(stringBuffer.toString(), args.toArray());
	}

	// 关于删除
	public static String DELETE_BY_ID = SqlBuild.delete(AdxSspFinanceSetting.TABLE_NAME);
	public static String BATCH_DELETE = SqlBuild.delete(AdxSspFinanceSetting.TABLE_NAME);

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

	private Long insertAndReturnId(AdxSspFinanceSetting adxSspFinanceSetting) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AdxSspFinanceSetting.TABLE_NAME);
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("account_type",
				adxSspFinanceSetting.getAccountType() == null ? null : adxSspFinanceSetting.getAccountType());
		args.put("certification_status", adxSspFinanceSetting.getCertificationStatus() == null ? null
				: adxSspFinanceSetting.getCertificationStatus());
		args.put("name", adxSspFinanceSetting.getName() == null ? null : adxSspFinanceSetting.getName());
		args.put("bank_name", adxSspFinanceSetting.getBankName() == null ? null : adxSspFinanceSetting.getBankName());
		args.put("bank_account",
				adxSspFinanceSetting.getBankAccount() == null ? null : adxSspFinanceSetting.getBankAccount());
		args.put("document_type",
				adxSspFinanceSetting.getDocumentType() == null ? null : adxSspFinanceSetting.getDocumentType());
		args.put("identification_number", adxSspFinanceSetting.getIdentificationNumber() == null ? null
				: adxSspFinanceSetting.getIdentificationNumber());
		args.put("certificates_file",
				adxSspFinanceSetting.getCertificatesFile() == null ? null : adxSspFinanceSetting.getCertificatesFile());
		args.put("business_license",
				adxSspFinanceSetting.getBusinessLicense() == null ? null : adxSspFinanceSetting.getBusinessLicense());
		args.put("tax_reg_certificate", adxSspFinanceSetting.getTaxRegCertificate() == null ? null
				: adxSspFinanceSetting.getTaxRegCertificate());
		args.put("org_ins_certificates", adxSspFinanceSetting.getOrgInsCertificates() == null ? null
				: adxSspFinanceSetting.getOrgInsCertificates());
		args.put("user_id", adxSspFinanceSetting.getUser() == null ? null : adxSspFinanceSetting.getUser().getId());
		args.put("remarks", adxSspFinanceSetting.getRemarks() == null ? null : adxSspFinanceSetting.getRemarks());
		args.put("update_time", new Date());
		long id = jdbcInsert.executeAndReturnKey(args).longValue();
		return id;
	}

	private final class EntityRowMapper implements RowMapper<AdxSspFinanceSetting> {
		public AdxSspFinanceSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdxSspFinanceSetting adxSspMedia = new AdxSspFinanceSetting();
			adxSspMedia.setId(rs.getLong("id"));
			adxSspMedia.setAccountType(rs.getInt("account_type"));
			adxSspMedia.setCertificationStatus(rs.getInt("certification_status"));
			adxSspMedia.setName(rs.getString("name"));
			adxSspMedia.setBankName(rs.getString("bank_name"));
			adxSspMedia.setBankAccount(rs.getString("bank_account"));
			adxSspMedia.setDocumentType(rs.getInt("document_type"));
			adxSspMedia.setIdentificationNumber(rs.getString("identification_number"));
			adxSspMedia.setCertificatesFile(rs.getString("certificates_file"));
			adxSspMedia.setBusinessLicense(rs.getString("business_license"));
			adxSspMedia.setTaxRegCertificate(rs.getString("tax_reg_certificate"));
			adxSspMedia.setOrgInsCertificates(rs.getString("org_ins_certificates"));
			User user = null;
			try {
				user = userDao.findById(rs.getLong("user_id"));
			} catch (DaoException e) {
				e.printStackTrace();
			}
			adxSspMedia.setUser(user);
			adxSspMedia.setRemarks(rs.getString("remarks"));
			adxSspMedia.setUpdateTime(rs.getTimestamp("update_time"));
			return adxSspMedia;
		}
	}

}
