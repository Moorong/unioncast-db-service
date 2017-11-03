package com.unioncast.db.rdbms.core.dao.ssp.impl;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.*;
import com.unioncast.common.ssp.model.SspAdvertiser;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspDictLabel;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.UserDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspAdvertiserDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspCreativeDao;
import com.unioncast.db.rdbms.core.dao.ssp.SspDictLabelDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("sspCreativeDao")
public class SspCreativeDaoImpl extends SspGeneralDao<SspCreative, Long> implements SspCreativeDao {

	public final class SspCreativeRowMapper implements RowMapper<SspCreative> {

		@Override
		public SspCreative mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = null;
			SspAdvertiser advertiser = null;
			SspDictLabel[] labels = null;
			try {
				user = findByAcountId(rs.getLong("account_id"));
			} catch (Exception e) {
				throw new SQLException(e);
			}
			try {
				advertiser = findByAdvertiserId(rs.getLong("advertiser_id"));
			} catch (Exception e) {
				throw new SQLException(e);
			}
			
			try {
				labels = findByCreativeLabelIds(rs.getString("creative_label"));
			} catch (Exception e) {
				throw new SQLException(e);
			}
			
			return new SspCreative(rs.getLong("id"), user, advertiser, rs.getInt("creative_type"),
					rs.getString("creative_name"), labels,
					rs.getInt("creative_state"), rs.getString("creative_label_new"), rs.getString("creative_size"),
					rs.getString("pic_name"), rs.getDouble("pic_size"), rs.getString("creative_format"),
					rs.getLong("video_duration"), rs.getString("creative_url"), rs.getString("creative_click_address"),
					rs.getString("creative_monitor_address"), rs.getString("adv_title"), rs.getTimestamp("create_time"),
					rs.getTimestamp("update_time"), rs.getInt("height"), rs.getInt("width"),
					rs.getString("creative_source"),rs.getInt("delete_state"));
		}

	}

	
	@Resource(name="userDao")
	UserDao userDao;
	private final User findByAcountId(Long id) throws DaoException {
		User user = null;
		User[] users =  userDao.find(id);
		if(users != null && users.length != 0){
			user = users[0];
		}
		return user;
	}

	@Resource(name="sspAdvertiserDao")
	SspAdvertiserDao advertiserDao;
	private final SspAdvertiser findByAdvertiserId(Long id) throws IllegalAccessException, DaoException {
		SspAdvertiser advertiser = new SspAdvertiser();
		advertiser.setId(id);
		return advertiserDao.findT(advertiser)[0];
	}

	@Resource
	SspDictLabelDao dictLabelDao;
	private final SspDictLabel[] findByCreativeLabelIds(String ids) throws IllegalAccessException, DaoException {
		if(ids != null) {
			String[] idArr = ids.split(",");
			SspDictLabel[] labels = new SspDictLabel[idArr.length];
			for(int i=0; i<idArr.length; i++){
				SspDictLabel label = new SspDictLabel();
				label.setId(Long.parseLong(idArr[i]));
				label = dictLabelDao.findT(label)[0];
				labels[i] = label;
			}
			return labels;
		}
		return null;
	}

	private static String FIND_ALL = SqlBuild.select(SspCreative.TABLE_NAME, SspCreative.PROPERTIES);
	private static String COUNT_ALL = SqlBuild.countAll(SspCreative.TABLE_NAME);

	@Override
	public SspCreative[] findT(SspCreative s) throws DaoException, IllegalAccessException {
		List<SspCreative> list = new ArrayList<>();
		if (s != null) {
			SspCreative[] sspCreatives = find(s, new SspCreativeRowMapper(), SspCreative.class);
			return sspCreatives;
		} else {
			list = jdbcTemplate.query(FIND_ALL, new SspCreativeRowMapper());
		}
		return list.toArray(new SspCreative[] {});
	}

	@Override
	public int batchDelete(Long[] ids) throws DaoException {
		return delete(SspCreative.class, ids);
	}
	
	@Override
    public Long save(SspCreative entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

        Class<?> clazz = entity.getClass();
        MyTable myTable = clazz.getAnnotation(MyTable.class);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(myTable.value());
        Map<String, Object> args = new HashMap<String, Object>();
        MyColumn myColumn = null;
        MyId myId = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                myId = field.getAnnotation(MyId.class);
                if (myId != null) {
                    jdbcInsert.setGeneratedKeyName(myId.value());
                } else {
                    myColumn = field.getDeclaredAnnotation(MyColumn.class);
                    if (myColumn != null && StringUtils.isNotBlank(myColumn.value())) {
                        if (myColumn.value().equals("account_id")) {
                            args.put("account_id", entity.getUser() == null ? null : entity.getUser().getId());
                        } else if (myColumn.value().equals("advertiser_id")) {
                            args.put("advertiser_id", entity.getUser() == null ? null : entity.getSspAdvertiser().getId());
                        } else if (myColumn.value().equals("creative_label")) {
                        	String labels = null;
                        	for(int i=0; i<entity.getSspDictLabelArr().length; i++) {
                        		if(i == 0) labels = entity.getSspDictLabelArr()[i].getId()+"";
                        		else labels += "," + entity.getSspDictLabelArr()[i].getId();
                        	}
                        	args.put("creative_label", labels);
                        }else {
                            args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                        }
                    }
                }
            }
        }
        return jdbcInsert.executeAndReturnKey(args).longValue();
    }

	@Override
	public Pagination<SspCreative> page(PageCriteria pageCriteria) throws DaoException {
		List<SearchExpression> searchExpressionList = pageCriteria.getSearchExpressionList();
		StringBuilder pageSql = new StringBuilder(FIND_ALL + " where 1=1 and delete_state = 1");
		StringBuilder countAll = new StringBuilder(COUNT_ALL + " where 1=1 and delete_state = 1");
		if (searchExpressionList != null && searchExpressionList.size() != 0) {
			for (int i = 0; i < searchExpressionList.size(); i++) {
				String value = searchExpressionList.get(i).getValue().toString();
				if ("like".equalsIgnoreCase(searchExpressionList.get(i).getOperation().getOperator()))
					value = "'%" + value + "%'";
				String criteriaSql = " " + pageCriteria.getPredicate() + " "
						+ searchExpressionList.get(i).getPropertyName() + " "
						+ searchExpressionList.get(i).getOperation().getOperator() + " " + value;
				pageSql.append(criteriaSql);
				countAll.append(criteriaSql);
			}
		}
		List<OrderExpression> orderExpressionList = pageCriteria.getOrderExpressionList();
		if (orderExpressionList != null && orderExpressionList.size() != 0) {
			pageSql.append(" order by");
			for (int i = 0; i < orderExpressionList.size(); i++) {
				if (i > 0)
					pageSql.append(",");
				pageSql.append(
						" " + orderExpressionList.get(i).getPropertyName() + " " + orderExpressionList.get(i).getOp());
			}
		}
		pageSql.append(" limit " + (pageCriteria.getCurrentPageNo() - 1) * pageCriteria.getPageSize() + ","
				+ pageCriteria.getPageSize());
		List<SspCreative> list = jdbcTemplate.query(pageSql.toString(), new SspCreativeRowMapper());
		int totalCount = jdbcTemplate.queryForObject(countAll.toString(), int.class);
		return new Pagination<SspCreative>(totalCount, pageCriteria.getPageSize(), pageCriteria.getCurrentPageNo(),
				list.toArray(new SspCreative[] {}));
	}

	/*
	 * @Override public int batchDelete(Long[] ids) { List<Object[]> batchArgs = new ArrayList<>(); for (Long id : ids)
	 * { Object[] objects = new Object[] { id }; batchArgs.add(objects); } int[] intArray =
	 * jdbcTemplate.batchUpdate(DELETE_BY_ID, batchArgs); int j = 0; for (int i : intArray) { j += i; } return j; }
	 */
	/**
	 * 将跟计划id相关的创意删掉，逻辑删除，不是物理删除
	 */
	@Override
	public int deleteByPlanId(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SspCreative[] findByAdId(Long advertiserId) {
		String sql = FIND_ALL + " where delete_state = '1' and creative_state = '1' and advertiser_id = ?";
		List<SspCreative> creatives = jdbcTemplate.query(sql, new SspCreativeRowMapper(), advertiserId);
		return creatives.toArray(new SspCreative[] {});
	}

	@Override
	public Pagination<SspCreative> pageByPlanId(PlanCreativeModel planCreativeModel) {
		Long accountId = planCreativeModel.getAccountId();
		Long advertiserId = planCreativeModel.getAdvertiserId();
		//Long creativeGroupId = planCreativeModel.getCreativeGroupId();
		Integer creativeState = planCreativeModel.getCreativeState();
		Integer creativeType = planCreativeModel.getCreativeType();
		Integer currentPageNo = planCreativeModel.getCurrentPageNo();
		String name = planCreativeModel.getName();
		Integer pageSize = planCreativeModel.getPageSize();
		Long planId = planCreativeModel.getPlanId();
		StringBuffer sb = new StringBuffer();
		StringBuffer countSb = new StringBuffer();
		sb.append(
				//"SELECT * from ssp_plan_creative_relation cr LEFT JOIN ssp_creative cc ON cr.creater_id = cc.id where cr.plan_id =");
		//"SELECT cc* from ssp_creative cc LEFT JOIN ssp_plan_creative_relation cr ON cr.creater_id = cc.id where cc.delete_state = 1 and cr.plan_id =");
				"select cc.id,cr.state,cc.creative_url,cc.creative_name,cc.creative_type,cc.creative_format,cc.pic_size,cr.update_time"
				+ " from ssp_creative cc "
				+ " LEFT JOIN ssp_plan_creative_relation cr"
				+ " ON cr.creater_id = cc.id"
				+ " where cc.delete_state = 1 "
				+" and cc.creative_state = 1 "
						+ " and cr.delete_state = 1"
				+ " and cr.plan_id =");
				sb.append(planId);
		countSb.append(
				//"SELECT count(*) from ssp_plan_creative_relation cr LEFT JOIN ssp_creative cc ON cr.creater_id = cc.id where cr.plan_id =");
				"SELECT count(*) from ssp_creative cc LEFT JOIN ssp_plan_creative_relation cr  ON cr.creater_id = cc.id where  cc.delete_state = 1 and cc.creative_state = 1 and cr.delete_state = 1 and cr.plan_id =");
		
				countSb.append(planId);
		if (accountId != null) {
			sb.append(" and cc.account_id=" + accountId);
			countSb.append(" and cc.account_id=" + accountId);
		}
		if (advertiserId != null) {
			sb.append(" and cc.advertiser_id=" + advertiserId);
			countSb.append(" and cc.advertiser_id=" + advertiserId);
		}
		if (creativeState != null) {
			sb.append(" and cc.creative_state=" + creativeState);
			countSb.append(" and cc.creative_state=" + creativeState);
		}
		if (creativeType != null) {
			sb.append(" and cc.creative_type=" + creativeType);
			countSb.append(" and cc.creative_type=" + creativeType);
		}
		if (!StringUtils.isBlank(name)) {
			sb.append(" and cc.creative_name like " + name);
			countSb.append(" and cc.creative_name like " + name);
		}


		String countSql = countSb.toString();
        sb.append(" order by cc.update_time desc ");
		sb.append(" limit " + (currentPageNo - 1) * pageSize + "," + pageSize);

		String sql = sb.toString();

		List<Map<String, Object>>  result = jdbcTemplate.queryForList(sql);
		//{id=3, state=2, creative_url=http://www.baidu.com, creative_name=哈哈, creative_type=1, creative_format=null, pic_size=null, update_time=2017-02-22 10:34:24.0}, 
		
		List<SspCreative> creatives = new ArrayList<SspCreative>();
		if(result!=null&&result.size()>=0){
			for(Map<String, Object> map:result){
				SspCreative creative = new SspCreative();
				if(map.get("id")!=null){
					Long id = (Long) map.get("id");
					creative.setId(id);
				}
				if(map.get("state")!=null){
					Integer state =(Integer)map.get("state"); 
					creative.setCreativeState(state);
				}
				if(map.get("creative_url")!=null){
					String url = (String) map.get("creative_url");
					creative.setCreativeUrl(url);
				}
				if(map.get("creative_name")!=null){
					String creativeName = (String) map.get("creative_name");
					creative.setCreativeName(creativeName);
				}
				if(map.get("creative_type")!=null){
					Integer type =  (Integer) map.get("creative_type");
					creative.setCreativeType(type);
				}
				if(map.get("creative_format")!=null){
					String creativeformat = (String) map.get("creative_format");
					creative.setCreativeFormat(creativeformat);
				}
				if(map.get("pic_size")!=null){
					Double picSize = (Double) map.get("pic_size");
					creative.setPicSize(picSize);
				}
				if(map.get("update_time")!=null){
					Date updateTime = (Date) map.get("update_time");
					creative.setUpdateTime(updateTime);
				}
				creatives.add(creative);
				
			}
		}
		int totalCount = jdbcTemplate.queryForObject(countSql, int.class);
		return new Pagination<SspCreative>(totalCount, pageSize, currentPageNo,creatives.toArray(new SspCreative[] {}));
	}
	
	//根据创意id删除创意
	  public int deleteById(Long id){
		  Date now = new Date();
		  SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		  String  newDate = format.format(now);
		  String sql = "update ssp_creative set delete_state = 2,update_time = "+"'"+newDate+"'"+" where id = "+"'"+id+"'";
		 // String sql = "update ssp_creative set delete_state = 2,update_time = "+newDate+" where id =" +id;
		  System.out.println("执行sql是--"+sql);
		 int result = jdbcTemplate.update(sql);
		  System.out.println("返回值是"+result);
		  return 1 ;
	  };
	  
	  
	  @Override
	    public int updateAndReturnNum(SspCreative entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
	        List<Object> args = new ArrayList<>();
	        Class<?> clazz = entity.getClass();
	        MyTable myTable = clazz.getAnnotation(MyTable.class);
	        StringBuilder sb = new StringBuilder(SqlBuild.updateNotNullField(myTable.value()));
	        MyColumn myColumn = null;
	        MyId myId = null;
	        String strId = null;
	        Object objId = null;
	        Field[] fields = clazz.getDeclaredFields();

	        for (Field field : fields) {
	            if (!Modifier.isStatic(field.getModifiers())) {
	                field.setAccessible(true);
	                if (field.get(entity) != null) {
	                    myColumn = field.getDeclaredAnnotation(MyColumn.class);
	                    if (myColumn != null && StringUtils.isNotBlank(myColumn.value())) {
	                        if (field.getDeclaredAnnotation(MyId.class) != null) {
	                            myId = field.getDeclaredAnnotation(MyId.class);
	                            strId = myId.value();
	                            objId = field.get(entity);
	                            continue;
	                        }
	                        if (myColumn.value().equals("account_id")) {
	                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
	                                sb.append("set " + myColumn.value() + " = ?");
	                            else
	                                sb.append("," + myColumn.value() + " = ?");
	                            args.add(entity.getUser().getId());

	                        } else if (myColumn.value().equals("advertiser_id")) {
	                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
	                                sb.append("set " + myColumn.value() + " = ?");
	                            else
	                                sb.append("," + myColumn.value() + " = ?");
	                            args.add(entity.getSspAdvertiser().getId());
	                        } else if (myColumn.value().equals("creative_label")) {
	                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
	                                sb.append("set " + myColumn.value() + " = ?");
	                            else
	                                sb.append("," + myColumn.value() + " = ?");
	                            String labels = null;
	                        	for(int i=0; i<entity.getSspDictLabelArr().length; i++) {
	                        		if(i == 0) labels = entity.getSspDictLabelArr()[i].getId()+"";
	                        		else labels += "," + entity.getSspDictLabelArr()[i].getId();
	                        	}
	                            args.add(labels);
	                        } else {
	                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
	                                sb.append("set " + myColumn.value() + " = ?");
	                            else
	                                sb.append("," + myColumn.value() + " = ?");
	                            args.add(field.get(entity));
	                        }
	                    }
	                }
	            }
	        }
	        sb.append(" where " + strId + " = ?");
	        args.add(objId);
	       // System.out.println("sql语句是：==="+sb);
	        return jdbcTemplate.update(sb.toString(), args.toArray());
	    }
	  
	  @Override
	    public SspCreative findById (Long id) throws DaoException{
	        if (id != null) {
	            return jdbcTemplate.queryForObject(FIND_ALL + " where id = ?", new SspCreativeRowMapper(), id);
	        }
	        return null;
	    }
	  
	//根據條件查詢所有的創意
			public Pagination<SspPlanCreativeRelation> searchPlanCreativeRelationPage(
					Map<String, String> params) {
				if(params==null){
					return null;
				}else{
					Long planId = null;
					Integer pageSize = null;
					Integer  currentPageNo = null;
					String name = null;
					Long advertiserId = null;
					Integer state = null;
					Integer creativeType = null;
					Integer creativeLabel = null;
					
					if(params.get("planId")!=null&&params.get("planId")!=""){
						
						planId =  Long.parseLong(params.get("planId"));
					}
					if(params.get("currentPageNo")!=null&&params.get("currentPageNo")!=""){
						
						 currentPageNo = Integer.valueOf(params.get("currentPageNo"));
					}
					if(params.get("pageSize")!=null&&params.get("pageSize")!=""){
						pageSize = Integer.valueOf(params.get("pageSize"));
					}
					if(params.get("name")!=null&&params.get("name")!=""){
						name = (String) params.get("name");
					}
					if(params.get("advertiserId")!=null&&params.get("advertiserId")!=""){
						
						 advertiserId = Long.valueOf(params.get("advertiserId"));
					}
					if(params.get("state")!=null&&params.get("state")!=""){
						
						 state = Integer.valueOf(params.get("state"));
					}
					if(params.get("creativeType")!=null&&params.get("creativeType")!=""){
						
						 creativeType = Integer.valueOf(params.get("creativeType"));
					}
					if(params.get("creativeLabel")!=null&&params.get("creativeLabel")!=""){
						
						 creativeLabel = Integer.valueOf(params.get("creativeLabel"));
					}
					
					String sql = "select * from ssp_plan_creative_relation pcr left join ssp_creative cr on cr.id=pcr.creater_id where pcr.delete_state=1  and cr.creative_state = 1 ";
					String countsql = "select count(*) from ssp_plan_creative_relation pcr left join ssp_creative cr on cr.id=pcr.creater_id where pcr.delete_state=1 and cr.creative_state = 1 ";
					if(planId!=null){
						sql+=" and pcr.plan_id="+planId;
						countsql +=" and pcr.plan_id="+planId;
					}
					if(name!=null){
						sql+=" and cr.creative_name like '%"+name+"%'";
						countsql +=" and cr.creative_name like '%"+name+"%'"; 
					}if(advertiserId!=null){
						sql+=" and cr.advertiser_id = "+advertiserId;
						countsql +=" and cr.advertiser_id = "+advertiserId;
					}if(state!=null){
						sql+=" and pcr.state = "+state;
						countsql +=" and pcr.state = "+state;
					}if(creativeType!=null){
						sql+=" and cr.creative_type = "+creativeType;
						countsql +=" and cr.creative_type = "+creativeType;
					}if(creativeLabel!=null){
						sql+= String.format(" and find_in_set('%s',cr.creative_label)",creativeLabel);
						countsql += String.format(" and find_in_set('%s',cr.creative_label)",creativeLabel);
					}
					sql+=" order by pcr.update_time desc ";
					if(currentPageNo!=null){
						sql+= " limit "+(currentPageNo-1);
					}else{
						sql+= " limit "+(1-1);
					}
					if(pageSize!=null){
						sql+= " ,"+pageSize;
					}else{
						sql+= " ,"+10;
					}
					System.out.println("最后的sql语句是--"+sql);
					List<SspPlanCreativeRelation> creativeRelations = jdbcTemplate.query(sql,new SspPlanCreativeRelationDaoImpl.SspPlanCreativeRelationRowMapper());
					int totalCount = jdbcTemplate.queryForObject(countsql, int.class);
					return new Pagination<SspPlanCreativeRelation>(totalCount, pageSize, currentPageNo,
							creativeRelations.toArray(new SspPlanCreativeRelation[] {}));
				}
			}

	@Override
	public SspCreative[] findCreativeByAdvertiser(Long advertiserId,Integer creativeType, String creativeLabel, String creativeName) {
	  	StringBuffer sbf = new StringBuffer();
	  	sbf.append("select * from ssp_creative where 1=1 ");
		if(CommonUtil.isNotNull(advertiserId)){
			sbf.append(" and advertiser_id=");
			sbf.append(advertiserId);
		}
		if(CommonUtil.isNotNull(creativeType)){
			sbf.append(" and creative_type=");
			sbf.append(creativeType);
		}
		if(CommonUtil.isNotNull(creativeLabel)){
			sbf.append(String.format(" and find_in_set('%s',creative_label)",creativeLabel));
		}
		if(CommonUtil.isNotNull(creativeName)){
			sbf.append(" and creative_name like '%");
			sbf.append(creativeName);
			sbf.append("%'");
//			sbf.append(String.format(" and creative_name like '%%s%'",creativeName));
		}
		sbf.append(" and creative_state = 1");
		sbf.append(" and delete_state = 1");
		sbf.append(" order by update_time desc");


		List<SspCreative> listCreative = jdbcTemplate.query(sbf.toString(),new SspCreativeDaoImpl.SspCreativeRowMapper());
		if(CommonUtil.isNotNull(listCreative)&&!listCreative.isEmpty())
			return listCreative.toArray(new SspCreative[listCreative.size()]);
		return null;
	}
}
