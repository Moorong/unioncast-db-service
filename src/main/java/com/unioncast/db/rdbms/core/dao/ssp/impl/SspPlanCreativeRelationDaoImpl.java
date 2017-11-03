package com.unioncast.db.rdbms.core.dao.ssp.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.unioncast.common.user.model.User;
import com.unioncast.common.util.CommonUtil;
import com.unioncast.db.rdbms.core.service.common.UserService;
import com.unioncast.db.rdbms.core.service.ssp.SspCreativeService;
import com.unioncast.db.rdbms.core.service.ssp.SspPlanService;
import com.unioncast.db.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.unioncast.common.annotation.MyColumn;
import com.unioncast.common.annotation.MyId;
import com.unioncast.common.annotation.MyTable;
import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.common.ssp.model.SspCreative;
import com.unioncast.common.ssp.model.SspPlan;
import com.unioncast.common.ssp.model.SspPlanCreativeRelation;
import com.unioncast.db.api.rest.redis.SspRedisController;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.ssp.SspPlanCreativeRelationDao;
import com.unioncast.db.rdbms.core.exception.DaoException;


@Repository("sspPlanCreativeRelationDao")
public class SspPlanCreativeRelationDaoImpl extends SspGeneralDao<SspPlanCreativeRelation, Long> implements SspPlanCreativeRelationDao {

    private static String FIND_ALL = SqlBuild.select(SspPlanCreativeRelation.TABLE_NAME,
            SspPlanCreativeRelation.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(SspPlanCreativeRelation.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(SspPlanCreativeRelation.TABLE_NAME);
    @Resource
    private SspRedisController sspRedisController;

    @Override
    public SspPlanCreativeRelation[] find(Long id) throws DaoException {
        List<SspPlanCreativeRelation> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new SspPlanCreativeRelationDaoImpl.SspPlanCreativeRelationRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspPlanCreativeRelationDaoImpl.SspPlanCreativeRelationRowMapper());
        }

        return list.toArray(new SspPlanCreativeRelation[]{});
    }

    @Override
    public SspPlanCreativeRelation[] findT(SspPlanCreativeRelation s) throws DaoException, IllegalAccessException {
        List<SspPlanCreativeRelation> list = new ArrayList<>();
        if (s != null) {
            SspPlanCreativeRelation[] sspOrders = find(s, new SspPlanCreativeRelationRowMapper(), SspPlanCreativeRelation.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new SspPlanCreativeRelationRowMapper());
        }
        return list.toArray(new SspPlanCreativeRelation[]{});
    }

