package com.unioncast.db.nosql.redis;

import com.unioncast.common.ssp.model.*;
import com.unioncast.common.user.model.User;
import com.unioncast.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.Map.Entry;

@Repository
public class SspRedisMemoryImpl implements SspRedisMemory {
	public static final String SSPADVERTISER = "sspadvertiser:";
	public static final String SSPORDER = "ssporder:";
	public static final String SSPPLAN = "sspplan:";
	public static final String SSPCREATIVE = "sspcreative:";
	public static final String SSPPLANCREATIVERELATION = "sspplancreativerelation:";
	public static final String SSPDAYCONSUME = "sspdayconsume:";

	public static final String SSPADVERTISERORDER = "sspadvertiserorder:";
	public static final String SSPORDERPLAN = "ssporderplan:";
	public static final String SSPCREATIVEECPM = "sspcreativeecpm:";
	public static final String SSPORDERCONSUME = "ssporderconsume:";
	public static final String SSPPLANCONSUME = "sspplanconsume:";

	public static final String SSPAPPINFO = "sspappinfo:";
	public static final String SSPADPOSITIONINFO = "sspadpositioninfo:";

	public static final String USERINFO = "userinfo:";

	private static final Logger LOG = LogManager
			.getLogger(SspRedisMemoryImpl.class);

	@Autowired
	JedisCluster jedisCluster;

	@Override
	public Long batchAddSspAdvertiser(SspAdvertiser[] list) throws Exception {
		LOG.info("add - SspAdvertiser to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPADVERTISER + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - SspAdvertiser to redis num{}" + sum);
		return sum;
	}

	@Override
	public SspAdvertiser[] findAllBySspAdvertiserIds(Long[] ids)
			throws Exception {
		List<SspAdvertiser> list = new ArrayList();
		String key = null;
		String value = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPADVERTISER + ids[i];
			value = jedisCluster.get(key);

			SspAdvertiser sspAdvertiser = JsonUtil.json2Object(value,
					SspAdvertiser.class);

			list.add(sspAdvertiser);
		}