    @Override
    public int updateAndReturnNum(SspPlanCreativeRelation entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
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
                        if (myColumn.value().equals("plan_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspPlan().getId());

                        } else if (myColumn.value().equals("creater_id")) {
                            if (" ".equals(sb.subSequence(sb.length() - 1, sb.length())))
                                sb.append("set " + myColumn.value() + " = ?");
                            else
                                sb.append("," + myColumn.value() + " = ?");
                            args.add(entity.getSspCreative().getId());
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
        return jdbcTemplate.update(sb.toString(), args.toArray());
    }

    @Override
    public Pagination<SspPlanCreativeRelation> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(SspPlanCreativeRelation.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new SspPlanCreativeRelationDaoImpl.SspPlanCreativeRelationRowMapper(), "account_id", userId);

    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(SspPlanCreativeRelation.class, ids);
    }


    @Override
    public Long save(SspPlanCreativeRelation entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

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
                        if (myColumn.value().equals("plan_id")) {
                            args.put("plan_id", entity.getSspPlan() == null ? null : entity.getSspPlan().getId());
                        } else if (myColumn.value().equals("creater_id")) {
                            args.put("creater_id", entity.getSspCreative() == null ? null : entity.getSspCreative().getId());
                        } else {
                            args.put(myColumn.value(), field.get(entity) == null ? null : field.get(entity));
                        }
                    }
                }
            }
        }
        return jdbcInsert.executeAndReturnKey(args).longValue();
    }


    public static final class SspPlanCreativeRelationRowMapper implements RowMapper<SspPlanCreativeRelation> {
        @Override
        public SspPlanCreativeRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long lan = rs.getLong("plan_id");
            SspPlan spPlan = null;
            if (lan != null) {
                try {
                    SspPlanService planService = SpringUtils.getBean(SspPlanService.class);
                    spPlan = planService.findById(lan);
                } catch (Exception e) {
                    spPlan = new SspPlan();
                    spPlan.setId(lan);
                }
                if(CommonUtil.isNull(lan)){
                    spPlan = new SspPlan();
                    spPlan.setId(lan);
                }
            }

            Long reative = rs.getLong("creater_id");
            SspCreative spCreative = null;
            if (reative != null) {
                spCreative = new SspCreative();
                spCreative.setId(reative);
            }
            if (reative != null) {
                try {
                    SspCreativeService creativeService = SpringUtils.getBean(SspCreativeService.class);
                    spCreative = creativeService.findById(reative);
                } catch (Exception e) {
                    spCreative = new SspCreative();
                    spCreative.setId(reative);
                }
                if(CommonUtil.isNull(spCreative)){
                    spCreative = new SspCreative();
                    spCreative.setId(reative);
                }
            }


            return new SspPlanCreativeRelation(rs.getLong("id"), spPlan, spCreative, rs.getTimestamp("create_time"), rs.getTimestamp("update_time"),rs.getString("creative_group"),rs.getInt("state"),rs.getInt("delete_state"));
        }
    }

    @Override
    public SspPlanCreativeRelation[] findByAdvertiserId(Long advertiserId) throws DaoException {
        StringBuffer sbf = new StringBuffer();
        sbf.append("select pcr.* from ssp_plan_creative_relation pcr");
        sbf.append(" left join ssp_plan sp on sp.id = pcr.plan_id");
        sbf.append(" left join ssp_advertiser sad on sad.id = sp.advertiser_id");
        sbf.append(" where pcr.delete_state = 1");
        sbf.append(String.format(" and sad.id = %s",advertiserId));

        List<SspPlanCreativeRelation> list = jdbcTemplate.query(sbf.toString(), new SspPlanCreativeRelationRowMapper());
        if(list != null && list.size() > 0) {
            return list.toArray(new SspPlanCreativeRelation[0]);
        }
        return null;
    }
	@Override
	public SspPlanCreativeRelation[] findByPlanId(Long planId) throws DaoException {
		String sql = "select * from ssp_plan_creative_relation where delete_state = 1 and plan_id=" +planId;
		List<SspPlanCreativeRelation> list = jdbcTemplate.query(sql, new SspPlanCreativeRelationRowMapper());
		if(list != null && list.size() > 0) {
			return list.toArray(new SspPlanCreativeRelation[0]);
		}
		return null;
	}

	@Override
	public int addPlanCreativeRelations(SspPlanCreativeRelation[] relations) throws DaoException {
		if(relations == null) {
			return 0;
		}
		Long planId = relations[0].getSspPlan().getId();
		String deletePlanSql = "delete from ssp_plan_creative_relation where plan_id="+ planId;
		jdbcTemplate.update(deletePlanSql);
		
		for(SspPlanCreativeRelation relation : relations) {
			try {
				save(relation);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return 0;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}

	@Override
	public int changePlanCreativeRelationState(SspPlanCreativeRelation params) {
		Long CreativeId = null;
		Integer state = null;
		Long planId = null;
		SimpleDateFormat formater = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		String now = formater.format(new Date());
		String sql = "update ssp_plan_creative_relation set state = ";
		if(params!=null){
			if(params.getState()!=null){
				state = params.getState();
				sql+= state;
			}
			
			sql+=" , update_time = '"+ now+"'";
			if(params.getSspCreative()!=null&&params.getSspCreative().getId()!=null){
				CreativeId =params.getSspCreative().getId(); 
				sql+="  where creater_id = "+CreativeId;
			} 
			if(params.getSspPlan()!=null&&params.getSspPlan().getId()!=null){
				planId = params.getSspPlan().getId();
				sql +=" and plan_id = "+planId;
			}
			int result = jdbcTemplate.update(sql);
			System.out.println("结果是--"+result);
			return result;
		}else{
			return 0;
		}
	}

	//根据创意id将创意计划关系表中的创意删除
	public int deletePlanCreativeRelationById(SspPlanCreativeRelation pcr) {
		
		SimpleDateFormat formart = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss" );
		String now = formart.format(new Date());
		String sql = "update ssp_plan_creative_relation set delete_state = 2 ,update_time = '"+now+"'";
		Long creativeId = null;
		Long planId = null;
		int result = 0;
		if(pcr!=null){
			if(pcr.getSspCreative()!=null){
				SspCreative creative = pcr.getSspCreative();
				if(creative.getId()!=null){
					creativeId = creative.getId();
					sql+=" where creater_id = "+creativeId;
					
				}
			}
			if(pcr.getSspPlan()!=null){
				SspPlan plan = pcr.getSspPlan();
				if(plan.getId()!=null){
					planId = plan.getId();
					sql+=" and plan_id = "+planId;
				}
			}
			
			result = jdbcTemplate.update(sql); 
			System.out.println("删除结果是 --"+result);
			
		}
		
		
		return result;
	}
}