		return list.toArray(new SspAdvertiser[] {});

	}

	@Override
	public Long batchDeleteBySspAdvertiserIds(Long[] ids) throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPADVERTISER + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;

	}

	@Override
	public Long batchAddSspOrder(SspOrder[] list) throws Exception {
		LOG.info("add - SspOrder to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPORDER + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - SspOrder to redis Num" + sum);
		return sum;
	}

	@Override
	public SspOrder[] findAllBySspOrderIds(Long[] ids) throws Exception {
		List<SspOrder> list = new ArrayList();
		String key = null;
		String value = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPORDER + ids[i];
			value = jedisCluster.get(key);

			SspOrder sspAdvertiser = JsonUtil
					.json2Object(value, SspOrder.class);

			list.add(sspAdvertiser);
		}
		return list.toArray(new SspOrder[] {});

	}

	@Override
	public Long batchDeleteBySspOrderIds(Long[] ids) throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPORDER + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;

	}

	@Override
	public Long batchAddSspPlan(SspPlan[] list) throws Exception {
		LOG.info("add - SspPlan to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPPLAN + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - SspPlan to redis Num" + sum);
		return sum;
	}

	@Override
	public SspPlan[] findAllBySspPlanIds(Long[] ids) throws Exception {
		List<SspPlan> list = new ArrayList();
		String key = null;
		String value = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPPLAN + ids[i];
			value = jedisCluster.get(key);

			SspPlan sspAdvertiser = JsonUtil.json2Object(value, SspPlan.class);

			list.add(sspAdvertiser);
		}
		return list.toArray(new SspPlan[] {});

	}

	@Override
	public Long batchDeleteBySspPlanIds(Long[] ids) throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPPLAN + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;

	}

	@Override
	public Long batchAddSspCreative(SspCreative[] list) throws Exception {
		LOG.info("add - SspCreative to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPCREATIVE + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - SspCreative to redis Num" + sum);
		return sum;
	}

	@Override
	public SspCreative[] findAllBySspCreativeIds(Long[] ids) throws Exception {
		List<SspCreative> list = new ArrayList();
		String key = null;
		String value = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPCREATIVE + ids[i];
			value = jedisCluster.get(key);

			SspCreative sspAdvertiser = JsonUtil.json2Object(value,
					SspCreative.class);

			list.add(sspAdvertiser);
		}

		return list.toArray(new SspCreative[] {});

	}

	@Override
	public Long batchDeleteBySspCreativeIds(Long[] ids) throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPCREATIVE + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;

	}

	@Override
	public SspAdvertiser findSspAdvertiserById(Long id) throws Exception {
		String key = SSPADVERTISER + id;
		String value = jedisCluster.get(key);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		SspAdvertiser sspAdvertiser = JsonUtil.json2Object(value,
				SspAdvertiser.class);
		return sspAdvertiser;
	}

	@Override
	public SspOrder findSspOrderById(Long id) throws Exception {
		String key = SSPORDER + id;
		String value = jedisCluster.get(key);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		SspOrder sspOrder = JsonUtil.json2Object(value, SspOrder.class);
		return sspOrder;
	}

	@Override
	public SspPlan findSspPlanById(Long id) throws Exception {
		String key = SSPPLAN + id;
		String value = jedisCluster.get(key);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		SspPlan sspPlan = JsonUtil.json2Object(value, SspPlan.class);
		return sspPlan;
	}

	@Override
	public SspCreative findSspCreativeById(Long id) throws Exception {
		String key = SSPCREATIVE + id;
		String value = jedisCluster.get(key);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		SspCreative sspCreative = JsonUtil
				.json2Object(value, SspCreative.class);
		return sspCreative;
	}

	@Override
	public SspAdvertiser[] findSspAdvertiser() throws Exception {
		List<SspAdvertiser> list = new ArrayList();
		String value = null;
		TreeSet<String> keys = keys("sspadvertiser:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			SspAdvertiser sspAdvertiser = JsonUtil.json2Object(value,
					SspAdvertiser.class);

			list.add(sspAdvertiser);
		}
		return list.toArray(new SspAdvertiser[] {});

	}

	@Override
	public SspOrder[] findSspOrder() throws Exception {
		List<SspOrder> list = new ArrayList();
		String value = null;
		TreeSet<String> keys = keys("ssporder:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			SspOrder sspOrder = JsonUtil.json2Object(value, SspOrder.class);

			list.add(sspOrder);
		}
		return list.toArray(new SspOrder[] {});

	}

	@Override
	public SspPlan[] findSspPlan() throws Exception {
		List<SspPlan> list = new ArrayList();
		String value = null;
		TreeSet<String> keys = keys("sspplan:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			SspPlan sspPlan = JsonUtil.json2Object(value, SspPlan.class);

			list.add(sspPlan);
		}
		return list.toArray(new SspPlan[] {});

	}

	@Override
	public SspCreative[] findSspCreative() throws Exception {
		List<SspCreative> list = new ArrayList();
		String value = null;
		TreeSet<String> keys = keys("sspcreative:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			SspCreative sspCreative = JsonUtil.json2Object(value,
					SspCreative.class);

			list.add(sspCreative);
		}
		return list.toArray(new SspCreative[] {});

	}

	@Override
	public Long batchAddSspPlanCreativeRelation(SspPlanCreativeRelation[] list)
			throws Exception {
		LOG.info("add - SspPlanCreativeRelation to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPPLANCREATIVERELATION + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - SspPlanCreativeRelation to redis Num" + sum);
		return sum;
	}

	@Override
	public SspPlanCreativeRelation[] findAllBySspPlanCreativeRelationIds(
			Long[] ids) throws Exception {
		List<SspPlanCreativeRelation> list = new ArrayList();
		String key = null;
		String value = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPPLANCREATIVERELATION + ids[i];
			value = jedisCluster.get(key);

			SspPlanCreativeRelation sspAdvertiser = JsonUtil.json2Object(value,
					SspPlanCreativeRelation.class);

			list.add(sspAdvertiser);
		}
		return list.toArray(new SspPlanCreativeRelation[] {});

	}

	@Override
	public Long batchDeleteBySspPlanCreativeRelationIds(Long[] ids)
			throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPPLANCREATIVERELATION + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;

	}

	@Override
	public SspPlanCreativeRelation findSspPlanCreativeRelationById(Long id)
			throws Exception {
		String key = SSPPLANCREATIVERELATION + id;
		String value = jedisCluster.get(key);
		SspPlanCreativeRelation sspOrder = JsonUtil.json2Object(value,
				SspPlanCreativeRelation.class);
		return sspOrder;
	}

	@Override
	public SspPlanCreativeRelation[] findSspPlanCreativeRelation()
			throws Exception {
		List<SspPlanCreativeRelation> list = new ArrayList<>();
		String value = null;
		TreeSet<String> keys = keys("sspplancreativerelation:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			SspPlanCreativeRelation sspOrder = JsonUtil.json2Object(value,
					SspPlanCreativeRelation.class);

			list.add(sspOrder);
		}

		return list.toArray(new SspPlanCreativeRelation[] {});
	}

	public TreeSet<String> keys(String pattern) {
		// LOG.info("Start getting keys...");
		TreeSet<String> keys = new TreeSet<>();
		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
		for (String k : clusterNodes.keySet()) {
			// LOG.info("Getting keys from: {}", k);
			JedisPool jp = clusterNodes.get(k);
			Jedis connection = jp.getResource();
			try {
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
				LOG.error("Getting keys error: {}", e);
				return keys;
				//System.out.println(e.getMessage());
			} finally {
				// LOG.info("Connection closed.");
				connection.close();// 用完一定要close这个链接！！！
			}
		}
		// LOG.info("Keys gotten!");
		return keys;
	}

	@Override
	public Map<Long, SspOrder> findAllOrder() throws Exception {
		Map<Long, SspOrder> orderMap = new HashMap<Long, SspOrder>();
		String value = null;
		TreeSet<String> keys = keys("ssporder:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			LOG.info("orderkey:" + key + " value:" + value);
			SspOrder sspOrder = null;
			try {
				sspOrder = JsonUtil.json2Object(value, SspOrder.class);
			} catch (Exception e) {
				e.printStackTrace();
				sspOrder = null;
			}
			if (sspOrder == null || sspOrder.getId() == null) {
				continue;
			}

			orderMap.put(sspOrder.getId(), sspOrder);
		}
		return orderMap;
	}

	@Override
	public Map<Long, SspAdvertiser> findAllAdvertiser() throws Exception {
		Map<Long, SspAdvertiser> advertiserMap = new HashMap<Long, SspAdvertiser>();
		String value = null;
		TreeSet<String> keys = keys("sspadvertiser:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			LOG.info("advertiserkey:" + key + " value:" + value);

			SspAdvertiser sspOrder = null;
			try {
				sspOrder = JsonUtil.json2Object(value,
						SspAdvertiser.class);
			} catch (Exception e) {
				e.printStackTrace();
				sspOrder = null;
			}

			if (sspOrder == null || sspOrder.getId() == null) {
				continue;
			}

			advertiserMap.put(sspOrder.getId(), sspOrder);
		}
		return advertiserMap;
	}

	@Override
	public Map<Long, SspPlan> findAllPlan() throws Exception {
		Map<Long, SspPlan> planMap = new HashMap<Long, SspPlan>();
		String value = null;
		TreeSet<String> keys = keys("sspplan:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			LOG.info("plankey:" + key + " value:" + value);

			SspPlan sspPlan = null;
			try {
				sspPlan = JsonUtil.json2Object(value, SspPlan.class);
			} catch (Exception e) {
				e.printStackTrace();
				sspPlan = null;
			}

			if (sspPlan == null || sspPlan.getId() == null) {
				continue;
			}

			planMap.put(sspPlan.getId(), sspPlan);
		}
		return planMap;
	}

	@Override
	public Map<Long, SspCreative> findAllCreative() throws Exception {
		Map<Long, SspCreative> creativeMap = new HashMap<Long, SspCreative>();
		String value = null;
		TreeSet<String> keys = keys("sspcreative:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			LOG.info("creativekey:" + key + " value:" + value);

			SspCreative sspCreative = null;
			try {
				sspCreative = JsonUtil.json2Object(value,
						SspCreative.class);
			} catch(Exception e) {
				e.printStackTrace();
				sspCreative = null;
			}

			if (sspCreative == null || sspCreative.getId() == null) {
				continue;
			}

			creativeMap.put(sspCreative.getId(), sspCreative);
		}
		return creativeMap;
	}

	@Override
	public Map<Long, List<Long>> findAllPlanCreativeIds() throws Exception {
		Map<Long, List<Long>> planCreativeIdMap = new HashMap<Long, List<Long>>();
		String value = null;
		TreeSet<String> keys = keys("sspplancreativerelation:*");
		for (String key : keys) {
			value = jedisCluster.get(key);
			if (StringUtils.isBlank(value)) {
				continue;
			}
			String[] creativeIdArr = value.split(",");
			if (creativeIdArr == null || creativeIdArr.length == 0) {
				continue;
			}
			// 将key中的计划id截取
			String pId = key.substring(SSPPLANCREATIVERELATION.length());
			Long planId = Long.parseLong(pId);
			List<Long> creativeIds = new ArrayList<Long>();
			for (String creativeIdStr : creativeIdArr) {
				creativeIds.add(Long.parseLong(creativeIdStr));
			}

			planCreativeIdMap.put(planId, creativeIds);
		}
		return planCreativeIdMap;
	}

	// 根据计划id存入计划创意关系数据
	public String batchAddSspPlanCreativeRelationByPlanId(Map<Long, String> map)
			throws Exception {
		Set<Entry<Long, String>> keyset = map.entrySet();
		if (keyset != null && keyset.size() > 0) {
			for (Entry<Long, String> entry : keyset) {
				String key = SSPPLANCREATIVERELATION + entry.getKey();
				String value = entry.getValue();
				String set = jedisCluster.set(key, value);
				System.out.println("返回值是--" + set);
				return set;
			}

		}
		return null;
	}

	@Override
	public Long batchAddCreativeDayConsume(SspCreativeReport[] list)
			throws Exception {
		LOG.info("add - DayConsumeLog to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPDAYCONSUME + list[i].getSspCreative().getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - DayConsumeLog to redis num{}" + sum);
		return sum;
	}

	@Override
	public Long batchDeleteCreativeDayConsumeByCreativeIds(Long[] ids) {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPDAYCONSUME + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;
	}

	@Override
	public Map<Long, SspCreativeReport> findCreativeDayConsumeMap()
			throws Exception {
		Map<Long, SspCreativeReport> dayConsumeLogMap = new HashMap<Long, SspCreativeReport>();
		String value = null;
		TreeSet<String> keys = keys("sspdayconsume:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			SspCreativeReport dayConsumeLog = JsonUtil.json2Object(value,
					SspCreativeReport.class);

			if (dayConsumeLog == null
					|| dayConsumeLog.getSspCreative().getId() == null) {
				continue;
			}

			dayConsumeLogMap.put(dayConsumeLog.getSspCreative().getId(),
					dayConsumeLog);
		}
		return dayConsumeLogMap;
	}

	@Override
	public void deleteAllCreativeDayConsume() throws Exception {
		TreeSet<String> keys = keys("sspdayconsume:*");
		if(keys != null && keys.size() > 0) {
			for(String key : keys) {
				jedisCluster.del(key);
			}
		}
	}

	@Override
	public List<Long> findByAdvIdOrders(Long id) throws Exception {
		String key = SSPADVERTISERORDER + id;
		String value = jedisCluster.get(key);
		if(StringUtils.isBlank(value)) {
			return null;
		}

		@SuppressWarnings("unchecked")
		List<Long> orderIds = JsonUtil.json2Object(value, List.class);

		return orderIds;
	}

	@Override
	public void addByAdvIdOrders(Long id, List<Long> orderIds) throws Exception {
		LOG.info("add - advertiserId-orderIds to redis {}" + id);
		String key = SSPADVERTISERORDER + id;
		String value = JsonUtil.object2JsonString(orderIds);
		jedisCluster.set(key, value);
		LOG.info("add - SspOrder to redis Num" + 1);

	}

	@Override
	public Map<Long, List<Long>> findAllAdvertiserOrderIds() throws Exception {
		Map<Long, List<Long>> advertiserOrderIdMap = new HashMap<Long, List<Long>>();
		String value = null;
		TreeSet<String> keys = keys("sspadvertiserorder:*");
		for (String key : keys) {
			value = jedisCluster.get(key);
			if (StringUtils.isBlank(value)) {
				continue;
			}
			List<Long> orderIds = JsonUtil.json2Object(value, List.class);
			String pId = key.substring(SSPADVERTISERORDER.length());
			Long advertiserId = Long.parseLong(pId);
			advertiserOrderIdMap.put(advertiserId, orderIds);
		}
		return advertiserOrderIdMap;
	}

	//向redis中存入创意的ecpm
	public String updateEcpmRedis(Map<Long, Double> map) {
		String set = null;
		Set<Entry<Long, Double>> keyset = map.entrySet();
		if (keyset != null && keyset.size() > 0) {
			for (Entry<Long, Double> entry : keyset) {
				String key = SSPCREATIVEECPM + entry.getKey();
				Double value = entry.getValue();
				set = jedisCluster.set(key, Double.toString(value));
			}
			System.out.println("返回值是--" + set);
			return set;
		}
		return null;
	}

	@Override
	public Map<Long, Double> findCreativeEcpmMap() throws Exception {
		Map<Long, Double> creativeEcpmMap = new HashMap<Long, Double>();
		String value = null;
		TreeSet<String> keys = keys("sspcreativeecpm:*");
		for (String key : keys) {
			value = jedisCluster.get(key);
			if (StringUtils.isBlank(value)) {
				continue;
			}
			Double ecpm = JsonUtil.json2Object(value, Double.class);
			String creativeId = key.substring(SSPCREATIVEECPM.length());
			Long id = Long.parseLong(creativeId);
			creativeEcpmMap.put(id, ecpm);
		}
		return creativeEcpmMap;
	}

	@Override
	public Long updateRedisConsume(RedisConsume redisConsume) throws Exception {
		Map<Long, SspDayOrHourReport> orderConsumeMap = redisConsume.getOrderConsumeMap();
		Map<Long, SspDayOrHourReport> planConsumeMap = redisConsume.getPlanConsumeMap();

		TreeSet<String> orderkeys = keys("ssporderconsume:*");
		if(orderkeys != null && orderkeys.size() > 0) {
			for(String key : orderkeys) {
				jedisCluster.del(key);
			}
		}

		TreeSet<String> plankeys = keys("sspplanconsume:*");
		if(plankeys != null && plankeys.size() > 0) {
			for(String key : plankeys) {
				jedisCluster.del(key);
			}
		}

		long sum = 0;
		String key = null;
		String value = null;
		for (Entry<Long , SspDayOrHourReport> entry : orderConsumeMap.entrySet()) {
			key = SSPORDERCONSUME + entry.getKey();
			value = JsonUtil.object2JsonString(entry.getValue());
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - orderConsumeMap to redis num{}" + sum);



		long sum1 = 0;
		String key1 = null;
		String value1 = null;
		for (Entry<Long , SspDayOrHourReport> entry : planConsumeMap.entrySet()) {
			key1 = SSPPLANCONSUME + entry.getKey();
			value1 = JsonUtil.object2JsonString(entry.getValue());
			jedisCluster.set(key1, value1);
			sum1 = sum1 + 1;
		}
		LOG.info("add - planConsumeMap to redis num{}" + sum);
		return sum + sum1;
	}

	@Override
	public Map<Long, SspDayOrHourReport> findOrderConsumeMap()  throws Exception {
		Map<Long, SspDayOrHourReport> reportMap = new HashMap<Long, SspDayOrHourReport>();
		String value = null;
		TreeSet<String> keys = keys("ssporderconsume:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			LOG.info("orderconsumekey:" + key + " value:" + value);

			SspDayOrHourReport dayReport = null;
			try {
				dayReport = JsonUtil.json2Object(value,
						SspDayOrHourReport.class);
			} catch (Exception e) {
				e.printStackTrace();
				dayReport = null;
			}

			if (dayReport == null || dayReport.getId() == null) {
				continue;
			}

			reportMap.put(dayReport.getOrderId(), dayReport);
		}
		return reportMap;
	}

	@Override
	public Map<Long, SspDayOrHourReport> findPlanConsumeMap()  throws Exception{
		Map<Long, SspDayOrHourReport> reportMap = new HashMap<Long, SspDayOrHourReport>();
		String value = null;
		TreeSet<String> keys = keys("sspplanconsume:*");
		for (String key : keys) {
			value = jedisCluster.get(key);

			LOG.info("planconsumekey:" + key + " value:" + value);

			SspDayOrHourReport dayReport = null;
			try {
				dayReport = JsonUtil.json2Object(value,
						SspDayOrHourReport.class);
			} catch (Exception e) {
				e.printStackTrace();
				dayReport = null;
			}

			if (dayReport == null || dayReport.getId() == null) {
				continue;
			}

			reportMap.put(dayReport.getPlanId(), dayReport);
		}
		return reportMap;
	}

	public String findPlanCreativeRelationByPlanId(Long planId) {
		String  pid = null;
		if(planId!=null){
			pid = SSPPLANCREATIVERELATION + planId;
			String value = jedisCluster.get(pid);
			return value;
		}
		return null;
	}

	@Override
	public Long batchAddSspAppInfo(SspAppInfo[] list) throws Exception {
		LOG.info("add - SspAppInfo to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPAPPINFO + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - SspAppInfo to redis num{}" + sum);
		return sum;
	}

	@Override
	public Long batchDeleteBySspAppInfoIds(Long[] ids) throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPAPPINFO + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;
	}

	@Override
	public Long batchAddSspAdPositionInfo(SspAdPositionInfo[] list) throws Exception {
		LOG.info("add - SspAppInfo to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = SSPADPOSITIONINFO + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - SspAppInfo to redis num{}" + sum);
		return sum;
	}

	@Override
	public Long batchDeleteBySspAdPositionInfoIds(Long[] ids) throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = SSPADPOSITIONINFO + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;
	}

	@Override
	public Long batchAddUserInfo(User[] list) throws Exception {
		LOG.info("add - UserInfo to redis {}" + list);
		long sum = 0;
		String key = null;
		String value = null;
		for (int i = 0; i < list.length; i++) {
			key = USERINFO + list[i].getId();
			value = JsonUtil.object2JsonString(list[i]);
			jedisCluster.set(key, value);
			sum = sum + 1;
		}
		LOG.info("add - UserInfo to redis num{}" + sum);
		return sum;
	}

	@Override
	public Long batchDeleteByUserIds(Long[] ids) throws Exception {
		long sum = 0;
		String key = null;
		for (int i = 0; i < ids.length; i++) {
			key = USERINFO + ids[i];
			sum = sum + jedisCluster.del(key);
		}

		return sum;
	}
}
